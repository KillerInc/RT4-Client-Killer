OSRS Client Killer Edition - Modern UI Modder Template

Archive format
--------------
The distributable file is a normal ZIP container with the .uipack extension.
style.info must remain at the archive root.

Install
-------
Copy the completed .uipack file into:
  ui\styles\

Then enable Modern UI and select the style from the in-game Style Editor.

Inheritance
-----------
base=killer-modern means any asset not present in this archive is inherited
from the built-in Killer Modern UI pack. There is no fallback to the legacy
JS5/Index-8 UI renderer.

Asset paths
-----------
Override an asset by preserving its logical path. Example:
  icons\dropdown.svg

SVG and PNG artwork are supported by the Modern UI image resolver.
The default TTF logical path is:
  fonts\runescape_small.ttf

If you override that font, supply a valid TrueType font. Missing/invalid Modern
UI fonts fail explicitly; the client does not silently substitute the legacy
bitmap font or a system font.

For quick local experiments without repacking an archive, matching logical
paths can be placed under ui\overrides\.
