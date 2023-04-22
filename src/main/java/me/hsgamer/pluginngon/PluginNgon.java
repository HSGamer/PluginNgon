package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.bukkit.gui.BukkitGUIListener;
import me.hsgamer.hscore.bukkit.scheduler.Scheduler;
import me.hsgamer.hscore.bukkit.scheduler.Task;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;

public final class PluginNgon extends BasePlugin {
    private final NgonConfig ngonConfig = ConfigGenerator.newInstance(NgonConfig.class, new BukkitConfig(this, "ngon.yml"));
    private final NgonInv ngonInv = new NgonInv(this, ngonConfig);
    private final NgonRunnable ngonRunnable = new NgonRunnable();
    private Task ngonTask;

    public NgonInv getNgonInv() {
        return ngonInv;
    }

    public NgonConfig getNgonConfig() {
        return ngonConfig;
    }

    @Override
    public void enable() {
        MessageUtils.setPrefix("&8[&6PluginNgon&8] &7");
        BukkitGUIListener.init(this);
        ngonInv.init();
        registerCommand(new NgonCommand(this));
        getLogger().info("Plugin: " + ngonConfig.getNgon());
        ngonTask = Scheduler.current().async().runTaskTimer(ngonRunnable, 0, 20);
    }

    @Override
    public void disable() {
        ngonTask.cancel();
    }
}
