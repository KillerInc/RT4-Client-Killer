# Modern client research baseline

Checked 2026-10-05. This file records the public projects and current toolchain information used to choose the modern-client architecture. It is architectural research, not copied game/protocol code.

## Reference clients and frameworks

- RuneLite: https://github.com/runelite/runelite
  - Useful patterns: API/client separation, typed events, client-thread scheduling, widget original-vs-rendered geometry, overlay layers, input managers, plugin lifecycle/configuration.
- RuneLite RSPS: https://github.com/rsbox/runelite-rsps
- RuneLite Extended: https://github.com/Rune-Server/runelite-extended
- RuneLite RSPS / Alter-style injector: https://github.com/CalvoG/Runelite-RSPS
- Devious RSPS fork: https://github.com/RuneRelics/devious-client-rsps
  - Studied for architecture only. Do not import binaries or trust third-party distributions by default.

These RSPS projects target modern OSRS-era clients/protocols. They are references for launcher/plugin/injection/server-profile architecture, not drop-in replacements for revision 530.

## Chosen compatibility boundary

The existing revision-530 RT4 client remains authoritative for:

- login/RSA/ISAAC protocol
- gameplay packet encodings
- JS5/cache protocol and CRC behaviour
- CS1/CS2 semantics
- cache interface/component formats
- world/render/game state

Modernization happens around that compatibility boundary first. This avoids silently converting the current server into a modern OSRS protocol target.

## Current toolchain verified for this baseline

- Java 25 LTS runtime/toolchain
- Gradle 9.8.0
- Kotlin 2.4.20
- Gson 2.14.0
- JogAmp JOGL/GlueGen 2.6.0

The launcher modernization is maintained in KillerInc/Saradomin-Launcher_Killer on its matching `modern-client` branch and targets .NET 10 LTS.

## Later server/client integration

Public RSPS clients commonly externalize server target information instead of hard-coding every server fork. For Killer Edition, retain the revision-530 defaults now and later introduce a versioned connection/capability profile shared by launcher, client and server.

See `NETWORK-INTEGRATION-NOTES.md`.
