package me.hsgamer.pluginngon;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Collections;

public class NgonCommand extends Command {
    private static final Permission NGON_PERMISSION = new Permission("plugin.ngon", PermissionDefault.OP);

    static {
        Bukkit.getPluginManager().addPermission(NGON_PERMISSION);
    }

    private final PluginNgon pluginNgon;

    public NgonCommand(PluginNgon pluginNgon) {
        super("pluginngon", "Plugin ngon", "/pluginngon", Collections.singletonList("ngon"));
        this.pluginNgon = pluginNgon;
        setPermission(NGON_PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermissionSilent(sender) || !(sender instanceof Player)) {
            MessageUtils.sendMessage(sender, pluginNgon.get(NgonConfig.class).getNgonMessage());
            return false;
        }
        pluginNgon.get(NgonInv.class).createDisplay(((Player) sender).getUniqueId()).open();
        return true;
    }
}
