package com.xxmicloxx.NoteBlockAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.tek.sm.SimplyMusic;

public class NoteBlockPlayerMain {

    public static NoteBlockPlayerMain plugin;
    
    public Map<String, ArrayList<SongPlayer>> playingSongs = Collections.synchronizedMap(new HashMap<String, ArrayList<SongPlayer>>());
    public Map<String, Byte> playerVolume = Collections.synchronizedMap(new HashMap<String, Byte>());

    private boolean disabling = false;
    
    public static boolean isReceivingSong(Player p) {
        return ((plugin.playingSongs.get(p.getName()) != null) && (!plugin.playingSongs.get(p.getName()).isEmpty()));
    }

    public static void stopPlaying(Player p) {
        if (plugin.playingSongs.get(p.getName()) == null) {
            return;
        }
        for (SongPlayer s : plugin.playingSongs.get(p.getName())) {
            s.removePlayer(p);
        }
    }

    public static void setPlayerVolume(Player p, byte volume) {
        plugin.playerVolume.put(p.getName(), volume);
    }

    public static byte getPlayerVolume(Player p) {
        Byte b = plugin.playerVolume.get(p.getName());
        if (b == null) {
            b = 100;
            plugin.playerVolume.put(p.getName(), b);
        }
        return b;
    }
    
    public void enable() {
    	plugin = this;
    }
    
    public void disable() {
    	disabling = true;
        Bukkit.getScheduler().cancelTasks(SimplyMusic.inst());
    }
    
    public void doSync(Runnable r) {
        SimplyMusic.inst().getServer().getScheduler().runTask(SimplyMusic.inst(), r);
    }

    public void doAsync(Runnable r) {
    	SimplyMusic.inst().getServer().getScheduler().runTaskAsynchronously(SimplyMusic.inst(), r);
    }
    
    protected boolean isDisabling(){
    	return disabling;
    }
     
}
