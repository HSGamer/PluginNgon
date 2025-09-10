package me.hsgamer.pluginngon;

import io.github.projectunified.minelib.plugin.base.BasePlugin;
import io.github.projectunified.minelib.plugin.command.CommandComponent;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class PluginNgon extends BasePlugin {
    @Override
    protected List<Object> getComponents() {
        NgonConfig ngonConfig = ConfigGenerator.newInstance(NgonConfig.class, new BukkitConfig(this, "ngon.yml"));

        return Arrays.asList(
                ngonConfig,
                new NgonInv(this, ngonConfig),
                new NgonRunnable(this),
                new CommandComponent(this, new NgonCommand(this))
        );
    }

    @Override
    public void enable() {
        MessageUtils.setPrefix("&8[&6PluginNgon&8] &7");
        getLogger().info("Plugin: " + get(NgonConfig.class).getNgon());

        for (Map.Entry<String[], Object> entry : get(NgonConfig.class).config().getNormalizedValues(true).entrySet()) {
            getLogger().info(Arrays.toString(entry.getKey()) + ": " + entry.getValue());
        }
    }

    @Override
    public void disable() {

    }
}
