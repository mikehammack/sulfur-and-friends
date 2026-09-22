# Sulfur & Friends

A small quality-of-life mod for Minecraft 26.3 (Fabric + NeoForge) built around
sulfur: warnings, tooltips, cube persistence, and an in-game reference guide.

- Mod ID: `sulfurandfriends` · Version: `1.0.0`
- Minecraft: 26.3 · Loaders: Fabric, NeoForge
- License: MIT — © 2026 Maxim Arcana

## Features (v1)

- **Potent Sulfur Geyser warning** — get a heads-up before a geyser goes off.
- **Sulfur bucket archetype tooltip** — buckets show their archetype at a glance.
- **Sulfur cube persistence** — any sulfur cube carrying an absorbed block never
  despawns (mixin forces persistence). Caveat: in large automated farms this can
  let cube counts build up over time.
- **Archetype Field Guide** — a keybind-driven in-game reference screen covering
  the archetypes.

The friends-list feature is deferred to v1.1.

## Building

No Gradle required — the project builds with a plain `javac` toolchain:

```bash
./build.sh [version]
# → build/libs/sulfurandfriends-<version>-fabric.jar
# → build/libs/sulfurandfriends-<version>-neoforge.jar
```

Requires Java 25. `build.sh` expects a `libs/` directory next to it containing the
compile-only toolchain jars (not committed — too large and version-specific):

- `minecraft-26.3-client.jar` (official Mojang client jar, unobfuscated)
- `mc-deps/` — libraries from the Mojang version JSON
- `fabric-loader-*.jar`, `fabric-api-*.jar`, `fabric-modules/`
- `neoforge-*-universal.jar`, `fancymodloader-loader-*.jar`, `neoforge-bus-*.jar`
- `sponge-mixin.jar`, `stubs/` (compile-only API stubs, never packaged)

Layout: `common/` holds the shared code, `fabric/` and `neoforge/` the thin
loader entry points.
