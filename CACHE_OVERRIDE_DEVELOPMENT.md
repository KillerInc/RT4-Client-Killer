# Killer Edition cache override development

The client can load already-encoded JS5 files from a ZIP before falling back to
the normal RuneScape cache. This is intended for fast UI/cache iteration while
the final cache compiler/repack workflow is being validated.

## Default ZIP

Place one of these beside the client/client-home directory:

- `killer-overrides.zip` (preferred)
- `runescape-interfaces-decoded-compressed.zip`
- `runescape-interfaces-decoded.zip`

An exact file can also be selected with:

- JVM property: `-Dkiller.cache.override=C:\\path\\override.zip`
- environment variable: `KILLER_CACHE_OVERRIDE=C:\\path\\override.zip`

The ZIP is read into memory and then closed, so it is not held open on Windows.
If its modified time or size changes, the client reloads it on the next cache
lookup.

## Supported entry layouts

Generic JS5 file layout:

```text
cache/<index>/<group>/<file>.dat
```

Verbose equivalent:

```text
index-<index>/archive-<group>/file-<file>.dat
```

The decoded interface ZIP produced for this project is also understood directly:

```text
decoded-interfaces/interfaces/<interface>/raw/<component>.dat
```

That layout maps to JS5 index 3.

The client uses only encoded `.dat` bytes at runtime. JSON is the editable
source representation and must eventually be encoded back to `.dat`.

## Unified diagnostics

All Killer Edition diagnostic output goes to:

```text
logs/killer-client.log
```

Important markers include:

- `CACHE_OVERRIDE active`
- `CACHE_OVERRIDE HIT`
- `UI_LOAD`
- `UI_GRAPHICS_OPTIONS_COMPONENT_DUMP`
- `UI_STATE setHidden`
- `UI_STATE setText`
- `UI_CLICK graphicsOptions`
- `PERF`
- `PERF_STALL`
- `PERF_UI_STALL`
- `UNCAUGHT_EXCEPTION`

The previous Java-injected Modern UI Graphics Options selector is disconnected.
Graphics Options now uses the cache-defined components and scripts without the
selector injection/native-dropdown suppression hooks. A new selector can be
added later through the proper cache/interface workflow.
