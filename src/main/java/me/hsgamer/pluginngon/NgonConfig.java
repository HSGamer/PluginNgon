package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.utils.ColorUtils;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.config.annotation.ConfigPath;

public interface NgonConfig {
    @ConfigPath("plugin")
    default String getNgon() {
        return "Ngon";
    }

    @ConfigPath("ngon")
    default long getNgonLong() {
        return 1000L / 20L;
    }

    default String getNgonMessage() {
        return ColorUtils.colorize("&u&lPlugin Ngon");
    }
}
