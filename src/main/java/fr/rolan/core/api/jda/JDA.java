package fr.rolan.core.api.jda;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import fr.rolan.api.jda.IJDA;
import fr.rolan.core.APIPlugin;
import fr.rolan.core.api.jda.request.Request;
import fr.rolan.core.api.jda.userdata.Host;

public class JDA implements IJDA {
	
	private final net.dv8tion.jda.api.JDA jda;
	public final String requesthostid = "889175232118677577";
	private final String dxdGuild = "817484133311447061";
	private final HashMap<Long, Host> hosts = new HashMap<Long, Host>();
	
	public JDA(net.dv8tion.jda.api.JDA jda) {
		this.jda = jda;
		Request request = new Request(this);
		jda.addEventListener(request);
		StringBuilder builder = new StringBuilder();
		builder.append("Aucun Host de prévu\n");
		if(APIPlugin.getInstance().getBotConfig().contains("bot")) {
			builder = new StringBuilder();
			HashMap<Integer, String> h = new HashMap<Integer, String>();
			HashMap<Integer, String> hash = new HashMap<Integer, String>();
			for(String string : APIPlugin.getInstance().getBotConfig().getConfigurationSection("bot").getKeys(false)) {
				List<Integer> list = APIPlugin.getInstance().getBotConfig().getIntegerList("bot."+string+".dates");
				LocalDateTime date = LocalDateTime.of(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
				if(!h.containsKey(date.getDayOfMonth()*date.getMonthValue()+date.getYear()))
					h.put(date.getDayOfMonth()*24*60+date.getMonthValue()*30*24*60+date.getYear()*365*24*60, "● *"+request.translate(date.getDayOfWeek().name())+" "+date.getDayOfMonth()+" "+request.month[date.getMonthValue()-1]+"* :\n");
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
		}
		
		getJDA().getTextChannelById("889104470007492629").editMessageById("889934151929712661", 
				"**\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC**\n\n" +
				builder.toString() +
				"\n**\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC\u25AC**"
				).queue();
	}
	
	@Override
	public net.dv8tion.jda.api.JDA getJDA() {
		return this.jda;
	}
	
	public String getDxDGuild() {
		return this.dxdGuild;
	}

	public HashMap<Long, Host> getHosts() {
		return hosts;
	}
	
	public Host getHost(Long id) {
		return hosts.get(id);
	}
}
