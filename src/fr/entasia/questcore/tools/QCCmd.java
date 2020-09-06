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
            ItemStack item = new ItemStack(Material.STONE);
            QuestUtils.markQItem(item, Quests.BASE_SECRETE, 1);
            int a = QuestUtils.getQItemID(item, Quests.BASE_SECRETE);
            System.out.println(a);
            sender.sendMessage("Tu t'attendais à avoir quoi ici ?");
        }else sender.sendMessage("§cTu n'as pas accès à cette commande !");
        return true;
    }
}
