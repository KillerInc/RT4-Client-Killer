# Modern UI Pack Format

The native distributable style format is `.uipack`. A `.uipack` is a ZIP-compatible archive read in place by the client; it is never extracted into the game directory.

## Built-in pack

`KillerModernUI.uipack` is generated from `client/src/main/resources/ui/killer-modern/` during the client build and is embedded in the client JAR at:

`/ui/packs/KillerModernUI.uipack`

That embedded archive is the authoritative built-in Modern UI asset source. The loose `ui/killer-modern/` folder created beside the running client is inspection-only and is refreshed from the baked archive. Rendering never reads from that mirror.

## Search order

Modern UI assets resolve in this order:

1. `ui/overrides/`
2. enabled add-ons, highest priority first
3. selected custom style
4. the selected style's `base` chain
5. embedded `KillerModernUI.uipack`

There is no JS5/Index-8 visual fallback in the Modern UI path.

## Required manifest

Every external style/add-on archive has a root-level `style.info` Java properties file.

Required fields:

- `id`
- `name`

Supported fields:

- `version`
- `author`
- `description`
- `type=style|addon`
- `renderer=killer-modern`
- `base=<style id>`
- `requires=<comma-separated ids>`
- `provides=<comma-separated capability labels>`
- `priority=<integer>`

The client accepts both `.uipack` and legacy `.zip` archives in `ui/styles/`.

## Asset paths

Assets keep stable logical paths inside the archive. Current examples:

- `icons/dropdown.svg`
- `fonts/runescape_small.ttf`

Images may be SVG or PNG. Modern UI text is loaded from the layered style system as TrueType data. Missing or invalid Modern UI font data is an explicit failure; it does not fall back to the old RT4 bitmap-font renderer or a system font.

## Modder template

The build also produces `KillerModernUI-Modder-Template.uipack`. It inherits from `killer-modern`, includes a working `style.info`, and contains a small SVG override example. Copy it, change its ID/name/author, and add only the logical paths you want to replace.
