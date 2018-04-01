package com.tek.sm.commands;

import static com.tek.sm.util.enums.CommandPermissions.GUI;
import static com.tek.sm.util.enums.CommandPermissions.IMPORT;
import static com.tek.sm.util.enums.CommandPermissions.LIST;
import static com.tek.sm.util.enums.CommandPermissions.RELOAD;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tek.sm.SimplyMusic;
import com.tek.sm.gui.MusicGui;
import com.tek.sm.management.PlayerSession;
import com.tek.sm.util.Reference;
import com.tek.sm.util.enums.CommandPermissions;
import com.tek.sm.util.lang.Lang;
import com.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain;
import com.xxmicloxx.NoteBlockAPI.Song;

public class MusicCommand implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			if(args.length == 0) {
				p.sendMessage(Reference.commands());
			}
			
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase(Lang.translate("cmd_help"))) {
					p.sendMessage(Reference.commands());
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_reload"))) {
					if(p.hasPermission(RELOAD.toString())) {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("reloading"));
						
						SimplyMusic.inst().getSongManager().reloadSongs();
						Reference.loadLanguage();
						Reference.loadItems();
						
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("reloaded"));
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_list"))) {
					if(p.hasPermission(LIST.toString())) {
						p.sendMessage(SimplyMusic.inst().getSongManager().songs());
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_stop"))) {
					if(p.hasPermission(CommandPermissions.STOP.toString())) {
						SimplyMusic.inst().getSongManager().stop(p);
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("stopped"));
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_shuffle"))) {
					if(p.hasPermission(CommandPermissions.SHUFFLE.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							SimplyMusic.inst().getSongManager().shuffle(p);
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("shuffled"));
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nsongs"));
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_play"))) {
					if(p.hasPermission(CommandPermissions.PLAY.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							SimplyMusic.inst().getSongManager().playConsec(p);
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("playing"));
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nsongs"));
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_skip"))) {
					if(p.hasPermission(CommandPermissions.SKIP.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							if(SimplyMusic.inst().getSessionManager().getSession(p) != null && SimplyMusic.inst().getSessionManager().getSession(p).isPlaying()) {
								SimplyMusic.inst().getSongManager().next(p);
								p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("skipped"));
								
								PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(p);
								p.sendMessage(SimplyMusic.inst().getSongManager().nowPlaying(session.sp.getSong()));
							}else {
								p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nplaying"));
							}
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nsongs"));
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_gui"))) {
					if(p.hasPermission(GUI.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							p.openInventory(new MusicGui(1, p, "").getInventory());
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("opened"));
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nsongs"));
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else {
					p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("unknown"));
				}
			}
			
			else if(args.length == 2) {
				if(args[0].equalsIgnoreCase(Lang.translate("cmd_play"))) {
					if(p.hasPermission(CommandPermissions.PLAY.toString())) {
						if(Reference.intPattern.matcher(args[1]).matches()) {
							try {
								int songid = Integer.parseInt(args[1]);
								Song song = SimplyMusic.inst().getSongManager().getSong(songid - 1);
								
								if(song != null) {
									SimplyMusic.inst().getSongManager().playSong(p, song);
									p.sendMessage(SimplyMusic.inst().getSongManager().nowPlaying(song));
								}else {
									p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("isong"));
								}
							}catch(Exception e) { 
								p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("iint"));
							}
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("iint"));
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_loop"))) {
					if(p.hasPermission(CommandPermissions.LOOP.toString())) {
						if(Reference.intPattern.matcher(args[1]).matches()) {
							try {
								int songid = Integer.parseInt(args[1]);
								Song song = SimplyMusic.inst().getSongManager().getSong(songid - 1);
								
								if(song != null) {
									SimplyMusic.inst().getSongManager().loop(p, song);
									p.sendMessage(SimplyMusic.inst().getSongManager().nowLooping(song));
								}else {
									p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("isong"));
								}
							}catch(Exception e) { 
								p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("iint"));
							}
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("iint"));
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_tune"))) {
					if(p.hasPermission(CommandPermissions.TUNE.toString())) {
						Player player = SimplyMusic.inst().getServer().getPlayer(args[1]);
						
						if(player != null) {
							if(!player.getName().equals(p.getName())) {
								if(SimplyMusic.inst().getSongManager().isPlaying(player)) {
									SimplyMusic.inst().getSongManager().tune(p, player);
									p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("tuned") + ChatColor.GOLD + player.getName());
								}else {
									p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nlplayer"));
								}
							}else {
								p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("yplayer"));
							}
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("iplayer"));
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else if(args[0].equalsIgnoreCase(Lang.translate("cmd_volume"))) {
					if(p.hasPermission(CommandPermissions.VOLUME.toString())) {
						if(Reference.intPattern.matcher(args[1]).matches()) {
							try {
								int vol = Integer.parseInt(args[1]);
								
								if(vol >= 0 && vol <= 100) {
									NoteBlockPlayerMain.setPlayerVolume(p, (byte)vol);
									
									p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("cvolume") + vol);
								}else {
									p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("ivolume"));
								}
							}catch(Exception e) { 
								p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("iint"));
							}
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("iint"));
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else {
					p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("unknown"));
				}
			}
			
			else if(args.length == 3){
				if(args[0].equalsIgnoreCase(Lang.translate("cmd_import"))) {
					if(p.hasPermission(IMPORT.toString())) {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("downloading"));
						SimplyMusic.inst().getSongManager().downloadSong(args[2], args[1], p);
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
					}
				}
				
				else {
					p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("unknown"));
				}
			}
			
			else {
				p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("iargs"));
			}
		}else {
			sender.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nplayer"));
		}
		
		return false;
	}

}
