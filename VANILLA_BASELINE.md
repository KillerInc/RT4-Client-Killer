# Vanilla RT4 Baseline and Killer Edition Rules

## Frozen upstream reference

The unmodified reference source is pinned on branch `vanilla-pazaz-snapshot`.

- Upstream: https://github.com/pazaz/rt4-client
- Upstream master commit: `5f83213ebb08288c65f56ca64312ed628d86d1c5`
- Upstream `original` branch commit at capture time: `da63f335467a511ec1c3c8e61ab03ba6a9b3843d`
- Snapshot branch commit: `7d10e061f325507d163b3730af0124d4aa4ebd12`

The snapshot branch contains a gitlink pinned to the exact upstream master commit.

## Project invariant

Killer Edition changes are additive. Existing vanilla behavior is the baseline, not something to redesign while adding features.

Before changing an original RT4 file or function:

1. Read the corresponding implementation from the frozen upstream snapshot.
2. Identify the smallest hook point needed for the new feature.
3. Preserve the original code path and semantics unless the requested feature explicitly requires otherwise.
4. Keep new behavior behind Killer Edition state/feature boundaries.
5. Do not repair, reinterpret, re-parent, resize, or replace vanilla components merely to make new code easier.
6. Do not alter game mechanics, networking, cache semantics, input semantics, interface state, or session state as a side effect of UI work.
7. If a proposed change requires changing vanilla behavior, stop and ask first.

## Modern UI rules

- `Modern UI = No`: vanilla renderer, vanilla UI art, vanilla fonts, vanilla interactions and mechanics remain unchanged. The Killer Edition selector/Style Editor entry may be added, but existing components are not modified in-place.
- `Modern UI = Yes`: the replacement renderer may substitute visuals and fonts, but existing component state/scripts remain the source of truth until deliberately replaced.
- Injected Killer Edition components must be distinguishable from vanilla components and must not change existing component IDs, parents, scripts, hitboxes, or layout.
- Missing Modern UI artwork may use pink development diagnostics only inside `ModernUiRenderer`.
- A Modern UI reload must not log out, reconnect, reset the interface stack, or mutate gameplay state.
- Existing vanilla controls such as Anti-aliasing are reference-only. Never rewrite them to implement a Killer Edition control.

## Debugging rule

When a new feature misbehaves, diagnose the new code first. Do not compensate by changing a vanilla subsystem that already worked in the upstream snapshot.
