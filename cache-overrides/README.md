# Killer cache overrides

Files under `cache/` are already-encoded JS5 file overrides packaged by the
`modern-client` workflow into `killer-overrides.zip`.

Runtime path format:

```text
cache/<index>/<group>/<file>.dat
```

The initial `cache/3/744/104.dat` seed is byte-identical to the original
Graphics Options "Texture detail" component. It exists only to verify the
launcher download + client override path without changing game behavior.

As UI work progresses, only changed encoded files should be kept here.
