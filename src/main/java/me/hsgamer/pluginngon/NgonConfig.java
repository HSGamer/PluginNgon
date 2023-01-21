package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.config.annotation.ConfigPath;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public interface NgonConfig {
    NgonConfig INSTANCE = ConfigGenerator.newInstance(NgonConfig.class, new BukkitConfig(JavaPlugin.getProvidingPlugin(NgonConfig.class), "ngon.yml"));

    @ConfigPath("plugin")
    default String getNgon() {
        return "Ngon";
    }

    @ConfigPath("ngon")
    default long getNgonLong() {
        return 1000L / 20L;
    }

    default String getNgonMessage() {
        return MessageUtils.colorize("&u&lPlugin Ngon");
    }
}
