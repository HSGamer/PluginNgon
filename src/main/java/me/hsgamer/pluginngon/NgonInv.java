package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.gui.BukkitGUIDisplay;
import me.hsgamer.hscore.bukkit.gui.BukkitGUIHolder;
import me.hsgamer.hscore.bukkit.scheduler.Scheduler;
import me.hsgamer.hscore.bukkit.scheduler.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NgonInv extends BukkitGUIHolder {
    private final Map<UUID, Task> updateTasks = new ConcurrentHashMap<>();

    public NgonInv(Plugin plugin, NgonConfig config) {
        super(plugin);
        setTitleFunction(uuid -> config.getNgonMessage());
        setButtonMap(new NgonButtonMap(config));
        setSize(45);
    }

    @Override
    public BukkitGUIDisplay newDisplay(UUID uuid) {
        BukkitGUIDisplay display = super.newDisplay(uuid);
        Player player = Bukkit.getPlayer(uuid);
        updateTasks.put(uuid, Scheduler.current().async().runEntityTaskTimer(player, display::update, 0, 0));
        return display;
    }

    @Override
    protected void onRemoveDisplay(BukkitGUIDisplay display) {
        super.onRemoveDisplay(display);
        Optional.ofNullable(updateTasks.remove(display.getUniqueId())).ifPresent(Task::cancel);
    }
}
