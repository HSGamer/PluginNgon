package me.hsgamer.pluginngon;

import io.github.projectunified.craftux.spigot.SpigotInventoryUI;
import io.github.projectunified.craftux.spigot.SpigotInventoryUIListener;
import io.github.projectunified.minelib.plugin.base.Loadable;
import io.github.projectunified.minelib.scheduler.common.task.Task;
import io.github.projectunified.minelib.scheduler.entity.EntityScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NgonInv implements Loadable {
    private final Map<UUID, Task> updateTasks = new ConcurrentHashMap<>();
    private final Plugin plugin;
    private final NgonMask mask;
    private final String title;
    private final SpigotInventoryUIListener listener;

    public NgonInv(Plugin plugin, NgonConfig config) {
        this.plugin = plugin;
        this.mask = new NgonMask(config);
        this.title = config.getNgonMessage();
        this.listener = new SpigotInventoryUIListener(plugin);
    }

    public SpigotInventoryUI createInventory(Player player) {
        SpigotInventoryUI holder = new SpigotInventoryUI(player.getUniqueId(), title, 45) {
            @Override
            protected void onOpen(InventoryOpenEvent event) {
                updateTasks.put(event.getPlayer().getUniqueId(), EntityScheduler.get(plugin, event.getPlayer()).runTimer(this::update, 0, 0));
            }

            @Override
            protected void onClose(InventoryCloseEvent event) {
                Optional.ofNullable(updateTasks.remove(event.getPlayer().getUniqueId())).ifPresent(Task::cancel);
            }
        };
        holder.setMask(mask);
        holder.setDefaultButton((uuid, actionItem) -> {
            if (actionItem.getAction() == null) {
                actionItem.setAction(InventoryClickEvent.class, event -> mask.nextPage(event.getWhoClicked().getUniqueId()));
            }
            return true;
        });
        holder.setMoveItemOnBottom(true);
        return holder;
    }

    @Override
    public void enable() {
        mask.init();
        listener.register();
    }

    @Override
    public void disable() {
        mask.stop();
        listener.unregister();
    }
}
