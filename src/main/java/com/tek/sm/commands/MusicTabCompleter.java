package com.tek.sm.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.tek.sm.SimplyMusic;
import com.tek.sm.util.lang.Lang;

public class MusicTabCompleter implements TabCompleter{

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> suggestions = new ArrayList<String>();
		
		if(args.length == 1) {
			suggestions.add(Lang.translate("cmd_help"));
			suggestions.add(Lang.translate("cmd_reload"));
			suggestions.add(Lang.translate("cmd_list"));
			suggestions.add(Lang.translate("cmd_play"));
			suggestions.add(Lang.translate("cmd_stop"));
			suggestions.add(Lang.translate("cmd_tune"));
			suggestions.add(Lang.translate("cmd_shuffle"));
			suggestions.add(Lang.translate("cmd_skip"));
			suggestions.add(Lang.translate("cmd_gui"));
			suggestions.add(Lang.translate("cmd_import"));
			suggestions.add(Lang.translate("cmd_volume"));
			suggestions.add(Lang.translate("cmd_loop"));
		}
		
		else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Lang.translate("cmd_play"))) {
				for(int i = 0; i < SimplyMusic.inst().getSongManager().amount(); i++) {
					suggestions.add("" + (i + 1));
				}
			}
			
			else if(args[0].equalsIgnoreCase(Lang.translate("cmd_tune"))) {
				for(Player player : SimplyMusic.inst().getServer().getOnlinePlayers()) {
					suggestions.add(player.getName());
				}
			}
		}
		
		return suggestions;
	}

}
