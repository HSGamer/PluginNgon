package me.hsgamer.pluginngon;

import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import io.github.projectunified.minelib.plugin.base.BasePlugin;
import io.github.projectunified.minelib.plugin.base.Loadable;
import io.github.projectunified.minelib.scheduler.async.AsyncScheduler;
import io.github.projectunified.minelib.scheduler.common.task.Task;
import me.hsgamer.hscore.bukkit.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class NgonRunnable implements Runnable, Loadable {
    private final BasePlugin plugin;
    private final String[] messages = {
            ColorUtils.colorize("&6Plugin"),
            ColorUtils.colorize("&cNgon")
    };
    private int index = 0;
    private Task task;

    public NgonRunnable(BasePlugin plugin) {
        this.plugin = plugin;
    }

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

    @Override
    public void enable() {
        task = AsyncScheduler.get(plugin).runTimer(this, 0, 20);
    }

    @Override
    public void disable() {
        task.cancel();
    }
}
