package com.tek.sm.util;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.WHITE;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.tek.sm.SimplyMusic;
import com.xxmicloxx.NoteBlockAPI.Song;

public class Reference {
	
	public static String INVENTORY_TITLE, VOLUME_TITLE, TUNE_TITLE, PREFIX;
	public static final String PERMISSION_ROOT = "simplymusic.";
	
	public static final Pattern intPattern = Pattern.compile("(?<=\\s|^)\\d+(?=\\s|$)");
	
	public static ItemStack BACKMAIN, BACK, NEXT, STOP, SKIP, PLAY, SHUFFLE, TUNE, REFRESH, VOLUME, FILTER, SEPARATOR;
	
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
		return ItemUtil.createItemStack(material, 1, (short)0, ChatColor.AQUA + SimplyMusic.inst().getSongManager().songName(song), ChatColor.DARK_PURPLE + "Author: " + ChatColor.GOLD + SimplyMusic.inst().getSongManager().songAuthor(song));
	}
	
	public static String commands() {
		StringBuilder helpMenu = new StringBuilder();
		
		helpMenu.append(AQUA + "/music " + CMDHELP + GOLD + " - " + HELPHELP + "\n");
		helpMenu.append(AQUA + "/music " + CMDRELOAD + GOLD + " - " + HELPRELOAD + "\n");
		helpMenu.append(AQUA + "/music " + CMDLIST + GOLD + " - " + HELPLIST + "\n");
		helpMenu.append(AQUA + "/music " + CMDPLAY + GOLD + " - " + HELPPLAY + "\n");
		helpMenu.append(AQUA + "/music " + CMDPLAY + " <SongID>" + GOLD + " - " + HELPPLAYID + "\n");
		helpMenu.append(AQUA + "/music " + CMDTUNE + " <Player>" + GOLD + " - " + HELPTUNE + "\n");
		helpMenu.append(AQUA + "/music " + CMDSTOP + GOLD + " - " + HELPSTOP + "\n");
		helpMenu.append(AQUA + "/music " + CMDSHUFFLE + GOLD + " - " + HELPSHUFFLE + "\n");
		helpMenu.append(AQUA + "/music " + CMDSKIP + GOLD + " - " + HELPSKIP + "\n");
		helpMenu.append(AQUA + "/music " + CMDGUI + GOLD + " - " + HELPGUI + "\n");
		helpMenu.append(AQUA + "/music " + CMDIMPORT + " <name> <url>" + GOLD + " - " + HELPIMPORT + "\n");
		helpMenu.append(AQUA + "/music " + CMDVOLUME + " <volume>" + GOLD + " - " + HELPVOLUME);
		
		return helpMenu.toString();
	}
	
	public static void loadItems() {
		BACKMAIN = ItemUtil.createItemStack(Material.PAPER, 1, (short)0, ChatColor.GREEN + ITEMBACKMAIN);
		BACK = ItemUtil.createItemStack(Material.PAPER, 1, (short)0, ChatColor.GREEN + ITEMBACK);
		NEXT = ItemUtil.createItemStack(Material.PAPER, 1, (short)0, ChatColor.GREEN + ITEMNEXT);
		STOP = ItemUtil.createItemStack(Material.BARRIER, 1, (short)0, ChatColor.RED + ITEMSTOP);
		SKIP = ItemUtil.createItemStack(Material.ENDER_PEARL, 1, (short)0, ChatColor.DARK_AQUA + ITEMSKIP);
		PLAY = ItemUtil.createItemStack(Material.INK_SACK, 1, (short)10, ChatColor.GREEN + ITEMPLAY);
		SHUFFLE = ItemUtil.createItemStack(Material.SLIME_BALL, 1, (short)0, ChatColor.DARK_PURPLE + ITEMSHUFFLE);
		TUNE = ItemUtil.createItemStack(Material.RED_ROSE, 1, (short)0, ChatColor.LIGHT_PURPLE + ITEMTUNE);
		REFRESH = ItemUtil.createItemStack(Material.PAPER, 1, (short)0, ChatColor.GREEN + ITEMREFRESH);
	    VOLUME = ItemUtil.createItemStack(Material.DIODE, 1, (short)0, ChatColor.BLUE + ITEMVOLUME);
	    FILTER = ItemUtil.createItemStack(Material.SIGN, 1, (short)0, ChatColor.DARK_GREEN + ITEMFILTER, ChatColor.GOLD + MISCCLEARFILTER);
		SEPARATOR = ItemUtil.createItemStack(Material.STAINED_GLASS_PANE, 1, (short)0, " ");
	}
	
	//LANG
	public static String NEPERMISSIONS, NPLAYER, UNKNOWN, IARGS, IINT, ISONG, IPLAYER, NSONGS, YPLAYER, NLPLAYER, NPLAYING, IVOLUME, IDOWNLOAD, INTERRUPTED, RELOADING, RELOADED, NOWPLAYING, STOPPED, TUNED, SHUFFLED, SKIPPED, PLAYING, OPENED, DOWNLOADING, LISTENING, DOWNLOADED, CVOLUME, WTF;
	public static String HELPHELP, HELPRELOAD, HELPLIST, HELPPLAY, HELPPLAYID, HELPTUNE, HELPSTOP, HELPSHUFFLE, HELPSKIP, HELPGUI, HELPIMPORT, HELPVOLUME;
	public static String ITEMBACKMAIN, ITEMBACK, ITEMNEXT, ITEMSTOP, ITEMSKIP, ITEMPLAY, ITEMSHUFFLE, ITEMTUNE, ITEMREFRESH, ITEMVOLUME, ITEMFILTER;
	public static String CMDHELP, CMDRELOAD, CMDLIST, CMDPLAY, CMDTUNE, CMDSTOP, CMDSHUFFLE, CMDSKIP, CMDGUI, CMDIMPORT, CMDVOLUME;
	public static String MISCCLEARFILTER;
	
	public static void loadLang(boolean forceDefault) {
		SimplyMusic.inst().reloadConfig();
		
		SimplyMusic.log("Loading custom prefixes");
		
		if(SimplyMusic.inst().getConfig().getString("title_inventory") == null) SimplyMusic.inst().getConfig().set("title_inventory", MiscText.TITLEINVENTORY.toString());
		if(SimplyMusic.inst().getConfig().getString("title_volume") == null) SimplyMusic.inst().getConfig().set("title_volume", MiscText.TITLEVOLUME.toString());
		if(SimplyMusic.inst().getConfig().getString("title_tune") == null) SimplyMusic.inst().getConfig().set("title_tune", MiscText.TITLETUNE.toString());
		
		String titleinventory = SimplyMusic.inst().getConfig().getString("title_inventory");
		String titlevolume = SimplyMusic.inst().getConfig().getString("title_volume");
		String titletune = SimplyMusic.inst().getConfig().getString("title_tune");
		
		INVENTORY_TITLE = ChatColor.translateAlternateColorCodes('&', titleinventory);
		VOLUME_TITLE = ChatColor.translateAlternateColorCodes('&', titlevolume);
		TUNE_TITLE = ChatColor.translateAlternateColorCodes('&', titletune);
		
		PREFIX = INVENTORY_TITLE + ": " + WHITE;
		
		SimplyMusic.log("Loaded custom prefixes");
		
		boolean custom = SimplyMusic.inst().getConfig().getBoolean("custom_lang");
		
		if(custom == false || forceDefault) {
			NEPERMISSIONS = CommandResponse.NEPERMISSIONS.toString();
			NPLAYER       = CommandResponse.NPLAYER.toString();
			UNKNOWN       = CommandResponse.UNKNOWN.toString();
			IARGS         = CommandResponse.IARGS.toString();
			IINT          = CommandResponse.IINT.toString();
			ISONG         = CommandResponse.ISONG.toString();
			IPLAYER       = CommandResponse.IPLAYER.toString();
			NSONGS        = CommandResponse.NSONGS.toString();
			YPLAYER       = CommandResponse.YPLAYER.toString();
			NLPLAYER      = CommandResponse.NLPLAYER.toString();
			NPLAYING      = CommandResponse.NPLAYING.toString();
			IVOLUME       = CommandResponse.IVOLUME.toString();
			IDOWNLOAD     = CommandResponse.IDOWNLOAD.toString();
			INTERRUPTED   = CommandResponse.INTERRUPTED.toString();
			RELOADING     = CommandResponse.RELOADING.toString();
			RELOADED      = CommandResponse.RELOADED.toString();
			NOWPLAYING    = CommandResponse.NOWPLAYING.toString();
			STOPPED       = CommandResponse.STOPPED.toString();
			TUNED         = CommandResponse.TUNED.toString();
			SHUFFLED      = CommandResponse.SHUFFLED.toString();
			SKIPPED       = CommandResponse.SKIPPED.toString();
			PLAYING       = CommandResponse.PLAYING.toString();
			LISTENING     = CommandResponse.LISTENING.toString();
			OPENED        = CommandResponse.OPENED.toString();
			DOWNLOADING   = CommandResponse.DOWNLOADING.toString();
			DOWNLOADED    = CommandResponse.DOWNLOADED.toString();
			CVOLUME       = CommandResponse.CVOLUME.toString();
			WTF           = CommandResponse.WTF.toString();
			
			HELPHELP      = CommandDescriptions.HELP.toString();
			HELPRELOAD    = CommandDescriptions.RELOAD.toString();
			HELPLIST      = CommandDescriptions.LIST.toString();
			HELPPLAY      = CommandDescriptions.PLAY.toString();
			HELPPLAYID    = CommandDescriptions.PLAYID.toString();
			HELPTUNE      = CommandDescriptions.TUNE.toString();
			HELPSTOP      = CommandDescriptions.STOP.toString();
			HELPSHUFFLE   = CommandDescriptions.SHUFFLE.toString();
			HELPSKIP      = CommandDescriptions.SKIP.toString();
			HELPGUI       = CommandDescriptions.GUI.toString();
			HELPIMPORT    = CommandDescriptions.IMPORT.toString();
			HELPVOLUME    = CommandDescriptions.VOLUME.toString();
			
			ITEMBACKMAIN  = ItemNames.BACKMAIN.toString();
			ITEMBACK      = ItemNames.BACK.toString();
			ITEMNEXT      = ItemNames.NEXT.toString();
			ITEMSTOP      = ItemNames.STOP.toString();
		    ITEMSKIP      = ItemNames.SKIP.toString();
			ITEMPLAY      = ItemNames.PLAY.toString();
			ITEMSHUFFLE   = ItemNames.SHUFFLE.toString();
			ITEMTUNE      = ItemNames.TUNE.toString();
			ITEMREFRESH   = ItemNames.REFRESH.toString();
			ITEMVOLUME    = ItemNames.VOLUME.toString();
			ITEMFILTER    = ItemNames.FILTER.toString();
			
			CMDHELP       = Commands.HELP.toString();
			CMDRELOAD     = Commands.RELOAD.toString();
			CMDLIST       = Commands.LIST.toString();
			CMDPLAY       = Commands.PLAY.toString();
			CMDTUNE       = Commands.TUNE.toString();
			CMDSTOP       = Commands.STOP.toString();
			CMDSHUFFLE    = Commands.SHUFFLE.toString();
			CMDSKIP       = Commands.SKIP.toString();
			CMDGUI        = Commands.GUI.toString();
			CMDIMPORT     = Commands.IMPORT.toString();
			CMDVOLUME     = Commands.VOLUME.toString();
			
			MISCCLEARFILTER = MiscText.CLEARFILTER.toString();
		}else {
			YamlConfiguration lang = new YamlConfiguration();
			File langFile = new File("plugins/" + SimplyMusic.inst().getName() + "/" + SimplyMusic.inst().getConfig().getString("lang") + ".yml");
		
			if(!langFile.exists()) {
				System.out.println("Couldn't load language " + SimplyMusic.inst().getConfig().getString("lang"));
				loadLang(true);
			}else {
				try {
					lang.load(langFile);
				} catch (IOException | InvalidConfigurationException e) { }
			
				NEPERMISSIONS = PREFIX + ChatColor.RED + lang.getString("nepermissions");
				NPLAYER       = PREFIX + ChatColor.RED + lang.getString("nplayer");
				UNKNOWN       = PREFIX + ChatColor.RED + lang.getString("unknown");
				IARGS         = PREFIX + ChatColor.RED + lang.getString("iargs");
				IINT          = PREFIX + ChatColor.RED + lang.getString("iint");
				ISONG         = PREFIX + ChatColor.RED + lang.getString("isong");
				IPLAYER       = PREFIX + ChatColor.RED + lang.getString("iplayer");
				NSONGS        = PREFIX + ChatColor.RED + lang.getString("nsongs");
				YPLAYER       = PREFIX + ChatColor.RED + lang.getString("yplayer");
				NLPLAYER      = PREFIX + ChatColor.RED + lang.getString("nlplayer");
				NPLAYING      = PREFIX + ChatColor.RED + lang.getString("nplaying");
				IVOLUME       = PREFIX + ChatColor.RED + lang.getString("ivolume");
				IDOWNLOAD     = PREFIX + ChatColor.RED + lang.getString("idownload");
				INTERRUPTED   = PREFIX + ChatColor.RED + lang.getString("interrupted");
				RELOADING     = PREFIX + ChatColor.GREEN + lang.getString("reloading");
				RELOADED      = PREFIX + ChatColor.GREEN + lang.getString("reloaded");
				NOWPLAYING    = PREFIX + ChatColor.GREEN + lang.getString("nowplaying");
				STOPPED       = PREFIX + ChatColor.GREEN + lang.getString("stopped");
				TUNED         = PREFIX + ChatColor.GREEN + lang.getString("tuned");
				SHUFFLED      = PREFIX + ChatColor.GREEN + lang.getString("shuffled");
				SKIPPED       = PREFIX + ChatColor.GREEN + lang.getString("skipped");
				PLAYING       = PREFIX + ChatColor.GREEN + lang.getString("playing");
				LISTENING     = PREFIX + ChatColor.GREEN + lang.getString("listening");
				OPENED        = PREFIX + ChatColor.GREEN + lang.getString("opened");
				DOWNLOADING   = PREFIX + ChatColor.GREEN + lang.getString("downloading");
				DOWNLOADED    = PREFIX + ChatColor.GREEN + lang.getString("downloaded");
				CVOLUME       = PREFIX + ChatColor.GREEN + lang.getString("cvolume");
				WTF           = PREFIX + ChatColor.RED + lang.getString("wtf");
				
				HELPHELP      = lang.getString("help_help");
				HELPRELOAD    = lang.getString("help_reload");
				HELPLIST      = lang.getString("help_list");
				HELPPLAY      = lang.getString("help_play");
				HELPPLAYID    = lang.getString("help_playid");
				HELPTUNE      = lang.getString("help_tune");
				HELPSTOP      = lang.getString("help_stop");
				HELPSHUFFLE   = lang.getString("help_shuffle");
				HELPSKIP      = lang.getString("help_skip");
				HELPGUI       = lang.getString("help_gui");
				HELPIMPORT    = lang.getString("help_import");
				HELPVOLUME    = lang.getString("help_volume");
				
				ITEMBACKMAIN  = lang.getString("item_backmain");
				ITEMBACK      = lang.getString("item_back");
				ITEMNEXT      = lang.getString("item_next");
				ITEMSTOP      = lang.getString("item_stop");
			    ITEMSKIP      = lang.getString("item_skip");
				ITEMPLAY      = lang.getString("item_play");
				ITEMSHUFFLE   = lang.getString("item_shuffle");
				ITEMTUNE      = lang.getString("item_tune");
				ITEMREFRESH   = lang.getString("item_refresh");
				ITEMVOLUME    = lang.getString("item_volume");
				ITEMFILTER    = lang.getString("item_filter");
				
				CMDHELP       = lang.getString("cmd_help");
				CMDRELOAD     = lang.getString("cmd_reload");
				CMDLIST       = lang.getString("cmd_list");
				CMDPLAY       = lang.getString("cmd_play");
				CMDTUNE       = lang.getString("cmd_tune");
				CMDSTOP       = lang.getString("cmd_stop");
				CMDSHUFFLE    = lang.getString("cmd_shuffle");
				CMDSKIP       = lang.getString("cmd_skip");
				CMDGUI        = lang.getString("cmd_gui");
				CMDIMPORT     = lang.getString("cmd_import");
				CMDVOLUME     = lang.getString("cmd_volume");
				
				MISCCLEARFILTER = lang.getString("misc_clearfilter");
				
				SimplyMusic.log("Loaded language " + lang.getString("language"));
			}
		}
	}
}
