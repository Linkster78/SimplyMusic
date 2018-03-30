package com.tek.sm.util;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.wesjd.anvilgui.version.GlobalWrapper;

public class ActionBar {
	
	public static void sendActionBar(@Nonnull Player p, String message) {
        if(Integer.parseInt(GlobalWrapper.version().split("_")[1]) >= 12) {
        	p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
	}
	
}
