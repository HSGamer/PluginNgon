package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.gui.button.Button;
import me.hsgamer.hscore.bukkit.gui.button.ButtonMap;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class NgonButtonMap extends BukkitRunnable implements ButtonMap {
    private final List<List<Integer>> slotsList = new ArrayList<>();
    private int currentIndex = 0;

    public NgonButtonMap() {
        slotsList.add(Arrays.asList(2, 3, 4, 5, 11, 15, 20, 21, 22, 23, 29, 38)); // P
        slotsList.add(Arrays.asList(2, 11, 20, 29, 38, 39, 40, 41, 42)); // L
        slotsList.add(Arrays.asList(2, 6, 11, 15, 20, 24, 29, 33, 39, 40, 41)); // U
        slotsList.add(Arrays.asList(3, 4, 5, 11, 20, 22, 23, 29, 33, 39, 40, 41)); // G
        slotsList.add(Arrays.asList(2, 3, 4, 5, 6, 13, 22, 31, 38, 39, 40, 41, 42)); // I
        slotsList.add(Arrays.asList(2, 6, 11, 12, 15, 20, 22, 24, 29, 32, 33, 38, 42)); // N
        slotsList.add(Collections.emptyList());
        slotsList.add(Arrays.asList(2, 6, 11, 12, 15, 20, 22, 24, 29, 32, 33, 38, 42)); // N
        slotsList.add(Arrays.asList(3, 4, 5, 11, 20, 22, 23, 29, 33, 39, 40, 41)); // G
        slotsList.add(Arrays.asList(3, 4, 5, 11, 15, 20, 24, 29, 33, 39, 40, 41)); // O
        slotsList.add(Arrays.asList(2, 6, 11, 12, 15, 20, 22, 24, 29, 32, 33, 38, 42)); // N
        slotsList.add(Collections.emptyList());
    }

    @Override
    public Map<Button, List<Integer>> getButtons(UUID uuid) {
        return Collections.singletonMap(new Button() {
            @Override
            public ItemStack getItemStack(UUID uuid) {
                ItemStack itemStack = new ItemStack(Material.BEDROCK);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(NgonConfig.INSTANCE.getNgonMessage());
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            }

            @Override
            public void handleAction(UUID uuid, InventoryClickEvent event) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) return;
                MessageUtils.sendMessage(player, NgonConfig.INSTANCE.getNgonMessage());
            }
        }, slotsList.get(currentIndex));
    }

    @Override
    public void init() {
        runTaskTimer(JavaPlugin.getProvidingPlugin(getClass()), 20, 20);
    }

    @Override
    public void stop() {
        cancel();
    }

    @Override
    public void run() {
        currentIndex = (currentIndex + 1) % slotsList.size();
    }

    @Override
    public Button getDefaultButton(UUID uuid) {
        return new Button() {
            @Override
            public ItemStack getItemStack(UUID uuid) {
                ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(NgonConfig.INSTANCE.getNgonMessage());
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            }

            @Override
            public void handleAction(UUID uuid, InventoryClickEvent event) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) return;
                MessageUtils.sendMessage(player, NgonConfig.INSTANCE.getNgonMessage());
            }
        };
    }
}
