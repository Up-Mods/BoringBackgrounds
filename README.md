<img src="./src/main/resources/assets/boring_backgrounds/icon.png" align="right" width="128px"/>

# Boring Backgrounds

Boring Background is a mod that allows for changing the dirt background with another texture. You can simply change to a single texture, use a weighted list of textures, and even make it change every time the screen is changed! This mod requires Minecraft 1.20.1+ and the [Quilt Standard Libraries](https://modrinth.com/mod/qsl).

## Download

This mod is available for download on the following services:

- [Modrinth (recommended)](https://modrinth.com/mod/boring-backgrounds)
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/boring-backgrounds)
- [GitHub Releases](https://github.com/EnnuiL/BoringBackgrounds/releases)

## Configuration

There are two ways to configure this mod: through a resource pack, or through the global `config` folder.

The config format is shared by both methods, and can be seen below:

```jsonc
{
    // A map of texture identifiers and its weight. A bigger weight means more chances to be chosen over the others.
    "textures": {
        "minecraft:textures/gui/options_background.png": 1
    },
    // If this is enabled, a new background is chosen if a screen is opened, else, it's only chosen after a texture reload.
    "randomize_on_new_screen": false
}
```

If you want to configure the mod through a resource pack, create the file at the path `assets/boring_backgrounds/backgrounds/background_settings.json`.

If you want to use a global config file, the path is `.minecraft/config/boring_backgrounds/config.json`. Keep in mind that it will override any resource pack-provided settings.

If you want to reload the settings, you may do so through F3 + T. This is due to the configuration being handled by a resource reloader.

## License

This mod is licensed under the MIT license. You can freely include the mod on any modpack with no permission. Usage of this mod's code on other projects or derivatives of this mod is allowed as long as attribution is given.
