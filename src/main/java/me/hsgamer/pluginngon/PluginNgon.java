package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.hscore.bukkit.gui.BukkitGUIListener;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;

public final class PluginNgon extends BasePlugin {
    private final NgonInv ngonInv = new NgonInv(this);
    private final NgonRunnable ngonRunnable = new NgonRunnable();

    public NgonInv getNgonInv() {
        return ngonInv;
    }

    @Override
    public void enable() {
        MessageUtils.setPrefix("&8[&6PluginNgon&8] &7");
        BukkitGUIListener.init(this);
        ngonInv.init();
        registerCommand(new NgonCommand(this));
        getLogger().info("Plugin: " + NgonConfig.INSTANCE.getNgon());
        ngonRunnable.runTaskTimer(this, 0, 20);
    }

    @Override
    public void disable() {
        ngonRunnable.cancel();
    }
}
