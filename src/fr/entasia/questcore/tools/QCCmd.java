package fr.entasia.questcore.tools;

import fr.entasia.questcore.utils.QuestUtils;
import fr.entasia.questcore.utils.enums.Quests;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class QCCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender.hasPermission("skyblock.questcore")){
            sender.sendMessage("Tu t'attendais à avoir quoi ici ?");
        }else sender.sendMessage("§cTu n'as pas accès à cette commande !");
        return true;
    }
}
