package fr.rolan.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fr.rolan.core.APIPlugin;
import fr.rolan.core.api.stuff.StuffManager;

public class FileUtils {
	
	public static void createFile(File file) throws IOException{
		if(!file.exists()) {
			if(file.getParentFile().mkdirs())
				System.out.println("[UHCCore] Create Parent Directory for "+file.getName());
			if(file.createNewFile())
				System.out.println("[UHCCore] Create "+file.getName());
		}
	}
	
	public static void save(File file, String text) {
		try {
			createFile(file);
		}catch(IOException e) {
			e.printStackTrace();
		}
		try (FileWriter fw = new FileWriter(file)) {
			fw.write(text);
			fw.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveStuff(File file, StuffManager stuffManager) {
		try {
			createFile(file);
		}catch(IOException e) {
			e.printStackTrace();
		}
		FileConfiguration stuff = new YamlConfiguration();
		try {
			stuff.load(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		stuff.set("stuff_start", new ArrayList<ItemStack>(Arrays.asList(stuffManager.getStuffStart())));
		stuff.set("stuff_armor_start", new ArrayList<ItemStack>(Arrays.asList(stuffManager.getStuffArmorStart())));
		stuff.set("stuff_death", new ArrayList<ItemStack>(Arrays.asList(stuffManager.getStuffDeath())));
		stuff.set("stuff_armor_death", new ArrayList<ItemStack>(Arrays.asList(stuffManager.getStuffArmorDeath())));
		try {
			stuff.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadConfig(UUID uuid, String name) {
		File file = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+uuid.toString()+File.separator+name+".json");
		APIPlugin.getInstance().getAPI().s = Serializer.deserialize(loadContent(file));
		File fileStuff = new File(APIPlugin.getInstance().getDataFolder()+File.separator+"configs"+File.separator+uuid.toString()+File.separator+name+".yml");
		FileConfiguration stuff = new YamlConfiguration();
		try {
			stuff.load(fileStuff);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try{APIPlugin.getInstance().getAPI().getStuffManager().setStuffStart(stuff.getList("stuff_start").toArray(new ItemStack[0]));}catch(NullPointerException e) {}
		try{APIPlugin.getInstance().getAPI().getStuffManager().setStuffArmorStart(stuff.getList("stuff_armor_start").toArray(new ItemStack[0]));}catch(NullPointerException e) {}
		try{APIPlugin.getInstance().getAPI().getStuffManager().setStuffDeath(stuff.getList("stuff_death").toArray(new ItemStack[0]));}catch(NullPointerException e) {}
		try{APIPlugin.getInstance().getAPI().getStuffManager().setStuffArmorDeath(stuff.getList("stuff_armor_death").toArray(new ItemStack[0]));}catch(NullPointerException e) {}
	}
	
	public static String loadContent(File file) {
		if(file.exists())
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))){
				StringBuilder text = new StringBuilder();
				String line;
				while((line = reader.readLine()) != null)
					text.append(line);
				return text.toString();
			}catch(IOException e) {
				e.printStackTrace();
			}
		return "";
	}
}
