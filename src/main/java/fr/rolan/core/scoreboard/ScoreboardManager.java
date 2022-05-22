package fr.rolan.core.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;

import fr.rolan.api.scoreboard.IScoreboardManager;
import fr.rolan.core.APIPlugin;
import net.md_5.bungee.api.ChatColor;

public class ScoreboardManager implements IScoreboardManager {
    private final Map<UUID, PersonalScoreboard> scoreboards;
	@SuppressWarnings({ "rawtypes", "unused" })
	private final ScheduledFuture glowingTask;
	@SuppressWarnings({ "rawtypes", "unused" })
	private final ScheduledFuture reloadingTask;
    private int ipCharIndex;
    private int dxdCharIndex;
    private int cooldown;
    private int cooldowndxd;

    public ScoreboardManager() {
        scoreboards = new HashMap<>();
        ipCharIndex = 0;
        dxdCharIndex = 0;
        cooldown = 0;
        cooldowndxd = 0;

        glowingTask = APIPlugin.getInstance().getScheduledExecutorService().scheduleAtFixedRate(() ->
        {
        	String name = colorMentionAt();
        	String dxd = colorDxDUHCAt();
            for (PersonalScoreboard scoreboard : scoreboards.values())
            	APIPlugin.getInstance().getExecutorMonoThread().execute(() -> scoreboard.setLines(name, dxd));
        }, 80, 80, TimeUnit.MILLISECONDS);

        reloadingTask = APIPlugin.getInstance().getScheduledExecutorService().scheduleAtFixedRate(() ->
        {
            for (PersonalScoreboard scoreboard : scoreboards.values())
            	APIPlugin.getInstance().getExecutorMonoThread().execute(scoreboard::reloadData);
        }, 1, 1, TimeUnit.SECONDS);
    }
    
    public void onDisable() {
        scoreboards.values().forEach(PersonalScoreboard::onLogout);
    }

    public void onLogin(Player player) {
        if (scoreboards.containsKey(player.getUniqueId())) {
            return;
        }
        scoreboards.put(player.getUniqueId(), new PersonalScoreboard(player));
    }

    public void onLogout(Player player) {
        if (scoreboards.containsKey(player.getUniqueId())) {
            scoreboards.get(player.getUniqueId()).onLogout();
            scoreboards.remove(player.getUniqueId());
        }
    }
    
    /**
     * Les valeurs modifiables: %timerS%, %timerM%, %timerH%, %border%, %episode%, %players%, %gamemode%, %kill% %host%
     * @param title
     * @param map » La classe Integer dans ce map permet de donner la position de la ligne,
     *  sachant que 0 est la ligne la plus haute.
     */
    @Override
    public void setScoreboard(String title, Map<Integer, String> map) {
    	scoreboards.values().forEach(sb -> sb.setScoreboard(title, map));
    }
    
    @Override
    public void setPlayerScoreboard(UUID uuid, String title, Map<Integer, String> map) {
    	scoreboards.get(uuid).setScoreboard(title, map);
    }
    
    @Override
    public void setReplaceValue(String replace, Object value) {
    	scoreboards.values().forEach(sb -> sb.setReplaceValue(replace, value));
    }
    
    @Override
    public void setPlayerTitle(UUID uuid, String title) {
    	scoreboards.get(uuid).setTitle(title);
    }
    
    @Override
    public void setTitle(String title) {
    	scoreboards.values().forEach(sb -> sb.setTitle(title));
    }
    
    public void removeScore(Player player) {
    	scoreboards.get(player.getUniqueId()).removeScore(player);
    }
    public void update(Player player) {
        if (scoreboards.containsKey(player.getUniqueId()))
            scoreboards.get(player.getUniqueId()).reloadData();
    }
    
    private String colorMentionAt() {
        String ip = "@rolan10001";

        if (cooldown > 0) {
            cooldown--;
            return ChatColor.AQUA + ip;
        }

        StringBuilder formattedIp = new StringBuilder();

        if (ipCharIndex > 0) {
            formattedIp.append(ip.substring(0, ipCharIndex - 1));
            formattedIp.append(ChatColor.AQUA).append(ip.substring(ipCharIndex - 1, ipCharIndex));
        } else {
            formattedIp.append(ip.substring(0, ipCharIndex));
        }

        formattedIp.append(ChatColor.DARK_AQUA).append(ip.charAt(ipCharIndex));

        if (ipCharIndex + 1 < ip.length()) {
            formattedIp.append(ChatColor.DARK_AQUA).append(ip.charAt(ipCharIndex + 1));

            if (ipCharIndex + 2 < ip.length())
                formattedIp.append(ChatColor.AQUA).append(ip.substring(ipCharIndex + 2));

            ipCharIndex++;
        } else {
            ipCharIndex = 0;
            cooldown = 50;
        }
        return ChatColor.AQUA + formattedIp.toString();
    }
    
	private String colorDxDUHCAt() {
        if (cooldowndxd > 0) {
        	cooldowndxd--;
            return "§e§lDxD §c§lUHC";
        }

        StringBuilder formattedIp = new StringBuilder();
        
        if(dxdCharIndex <= 1)
        	formattedIp.append("§6§lD§e§lxD §c§lUHC");
        else if(dxdCharIndex <= 3)
        	formattedIp.append("§6§lDx§e§lD §c§lUHC");
        else if(dxdCharIndex <= 5)
        	formattedIp.append("§e§lD§6§lxD §c§lUHC");
        else if(dxdCharIndex <= 7)
        	formattedIp.append("§e§lDx§6§lD §c§lUHC");
        else if(dxdCharIndex <= 9)
        	formattedIp.append("§e§lDxD §4§lU§c§lHC");
        else if(dxdCharIndex <= 11)
        	formattedIp.append("§e§lDxD §4§lUH§c§lC");
        else if(dxdCharIndex <= 13)
        	formattedIp.append("§e§lDxD §c§lU§4§lHC");
        else if(dxdCharIndex <= 15)
        	formattedIp.append("§e§lDxD §c§lUH§4§lC");
        else if(dxdCharIndex <= 17)
        	formattedIp.append("§c§lDxD UHC");
        else if(dxdCharIndex <= 20)
        	formattedIp.append("§f§lDxD UHC");
        else if(dxdCharIndex <= 23)
        	formattedIp.append("§c§lDxD UHC");
        else if(dxdCharIndex <= 26)
        	formattedIp.append("§f§lDxD UHC");
        else if(dxdCharIndex >= 29){
        	formattedIp.append("§e§lDxD §c§lUHC");
        	dxdCharIndex = -1;
        	cooldowndxd = 50;
        }else
        	formattedIp.append("§e§lDxD §c§lUHC");
        dxdCharIndex++;
        return formattedIp.toString();
    }
}