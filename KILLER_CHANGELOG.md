# Killer RT4 Client Changelog

This file tracks **Killer RT4 client changes only**. Upstream 2009Scape/Pazaz history and changelogs are intentionally left untouched.

## v0.8.2 — 2026-10-04

- Keeps the validated independent text scaler unchanged.
- Returns scaled UI coordinates to CS2 scripts in native 1.0 logical units so script-built interfaces do not double-scale their own layout.
- Makes the minimap and compass true UI-scaled surfaces while leaving the 3D game viewport unscaled.
- Resamples minimap/compass click masks to the scaled viewport so drawing and mouse hit-testing use matching geometry.
- Compensates minimap source zoom for UI scale so the enlarged map does not simply reveal a larger world area.
- Scales minimap/compass fallback artwork to the component bounds.

## v0.8.1 — 2026-10-04

- Kept the validated TTF text scaler unchanged.
- Added scale-aware rendering for indexed UI sprites in both software and OpenGL renderers.
- Scales scrollbar arrow artwork with the rewritten scrollbar geometry instead of leaving 1.0-size arrows inside enlarged bars.
- Scales inventory/decorative slot sprites with their UI cells.
- Added a non-1.0 path for tiled interface sprites so panel textures, frames, parchment pieces, and other repeated artwork grow with the UI instead of repeating at native 1.0 tile size.
- Preserves the exact 1.0 tiled-sprite rendering path to avoid changing the validated baseline.

## v0.8.0 — 2026-10-04

- Split UI geometry scaling from the independent Font Scale control.
- UI geometry now follows `sun.java2d.uiScale` only; changing Font Scale no longer resizes panels, sprites, slots, scrollbars, minimap framing, login parchment, or other UI layout.
- Font Scale now acts as a text-only multiplier through `killerFontScale`.
- Effective text scale is `UI Scale × Font Scale`, so the UI scaler can still grow text together with the rest of the interface.
- Normal mouse-over/action tooltip text is static again; explicit RuneScape wave/wave2/shake effects remain available only on text that actually requests them.
- Runtime UI diagnostics now log UI Scale, Font Scale, and effective text scale separately.

## v0.4.0 — 2026-10-04

- Replaced the experimental RT4 bitmap-font conversion approach with a direct TTF rendering path.
- Restored the stock `SoftwareFont` and `GlFont` implementations unchanged.
- Added dedicated `KillerSoftwareTtfFont` and `KillerGlTtfFont` renderers that consume RuneScape TTF glyphs directly.
- Keeps RT4's existing text parser and effects layer, so color tags, shadows, transparency, wave/shake positioning and other text codes continue to be processed by the normal `Font` logic.
- TTF advances, glyph bounds, offsets, line height and kerning are generated directly from the vector font at `native size × Font Scale`.
- UI Scale is no longer used to size the TTF; the existing UI scaler remains responsible for whole-client scaling.
- Added direct-TTF diagnostics to `logs/killer-font.log`, including source TTF, logical size, generated glyph count, kerning source, renderer type, install status and cache-font fallback.

## v0.3.3 — 2026-10-04

- Font Scale now changes the actual generated RT4 font size instead of only the source rasterization quality.
- Scales generated glyph boxes, X/Y offsets, advances, kerning, and line height by Font Scale.
- UI Scale remains untouched and continues to control the whole client exactly as before.
- Vector rasterization still uses `native size × UI Scale × Font Scale` for quality.
- Per-glyph fallback masks are resized to the scaled glyph box so fallback characters remain compatible.
- Font cache keys now include both UI Scale and Font Scale.
- Font diagnostics now report the scaled-output mode and the effective Font Scale.

## v0.3.2 — 2026-10-04

- Added launcher-controlled `killerFontScale` for per-display font raster tuning.
- Final vector raster size is now `native font size × UI Scale × Font Scale`.
- UI/window scaling code remains untouched.
- Font diagnostics now record `uiScale`, `fontScale`, and final `rasterSize` for every generated font.
- Keeps the original RT4 glyph boxes and font layout while tuning the source rasterization quality.

