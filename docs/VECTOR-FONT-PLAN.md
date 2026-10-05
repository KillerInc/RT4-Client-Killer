# Vector Font Reintegration Plan

The modern-client baseline deliberately ships with the stock revision-530 bitmap-font path. The previous Killer text override is not carried forward. Vector text will return as a first-class renderer after the modern baseline is proven against the current server.

## Goals

- Preserve the exact game/server/cache behaviour of revision 530.
- Keep stock bitmap rendering available as a compatibility and regression backend.
- Render scalable text from vector font files without converting character images into a font at runtime.
- Measure text before layout so containers know the real width, line count and height.
- Support the RuneScape text language: color/shadow tags, inline icons, transparency, alignment, wrapping and animated wave/wave2/shake effects.
- Make software and OpenGL rendering agree on metrics.
- Avoid coupling typography to a global UI-scale patch.

## Architecture

Introduce a small text subsystem between components and rasterization:

```
Component / world-text source
        |
        v
TextStyle + FontRole + RichText
        |
        v
TextLayoutEngine
        |
        +-- BitmapTextBackend (legacy compatibility)
        |
        +-- VectorTextBackend
                |
                +-- Software glyph raster/cache
                +-- OpenGL glyph atlas
```

### Font roles

Do not key new code directly to one TTF filename. Map legacy cache font IDs and special text uses to semantic roles such as:

- UI_SMALL
- UI_NORMAL
- UI_BOLD
- UI_TITLE
- CHAT
- MENU
- ITEM_QUANTITY
- OVERHEAD
- HITMARK
- WORLD_LABEL
- QUEST/DECORATIVE roles where the cache requires them

A theme can map each role to a vector face and size.

### Text layout

`TextLayoutEngine` owns:

- shaping/kerning
- line breaking
- word wrapping and character fallback for long tokens
- ascent/descent/baseline
- line spacing
- horizontal/vertical alignment
- inline sprite/icon placement
- measured bounds

A layout result is immutable and can be cached by text/style/available-width.

### Compatibility path

Phase 1 keeps stock bitmap fonts as the default backend and records golden screenshots/metrics.
Phase 2 switches selected roles to vector while comparing width, baseline, wrapping and effects.
Phase 3 makes vector the default after the full UI layer consumes measured text bounds.

## OpenGL implementation

Use a glyph atlas rather than one texture per glyph draw. Cache glyphs by face, size, style and rasterization mode. Batch glyph quads where the RT4 renderer permits it. Preserve the existing software renderer with an equivalent raster cache so SD mode remains functional.

## Scaling model

Typography receives logical size from the UI layout. A future user-facing text-size preference becomes a typography multiplier used during layout; it never directly mutates component geometry.

## Validation

1. Login and world-select text.
2. Chat, including long names and wrapped messages.
3. Context menus/tooltips.
4. Inventory quantities.
5. Quest/music/skills panels.
6. Overhead names, hit text and animated effects.
7. Plugin text.
8. Software and OpenGL screenshot comparison.

The old v0.8.x font experiments remain historical reference only; code should be reimplemented around this boundary rather than copied wholesale.
