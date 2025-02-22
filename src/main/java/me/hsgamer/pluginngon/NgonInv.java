package me.hsgamer.pluginngon;

import io.github.projectunified.minelib.plugin.base.Loadable;
import io.github.projectunified.minelib.scheduler.common.task.Task;
import io.github.projectunified.minelib.scheduler.entity.EntityScheduler;
import me.hsgamer.hscore.bukkit.gui.holder.BukkitGUIHolder;
import me.hsgamer.hscore.bukkit.gui.holder.listener.HolderBukkitInventoryListener;
import me.hsgamer.hscore.minecraft.gui.common.button.ButtonMap;
import me.hsgamer.hscore.minecraft.gui.holder.event.CloseEvent;
import me.hsgamer.hscore.minecraft.gui.holder.event.OpenEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class NgonInv implements Loadable {
    private final Map<UUID, Task> updateTasks = new ConcurrentHashMap<>();
    private final Plugin plugin;
    private final ButtonMap buttonMap;
    private final HolderBukkitInventoryListener listener;
    private final BiFunction<UUID, BukkitGUIHolder, Inventory> inventoryFunction;

    public NgonInv(Plugin plugin, NgonConfig config) {
        this.plugin = plugin;
        this.buttonMap = new NgonButtonMap(config);
        this.listener = new HolderBukkitInventoryListener(plugin);
        this.inventoryFunction = listener.create(
                uuid -> InventoryType.CHEST,
                uuid -> 45,
                uuid -> config.getNgonMessage()
        );
    }

    public BukkitGUIHolder createHolder(Player player) {
        BukkitGUIHolder holder = new BukkitGUIHolder(player.getUniqueId(), inventoryFunction) {
            @Override
            public void handleOpen(OpenEvent event) {
                super.handleOpen(event);
                UUID uuid = event.getViewerID();
                Player player = Bukkit.getPlayer(uuid);
                updateTasks.put(uuid, EntityScheduler.get(plugin, player).runTimer(this::update, 0, 0));
            }

            @Override
            public void handleClose(CloseEvent event) {
                super.handleClose(event);
                Optional.ofNullable(updateTasks.remove(event.getViewerID())).ifPresent(Task::cancel);
            }
        };
        holder.setButtonMap(buttonMap);
        holder.setMoveItemOnBottom(true);
        holder.init();
        return holder;
    }

    @Override
    public void enable() {
        listener.init();
        buttonMap.init();
    }

    @Override
    public void disable() {
        buttonMap.stop();
        listener.stop();
    }
}
