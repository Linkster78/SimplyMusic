package com.tek.sm.util;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GOLD;

import java.io.File;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.tek.sm.SimplyMusic;
import com.tek.sm.util.enums.CommandDescriptions;
import com.tek.sm.util.enums.CommandResponse;
import com.tek.sm.util.enums.Commands;
import com.tek.sm.util.enums.ItemNames;
import com.tek.sm.util.enums.MiscText;
import com.tek.sm.util.lang.Lang;
import com.tek.sm.util.misc.ItemUtil;
import com.xxmicloxx.NoteBlockAPI.Song;

public class Reference {
	
	public static final String PERMISSION_ROOT = "simplymusic.";
	
	public static final Pattern intPattern = Pattern.compile("(?<=\\s|^)\\d+(?=\\s|$)");
	
	public static ItemStack BACKMAIN, BACK, NEXT, STOP, SKIP, PLAY, SHUFFLE, TUNE, REFRESH, VOLUME, FILTER, PLAYLIST, SEPARATOR;
	
	public static ItemStack playlist(int id) {
		return ItemUtil.createItemStack(Material.BOOK, id, (short)0, Lang.translate("item_playlist"));
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack randomDisk(Song song) {
		int[] materials = new int[11];
		int it = 0;
		for(int i = 2256; i <= 2265; i++) {
			materials[it] = i;
			it++;
		}
		materials[10] = 2267;
		
		Material material = Material.getMaterial(materials[SimplyMusic.inst().getSongManager().getIndex(song) % 11]);
		return ItemUtil.createItemStack(material, 1, (short)0, ChatColor.AQUA + SimplyMusic.inst().getSongManager().songName(song), ChatColor.DARK_PURPLE + "Author: " + ChatColor.GOLD + SimplyMusic.inst().getSongManager().songAuthor(song), ChatColor.BLUE + Lang.translate("misc_loop"));
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack playlistDisk(Song song) {
		int[] materials = new int[11];
		int it = 0;
		for(int i = 2256; i <= 2265; i++) {
			materials[it] = i;
			it++;
		}
		materials[10] = 2267;
		
		Material material = Material.getMaterial(materials[SimplyMusic.inst().getSongManager().getIndex(song) % 11]);
		return ItemUtil.createItemStack(material, 1, (short)0, ChatColor.AQUA + SimplyMusic.inst().getSongManager().songName(song), ChatColor.DARK_PURPLE + "Author: " + ChatColor.GOLD + SimplyMusic.inst().getSongManager().songAuthor(song), ChatColor.BLUE + Lang.translate("misc_remove"));
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack addingDisk(Song song) {
		int[] materials = new int[11];
		int it = 0;
		for(int i = 2256; i <= 2265; i++) {
			materials[it] = i;
			it++;
		}
		materials[10] = 2267;
		
		Material material = Material.getMaterial(materials[SimplyMusic.inst().getSongManager().getIndex(song) % 11]);
		return ItemUtil.createItemStack(material, 1, (short)0, ChatColor.AQUA + SimplyMusic.inst().getSongManager().songName(song), ChatColor.DARK_PURPLE + "Author: " + ChatColor.GOLD + SimplyMusic.inst().getSongManager().songAuthor(song), ChatColor.BLUE + Lang.translate("misc_add"));
	}
	
	public static String commands() {
		StringBuilder helpMenu = new StringBuilder();
		
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_help") + GOLD + " - " + Lang.translate("help_help") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_reload") + GOLD + " - " + Lang.translate("help_reload") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_list") + GOLD + " - " + Lang.translate("help_list") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_play") + GOLD + " - " + Lang.translate("help_play") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_play") + " <SongID>" + GOLD + " - " + Lang.translate("help_playid") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_loop") + " <SongID>" + GOLD + " - " + Lang.translate("help_loop") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_tune") + " <Player>" + GOLD + " - " + Lang.translate("help_tune") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_stop") + GOLD + " - " + Lang.translate("help_stop") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_shuffle") + GOLD + " - " + Lang.translate("help_shuffle") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_skip") + GOLD + " - " + Lang.translate("help_skip") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_gui") + GOLD + " - " + Lang.translate("help_gui") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_import") + " <name> <url>" + GOLD + " - " + Lang.translate("help_import") + "\n");
		helpMenu.append(AQUA + "/music " + Lang.translate("cmd_volume") + " <volume>" + GOLD + " - " + Lang.translate("help_volume"));
		
		return helpMenu.toString();
	}
	
	public static void loadItems() {
		BACKMAIN = ItemUtil.createItemStack(Material.PAPER, 1, (short)0, ChatColor.GREEN + Lang.translate("item_backmain"));
		BACK = ItemUtil.createItemStack(Material.PAPER, 1, (short)0, ChatColor.GREEN + Lang.translate("item_back"));
		NEXT = ItemUtil.createItemStack(Material.PAPER, 1, (short)0, ChatColor.GREEN + Lang.translate("item_next"));
		STOP = ItemUtil.createItemStack(Material.BARRIER, 1, (short)0, ChatColor.RED + Lang.translate("item_stop"));
		SKIP = ItemUtil.createItemStack(Material.ENDER_PEARL, 1, (short)0, ChatColor.DARK_AQUA + Lang.translate("item_skip"));
		PLAY = ItemUtil.createItemStack(Material.INK_SACK, 1, (short)10, ChatColor.GREEN + Lang.translate("item_play"));
		SHUFFLE = ItemUtil.createItemStack(Material.SLIME_BALL, 1, (short)0, ChatColor.DARK_PURPLE + Lang.translate("item_shuffle"));
		TUNE = ItemUtil.createItemStack(Material.RED_ROSE, 1, (short)0, ChatColor.LIGHT_PURPLE + Lang.translate("item_tune"));
		REFRESH = ItemUtil.createItemStack(Material.PAPER, 1, (short)0, ChatColor.GREEN + Lang.translate("item_refresh"));
	    VOLUME = ItemUtil.createItemStack(Material.DIODE, 1, (short)0, ChatColor.BLUE + Lang.translate("item_volume"));
	    FILTER = ItemUtil.createItemStack(Material.SIGN, 1, (short)0, ChatColor.DARK_GREEN + Lang.translate("item_filter"), ChatColor.GOLD + Lang.translate("misc_clearfilter"));
	    PLAYLIST = ItemUtil.createItemStack(Material.BOOK, 1, (short)0, ChatColor.AQUA + Lang.translate("item_playlist"));
		SEPARATOR = ItemUtil.createItemStack(Material.STAINED_GLASS_PANE, 1, (short)0, " ");
	}
	
	public static void loadLanguage() {
		SimplyMusic.inst().reloadConfig();
		
		Lang.setTranslation(new File("plugins/" + SimplyMusic.inst().getName() + "/config.yml"));
		
		Lang.registerTranslation("title_inventory", MiscText.TITLEINVENTORY.toString());
		Lang.registerTranslation("title_volume", MiscText.TITLEVOLUME.toString());
		Lang.registerTranslation("title_tune", MiscText.TITLETUNE.toString());
		Lang.registerTranslation("title_playlist", MiscText.TITLEPLAYLIST.toString());
		Lang.registerTranslation("title_choose", MiscText.TITLECHOOSEPLAYLIST.toString());
		Lang.registerTranslation("title_prefix", MiscText.PREFIX.toString());
		
		boolean custom = SimplyMusic.inst().getConfig().getBoolean("custom_lang");
		if(custom) {
			Lang.setTranslation(new File("plugins/" + SimplyMusic.inst().getName() + "/" + SimplyMusic.inst().getConfig().getString("lang") + ".yml"));
		}else {
			Lang.setTranslation(null);
		}
		
		Lang.registerTranslation("nepermissions", CommandResponse.NEPERMISSIONS.toString());
		Lang.registerTranslation("nplayer", CommandResponse.NPLAYER.toString());
		Lang.registerTranslation("unknown", CommandResponse.UNKNOWN.toString());
		Lang.registerTranslation("iargs", CommandResponse.IARGS.toString());
		Lang.registerTranslation("iint", CommandResponse.IINT.toString());
		Lang.registerTranslation("isong", CommandResponse.ISONG.toString());
		Lang.registerTranslation("iplayer", CommandResponse.IPLAYER.toString());
		Lang.registerTranslation("nsongs", CommandResponse.NSONGS.toString());
		Lang.registerTranslation("yplayer", CommandResponse.YPLAYER.toString());
		Lang.registerTranslation("nlplayer", CommandResponse.NLPLAYER.toString());
		Lang.registerTranslation("nplaying", CommandResponse.NPLAYING.toString());
		Lang.registerTranslation("ivolume", CommandResponse.IVOLUME.toString());
		Lang.registerTranslation("idownload", CommandResponse.IDOWNLOAD.toString());
		Lang.registerTranslation("interrupted", CommandResponse.INTERRUPTED.toString());
		Lang.registerTranslation("eplaylist", CommandResponse.EPLAYLIST.toString());
		Lang.registerTranslation("fplaylist", CommandResponse.FPLAYLIST.toString());
		Lang.registerTranslation("reloading", CommandResponse.RELOADING.toString());
		Lang.registerTranslation("reloaded", CommandResponse.RELOADED.toString());
		Lang.registerTranslation("nowplaying", CommandResponse.NOWPLAYING.toString());
		Lang.registerTranslation("nowlooping", CommandResponse.NOWLOOPING.toString());
		Lang.registerTranslation("stopped", CommandResponse.STOPPED.toString());
		Lang.registerTranslation("tuned", CommandResponse.TUNED.toString());
		Lang.registerTranslation("shuffled", CommandResponse.SHUFFLED.toString());
		Lang.registerTranslation("skipped", CommandResponse.SKIPPED.toString());
		Lang.registerTranslation("playing", CommandResponse.PLAYING.toString());
		Lang.registerTranslation("added", CommandResponse.ADDED.toString());
		Lang.registerTranslation("removed", CommandResponse.REMOVED.toString());
		Lang.registerTranslation("listening", CommandResponse.LISTENING.toString());
		Lang.registerTranslation("opened", CommandResponse.OPENED.toString());
		Lang.registerTranslation("downloading", CommandResponse.DOWNLOADING.toString());
		Lang.registerTranslation("downloaded", CommandResponse.DOWNLOADED.toString());
		Lang.registerTranslation("cvolume", CommandResponse.CVOLUME.toString());
		Lang.registerTranslation("wtf", CommandResponse.WTF.toString());
		
		Lang.registerTranslation("help_help", CommandDescriptions.HELP.toString());
		Lang.registerTranslation("help_reload", CommandDescriptions.RELOAD.toString());
		Lang.registerTranslation("help_list", CommandDescriptions.LIST.toString());
		Lang.registerTranslation("help_play", CommandDescriptions.PLAY.toString());
		Lang.registerTranslation("help_playid", CommandDescriptions.PLAYID.toString());
		Lang.registerTranslation("help_loop", CommandDescriptions.LOOP.toString());
		Lang.registerTranslation("help_tune", CommandDescriptions.TUNE.toString());
		Lang.registerTranslation("help_stop", CommandDescriptions.STOP.toString());
		Lang.registerTranslation("help_shuffle", CommandDescriptions.SHUFFLE.toString());
		Lang.registerTranslation("help_skip", CommandDescriptions.SKIP.toString());
		Lang.registerTranslation("help_gui", CommandDescriptions.GUI.toString());
		Lang.registerTranslation("help_import", CommandDescriptions.IMPORT.toString());
		Lang.registerTranslation("help_volume", CommandDescriptions.VOLUME.toString());
		
		Lang.registerTranslation("item_backmain", ItemNames.BACKMAIN.toString());
		Lang.registerTranslation("item_back", ItemNames.BACK.toString());
		Lang.registerTranslation("item_next", ItemNames.NEXT.toString());
		Lang.registerTranslation("item_stop", ItemNames.STOP.toString());
		Lang.registerTranslation("item_skip", ItemNames.SKIP.toString());
		Lang.registerTranslation("item_play", ItemNames.PLAY.toString());
		Lang.registerTranslation("item_shuffle", ItemNames.SHUFFLE.toString());
		Lang.registerTranslation("item_tune", ItemNames.TUNE.toString());
		Lang.registerTranslation("item_refresh", ItemNames.REFRESH.toString());
		Lang.registerTranslation("item_volume", ItemNames.VOLUME.toString());
		Lang.registerTranslation("item_filter", ItemNames.FILTER.toString());
		Lang.registerTranslation("item_playlist", ItemNames.PLAYLIST.toString());
		
		Lang.registerTranslation("cmd_help", Commands.HELP.toString());
		Lang.registerTranslation("cmd_reload", Commands.RELOAD.toString());
		Lang.registerTranslation("cmd_list", Commands.LIST.toString());
		Lang.registerTranslation("cmd_play", Commands.PLAY.toString());
		Lang.registerTranslation("cmd_loop", Commands.LOOP.toString());
		Lang.registerTranslation("cmd_tune", Commands.TUNE.toString());
		Lang.registerTranslation("cmd_stop", Commands.STOP.toString());
		Lang.registerTranslation("cmd_shuffle", Commands.SHUFFLE.toString());
		Lang.registerTranslation("cmd_skip", Commands.SKIP.toString());
		Lang.registerTranslation("cmd_gui", Commands.GUI.toString());
		Lang.registerTranslation("cmd_import", Commands.IMPORT.toString());
		Lang.registerTranslation("cmd_volume", Commands.VOLUME.toString());
		
		Lang.registerTranslation("misc_clearfilter", MiscText.CLEARFILTER.toString());
		Lang.registerTranslation("misc_loop", MiscText.LOOP.toString());
		Lang.registerTranslation("misc_add", MiscText.ADD.toString());
		Lang.registerTranslation("misc_remove", MiscText.REMOVE.toString());
	}
}
