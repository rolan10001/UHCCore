package fr.rolan.core.api.jda.request;

import java.awt.Color;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.configuration.file.FileConfiguration;

import fr.rolan.core.APIPlugin;
import fr.rolan.core.api.jda.ConfigState;
import fr.rolan.core.api.jda.JDA;
import fr.rolan.core.api.jda.userdata.Host;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Request extends ListenerAdapter {
	public final JDA bot;
	public final String[] month = new String[] {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
	
	public Request(JDA jda) {
		this.bot = jda;
	}
	
	@Override
	public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
		if(event.getUser().isBot())
			return;
		if(event.getMessageId().equals(bot.requesthostid)) {
			if(bot.getHosts().containsKey(event.getUserIdLong()) && bot.getHost(event.getUserIdLong()).hasHascreatehost())
				return;
			event.getChannel().removeReactionById(event.getMessageIdLong(), "✅", event.getUser()).queue();
			User user = event.getUser();
			PrivateChannel mp = user.openPrivateChannel().complete();
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("Bonjour, je vais vous aider pour la création et gestion de votre host. Vous pouvez annuler à tout moment la configuration grâce à la commande `!annule`"
					+ "\n\nTout d'abord, __ajoutez une date__, pour cela saisissez le jour, le mois, l'année puis l'heure, voici un exemple `03:09:2021:15:15`");
			mp.sendMessage(embed.build()).queue();
			bot.getHosts().put(event.getUserIdLong(), new Host(event.getUser().getIdLong()));
			bot.getHost(event.getUserIdLong()).setState(ConfigState.DATE);
		}else if(event.getChannel().getId().equals("889104773385691147")) {
			try {event.getChannel().removeReactionById(event.getMessageIdLong(), event.getReactionEmote().getEmoji(), event.getUser()).queue();}catch(Exception e) {}
			Host host = null;
			for(Host h : bot.getHosts().values())
				if(h.getIDMessageConfirm().equals(event.getMessageId())) {
					host = h;
					break;
				}
			if(host == null) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Erreur**, impossible de trouver l'host de cette demande, veuillez contacter l'host et lui demander de refaire sa configuration.");
				event.getChannel().sendMessage(embed.build()).queue();
				return;
			}
			if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("\uD83D\uDCDC")) {
				User user = event.getUser();
				PrivateChannel mp = user.openPrivateChannel().complete();
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Host:** "+bot.getJDA().retrieveUserById(host.getId()).complete().getName()
						+ "\n**Slot:** "+host.getSlot()
						+ "\n\n**Message:** "+(host.getMessage() == null ? "L'host n'a pas ajouté de message." : host.getMessage())
						+ "\n\n**Configuration:** "+(!host.hasConfig() ? "L'host n'a pas ajouté de configuration." : "\n\n"
						+ "__**Equipement :**__ \uD83D\uDEE0\uFE0F\n"
						+ "**- "+host.armor+" pièces diamant**\n"
						+ "**- P"+host.diamond_prot+" Diamant / P"+host.iron_prot+" Fer**\n"
						+ "**- T"+host.diamond_sharp+" Diamant / T"+host.iron_sharp+" Fer**\n"
						+ "**- Power "+host.power+"**\n\n"
						+ "__**Détails**__ ⌛\n"
						+ "- **PvP:** "+host.pvp+" minutes\n"
						+ "- **Rôles:** "+host.roles+" minutes\n"
						+ "- **Bordure:** "+host.timer_border+" minutes\n"
						+ "- **Taille Bordure:** "+host.size_border+"+/-\n"
						+ "- **Taille finale Bordure:** "+host.size_final_border+"+/-\n"));
				mp.sendMessage(embed.build()).queue();
			}else if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
				event.getChannel().deleteMessageById(event.getMessageId()).queue();
				host.setHascreatehost(false);
				host.setState(ConfigState.NONE);
				host.setMessage(null);
				User user = bot.getJDA().retrieveUserById(host.getId()).complete();
				PrivateChannel mp = user.openPrivateChannel().complete();
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**L'administration de DxD UHC a refusé votre host.**");
				mp.sendMessage(embed.build()).queue();
			}else if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("✅")) {
				User user = bot.getJDA().retrieveUserById(host.getId()).complete();
				PrivateChannel mp = user.openPrivateChannel().complete();
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**L'administration de DxD UHC a accepté votre host.**");
				mp.sendMessage(embed.build()).queue();
				if(host.getServer().contains("892453924748021860")) {
					embed.clear();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					embed.appendDescription(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getEmbeds().get(0).getDescription());
					bot.getJDA().getTextChannelById("892453924748021860").sendMessage(embed.build()).queue(message -> {
						message.addReaction("✅").queue();
						message.addReaction("❌").queue();
						message.addReaction("\uD83D\uDCDC").queue();
						Host ho = null;
						for(Host h : bot.getHosts().values())
							if(h.getIDMessageConfirm().equals(event.getMessageId())) {
								ho = h;
								break;
							}
						ho.setIDMessageConfirm(message.getId());
					});
					if(host.getServer().contains("889104773385691147")) {
						embed.clear();
						embed.setColor(Color.red);
						embed.setTitle("Host DxD UHC");
						embed.appendDescription((host.getMessage() == null ? "" : host.getMessage()+"\n\n")+
								"**Date/Heure:** "+host.getDate().getDayOfMonth()+" "+month[host.getDate().getMonthValue()-1]+" "+host.getDate().getYear()+
								" à *"+new DecimalFormat("00").format(host.getDate().getHour())+"h"+new DecimalFormat("00").format(host.getDate().getMinute())+"*\n\n" +
								"**Host:** "+bot.getJDA().retrieveUserById(host.getId()).complete().getName() +
								"\n**Serveur/IP:** mc.dxduhc.com" + 
								"\n**Slots:** "+host.getSlot()+" :lock:" +
								"\n**Vocal:** mumble.dxduhc.com **Port:**10007 :loud_sound:"+(!host.hasConfig() ? "\n" : "\n\n"
										+ "__**Equipement :**__ \uD83D\uDEE0\uFE0F\n"
										+ "**- "+host.armor+" pièces diamant**\n"
										+ "**- P"+host.diamond_prot+" Diamant / P"+host.iron_prot+" Fer**\n"
										+ "**- T"+host.diamond_sharp+" Diamant / T"+host.iron_sharp+" Fer**\n"
										+ "**- Power "+host.power+"**\n\n"
										+ "__**Détails**__ ⌛\n"
										+ "- **PvP:** "+host.pvp+" minutes\n"
										+ "- **Rôles:** "+host.roles+" minutes\n"
										+ "- **Bordure:** "+host.timer_border+" minutes\n"
										+ "- **Taille Bordure:** "+host.size_border+"+/-\n"
										+ "- **Taille finale Bordure:** "+host.size_final_border+"+/-\n"));
						embed.appendDescription("\nVeuillez ajouter une réaction afin de pouvoir accéder au salon whitelist et y mettre votre pseudo.");
						bot.getJDA().getTextChannelById("889922216316239973").sendMessage(embed.build()).queue(message -> message.addReaction("✅").queue());
						
						FileConfiguration config = APIPlugin.getInstance().getBotConfig();
						int id = 1;
						if(config.contains("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+"1.dates")) {
							for(String s : config.getConfigurationSection("bot").getKeys(false))
								if(s.equals(bot.getJDA().retrieveUserById(host.getId()).complete().getName()+(id)))
									id++;
						}
						config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".server", host.getServer());
						config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".slot", host.getSlot());
						config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".message", host.getMessage());
						config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".slot", host.getSlot());
						config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".config", host.hasConfig());
						List<Integer> dates = new ArrayList<Integer>(Arrays.asList(host.getDate().getYear(), host.getDate().getMonthValue(), host.getDate().getDayOfMonth(), host.getDate().getHour(), host.getDate().getMinute()));
						config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".dates", dates);
						APIPlugin.getInstance().saveBotConfig();
						StringBuilder builder = new StringBuilder();
						HashMap<Integer, String> h = new HashMap<Integer, String>();
						HashMap<Integer, String> hash = new HashMap<Integer, String>();
						for(String string : config.getConfigurationSection("bot").getKeys(false)) {
							List<Integer> list = config.getIntegerList("bot."+string+".dates");
							LocalDateTime date = LocalDateTime.of(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
							if(!h.containsKey(date.getDayOfMonth()*date.getMonthValue()+date.getYear()))
								h.put(date.getDayOfMonth()*24*60+date.getMonthValue()*30*24*60+date.getYear()*365*24*60, "● *"+translate(date.getDayOfWeek().name())+" "+date.getDayOfMonth()+" "+month[date.getMonthValue()-1]+"* :\n");
							hash.put(date.getDayOfMonth()*24*60+date.getMonthValue()*30*24*60+date.getYear()*365*24*60+date.getHour()*60+date.getMinute(), "- **DxD UHC V2.5** organisé par **"+string.substring(0, string.length()-1)+"** à "+new DecimalFormat("00").format(date.getHour())+"h"+new DecimalFormat("00").format(date.getMinute())+"\n");
						}
						
						List<Integer> integers = new ArrayList<Integer>(h.keySet());
						Collections.sort(integers);
						Collections.reverse(integers);
						
						for(int i : integers) {
							String day = h.get(i);
							if(i != integers.get(0))
								builder.append("\n");
							builder.append(day);
							for(int j : hash.keySet())
								if(j >= i && j < i+1440)
									builder.append(hash.get(j));
						}
						
						bot.getJDA().getTextChannelById("889104470007492629").editMessageById("889934151929712661", 
								"**\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC**\n\n" +
								builder.toString() +
								"\n**\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC**"
								).queue();
					}
				}else {
					embed.clear();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					embed.appendDescription((host.getMessage() == null ? "" : host.getMessage()+"\n\n")+
							"**Date/Heure:** "+host.getDate().getDayOfMonth()+" "+month[host.getDate().getMonthValue()-1]+" "+host.getDate().getYear()+
							" à *"+new DecimalFormat("00").format(host.getDate().getHour())+"h"+new DecimalFormat("00").format(host.getDate().getMinute())+"*\n\n" +
							"**Host:** "+bot.getJDA().retrieveUserById(host.getId()).complete().getName() +
							"\n**Serveur/IP:** mc.dxduhc.com" + 
							"\n**Slots:** "+host.getSlot()+" :lock:" +
							"\n**Vocal:** mumble.dxduhc.com **Port:**10007 :loud_sound:"+(!host.hasConfig() ? "\n" : "\n\n"
									+ "__**Equipement :**__ \uD83D\uDEE0\uFE0F\n"
									+ "**- "+host.armor+" pièces diamant**\n"
									+ "**- P"+host.diamond_prot+" Diamant / P"+host.iron_prot+" Fer**\n"
									+ "**- T"+host.diamond_sharp+" Diamant / T"+host.iron_sharp+" Fer**\n"
									+ "**- Power "+host.power+"**\n\n"
									+ "__**Détails**__ ⌛\n"
									+ "- **PvP:** "+host.pvp+" minutes\n"
									+ "- **Rôles:** "+host.roles+" minutes\n"
									+ "- **Bordure:** "+host.timer_border+" minutes\n"
									+ "- **Taille Bordure:** "+host.size_border+"+/-\n"
									+ "- **Taille finale Bordure:** "+host.size_final_border+"+/-\n"));
					embed.appendDescription("\nVeuillez ajouter une réaction afin de pouvoir accéder au salon whitelist et y mettre votre pseudo.");
					bot.getJDA().getTextChannelById("889922216316239973").sendMessage(embed.build()).queue(message -> message.addReaction("✅").queue());
					
					FileConfiguration config = APIPlugin.getInstance().getBotConfig();
					int id = 1;
					if(config.contains("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+"1.dates")) {
						for(String s : config.getConfigurationSection("bot").getKeys(false))
							if(s.equals(bot.getJDA().retrieveUserById(host.getId()).complete().getName()+(id)))
								id++;
					}
					config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".server", host.getServer());
					config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".slot", host.getSlot());
					config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".message", host.getMessage());
					config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".slot", host.getSlot());
					config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".config", host.hasConfig());
					List<Integer> dates = new ArrayList<Integer>(Arrays.asList(host.getDate().getYear(), host.getDate().getMonthValue(), host.getDate().getDayOfMonth(), host.getDate().getHour(), host.getDate().getMinute()));
					config.set("bot."+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+id+".dates", dates);
					APIPlugin.getInstance().saveBotConfig();
					StringBuilder builder = new StringBuilder();
					HashMap<Integer, String> h = new HashMap<Integer, String>();
					HashMap<Integer, String> hash = new HashMap<Integer, String>();
					for(String string : config.getConfigurationSection("bot").getKeys(false)) {
						List<Integer> list = config.getIntegerList("bot."+string+".dates");
						LocalDateTime date = LocalDateTime.of(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
						if(!h.containsKey(date.getDayOfMonth()*date.getMonthValue()+date.getYear()))
							h.put(date.getDayOfMonth()*24*60+date.getMonthValue()*30*24*60+date.getYear()*365*24*60, "● *"+translate(date.getDayOfWeek().name())+" "+date.getDayOfMonth()+" "+month[date.getMonthValue()-1]+"* :\n");
						hash.put(date.getDayOfMonth()*24*60+date.getMonthValue()*30*24*60+date.getYear()*365*24*60+date.getHour()*60+date.getMinute(), "- **DxD UHC V2.5** organisé par **"+string.substring(0, string.length()-1)+"** à "+new DecimalFormat("00").format(date.getHour())+"h"+new DecimalFormat("00").format(date.getMinute())+"\n");
					}
					
					List<Integer> integers = new ArrayList<Integer>(h.keySet());
					Collections.sort(integers);
					Collections.reverse(integers);
					
					for(int i : integers) {
						String day = h.get(i);
						if(i != integers.get(0))
							builder.append("\n");
						builder.append(day);
						for(int j : hash.keySet())
							if(j >= i && j < i+1440)
								builder.append(hash.get(j));
					}
					
					bot.getJDA().getTextChannelById("889104470007492629").editMessageById("889934151929712661", 
							"**\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC**\n\n" +
							builder.toString() +
							"\n**\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC**"
							).queue();
					host.setHascreatehost(false);
					host.setState(ConfigState.NONE);
					host.setMessage(null);
				}
				event.getChannel().deleteMessageById(event.getMessageId()).queue();
			}
		}else if(event.getChannel().getId().equals("889922216316239973")) {
			try {event.getChannel().removeReactionById(event.getMessageIdLong(), event.getReactionEmote().getEmoji(), event.getUser()).queue();}catch(Exception e) {}
			if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("✅")) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getEmbeds().get(0).getDescription());
				bot.getJDA().getTextChannelById("817484133767970828").sendMessage("@everyone").queue();
				bot.getJDA().getTextChannelById("817484133767970828").sendMessage(embed.build()).queue(message -> {
					message.addReaction("✅").queue();
					message.addReaction("\uD83E\uDD14").queue();
					message.addReaction("❌").queue();
					});
				event.getChannel().deleteMessageById(event.getMessageId()).queue();
			}
		}else if(event.getChannel().getId().equals("892453924748021860")) {
			try {event.getChannel().removeReactionById(event.getMessageIdLong(), event.getReactionEmote().getEmoji(), event.getUser()).queue();}catch(Exception e) {}
			Host host = null;
			for(Host h : bot.getHosts().values())
				if(h.getIDMessageConfirm().equals(event.getMessageId())) {
					host = h;
					break;
				}
			if(host == null) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Erreur**, impossible de trouver l'host de cette demande, veuillez contacter l'host et lui demander de refaire sa configuration.");
				event.getChannel().sendMessage(embed.build()).queue();
				return;
			}
			if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("\uD83D\uDCDC")) {
				User user = event.getUser();
				PrivateChannel mp = user.openPrivateChannel().complete();
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Host:** "+bot.getJDA().retrieveUserById(host.getId()).complete().getName()
						+ "\n**Slot:** "+host.getSlot()
						+ "\n\n**Message:** "+(host.getMessage() == null ? "L'host n'a pas ajouté de message." : host.getMessage())
						+ "\n\n**Configuration:** "+(!host.hasConfig() ? "L'host n'a pas ajouté de configuration." : "\n\n"
						+ "__**Equipement :**__ \uD83D\uDEE0\uFE0F\n"
						+ "**- "+host.armor+" pièces diamant**\n"
						+ "**- P"+host.diamond_prot+" Diamant / P"+host.iron_prot+" Fer**\n"
						+ "**- T"+host.diamond_sharp+" Diamant / T"+host.iron_sharp+" Fer**\n"
						+ "**- Power "+host.power+"**\n\n"
						+ "__**Détails**__ ⌛\n"
						+ "- **PvP:** "+host.pvp+" minutes\n"
						+ "- **Rôles:** "+host.roles+" minutes\n"
						+ "- **Bordure:** "+host.timer_border+" minutes\n"
						+ "- **Taille Bordure:** "+host.size_border+"+/-\n"
						+ "- **Taille finale Bordure:** "+host.size_final_border+"+/-\n"));
				mp.sendMessage(embed.build()).queue();
			}else if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
				event.getChannel().deleteMessageById(event.getMessageId()).queue();
				host.setHascreatehost(false);
				host.setState(ConfigState.NONE);
				host.setMessage(null);
				User user = bot.getJDA().retrieveUserById(host.getId()).complete();
				PrivateChannel mp = user.openPrivateChannel().complete();
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**L'administration de Phyloria a refusé votre host.**");
				mp.sendMessage(embed.build()).queue();
			}else if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("✅")) {
				event.getChannel().deleteMessageById(event.getMessageId()).queue();
				User user = bot.getJDA().retrieveUserById(host.getId()).complete();
				PrivateChannel mp = user.openPrivateChannel().complete();
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**L'administration de Phyloria a accepté votre host.**");
				mp.sendMessage(embed.build()).queue();
				mp = event.getUser().openPrivateChannel().complete();
				embed.clear();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription((host.getMessage() == null ? "" : host.getMessage()+"\n\n")+
						"**Date/Heure:** "+host.getDate().getDayOfMonth()+" "+month[host.getDate().getMonthValue()-1]+" "+host.getDate().getYear()+
						" à *"+new DecimalFormat("00").format(host.getDate().getHour())+"h"+new DecimalFormat("00").format(host.getDate().getMinute())+"*\n\n" +
						"**Host:** "+bot.getJDA().retrieveUserById(host.getId()).complete().getName() +
						"\n**Serveur/IP:** mc.dxduhc.com" + 
						"\n**Slots:** "+host.getSlot()+" :lock:" +
						"\n**Vocal:** mumble.dxduhc.com **Port:**10007 :loud_sound:"+(!host.hasConfig() ? "\n" : "\n\n"
								+ "__**Equipement :**__ \uD83D\uDEE0\uFE0F\n"
								+ "**- "+host.armor+" pièces diamant**\n"
								+ "**- P"+host.diamond_prot+" Diamant / P"+host.iron_prot+" Fer**\n"
								+ "**- T"+host.diamond_sharp+" Diamant / T"+host.iron_sharp+" Fer**\n"
								+ "**- Power "+host.power+"**\n\n"
								+ "__**Détails**__ ⌛\n"
								+ "- **PvP:** "+host.pvp+" minutes\n"
								+ "- **Rôles:** "+host.roles+" minutes\n"
								+ "- **Bordure:** "+host.timer_border+" minutes\n"
								+ "- **Taille Bordure:** "+host.size_border+"+/-\n"
								+ "- **Taille finale Bordure:** "+host.size_final_border+"+/-\n"));
				embed.appendDescription("\nVeuillez ajouter une réaction afin de pouvoir accéder au salon whitelist et y mettre votre pseudo.");
				mp.sendMessage(embed.build()).queue();
				host.setHascreatehost(false);
				host.setState(ConfigState.NONE);
				host.setMessage(null);
			}
		}
	}
	
	@Override
	public void onPrivateMessageReactionAdd(@Nonnull PrivateMessageReactionAddEvent event) {
		if(event.getUser() != null && event.getUser().isBot())
			return;
		if(bot.getHost(event.getChannel().getUser().getIdLong()) == null)
			return;
		Host host = bot.getHost(event.getChannel().getUser().getIdLong());
		event.getChannel().removeReactionById(event.getMessageId(), event.getReactionEmote().getEmoji()).queue();
		if(host.getState().equals(ConfigState.SERVER)) {
			if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
				host.setState(ConfigState.DATE);
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("__Choisissez une nouvelle date__, pour cela saisissez le jour, le mois, l'année puis l'heure, voici un exemple `03:09:2021:15:15`");
				event.getChannel().sendMessage(embed.build()).queue();
			}else if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("1\uFE0F\u20E3")) {
				host.getServer().add("889104773385691147");
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("Vous avez choisi d'Host sur le serveur **DxD UHC Host**, cliquez sur la réaction ❌ ci-dessous, si vous avez choisi la mauvaise option.\n\n"
						+ "__Combien de slot voulez-vous ajouter ?__");
				event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction("❌").queue());
				host.setState(ConfigState.SLOT);
			}else if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("2\uFE0F\u20E3")) {
				host.getServer().add("892453924748021860");
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("Vous avez choisi d'Host sur le serveur **Phyloria**, cliquez sur la réaction ❌ ci-dessous, si vous avez choisi la mauvaise option.\n\n"
						+ "__Combien de slot voulez-vous ajouter ?__");
				event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction("❌").queue());
				host.setState(ConfigState.SLOT);
			}else if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("3\uFE0F\u20E3")) {
				host.getServer().add("889104773385691147");
				host.getServer().add("892453924748021860");
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("Vous avez choisi d'Host sur les serveurs **DxD UHC Host & Phyloria**, cliquez sur la réaction ❌ ci-dessous, si vous avez choisi la mauvaise option.\n\n"
						+ "__Combien de slot voulez-vous ajouter ?__");
				event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction("❌").queue());
				host.setState(ConfigState.SLOT);
			}
		}else if(host.getState().equals(ConfigState.SLOT)) {
			host.setState(ConfigState.SERVER);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("Choisissez le ou les serveurs sur lequels vous voulez host :\n"+
					":one: » **DxD UHC Host**\n"+
					":two: » **Phyloria**\n"+
					":three: » **DxD UHC Host & Phyloria**");
			event.getChannel().sendMessage(embed.build()).queue(message -> {
				message.addReaction("❌").queue();
				message.addReaction("1\uFE0F\u20E3").queue();
				message.addReaction("2\uFE0F\u20E3").queue();
				message.addReaction("3\uFE0F\u20E3").queue();
				});
		}else if(host.getState().equals(ConfigState.MESSAGE)) {
			if(!event.getMessageId().equals(event.getChannel().getLatestMessageId()) && event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
				host.setState(ConfigState.SLOT);
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("Combien de slot voulez-vous ajouter ?");
				event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction("❌").queue());
			}else if(event.getMessageId().equals(event.getChannel().getLatestMessageId())) {
				if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					embed.appendDescription("Vous avez choisi de ne pas ajouter de message.\n\n"
							+ "__Voulez-vous ajouter une configuration de partie ?__\n"
							+ "✅ » **Oui**\n"
							+ "❌ » **Non**");
					event.getChannel().sendMessage(embed.build()).queue(message -> {
						message.addReaction("✅").queue();
						message.addReaction("❌").queue();
					});
					host.setState(ConfigState.CONFIG);
				}else if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("✅")) {
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					embed.appendDescription("Vous avez choisi d'ajouter un message, écrivez ci-dessous votre message.");
					event.getChannel().sendMessage(embed.build()).queue();
				}
			}
		}else if(host.getState().equals(ConfigState.CONFIG)) {
			if(!event.getMessageId().equals(event.getChannel().getLatestMessageId()) && event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
				host.setState(ConfigState.MESSAGE);
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("Ecrivez ci-dessous votre message.");
				event.getChannel().sendMessage(embed.build()).queue();
			}else if(event.getMessageId().equals(event.getChannel().getLatestMessageId())) {
				if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
					host.setConfig(false);
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					if(host.getServer().contains("892453924748021860")) {
						embed.appendDescription("**Votre demande d'Host a été envoyé**, l'administration de DxD UHC va dans un premier temps valider votre host, il sera ensuite valider par l'administration de Phyloria. Vous recevrez un message de suivi à chaque action effectué.");
					}else {
						embed.appendDescription("**Votre demande d'Host a été envoyé**, vous recevrez un message lorsque les administrateurs auront répondu.");
					}
					event.getChannel().sendMessage(embed.build()).queue();
					host.setState(ConfigState.NONE);
					embed.clear();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					embed.appendDescription("● Host: **"+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+"**\n"
							+ "● Mode de Jeu: **DxD UHC**\n"
							+ "● Date: **"+host.getDate().getDayOfMonth()+" "+month[host.getDate().getMonthValue()-1]+" "+host.getDate().getYear()+"**\n"
									+ "● Heure: **"+new DecimalFormat("00").format(host.getDate().getHour())+"h"+new DecimalFormat("00").format(host.getDate().getMinute())+"**\n\n"
											+ "Choisissez parmi les différents réactions: \n"
											+ "✅ » **Host accepté**\n"
											+ "❌ » **Host refusé**\n"
											+ "\uD83D\uDCDC » **Voir la configuration**"
											+ "");
					bot.getJDA().getTextChannelById("889104773385691147").sendMessage(embed.build()).queue(message -> {
						message.addReaction("✅").queue();
						message.addReaction("❌").queue();
						message.addReaction("\uD83D\uDCDC").queue();
						host.setIDMessageConfirm(message.getId());
					});
				}else if(event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("✅")) {
					host.setConfig(true);
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					embed.appendDescription("Nous allons passer à la configuration de l'Host.\nTout d'abord, nous allons ajouter des limites d'équipements. Comme pour la date, écrivez un message contenant :"
							+ "\n- **le nombre de pièce en diamant**"
							+ "\n- **Limite de protection pour le diamant**"
							+ "\n- **Limite de protection pour le fer**"
							+ "\n- **Limite de sharpness pour le diamant**"
							+ "\n- **Limite de sharpness pour le fer**"
							+ "\n- **Limite de power pour l'arc**"
							+ "\n\nVoici un exemple : `2:2:3:3:4:3`");
					event.getChannel().sendMessage(embed.build()).queue();
					host.setState(ConfigState.LIMITES);
				}
			}
		}else if(host.getState().equals(ConfigState.PVP) && event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
			host.setState(ConfigState.LIMITES);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("__Choisissez de nouvelles limites d'équipements__, comme pour la date, écrivez un message contenant :"
					+ "\n- **le nombre de pièce en diamant**"
					+ "\n- **Limite de protection pour le diamant**"
					+ "\n- **Limite de protection pour le fer**"
					+ "\n- **Limite de sharpness pour le diamant**"
					+ "\n- **Limite de sharpness pour le fer**"
					+ "\n- **Limite de power pour l'arc**"
					+ "\n\nVoici un exemple : `2:2:3:3:4:3`");
			event.getChannel().sendMessage(embed.build()).queue();
		}else if(host.getState().equals(ConfigState.ROLES) && event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
			host.setState(ConfigState.PVP);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("__Choisissez un nouveau moment pour l'activation du PvP__, écrivez un nombre de minutes.");
			event.getChannel().sendMessage(embed.build()).queue();
		}else if(host.getState().equals(ConfigState.BORDER) && event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("❌")) {
			host.setState(ConfigState.ROLES);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("__Choisissez un nouveau moment pour d'annonce des rôles__, écrivez un nombre de minutes.");
			event.getChannel().sendMessage(embed.build()).queue();
		}else if(host.getState().equals(ConfigState.BORDER) && event.getReactionEmote().isEmoji() && event.getReactionEmote().getEmoji().equals("✅")) {
			host.setState(ConfigState.NONE);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			if(host.getServer().contains("892453924748021860")) {
				embed.appendDescription("**Votre demande d'Host a été envoyé**, l'administration de DxD UHC va dans un premier temps valider votre host, il sera ensuite valider par l'administration de Phyloria. Vous recevrez un message de suivi à chaque action effectué.");
			}else {
				embed.appendDescription("**Votre demande d'Host a été envoyé**, vous recevrez un message lorsque les administrateurs auront répondu.");
			}
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("● Host: **"+bot.getJDA().retrieveUserById(host.getId()).complete().getName()+"**\n"
					+ "● Mode de Jeu: **DxD UHC**\n"
					+ "● Date: **"+host.getDate().getDayOfMonth()+" "+month[host.getDate().getMonthValue()-1]+" "+host.getDate().getYear()+"**\n"
							+ "● Heure: **"+new DecimalFormat("00").format(host.getDate().getHour())+"h"+new DecimalFormat("00").format(host.getDate().getMinute())+"**\n\n"
									+ "Choisissez parmi les différents réactions: \n"
									+ "✅ » **Host accepté**\n"
									+ "❌ » **Host refusé**\n"
									+ "\uD83D\uDCDC » **Voir la configuration**");
			bot.getJDA().getTextChannelById("889104773385691147").sendMessage(embed.build()).queue(message -> {
				message.addReaction("✅").queue();
				message.addReaction("❌").queue();
				message.addReaction("\uD83D\uDCDC").queue();
				host.setIDMessageConfirm(message.getId());
			});
		}
	}
	
	@Override
	public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
		if(event.getAuthor().isBot())
			return;
		if(bot.getHost(event.getAuthor().getIdLong()) == null)
			return;
		Host host = bot.getHost(event.getAuthor().getIdLong());
		if(host.getState().equals(ConfigState.NONE))
			return;
		if(event.getMessage().getContentRaw().startsWith("!annule")) {
			host.setHascreatehost(false);
			host.setState(ConfigState.NONE);
			host.setMessage(null);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("**Votre host a été annulé avec succès**");
			event.getChannel().sendMessage(embed.build()).queue();
		}else if(host.getState().equals(ConfigState.DATE)) {
			String[] args = event.getMessage().getContentRaw().split(":");
			List<Integer> integers = new ArrayList<Integer>();
			for(String s : args)
				try {
					integers.add(Integer.valueOf(s));
				}catch(NumberFormatException e) {
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					embed.appendDescription("**Une erreur est survenue**, veuillez vérifier que votre message est formulé comme dans l'exemple ci-dessus.");
					event.getChannel().sendMessage(embed.build()).queue();
					return;
				}
			if(integers.size() < 5) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Une erreur est survenue**, veuillez vérifier que votre message est formulé comme dans l'exemple ci-dessus.");
				event.getChannel().sendMessage(embed.build()).queue();
				return;
			}
			DecimalFormat format = new DecimalFormat("00");
			host.setDate(LocalDateTime.of(integers.get(2), integers.get(1), integers.get(0), integers.get(3), integers.get(4)));
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("La date de votre host a été enregistré pour le **"+integers.get(0)+" "+month[integers.get(1)-1]+" "+integers.get(2)+", à "+format.format(integers.get(3))+"h"+format.format(integers.get(4))+
					"**\nCliquez sur la réaction ❌ ci-dessous, si la date est incorrect.\n\n"+
					"Choisissez le ou les serveurs sur lequels vous voulez host :\n"+
					":one: » **DxD UHC Host**\n"+
					":two: » **Phyloria**\n"+
					":three: » **DxD UHC Host & Phyloria**");
			event.getChannel().sendMessage(embed.build()).queue(message -> {
				message.addReaction("❌").queue();
				message.addReaction("1\uFE0F\u20E3").queue();
				message.addReaction("2\uFE0F\u20E3").queue();
				message.addReaction("3\uFE0F\u20E3").queue();
				});
			host.setState(ConfigState.SERVER);
		}else if(host.getState().equals(ConfigState.SLOT)) {
			int slot = 0;
			try {
				slot = Integer.valueOf(event.getMessage().getContentRaw());
			}catch(NumberFormatException e) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Une erreur est survenue**, veuillez saisir un nombre de slot.");
				event.getChannel().sendMessage(embed.build()).queue();
				return;
			}
			host.setSlot(slot);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("Vous avez configuré le nombre de slot maximum à **"+slot+" places**."+
					"\nCliquez sur la réaction ❌ ci-dessous, si le nombre de slot est incorrect.");
			event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction("❌").queue());
			embed.clear();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("__Voulez vous ajouter un message qui apparaîtra dans votre message d'Host ?__\n"
					+ "✅ » **Oui**\n"
					+ "❌ » **Non**");
			event.getChannel().sendMessage(embed.build()).queue(message -> {
				message.addReaction("✅").queue();
				message.addReaction("❌").queue();
			});
			host.setState(ConfigState.MESSAGE);
		}else if(host.getState().equals(ConfigState.MESSAGE)) {
			host.setMessage(event.getMessage().getContentRaw());
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("Votre message a bien été enregistré."+
					"\nCliquez sur la réaction ❌ ci-dessous, si vous vous voulez modifier votre message.");
			event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction("❌").queue());
			embed.clear();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("__Voulez-vous ajouter une configuration de partie ?__\n"
					+ "✅ » **Oui**\n"
					+ "❌ » **Non**");
			event.getChannel().sendMessage(embed.build()).queue(message -> {
				message.addReaction("✅").queue();
				message.addReaction("❌").queue();
			});
			host.setState(ConfigState.CONFIG);
		}else if(host.getState().equals(ConfigState.LIMITES)) {
			String[] args = event.getMessage().getContentRaw().split(":");
			List<Integer> integers = new ArrayList<Integer>();
			for(String s : args)
				try {
					integers.add(Integer.valueOf(s));
				}catch(NumberFormatException e) {
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					embed.appendDescription("**Une erreur est survenue**, veuillez vérifier que votre message est formulé comme dans l'exemple ci-dessus.");
					event.getChannel().sendMessage(embed.build()).queue();
					return;
				}
			if(integers.size() < 6) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Une erreur est survenue**, veuillez vérifier que votre message est formulé comme dans l'exemple ci-dessus.");
				event.getChannel().sendMessage(embed.build()).queue();
				return;
			}
			host.armor = integers.get(0);
			host.diamond_prot = integers.get(1);
			host.iron_prot = integers.get(2);
			host.diamond_sharp = integers.get(3);
			host.iron_sharp = integers.get(4);
			host.power = integers.get(5);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("Les limites d'équipements sont les suivantes :\n"
					+ "\n- **"+host.armor+" pièces diamant**"
					+ "\n- **P"+host.diamond_prot+" Diamant / P"+host.iron_prot+" Fer**"
					+ "\n- **T"+host.diamond_sharp+" Diamant / T"+host.iron_sharp+" Fer**"
					+ "\n- **Power "+host.power+"**"
							+ "\n\nCliquez sur la réaction ❌ ci-dessous, pour modifier les limites.\n"
							+ "Ajoutez désormais le moment d'activation du PvP, écrivez un nombre de minutes.");
			event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction("❌").queue());
			host.setState(ConfigState.PVP);
		}else if(host.getState().equals(ConfigState.PVP)) {
			try {
				host.pvp = Integer.valueOf(event.getMessage().getContentRaw());
			}catch(NumberFormatException e) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Une erreur est survenue**, veuillez vérifier que votre message est formulé comme dans l'exemple ci-dessus.");
				event.getChannel().sendMessage(embed.build()).queue();
				return;
			}
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("Le PvP sera activé à **"+host.pvp+" minutes**"
					+ "\n\nCliquez sur la réaction ❌ ci-dessous, pour modifier cette configuration.\n"
					+ "Ajoutez désormais le moment d'annonce des rôles, écrivez un nombre de minutes.");
			event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction("❌").queue());
			host.setState(ConfigState.ROLES);
		}else if(host.getState().equals(ConfigState.ROLES)) {
			try {
				host.roles = Integer.valueOf(event.getMessage().getContentRaw());
			}catch(NumberFormatException e) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Une erreur est survenue**, veuillez vérifier que votre message est formulé comme dans l'exemple ci-dessus.");
				event.getChannel().sendMessage(embed.build()).queue();
				return;
			}
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("Les rôles seront annoncés à **"+host.roles+" minutes**"
					+ "\n\nCliquez sur la réaction ❌ ci-dessous, pour modifier cette configuration.\n"
					+ "Ajoutez désormais les configurations de la bordure en précisant un nombre séparé de ':' dans l'ordre suivant: le temps d'activation de la bordure, sa taille, sa taille finale.\n"
					+ "Voici un exemple : `90:1500:350`");
			event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction("❌").queue());
			host.setState(ConfigState.BORDER);
		}else if(host.getState().equals(ConfigState.BORDER)) {
			String[] args = event.getMessage().getContentRaw().split(":");
			List<Integer> integers = new ArrayList<Integer>();
			for(String s : args)
				try {
					integers.add(Integer.valueOf(s));
				}catch(NumberFormatException e) {
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(Color.red);
					embed.setTitle("Host DxD UHC");
					embed.appendDescription("**Une erreur est survenue**, veuillez vérifier que votre message est formulé comme dans l'exemple ci-dessus.");
					event.getChannel().sendMessage(embed.build()).queue();
					return;
				}
			if(integers.size() < 3) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.red);
				embed.setTitle("Host DxD UHC");
				embed.appendDescription("**Une erreur est survenue**, veuillez vérifier que votre message est formulé comme dans l'exemple ci-dessus.");
				event.getChannel().sendMessage(embed.build()).queue();
				return;
			}
			host.timer_border = integers.get(0);
			host.size_border = integers.get(1);
			host.size_final_border = integers.get(2);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.red);
			embed.setTitle("Host DxD UHC");
			embed.appendDescription("La bordure sera en mouvement à **"+host.timer_border+" minutes**, elle débutera en **"+host.size_border+"+/-** et s'arrêtera en **"+host.size_final_border+"+/-**."
					+ "\n\nCliquez sur la réaction ❌ ci-dessous, pour modifier cette configuration.\n"
					+ "Cliquez sur la réaction ✅ pour confirmer votre host.");
			event.getChannel().sendMessage(embed.build()).queue(message -> {
				message.addReaction("❌").queue();
				message.addReaction("✅").queue();
			});
		}
	}
	
	public String translate(String string) {
		if(string.equalsIgnoreCase("Monday")) {
			return "Lundi";
		}else if(string.equalsIgnoreCase("Tuesday")) {
			return "Mardi";
		}else if(string.equalsIgnoreCase("Wednesday")) {
			return "Mercredi";
		}else if(string.equalsIgnoreCase("Thusday")) {
			return "Jeudi";
		}else if(string.equalsIgnoreCase("Friday")) {
			return "Vendredi";
		}else if(string.equalsIgnoreCase("Saturday")) {
			return "Samedi";
		}else if(string.equalsIgnoreCase("Sunday")) {
			return "Dimanche";
		}
		return "";
	}
}
