package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.utils.ColorUtils;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.config.annotation.ConfigPath;

public interface NgonConfig {
    @ConfigPath({"plugin", "ngon"})
    default String getNgon() {
        return "Ngon";
    }

    @ConfigPath({"ngon", "plugin"})
    default long getNgonLong() {
        return 1000L / 20L;
    }

    default String getNgonMessage() {
        return ColorUtils.colorize("&u&lPlugin Ngon");
    }

    Config config();
}
