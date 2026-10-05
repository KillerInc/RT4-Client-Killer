# Killer Edition external plugin format

The modern client uses one JAR per external plugin.

Place plugin JARs in the configured `plugins` directory. Each JAR contains:

```
META-INF/killer-plugin.properties
```

Example:

```properties
ID=example.plugin
NAME=Example Plugin
AUTHOR=KillerInc
VERSION=1.0.0
DESCRIPTION=Example external client plugin
MAIN_CLASS=example.ExamplePlugin
```

The main class extends `plugin.Plugin`. `startUp()` and `shutDown()` are the preferred lifecycle methods. Existing historic callbacks remain available while plugins migrate.

The launcher installs/removes complete JAR files. It no longer needs to copy loose `.class` files into plugin directories. The client retains a legacy loose-plugin reader only as a compatibility bridge.
