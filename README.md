# GelenceSignShop

A Spigot plugin that lets players trade items through in-world signs using your Vault economy provider.

## What it does

- Create buy or sell shops directly on signs.
- Bind the exact item (including amount and metadata) by right-clicking the sign with the item in your main hand.
- Process transactions through Vault economy.
- Protect shop signs and supporting blocks from accidental breaking.
- Save and reload all shop signs from disk (`plugins/GelenceSignShop/signs.yml`).
- Provide maintenance commands for checking and fixing orphaned sign records.

## Requirements
- Spigot/Paper 1.20.x
- [Vault](https://www.spigotmc.org/resources/vault.34315/)
- [EssentialsX](https://github.com/EssentialsX/Essentials/releases)

## Installation

1. Build or download the plugin jar.
2. Put the jar into your server `plugins` folder.
3. Make sure `Vault` and `Essentials` are installed.
4. Start or restart the server.

## Quick guide to creating a shop

1. Place a sign.
2. First line: `[shop]`
3. Fourth line: either `buy: <price>` or `sell: <price>`
4. Right-click the sign while holding the item stack you want this shop to trade.

At that point the sign is bound and players can use it.

## Commands

- `/signshop help` - show command help in chat
- `/signshop helpsetup [1|2|3]` - show setup guide pages
- `/signshop datareload` - reload all shop signs from `signs.yml`
- `/signshop datacheck` - list invalid saved shop locations (saved, but no sign exists there)
- `/signshop datafix` - remove invalid saved shop locations

## Data and persistence

- The plugin stores shop data in YAML using location as key
- Maintenance flow:
  - Use `datacheck` to inspect orphan records.
  - Use `datafix` to remove them.

## Notes and limitations

- The plugin currently has no explicit permission-node checks in command/listener logic.
- There are many ways to break the shop signs (e.g. breaking supporting blocks, using pistons, etc.) that are not currently handled by the plugin. Use with caution and check `datacheck` regularly. The plugin does not have automatic checks or fixes for broken signs, so manual maintenance is required.
- Currency text is shown as `GV` in chat messages.
