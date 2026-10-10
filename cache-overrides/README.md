# Killer cache overrides

The development override package is built from editable interface JSON plus any
already-encoded fallback files.

Runtime path format:

```text
cache/<index>/<group>/<file>.dat
```

Editable source lives under:

```text
source/<index>/<group>/<file>.json
```

The `modern-client` workflow runs `tools/cache/encode_interfaces.py` for every
JSON source file and writes the encoded result into the matching runtime
`.dat` path before building `killer-overrides.zip`.

The encoder implements the RT4 IF1/IF3 layouts used by this project's frozen
vanilla baseline. During development it was round-trip tested against all
31,622 decoded interface components from the original cache with zero byte
differences.

Current Graphics Options override:
- `3/744/104` is restored to the original **Texture detail** component.
- `3/744/434` is the new Modern UI container in the fifth advanced-options column.
- `3/744/435` is the cache-defined **Modern UI** label.
- `3/744/436-438` are the native selector left/right/fill sprite pieces.
- `3/744/439` is the cache-defined selector hit area.
- `3/744/440` is the cache-defined Standard UI value text (`No`).

The selector uses the same native sprite IDs and geometry pattern as the
revision-530 Graphics Options dropdowns. Because the new row is appended at
child 434 while the vanilla fifth-column rows are child 103 (Scenery shadows)
and child 111 (Texture detail), the renderer gives the tagged Modern UI parent
the same back-to-front ordering a native third row would have had. This keeps
Scenery shadows/Texture detail popups in front of the lower Modern UI row. Killer client behavior only owns the
new selector's two-state popup and maps Off/On to the existing Modern UI
preference. Standard mode now remains entirely in the original RT4 component renderer: the cache-defined Fog-style containers receive native runtime child Components for the closed value and Off/On popup. Java only toggles visibility/state; it no longer draws Standard-mode popup rectangles or glyphs. Modern mode replaces those tagged pieces with the scalable UI-pack control.
