package me.hsgamer.pluginngon;

import com.cryptomorin.xseries.XMaterial;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.minecraft.gui.button.AnimatedButton;
import me.hsgamer.hscore.minecraft.gui.button.SimpleButton;
import me.hsgamer.hscore.minecraft.gui.common.button.ButtonMap;
import me.hsgamer.hscore.minecraft.gui.common.inventory.InventoryPosition;
import me.hsgamer.hscore.minecraft.gui.common.item.ActionItem;
import me.hsgamer.hscore.minecraft.gui.mask.AnimatedMask;
import me.hsgamer.hscore.minecraft.gui.mask.HybridMask;
import me.hsgamer.hscore.minecraft.gui.mask.MultiSlotsMask;
import me.hsgamer.hscore.minecraft.gui.mask.util.MaskUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NgonButtonMap extends HybridMask {
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
        MultiSlotsMask outlineMask = new MultiSlotsMask(context -> MaskUtils.generateOutlineSlots(InventoryPosition.of(0, 0), InventoryPosition.of(8, 4)).map(context::getSlot).collect(Collectors.toList()));
        addMask(outlineMask);

        AnimatedButton animatedButton = new AnimatedButton();
        animatedButton.setPeriodMillis(1000);
        animatedButton.addButton(
                new SimpleButton(
                        uuid -> {
                            ItemStack itemStack = XMaterial.BLACK_STAINED_GLASS_PANE.parseItem();
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setDisplayName(config.getNgonMessage());
                            itemStack.setItemMeta(itemMeta);
                            return itemStack;
                        },
                        event -> {
                            MessageUtils.sendMessage(event.getViewerID(), config.getNgonMessage());
                        }
                ),
                new SimpleButton(
                        uuid -> {
                            ItemStack itemStack = XMaterial.WHITE_STAINED_GLASS_PANE.parseItem();
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setDisplayName(config.getNgonMessage());
                            itemStack.setItemMeta(itemMeta);
                            return itemStack;
                        },
                        event -> {
                            MessageUtils.sendMessage(event.getViewerID(), config.getNgonMessage());
                        }
                )
        );
        outlineMask.addButton(animatedButton);

        List<ButtonMap> frameMasks = new ArrayList<>();
        slotsList.forEach(slots -> {
            ButtonMap buttonMap = context -> {
                ItemStack itemStack = new ItemStack(Material.BEDROCK);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(config.getNgonMessage());
                itemStack.setItemMeta(itemMeta);

                ActionItem actionItem = new ActionItem()
                        .setItem(itemStack)
                        .setAction(event -> MessageUtils.sendMessage(event.getViewerID(), config.getNgonMessage()));

                return slots.stream().collect(Collectors.toMap(slot -> slot, slot -> actionItem));
            };
            frameMasks.add(buttonMap);
        });
        AnimatedMask animatedMask = new AnimatedMask();
        animatedMask.addMask(frameMasks);
        animatedMask.setPeriodMillis(config.getNgonLong());
        addMask(animatedMask);
    }
}
