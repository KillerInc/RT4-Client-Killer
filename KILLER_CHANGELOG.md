# Killer RT4 Client Changelog

This file tracks **Killer RT4 client changes only**. Upstream 2009Scape/Pazaz history and changelogs are intentionally left untouched.

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
