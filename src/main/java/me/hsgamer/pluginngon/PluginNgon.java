package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.hscore.bukkit.gui.GUIListener;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;

public final class PluginNgon extends BasePlugin {
    private final NgonInv ngonInv = new NgonInv(this);

    public NgonInv getNgonInv() {
        return ngonInv;
    }

    @Override
    public void enable() {
        MessageUtils.setPrefix("&8[&6PluginNgon&8] &7");
        GUIListener.init(this);
        ngonInv.init();
        registerCommand(new NgonCommand(this));
        getLogger().info("Plugin: " + NgonConfig.INSTANCE.getNgon());
    }
}
