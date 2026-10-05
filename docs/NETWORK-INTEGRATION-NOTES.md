# Future server/client connection integration

The modern-client branch deliberately keeps revision-530 network behaviour compatible with the current 2009Scape server. No packet formats are changed in this modernization pass.

## Existing compatibility switches to preserve

The RT4 core already has server-compatibility behaviour in `GlobalConfig`, including the RSA key, game/world-list/JS5 ports, string-vs-base37 login fields, extra login information, the compatibility idx28 CRC, ISAAC enable/disable, world selection and JS5 timeout.

These are protocol concerns and should remain separate from the future UI/layout layer.

## Planned connection profile

A later client/server update should replace scattered server-specific flags with a versioned connection profile containing revision, hosts/ports, RSA information and protocol capabilities. The server and launcher can eventually publish the same capability document, while the client keeps an explicit legacy revision-530 profile as a known-good fallback.

Useful future boundaries:

1. Login protocol capabilities.
2. Gameplay packet capabilities.
3. JS5/cache capabilities.
4. Client/plugin API capabilities.

This lets client and server evolve independently without silently changing revision-530 defaults.
