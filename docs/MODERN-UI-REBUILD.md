# Modern UI Disassembly and Rebuild Plan

The revision-530 client already stores most interfaces as generic cache-driven component trees. The goal is to preserve those interfaces as the game/UI model while replacing the 2009-era layout/render/input layer with a modern, inspectable and moddable UI system.

## What exists today

`Component.decodeIf1()` and `Component.decodeIf3()` decode interface definitions from the cache. Important generic component kinds include:

- type 0: container/scroll area
- type 2: inventory/item grid
- type 3: rectangle
- type 4: text
- type 5: sprite/image
- type 6: model viewport
- type 9: line

Components carry original/cache geometry (`baseX/baseY/baseWidth/baseHeight`) and calculated geometry (`x/y/width/height`). CS1/CS2 scripts can change text, sprites, position, size, visibility, scrolling and dynamic children. A few systems remain special-purpose Java renderers: scene viewport, minimap/compass, context menu, tooltips and some world overlays.

## Stage 1 - Disassemble everything

Build a developer interface dumper that exports every loaded interface to machine-readable JSON:

- interface/group/component IDs
- parent/child hierarchy
- IF1/IF3 format
- component type/clientCode
- original geometry and position/size modes
- calculated geometry
- scroll dimensions
- sprite/model/font IDs
- text/color/alignment/padding
- inventory grid dimensions/margins/slot offsets
- actions and target properties
- CS1 data
- CS2/event listener references
- dynamic children

Build a sprite/asset dump alongside it. Record dimensions, transparency, tiling, frame relationships and repeated edge/corner pieces. This is where image-tile/nine-slice candidates are identified instead of guessed.

Add a live Widget Inspector: point at any visible element and display its ID, hierarchy, logical bounds, rendered bounds, source assets, scripts/events and current state.

## Stage 2 - Generic adapter, not per-window rewrites

Convert cache components into a modern node tree:

```
revision-530 Component tree
          |
          v
LegacyWidgetAdapter
          |
          v
UiNode tree
```

Base mappings:

- type 0 -> Container / ScrollView
- type 2 -> ItemGrid
- type 3 -> Rectangle
- type 4 -> Text
- type 5 -> Image / NineSlice / TiledImage
- type 6 -> ModelView
- type 9 -> Line

Special nodes:

- SceneViewport
- Minimap
- Compass
- ContextMenu
- Tooltip
- OverheadLayer

The success criterion is that Inventory, Skills, Quest, Music, Bank and Shop all pass through the same adapter. A new interface should not require another scale/render patch.

## Stage 3 - Logical layout system

All game scripts operate in logical UI units. Physical pixels belong only to the renderer/input transform.

Supported layout features:

- anchors: left/right/top/bottom/center
- minimum/maximum size
- fixed and content-sized dimensions
- horizontal/vertical flow
- grid layout
- padding/margins/gaps
- clipping/scrolling
- aspect constraints
- text-driven measurement
- safe-area/window constraints

CS2 setters update logical node properties. Query opcodes return logical values. Rendering computes physical bounds after layout.

## Stage 4 - Window manager

Interfaces that make sense as windows get a `UiWindow` wrapper with:

- drag/move
- optional resize
- min/max dimensions
- docking/snap zones
- z-order/focus
- close/minimize where appropriate
- persisted position/size per profile
- reset-to-default layout

Bank, shop and other large dialogs can be resize-capable. Inventory/equipment/minimap can be movable/dockable with sensible size/aspect constraints rather than blindly resizable.

## Stage 5 - Exact text-aware sizing

The vector text layout engine measures content before final layout:

```
available content width
       |
       v
measure + wrap text
       |
       v
line count / exact height
       |
       v
container layout
```

This removes fixed-character-limit chat logic and prevents labels from disappearing outside windows. Usernames/prefixes naturally consume width because wrapping is pixel-based.

## Stage 6 - Mod-friendly description layer

Expose stable semantic IDs/tags in addition to legacy component IDs:

```
interface.bank
bank.item-grid
bank.close
chat.history
chat.input
minimap.viewport
```

Themes/layout mods can override properties in external files without rebuilding the client. Example concepts:

```
interface.bank {
  anchor: center;
  min-width: 420;
  resize: both;
}

bank.item-grid {
  layout: grid;
  cell-size: 36;
  gap: 4;
}

chat.history {
  wrap: word;
  line-gap: 2;
}
```

The exact file syntax will be chosen after the dumper shows how much legacy state must be represented. Keep the schema versioned and hot-reloadable.

## Stage 7 - Unified input

One hit-test tree handles mouse input after converting physical screen coordinates into logical UI coordinates. Menus, inventory slots, minimap, dragging and resizing use the same transform. No separate hard-coded scaled hitboxes.

## Stage 8 - Plugin API

Add typed UI/plugin events and overlay layers inspired by RuneLite while keeping a compatibility adapter for historic Killer plugins.

Useful events/layers:

- WidgetLoaded / WidgetChanged / WidgetClosed
- MenuEntryAdded / MenuClicked
- ChatMessage
- VarpChanged / InventoryChanged
- ClientTick / GameTick
- ABOVE_SCENE / UNDER_UI / AFTER_INTERFACE / ALWAYS_ON_TOP

UI plugins should add/modify nodes through a stable API instead of touching RT4 arrays directly.

## Migration rule

The current server, cache and CS2 behaviour remain authoritative. The new UI consumes them; it does not require a server protocol rewrite. Where future server/client cooperation would help, add a versioned capability to the connection-profile notes rather than silently changing revision-530 behaviour.
