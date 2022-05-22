package fr.rolan.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		sender.sendMessage("§6§lListe des commandes du serveur.");
		sender.sendMessage("");
		sender.sendMessage("§8§l» §2§lGénéral");
		sender.sendMessage("§e/uhc §8- §7Voir les règles de la partie.");
		sender.sendMessage("§e/helpop §8- §7Posez une question à un membre du Staff.");
		sender.sendMessage("§e/help §8- §7Liste des commandes.");
		sender.sendMessage("§e/arena §8- §7Accéder l'Arena lorsque la partie n'a pas encore commencé.");
		sender.sendMessage("§e/kit §8- §7Faire son kit pour l'Arena.");
		sender.sendMessage("§e/message §8- §7Envoyer un message privé à un joueur.");
		sender.sendMessage("");
		sender.sendMessage("§8§l» §2§lEn Jeu");
		sender.sendMessage("§e/tl §8- §7Envoyer ses coordonnés aux membres de son équipe.");
		sender.sendMessage("§e/ti §8- §7Ouvrir l'inventaire de Team.");
		sender.sendMessage("");
		sender.sendMessage("§8§l» §2§lMode de Jeu");
		sender.sendMessage("§e/king §8- §7Commande liée au mode de Jeu Save The King.");
		sender.sendMessage("§e/dxd §8- §7Commande liée au mode de Jeu DxD UHC.");
		sender.sendMessage("§e/taupe §8- §7Commande liée au mode de Jeu Taupe Gun.");
		sender.sendMessage("§e/claim §8- §7Obtenir son kit de Taupe.");
		sender.sendMessage("§e/reveal §8- §7Révélez votre identité de Taupe aux yeux de tous.");
		sender.sendMessage("§e/sreveal §8- §7Révélez votre identité de SuperTaupe aux yeux de tous.");
		
		return false;
	}

}