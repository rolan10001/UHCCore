package fr.rolan.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.rolan.api.game.Settings;

public class Serializer {
	public static Gson gson() {
		return (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	}
	
	public static String serialize(Settings config) {
		return gson().toJson(config);
	}
	
	public static Settings deserialize(String json) {
		return (Settings)gson().fromJson(json, Settings.class);
	}
}
