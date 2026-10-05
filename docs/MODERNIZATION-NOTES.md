# Modern Client Baseline Notes

This branch is a clean modernization of the revision-530 RT4 client used by the current server. It intentionally does **not** include the earlier Killer UI scaler, text scaler or generated/vector-font renderer.

## Baseline decisions

- Keep revision-530 login, gameplay packet, JS5/cache and CS2 behaviour unchanged.
- Keep the existing software and JOGL renderers until the replacement UI/render boundaries are proven.
- Target Java 25 LTS.
- Build with Gradle 9.8 and Kotlin 2.4.20.
- Resolve Gson and JogAmp dependencies from maintained repositories instead of the old checked-in/flat-dir dependency model.
- Treat external plugins as isolated JARs with explicit metadata and lifecycle.
- Keep a legacy loose-plugin reader only as a migration bridge.
- Publish a rolling `modern-client-latest` preview independently of the old v0.8.x release line.

## Deliberately retained legacy code

The RT4 shell still contains Applet-era APIs and several `finalize()` methods. Java 25 still compiles these, but they are deprecated for removal. Removing them is a separate modernization task because the applet shell, signlink/browser helpers, native renderer startup and shutdown paths are intertwined.

The safe order is:

1. Prove the Java-25 standalone desktop build against the current server.
2. Replace finalizer-based native/resource cleanup with explicit ownership/cleanup.
3. Split standalone desktop boot from historical applet helpers.
4. Remove applet-only browser-control paths once no supported launch mode uses them.

## Connection code deliberately preserved

Do not “clean up” packet formats, RSA/login fields, ISAAC behaviour, world-list/JS5 ports or compatibility CRC handling merely because they look old. These are server compatibility boundaries.

See `NETWORK-INTEGRATION-NOTES.md` for the future versioned capability/profile design.

## RuneLite / RSPS lessons incorporated

Public RuneLite and RuneLite-derived RSPS clients show several architectural patterns worth adopting without replacing the revision-530 core:

- explicit plugin lifecycle and isolation
- typed events rather than ever-growing base-plugin callbacks
- client-thread scheduling for game-state mutation
- original/logical widget geometry separate from rendered/canvas geometry
- centralized input coordinate mapping
- overlay/render layers
- server target/configuration profiles rather than hard-coded private-server forks

These are migration targets for the new client framework, not reasons to replace the current protocol/cache engine with a modern OSRS gamepack.
