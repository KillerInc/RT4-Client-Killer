# Killer RT4 Client Changelog

This file tracks **Killer RT4 client changes only**. Upstream 2009Scape/Pazaz history and changelogs are intentionally left untouched.

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
