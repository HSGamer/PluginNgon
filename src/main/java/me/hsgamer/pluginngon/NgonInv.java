package me.hsgamer.pluginngon;

import io.github.projectunified.minelib.plugin.base.Loadable;
import io.github.projectunified.minelib.scheduler.common.task.Task;
import io.github.projectunified.minelib.scheduler.entity.EntityScheduler;
import me.hsgamer.hscore.bukkit.gui.BukkitGUIDisplay;
import me.hsgamer.hscore.bukkit.gui.BukkitGUIHolder;
import me.hsgamer.hscore.bukkit.gui.BukkitGUIListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NgonInv extends BukkitGUIHolder implements Loadable {
    private final Map<UUID, Task> updateTasks = new ConcurrentHashMap<>();
    private final Plugin plugin;
    private Listener listener;

    public NgonInv(Plugin plugin, NgonConfig config) {
        super(plugin);
        this.plugin = plugin;
        setTitleFunction(uuid -> config.getNgonMessage());
        setButtonMap(new NgonButtonMap(config));
        setSize(45);
    }

    @Override
    public BukkitGUIDisplay newDisplay(UUID uuid) {
        BukkitGUIDisplay display = super.newDisplay(uuid);
        Player player = Bukkit.getPlayer(uuid);
        updateTasks.put(uuid, EntityScheduler.get(plugin, player).runTimer(display::update, 0, 0));
        return display;
    }

    @Override
    protected void onRemoveDisplay(BukkitGUIDisplay display) {
        super.onRemoveDisplay(display);
        Optional.ofNullable(updateTasks.remove(display.getUniqueId())).ifPresent(Task::cancel);
    }

    @Override
    public void enable() {
        listener = BukkitGUIListener.init(plugin);
        init();
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(listener);
    }
}
