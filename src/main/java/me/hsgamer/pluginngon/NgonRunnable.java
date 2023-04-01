package me.hsgamer.pluginngon;

import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import me.hsgamer.hscore.bukkit.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class NgonRunnable implements Runnable {
    private final String[] messages = {
            ColorUtils.colorize("&6Plugin"),
            ColorUtils.colorize("&cNgon")
    };
    private int index = 0;

    @Override
    public void run() {
        String message = messages[index];
        index = (index + 1) % messages.length;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Titles.sendTitle(player, 0, 20, 0, message, Integer.toString(ThreadLocalRandom.current().nextInt(-10000000, 10000001)));
            ActionBar.sendActionBar(player, message);
            XSound.BLOCK_NOTE_BLOCK_PLING.play(player);
        }
    }
}
