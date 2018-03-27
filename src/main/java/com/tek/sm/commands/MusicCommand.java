package com.tek.sm.commands;

import static com.tek.sm.util.CommandPermissions.GUI;
import static com.tek.sm.util.CommandPermissions.IMPORT;
import static com.tek.sm.util.CommandPermissions.LIST;
import static com.tek.sm.util.CommandPermissions.RELOAD;
import static com.tek.sm.util.Reference.CVOLUME;
import static com.tek.sm.util.Reference.DOWNLOADING;
import static com.tek.sm.util.Reference.IARGS;
import static com.tek.sm.util.Reference.IINT;
import static com.tek.sm.util.Reference.IPLAYER;
import static com.tek.sm.util.Reference.ISONG;
import static com.tek.sm.util.Reference.IVOLUME;
import static com.tek.sm.util.Reference.NEPERMISSIONS;
import static com.tek.sm.util.Reference.NLPLAYER;
import static com.tek.sm.util.Reference.NPLAYER;
import static com.tek.sm.util.Reference.NPLAYING;
import static com.tek.sm.util.Reference.NSONGS;
import static com.tek.sm.util.Reference.OPENED;
import static com.tek.sm.util.Reference.PLAYING;
import static com.tek.sm.util.Reference.RELOADED;
import static com.tek.sm.util.Reference.RELOADING;
import static com.tek.sm.util.Reference.SHUFFLED;
import static com.tek.sm.util.Reference.SKIPPED;
import static com.tek.sm.util.Reference.STOPPED;
import static com.tek.sm.util.Reference.TUNED;
import static com.tek.sm.util.Reference.UNKNOWN;
import static com.tek.sm.util.Reference.YPLAYER;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tek.sm.SimplyMusic;
import com.tek.sm.gui.MusicGui;
import com.tek.sm.management.PlayerSession;
import com.tek.sm.util.CommandPermissions;
import com.tek.sm.util.Reference;
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
				if(args[0].equalsIgnoreCase(Reference.CMDHELP)) {
					p.sendMessage(Reference.commands());
				}
				
				else if(args[0].equalsIgnoreCase(Reference.CMDRELOAD)) {
					if(p.hasPermission(RELOAD.toString())) {
						p.sendMessage(RELOADING.toString());
						
						SimplyMusic.inst().getSongManager().reloadSongs();
						Reference.loadLang(false);
						Reference.loadItems();
						
						p.sendMessage(RELOADED.toString());
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else if(args[0].equalsIgnoreCase(Reference.CMDLIST)) {
					if(p.hasPermission(LIST.toString())) {
						p.sendMessage(SimplyMusic.inst().getSongManager().songs());
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else if(args[0].equalsIgnoreCase(Reference.CMDSTOP)) {
					if(p.hasPermission(CommandPermissions.STOP.toString())) {
						SimplyMusic.inst().getSongManager().stop(p);
						p.sendMessage(STOPPED.toString());
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else if(args[0].equalsIgnoreCase(Reference.CMDSHUFFLE)) {
					if(p.hasPermission(CommandPermissions.SHUFFLE.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							SimplyMusic.inst().getSongManager().shuffle(p);
							p.sendMessage(SHUFFLED.toString());
						}else {
							p.sendMessage(NSONGS.toString());
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else if(args[0].equalsIgnoreCase(Reference.CMDPLAY)) {
					if(p.hasPermission(CommandPermissions.PLAY.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							SimplyMusic.inst().getSongManager().playConsec(p);
							p.sendMessage(PLAYING.toString());
						}else {
							p.sendMessage(NSONGS.toString());
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else if(args[0].equalsIgnoreCase(Reference.CMDSKIP)) {
					if(p.hasPermission(CommandPermissions.SKIP.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							if(SimplyMusic.inst().getSessionManager().getSession(p) != null && SimplyMusic.inst().getSessionManager().getSession(p).isPlaying()) {
								SimplyMusic.inst().getSongManager().next(p);
								p.sendMessage(SKIPPED.toString());
								
								PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(p);
								p.sendMessage(SimplyMusic.inst().getSongManager().nowPlaying(session.sp.getSong()));
							}else {
								p.sendMessage(NPLAYING.toString());
							}
						}else {
							p.sendMessage(NSONGS.toString());
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else if(args[0].equalsIgnoreCase(Reference.CMDGUI)) {
					if(p.hasPermission(GUI.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							p.openInventory(new MusicGui(1, p).getInventory());
							p.sendMessage(OPENED.toString());
						}else {
							p.sendMessage(NSONGS.toString());
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else {
					p.sendMessage(UNKNOWN.toString());
				}
			}
			
			else if(args.length == 2) {
				if(args[0].equalsIgnoreCase(Reference.CMDPLAY)) {
					if(p.hasPermission(CommandPermissions.PLAY.toString())) {
						if(Reference.intPattern.matcher(args[1]).matches()) {
							try {
								int songid = Integer.parseInt(args[1]);
								Song song = SimplyMusic.inst().getSongManager().getSong(songid - 1);
								
								if(song != null) {
									SimplyMusic.inst().getSongManager().playSong(p, song);
									p.sendMessage(SimplyMusic.inst().getSongManager().nowPlaying(song));
								}else {
									p.sendMessage(ISONG.toString());
								}
							}catch(Exception e) { 
								p.sendMessage(IINT.toString());
							}
						}else {
							p.sendMessage(IINT.toString());
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else if(args[0].equalsIgnoreCase(Reference.CMDTUNE)) {
					if(p.hasPermission(CommandPermissions.TUNE.toString())) {
						Player player = SimplyMusic.inst().getServer().getPlayer(args[1]);
						
						if(player != null) {
							if(!player.getName().equals(p.getName())) {
								if(SimplyMusic.inst().getSongManager().isPlaying(player)) {
									SimplyMusic.inst().getSongManager().tune(p, player);
									p.sendMessage(TUNED.toString() + ChatColor.GOLD + player.getName());
								}else {
									p.sendMessage(NLPLAYER.toString());
								}
							}else {
								p.sendMessage(YPLAYER.toString());
							}
						}else {
							p.sendMessage(IPLAYER.toString());
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else if(args[0].equalsIgnoreCase(Reference.CMDVOLUME)) {
					if(p.hasPermission(CommandPermissions.VOLUME.toString())) {
						if(Reference.intPattern.matcher(args[1]).matches()) {
							try {
								int vol = Integer.parseInt(args[1]);
								
								if(vol >= 0 && vol <= 100) {
									NoteBlockPlayerMain.setPlayerVolume(p, (byte)vol);
									
									p.sendMessage(CVOLUME.toString() + vol);
								}else {
									p.sendMessage(IVOLUME.toString());
								}
							}catch(Exception e) { 
								p.sendMessage(IINT.toString());
							}
						}else {
							p.sendMessage(IINT.toString());
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else {
					p.sendMessage(UNKNOWN.toString());
				}
			}
			
			else if(args.length == 3){
				if(args[0].equalsIgnoreCase(Reference.CMDIMPORT)) {
					if(p.hasPermission(IMPORT.toString())) {
						p.sendMessage(DOWNLOADING.toString());
						SimplyMusic.inst().getSongManager().downloadSong(args[2], args[1], p);
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				else {
					p.sendMessage(UNKNOWN.toString());
				}
			}
			
			else {
				p.sendMessage(IARGS.toString());
			}
		}else {
			sender.sendMessage(NPLAYER.toString());
		}
		
		return false;
	}

}
