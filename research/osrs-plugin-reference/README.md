# OSRS plugin ecosystem reference snapshot

This directory is **research/reference material only**. It is intentionally outside the live client source sets and is not wired into the Killer Edition build or plugin catalog.

The goal is to preserve representative OSRS/RuneLite-family plugin designs so we can compare them while evolving the revision-530 Killer plugin API.

## Included upstreams

| Folder | Upstream | Pinned commit | License | Why it is here |
|---|---|---|---|---|
| runelite | https://github.com/runelite/runelite | `ddd0669ef4d7494fd420cb507e3c1fa62bab8023` | BSD-2-Clause | Active. Core RuneLite plugin framework and representative built-in plugins. |
| pluginhub | https://github.com/runelite/plugin-hub | `54c68c1377c1ba3a39c015cb10a4e41ffbe00dda` | BSD-2-Clause | Active. External-plugin catalog; individual third-party plugin repositories may have their own licenses. |
| meteor | https://github.com/MeteorLite/meteor-client | `9849eef8c6a423aa5c42917dbb9033ac16f1f067` | BSD-3-Clause | Kotlin-oriented RuneLite-family client/plugin implementation. |
| openosrs | https://github.com/open-osrs/runelite | `915fb55c0a2a1c000dc04a431e68f3bbb29a40cf` | BSD-2-Clause | Archived upstream; useful external-JAR/plugin-loader reference. |
| rlhd | https://github.com/RS117/RLHD | `c079c66f6b844c07fae53a75d59414b3bac8cca0` | BSD-2-Clause | Large real-world graphics plugin reference. |

## Useful ideas to study later

- Plugin lifecycle, dependency ordering, enable/disable behavior
- External JAR loading and classloader isolation
- Event bus/subscription APIs
- Plugin metadata and manifests
- Plugin discovery/update catalogs
- Overlay/config APIs
- Large graphics plugin organization
- Representative gameplay/UI helpers such as Ground Items and Bank features

## Compatibility warning

These projects target modern OSRS/RuneLite-family APIs. They are **not drop-in compatible with the revision-530/2009Scape client**. Modern widget IDs, item/NPC IDs, events, rendering hooks, cache structures and protocols must not be assumed to match our client.

When we port something, treat these as design/source references and adapt it deliberately to Killer Edition's API.

## Licensing

The copied upstream license files are retained in each project folder. Keep those notices with any redistributed/adapted source. The RuneLite Plugin Hub references many third-party repositories; those individual repositories can use licenses different from the Plugin Hub repository itself, so verify each plugin before copying its source into Killer Edition.
