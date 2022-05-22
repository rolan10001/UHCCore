package fr.rolan.core.commands.host.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.rolan.api.commands.Commands;

public class HelpCommand implements Commands {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		sender.sendMessage("§6§lListe des commandes de Host.");
		sender.sendMessage("§e/h config §8-§7Ouvrir le menu de configuration.");
		sender.sendMessage("§e/h heal §8- §7Heal tous les joueurs de la partie.");
		sender.sendMessage("§e/h say §8- §7Faire un message général.");
		sender.sendMessage("§e/h unbreakble §8- §7Rendre l'item dans sa main incassable.");
		sender.sendMessage("§e/h kill §8- §7Retirer un joueur de la partie qu'il soit connecté ou non.");
		sender.sendMessage("§e/h group §8- §7Fixer une nouvelle limite de groupe.");
		sender.sendMessage("§e/h tp §8- §7Téléporter aléatoirement un joueur.");
		
		return false;
	}

}
