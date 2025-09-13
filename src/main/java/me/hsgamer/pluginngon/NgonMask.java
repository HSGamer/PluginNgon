package me.hsgamer.pluginngon;

import com.cryptomorin.xseries.XMaterial;
import io.github.projectunified.craftux.button.AnimatedButton;
import io.github.projectunified.craftux.common.Mask;
import io.github.projectunified.craftux.common.Position;
import io.github.projectunified.craftux.mask.HybridMask;
import io.github.projectunified.craftux.mask.MaskPaginatedMask;
import io.github.projectunified.craftux.mask.MaskUtils;
import io.github.projectunified.craftux.mask.MultiPositionMask;
import io.github.projectunified.craftux.spigot.SpigotInventoryUtil;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class NgonMask extends HybridMask {
    private final List<List<Integer>> slotsList = new ArrayList<>();
    private final NgonConfig config;
    private Consumer<UUID> nextPageConsumer;

    public NgonMask(NgonConfig config) {
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
        MultiPositionMask outlineMask = new MultiPositionMask(uuid -> MaskUtils.generateOutlinePositions(Position.of(0, 0), Position.of(8, 4)));
        add(outlineMask);

        AnimatedButton animatedButton = new AnimatedButton();
        animatedButton.setPeriodMillis(1000);
        animatedButton.addButton(
                (uuid, actionItem) -> {
                    ItemStack itemStack = XMaterial.BLACK_STAINED_GLASS_PANE.parseItem();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(config.getNgonMessage());
                    itemStack.setItemMeta(itemMeta);

                    actionItem.setItem(itemStack);
                    actionItem.setAction(InventoryClickEvent.class,
                            event -> MessageUtils.sendMessage(event.getWhoClicked().getUniqueId(), config.getNgonMessage())
                    );
                    return true;
                },
                (uuid, actionItem) -> {
                    ItemStack itemStack = XMaterial.WHITE_STAINED_GLASS_PANE.parseItem();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(config.getNgonMessage());
                    itemStack.setItemMeta(itemMeta);

                    actionItem.setItem(itemStack);
                    actionItem.setAction(InventoryClickEvent.class,
                            event -> MessageUtils.sendMessage(event.getWhoClicked().getUniqueId(), config.getNgonMessage())
                    );
                    return true;
                }
        );
        outlineMask.add(animatedButton);

        List<Mask> masks = slotsList.stream().map(slots -> {
            List<Position> positions = slots.stream().map(slot -> SpigotInventoryUtil.toPosition(slot, InventoryType.CHEST)).collect(Collectors.toList());
            MultiPositionMask multiPositionMask = new MultiPositionMask(uuid -> positions);
            multiPositionMask.add(
                    (uuid, actionItem) -> {
                        ItemStack itemStack = XMaterial.BEDROCK.parseItem();
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(config.getNgonMessage());
                        itemStack.setItemMeta(itemMeta);

                        actionItem.setItem(itemStack);
                        actionItem.setAction(InventoryClickEvent.class,
                                event -> MessageUtils.sendMessage(event.getWhoClicked().getUniqueId(), config.getNgonMessage())
                        );
                        return true;
                    },
                    (uuid, actionItem) -> {
                        ItemStack itemStack = XMaterial.REDSTONE_BLOCK.parseItem();
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(config.getNgonMessage());
                        itemStack.setItemMeta(itemMeta);

                        actionItem.setItem(itemStack);
                        actionItem.setAction(InventoryClickEvent.class,
                                event -> MessageUtils.sendMessage(event.getWhoClicked().getUniqueId(), config.getNgonMessage())
                        );
                        return true;
                    }
            );
            return multiPositionMask;
        }).collect(Collectors.toList());

        MaskPaginatedMask pageMask = new MaskPaginatedMask() {
            @Override
            public List<Mask> getMasks(UUID uuid) {
                return masks;
            }
        };
        pageMask.setCycle(true);
        add(pageMask);

        nextPageConsumer = pageMask::nextPage;
    }

    public void nextPage(UUID uuid) {
        if (nextPageConsumer != null) nextPageConsumer.accept(uuid);
    }
}
