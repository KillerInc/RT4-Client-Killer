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

Current Graphics Options test:
- `3/744/104` is restored to the original **Texture detail** component.
- `3/744/434` is a new empty-slot container in the fifth advanced-options column.
- `3/744/435` is a new cache-defined **Modern UI** label inside that container.

The new files intentionally stop at a label for this test. No Java selector is
being reintroduced. Once the new-component path is visually confirmed, the
dropdown/value/script pieces can be added as cache-defined components.
