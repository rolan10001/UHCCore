package fr.rolan.core.scoreboard;

import static fr.rolan.api.game.GameSettings.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.rolan.api.UHCAPI;
import fr.rolan.api.player.IUser;
import fr.rolan.core.APIPlugin;
import fr.rolan.tools.scoreboard.ObjectiveSign;

public class PersonalScoreboard {
	private final DecimalFormat formatter = new DecimalFormat("00");
	private final IUser user;
	private Map<Integer, String> lines = new HashMap<>();
	private String title;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
    private Map<String, Object> replaces = new HashMap<>();

    PersonalScoreboard(Player player) {
        uuid = player.getUniqueId();
        user = APIPlugin.getInstance().getAPI().getUser(player);
        objectiveSign = new ObjectiveSign("sidebar", "DevPlugin");

        reloadData();
        objectiveSign.addReceiver(player);
    }
    
    public void reloadData(){}
    
    public void setLines(String footer, String dxd) {
    	objectiveSign.clearScores();
    	objectiveSign.setDisplayName(title.equalsIgnoreCase("DxD UHC") ? dxd : "§e§l"+title);
    	for(int i = 0; i < lines.size(); i++) {
    		String line = lines.get(i)
    				.replace("%timerS%", String.valueOf(formatter.format(SECOND)))
    				.replace("%timerM%", String.valueOf(formatter.format(MINUTE)))
    				.replace("%timerH%", String.valueOf(formatter.format(HEURE)))
    				.replace("%border%", String.valueOf(new DecimalFormat("#####.0").format(BORDER.getSize()/2)))
    				.replace("%players%", String.valueOf(PLAYERS))
    				.replace("%episode%", String.valueOf(EPISODE))
    				.replace("%gamemode%", APIPlugin.getInstance().getAPI().s.GAMEMODE.getDisplayName())
    				.replace("%kill%", String.valueOf(user.getKillStreak()))
    				.replace("%host%", APIPlugin.getInstance().getAPI().s.HOST == null ? "/h add <player>" : Bukkit.getOfflinePlayer(APIPlugin.getInstance().getAPI().s.HOST).getName())
    				.replace("%slot%", String.valueOf(APIPlugin.getInstance().getAPI().s.SLOT))
    				.replace("%team%", APIPlugin.getInstance().getAPI().s.TEAM ? user.getTeam().getDisplayName() : "FFA")
    				.replace("%center%", String.valueOf(UHCAPI.get().getGameManager().updateArrow(Bukkit.getPlayer(uuid), Bukkit.getWorld("Host").getSpawnLocation())))
    				.replace("%center_distance%", String.valueOf(UHCAPI.get().getGameManager().playerDistance(Bukkit.getPlayer(uuid), Bukkit.getWorld("Host").getSpawnLocation()) != Integer.MAX_VALUE ? new DecimalFormat("###########0.0").format(UHCAPI.get().getGameManager().playerDistance(Bukkit.getPlayer(uuid), Bukkit.getWorld("Host").getSpawnLocation())) : "?"));
    		if(!replaces.isEmpty())
    			for(String s : replaces.keySet()) {
    				line = line.replace(s, String.valueOf(replaces.get(s)));
    			}
    		objectiveSign.setLine(i, line, lines.size()-i);
    	}
    	objectiveSign.setLine(lines.size(), footer, 0);
    	objectiveSign.updateLines();
    }
    
    public void setScoreboard(String title, Map<Integer, String> map) {
    	this.title = title;
    	this.lines = map;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public void setReplaceValue(String replace, Object value) {
    	if(replaces.containsKey(replace)) replaces.replace(replace, value); else replaces.put(replace, value);
    }
    
    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
    public void removeScore(Player player) {
    	objectiveSign.removeScore(player);
    }
}
