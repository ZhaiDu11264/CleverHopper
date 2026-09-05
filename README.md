# Clever Hopper

A tiny Create addon that lets the vanilla **Hopper** receive items from Create belts — just like a basin.

## Why

When a Create belt ends in front of a vanilla hopper, items are ejected onto the floor: the belt only inserts into blocks that provide Create's `DirectBeltInputBehaviour` (basin, packager, ...). This addon wraps the hopper's item handler in that behaviour, so belts feed hoppers smoothly.

## Features

- Belt items are inserted into the hopper exactly like a basin would
- Redstone-locked hoppers are treated as **full** — items pile up on the belt instead of being ejected, and resume flowing once unlocked
- Anything that already registers `DirectBeltInputBehaviour` (basins, packagers, other Create blocks) keeps its original behaviour — zero interference
- Only the vanilla hopper is affected; chests, barrels and other modded containers are untouched

## Compatibility

- Minecraft 1.21.1
- NeoForge 21.1.x
- [Create](https://modrinth.com/mod/create) 6.0.10 (required)

## Installation

1. Install [Create](https://modrinth.com/mod/create) 6.0.10+
2. Drop the jar from [Releases](https://github.com/ZhaiDu11264/CleverHopper/releases) into your `mods` folder

## License

[MIT](LICENSE)
