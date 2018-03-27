package com.tek.sm.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.tek.sm.SimplyMusic;
import com.tek.sm.util.Reference;

public class MusicTabCompleter implements TabCompleter{

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> suggestions = new ArrayList<String>();
		
		if(args.length == 1) {
			suggestions.add(Reference.CMDHELP);
			suggestions.add(Reference.CMDRELOAD);
			suggestions.add(Reference.CMDLIST);
			suggestions.add(Reference.CMDPLAY);
			suggestions.add(Reference.CMDSTOP);
			suggestions.add(Reference.CMDTUNE);
			suggestions.add(Reference.CMDSHUFFLE);
			suggestions.add(Reference.CMDSKIP);
			suggestions.add(Reference.CMDGUI);
			suggestions.add(Reference.CMDIMPORT);
			suggestions.add(Reference.CMDVOLUME);
		}
		
		else if(args.length == 2) {
			if(args[0].equalsIgnoreCase(Reference.CMDPLAY)) {
				for(int i = 0; i < SimplyMusic.inst().getSongManager().amount(); i++) {
					suggestions.add("" + (i + 1));
				}
			}
			
			else if(args[0].equalsIgnoreCase(Reference.CMDTUNE)) {
				for(Player player : SimplyMusic.inst().getServer().getOnlinePlayers()) {
					suggestions.add(player.getName());
				}
			}
		}
		
		return suggestions;
	}

}
