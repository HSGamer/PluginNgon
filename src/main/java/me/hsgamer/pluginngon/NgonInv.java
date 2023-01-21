package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.gui.BukkitGUIDisplay;
import me.hsgamer.hscore.bukkit.gui.BukkitGUIHolder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NgonInv extends BukkitGUIHolder {
    private final Map<UUID, BukkitTask> updateTasks = new ConcurrentHashMap<>();

    public NgonInv(Plugin plugin, NgonConfig config) {
        super(plugin);
        setTitleFunction(uuid -> config.getNgonMessage());
        setButtonMap(new NgonButtonMap(config));
        setSize(45);
    }

    @Override
    public BukkitGUIDisplay newDisplay(UUID uuid) {
        BukkitGUIDisplay display = super.newDisplay(uuid);
        updateTasks.put(uuid, Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(), display::update, 0, 0));
        return display;
    }

    @Override
    protected void onRemoveDisplay(BukkitGUIDisplay display) {
        super.onRemoveDisplay(display);
        Optional.ofNullable(updateTasks.remove(display.getUniqueId())).ifPresent(BukkitTask::cancel);
    }
}
