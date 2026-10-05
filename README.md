# OSRS Client Killer Edition — RT4/2009Scape core

This branch is a clean modernization of the revision-530 RT4 client used by the current 2009Scape server.

The game/network/cache/CS2 core remains revision 530. The modernization work is deliberately separated from the previous UI-scaler and vector-font experiments so the new architecture can be validated from a known-good game baseline.

## Modern baseline

- Java 25 LTS toolchain/runtime target.
- Gradle 9.8.
- Kotlin 2.4.20 for Kotlin plugin sources.
- Gson 2.14.
- JOGL/GlueGen 2.6 from Maven Central.
- JAR-based external plugins with per-plugin class loaders and lifecycle cleanup.
- Rolling `modern-client-latest` preview release for launcher integration.

## Preserved RT4 behaviour

The current revision-530 protocol, JS5/cache handling, CS1/CS2 interfaces, software renderer and OpenGL renderer remain the game core.

This branch intentionally does **not** contain the experimental UI scaler, independent text scaler or TTF replacement.

See `docs/NETWORK-INTEGRATION-NOTES.md` and `docs/PLUGIN-FORMAT.md`.
