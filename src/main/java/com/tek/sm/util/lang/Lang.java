package com.tek.sm.util.lang;

import java.io.File;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class Lang {
	
	private static HashMap<String, String> translations = new HashMap<String, String>();
	private static YamlConfiguration langConfiguration;
	public static final String UNKNOWN = "Unknown translation";
	
	public static void registerTranslation(String id, String defaultTranslation) {
		if(defaultTranslation != null) {
			if(langConfiguration == null) translations.put(id, ChatColor.translateAlternateColorCodes('&', defaultTranslation));
			String langTranslation = langConfiguration.getString(id);
			translations.put(id, langTranslation == null ? ChatColor.translateAlternateColorCodes('&', defaultTranslation) : ChatColor.translateAlternateColorCodes('&', langTranslation));
		}
	}
	
	public static String translate(String id) {
		if(!translations.containsKey(id)) {
			return UNKNOWN;
		}else {
			return translations.get(id);
		}
	}
	
	public static void setTranslation(File translationFile) {
		if(translationFile != null && translationFile.exists()) {
			try {
				YamlConfiguration lang = new YamlConfiguration();
				lang.load(translationFile);
				langConfiguration = lang;
			}catch(Exception e) { }
		}else {
			translationFile = null;
		}
	}
	
}