## v0.3.1 — 2026-10-04

- Supersamples RuneScape vector glyphs at the active UI scale while leaving the existing UI scaler untouched.
- Downsamples the high-resolution vector raster into the original RT4 glyph box.
- Converts the result back to RT4's expected binary glyph-mask format so software/OpenGL font renderers do not treat grayscale edge pixels as fully solid.
- Added log fields for `logicalSize`, `uiScale`, `rasterSize`, `maxSourceRaster`, mask mode, and threshold.
- Font log START entries now explicitly confirm supersampling is enabled and UI scaling is unchanged.

## v0.3.0 — 2026-10-04

- Reintroduced the font generator without touching the existing UI scaling path.
- UI Scale remains the single scaler and continues to control the whole client the old way.
- The generator preserves the original RT4 cache font metrics, kerning, offsets, line height, glyph box sizes and layout.
- Only the glyph pixel masks are regenerated from RuneScape Plain 11, Plain 12 and Bold 12 vector fonts.
- Vector glyphs are generated at the native font sizes (11/12/12) and fitted into the exact cache-provided glyph boxes.
- Added session caching so display-mode/fullscreen reloads reuse generated glyph masks instead of rasterizing again.
- Added dedicated font diagnostics at `logs/killer-font.log`.
- The font log records START, SUCCESS, FALLBACK and FAILED entries for each font.
- Any failed vector font or glyph generation falls back to the original cache glyph pixels.

## v0.2.2 — 2026-10-04

- Fully removed the experimental vector/generated-font rendering path.
- Restored the original 2009Scape RT4 `Font`, `SoftwareFont`, `GlFont`, and `Fonts` behavior.
- Removed the vector font generator and build-time RuneStar font injection.
- Returned UI/text rendering to the same single UI-scale path used before the text experiments.
- Keeps the Killer client release/update plumbing so the launcher will automatically fetch this corrected client.

## v0.2.1 — 2026-10-04

- Removed the separate text-scaling property path.
- Generated RuneScape vector fonts now read the same `sun.java2d.uiScale` value used by the client.
- UI and generated text are again driven by a single scale setting.
- Keeps the startup glyph-generation and session caching added in v0.2.0.

## v0.2.0 — 2026-10-04

- Based on the current 2009Scape RT4 client.
- Removed the experimental post-load bitmap font enlargement approach.
- Restored the original 2009Scape font/window behavior before implementing the new text system.
- Added startup generation of RT4-compatible glyph masks from RuneScape vector fonts.
- Added support for RuneScape **Plain 11**, **Plain 12**, and **Bold 12** vector font sources.
- Vector fonts are fetched from the RuneStar font release during the client build and bundled into the client resources.
- Text generation occurs during RT4's normal startup font-loading phase (around `mainLoadState == 65`, roughly 45% loading).
- Generated glyphs are cached for the rest of the process so display-mode/fullscreen resource reloads do not regenerate them.
- Initial vector-font build used an independent text-scale property; superseded by v0.2.1.
- Preserved original cache fonts as a fallback if vector generation fails.
- Added generated glyph metrics support to both software and OpenGL font renderers.
- Preserved RT4 baseline/line-height behavior while applying generated glyph metrics.

## v0.1.1 — 2026-10-04

- Rebased Killer RT4 on the current 2009Scape RT4 client after the initial raw Pazaz-based build proved incompatible with the live launcher/client environment.
- Reapplied the first experimental bitmap text-scaling patch on the correct 2009Scape base.

## v0.1.0 — 2026-10-04

- Initial Killer RT4 experiment.
- Added first-pass fractional bitmap-font scaling.
- This build was based directly on Pazaz RT4 and produced a black-screen incompatibility in the 2009Scape launcher environment; it was superseded by v0.1.1.

---
Going forward, Killer-specific RT4 changes should be recorded here without replacing upstream history.
