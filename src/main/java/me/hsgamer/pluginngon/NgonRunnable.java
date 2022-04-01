package me.hsgamer.pluginngon;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class NgonRunnable extends BukkitRunnable {
    private final String[] messages = {
            MessageUtils.colorize("&6Plugin"),
            MessageUtils.colorize("&cNgon")
    };
    private int index = 0;

    @Override
    public void run() {
        String message = messages[index];
        index = (index + 1) % messages.length;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Titles.sendTitle(player, 0, 20, 0, message, Integer.toString(ThreadLocalRandom.current().nextInt(-10000000, 10000001)));
            ActionBar.sendActionBar(player, message);
        }
    }
}
