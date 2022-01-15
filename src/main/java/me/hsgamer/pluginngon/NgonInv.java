package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.gui.GUIDisplay;
import me.hsgamer.hscore.bukkit.gui.GUIHolder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NgonInv extends GUIHolder {
    private final Map<UUID, BukkitTask> updateTasks = new ConcurrentHashMap<>();

    public NgonInv(Plugin plugin) {
        super(plugin);
        setTitleFunction(uuid -> NgonConfig.INSTANCE.getNgonMessage());
        setButtonMap(new NgonButtonMap());
        setSize(45);
    }

    @Override
    public GUIDisplay createDisplay(UUID uuid) {
        GUIDisplay display = super.createDisplay(uuid);
        updateTasks.put(uuid, Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(), display::update, 10, 10));
        return display;
    }

    @Override
    public void removeDisplay(UUID uuid) {
        super.removeDisplay(uuid);
        Optional.ofNullable(updateTasks.remove(uuid)).ifPresent(BukkitTask::cancel);
    }
}
