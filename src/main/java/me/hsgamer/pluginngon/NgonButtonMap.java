package me.hsgamer.pluginngon;

import com.cryptomorin.xseries.XMaterial;
import me.hsgamer.hscore.bukkit.gui.object.BukkitItem;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.minecraft.gui.advanced.AdvancedButtonMap;
import me.hsgamer.hscore.minecraft.gui.button.Button;
import me.hsgamer.hscore.minecraft.gui.button.impl.AnimatedButton;
import me.hsgamer.hscore.minecraft.gui.event.ClickEvent;
import me.hsgamer.hscore.minecraft.gui.mask.MaskUtils;
import me.hsgamer.hscore.minecraft.gui.mask.impl.AnimatedMask;
import me.hsgamer.hscore.minecraft.gui.mask.impl.ButtonMapMask;
import me.hsgamer.hscore.minecraft.gui.mask.impl.MultiSlotsMask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class NgonButtonMap extends AdvancedButtonMap {
    private final List<List<Integer>> slotsList = new ArrayList<>();
    private final NgonConfig config;

    public NgonButtonMap(NgonConfig config) {
        this.config = config;
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
        prepareMasks();
    }

    private void prepareMasks() {
        addMask(
                new MultiSlotsMask("default", MaskUtils.generateAreaSlots(0, 0, 9, 6).boxed().collect(Collectors.toList()))
                        .addButton(
                                new AnimatedButton()
                                        .setPeriodTicks(20)
                                        .addButton(new Button() {
                                            @Override
                                            public BukkitItem getItem(UUID uuid) {
                                                ItemStack itemStack = XMaterial.BLACK_STAINED_GLASS_PANE.parseItem();
                                                ItemMeta itemMeta = itemStack.getItemMeta();
                                                itemMeta.setDisplayName(config.getNgonMessage());
                                                itemStack.setItemMeta(itemMeta);
                                                return new BukkitItem(itemStack);
                                            }

                                            @Override
                                            public void handleAction(ClickEvent event) {
                                                Player player = Bukkit.getPlayer(event.getViewerID());
                                                if (player == null) return;
                                                MessageUtils.sendMessage(player, config.getNgonMessage());
                                            }
                                        })
                                        .addButton(new Button() {
                                            @Override
                                            public BukkitItem getItem(UUID uuid) {
                                                ItemStack itemStack = XMaterial.WHITE_STAINED_GLASS_PANE.parseItem();
                                                ItemMeta itemMeta = itemStack.getItemMeta();
                                                itemMeta.setDisplayName(config.getNgonMessage());
                                                itemStack.setItemMeta(itemMeta);
                                                return new BukkitItem(itemStack);
                                            }

                                            @Override
                                            public void handleAction(ClickEvent event) {
                                                Player player = Bukkit.getPlayer(event.getViewerID());
                                                if (player == null) return;
                                                MessageUtils.sendMessage(player, config.getNgonMessage());
                                            }
                                        })
                        )
        );

        List<ButtonMapMask> frameMasks = new ArrayList<>();
        slotsList.forEach(slots -> {
            ButtonMapMask mask = new ButtonMapMask("plugin");
            mask.addButton(new Button() {
                @Override
                public BukkitItem getItem(UUID uuid) {
                    ItemStack itemStack = new ItemStack(Material.BEDROCK);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(config.getNgonMessage());
                    itemStack.setItemMeta(itemMeta);
                    return new BukkitItem(itemStack);
                }

                @Override
                public void handleAction(ClickEvent event) {
                    Player player = Bukkit.getPlayer(event.getViewerID());
                    if (player == null) return;
                    MessageUtils.sendMessage(player, config.getNgonMessage());
                }
            }, slots);
            frameMasks.add(mask);
        });
        addMask(new AnimatedMask("plugin_animated").setPeriodMillis(config.getNgonLong()).addMask(frameMasks));
    }
}
