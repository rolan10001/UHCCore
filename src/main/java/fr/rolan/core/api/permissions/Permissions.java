package fr.rolan.core.api.permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import fr.rolan.api.permissions.IPermissions;
import fr.rolan.core.APIPlugin;

public class Permissions implements IPermissions {
	private final List<UUID> admins = new ArrayList<UUID>(Arrays.asList(UUID.fromString("bc523ba9-4c37-41fb-a52e-8a3f855393e8"), UUID.fromString("6bf623db-b58a-4817-a129-4abec413fe0f"), UUID.fromString("d910e120-c687-4de9-995c-a9f51239748b")));
	private final List<UUID> staffs = new ArrayList<UUID>(Arrays.asList(UUID.fromString("8279cf96-1d7f-4598-86e2-50dd53d354bb"), UUID.fromString("e5ca611c-a0df-4e1c-b8cb-e0bffca513df"), UUID.fromString("e07ddd39-7437-4b23-acae-a23a03461670"), UUID.fromString("aa5e4fc9-eab1-4c08-aba2-7a42d6ec9f83"), UUID.fromString("ee740ee0-a4cb-4a7a-8409-bf097f307e74")));
	
	private final HashMap<UUID, PermissionAttachment> permissions = new HashMap<UUID, PermissionAttachment>();
	
	@Override
	public void updatePermissionsForPlayer(Player player) {
		if(admins.contains(player.getUniqueId())) {
			player.setOp(true);
			player.setPlayerListName("§c§lADMIN ▏ §"+Integer.toHexString(APIPlugin.getInstance().getAPI().getUser(player).getTeam().getColor())+player.getName());
		}else if(staffs.contains(player.getUniqueId())) {
			player.setOp(true);
			player.setPlayerListName("§a§lSTAFF ▏ §"+Integer.toHexString(APIPlugin.getInstance().getAPI().getUser(player).getTeam().getColor())+player.getName());
		}else if(APIPlugin.getInstance().getAPI().s.HOST != null && APIPlugin.getInstance().getAPI().s.HOST.equals(player.getUniqueId())) {
			player.setPlayerListName("§e§lHOST ▏ §"+Integer.toHexString(APIPlugin.getInstance().getAPI().getUser(player).getTeam().getColor())+player.getName());
			PermissionAttachment attachment = player.addAttachment(APIPlugin.getInstance());
			if(!permissions.containsKey(player.getUniqueId())) {permissions.put(player.getUniqueId(), attachment);}else {permissions.replace(player.getUniqueId(), attachment);}
			PermissionAttachment perm = permissions.get(player.getUniqueId());
			perm.setPermission("bukkit.command.help", false);
			perm.setPermission("bukkit.command.plugins", false);
			perm.setPermission("bukkit.command.version", false);
			perm.setPermission("minecraft.command.me", false);
			perm.setPermission("minecraft.command.msg", false);
			perm.setPermission("minecraft.command.help", false);
			perm.setPermission("minecraft.command.tp", true);
			perm.setPermission("minecraft.command.give", true);
			perm.setPermission("minecraft.command.effect", true);
			perm.setPermission("minecraft.command.ban", true);
			perm.setPermission("minecraft.command.enchant", true);
			perm.setPermission("minecraft.command.kill", true);
			perm.setPermission("minecraft.command.pardon", true);
			perm.setPermission("minecraft.command.whitelist", true);
			perm.setPermission("minecraft.command.kick", true);
			perm.setPermission("host.use", true);
			perm.setPermission("gamemode.use", true);
			perm.setPermission("move.use", true);
		}else if(APIPlugin.getInstance().getAPI().s.COHOST.contains(player.getUniqueId())) {
			player.setPlayerListName("§e§lCO-HOST ▏ §"+Integer.toHexString(APIPlugin.getInstance().getAPI().getUser(player).getTeam().getColor())+player.getName());
			PermissionAttachment attachment = player.addAttachment(APIPlugin.getInstance());
			if(!permissions.containsKey(player.getUniqueId())) {permissions.put(player.getUniqueId(), attachment);}else {permissions.replace(player.getUniqueId(), attachment);}
			PermissionAttachment perm = permissions.get(player.getUniqueId());
			perm.setPermission("bukkit.command.help", false);
			perm.setPermission("bukkit.command.plugins", false);
			perm.setPermission("bukkit.command.version", false);
			perm.setPermission("minecraft.command.me", false);
			perm.setPermission("minecraft.command.msg", false);
			perm.setPermission("minecraft.command.help", false);
			perm.setPermission("minecraft.command.tp", true);
			perm.setPermission("minecraft.command.give", true);
			perm.setPermission("minecraft.command.effect", true);
			perm.setPermission("minecraft.command.ban", true);
			perm.setPermission("minecraft.command.enchant", true);
			perm.setPermission("minecraft.command.kill", true);
			perm.setPermission("minecraft.command.pardon", true);
			perm.setPermission("minecraft.command.whitelist", true);
			perm.setPermission("minecraft.command.kick", true);
			perm.setPermission("host.use", true);
			perm.setPermission("gamemode.use", true);
			perm.setPermission("move.use", true);
		}else {
			player.setPlayerListName("§"+Integer.toHexString(APIPlugin.getInstance().getAPI().getUser(player).getTeam().getColor())+player.getName());
			PermissionAttachment attachment = player.addAttachment(APIPlugin.getInstance());
			if(!permissions.containsKey(player.getUniqueId())) {permissions.put(player.getUniqueId(), attachment);}else {permissions.replace(player.getUniqueId(), attachment);}
			PermissionAttachment perm = permissions.get(player.getUniqueId());
			perm.setPermission("bukkit.command.help", false);
			perm.setPermission("bukkit.command.plugins", false);
			perm.setPermission("bukkit.command.version", false);
			perm.setPermission("minecraft.command.me", false);
			perm.setPermission("minecraft.command.msg", false);
			perm.setPermission("minecraft.command.help", false);
			perm.setPermission("minecraft.command.tp", false);
			perm.setPermission("minecraft.command.give", false);
			perm.setPermission("minecraft.command.effect", false);
			perm.setPermission("minecraft.command.ban", false);
			perm.setPermission("minecraft.command.enchant", false);
			perm.setPermission("minecraft.command.kill", false);
			perm.setPermission("minecraft.command.pardon", false);
			perm.setPermission("minecraft.command.whitelist", false);
			perm.setPermission("minecraft.command.kick", false);
			perm.setPermission("host.use", false);
			perm.setPermission("gamemode.use", false);
			perm.setPermission("move.use", false);
		}
	}

	@Override
	public boolean isAdmin(UUID uuid) {
		return admins.contains(uuid);
	}

	@Override
	public boolean isStaff(UUID uuid) {
		return staffs.contains(uuid);
	}
}
