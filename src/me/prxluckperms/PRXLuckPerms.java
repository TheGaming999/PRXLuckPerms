package me.prxluckperms;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.prisonranksx.api.PRXAPI;
import me.prisonranksx.events.AsyncAutoPrestigeEvent;
import me.prisonranksx.events.AsyncAutoRankupEvent;
import me.prisonranksx.events.AsyncPrestigeMaxEvent;
import me.prisonranksx.events.AsyncRankupMaxEvent;
import me.prisonranksx.events.PrestigeUpdateEvent;
import me.prisonranksx.events.RankUpdateEvent;
import me.prxluckperms.listeners.IRankUpdateListener;
import me.prxluckperms.listeners.PrestigeUpdateListener;
import me.prxluckperms.listeners.RankUpdateListener;
import me.prxluckperms.listeners.RankUpdateListenerTrack;

public class PRXLuckPerms extends JavaPlugin implements Listener {

	private EasyLuckPerms ezLuckPerms;
	private String trackName;
	private boolean resetPlayerGroupOnPrestige;
	private String resetPlayerGroupName;
	private IRankUpdateListener rankUpdateListener;
	private PrestigeUpdateListener prestigeUpdateListener;
	private PRXAPI api;

	public void onEnable() {
		this.ezLuckPerms = new EasyLuckPerms();
		this.api = new PRXAPI();
		this.getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();
		this.loadData();
		this.registerListeners();
		this.getLogger().info("PRXLuckPerms is on!");
	}

	public void loadData() {
		this.trackName = getConfig().getString("track", "");
		if(this.trackName.isEmpty()) this.trackName = null;
		this.resetPlayerGroupOnPrestige = getConfig().getBoolean("reset-player-group-on-prestige", false);
		this.resetPlayerGroupName = getConfig().getString("reset-player-group-name", "");
		if(this.resetPlayerGroupName.isEmpty()) this.resetPlayerGroupName = null; 
	}

	public void registerListeners() {
		if(trackName == null)
			rankUpdateListener = new RankUpdateListener(this);
		else
			rankUpdateListener = new RankUpdateListenerTrack(this);
		
		if(resetPlayerGroupOnPrestige) prestigeUpdateListener = new PrestigeUpdateListener(this);
	}

	public void unregisterListeners() {
		RankUpdateEvent.getHandlerList().unregister(rankUpdateListener);
		AsyncRankupMaxEvent.getHandlerList().unregister(rankUpdateListener);
		AsyncAutoRankupEvent.getHandlerList().unregister(rankUpdateListener);
		PrestigeUpdateEvent.getHandlerList().unregister(prestigeUpdateListener);
		AsyncPrestigeMaxEvent.getHandlerList().unregister(prestigeUpdateListener);
		AsyncAutoPrestigeEvent.getHandlerList().unregister(prestigeUpdateListener);
	}

	public void onDisable() {
		unregisterListeners();
		getLogger().info("PRXLuckPerms is off!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.isOp()) return true;
		if(cmd.getLabel().equalsIgnoreCase("prxluckperms")) {
			sender.sendMessage("Reloading...");
			reloadConfig();
			unregisterListeners();
			loadData();
			registerListeners();
			String gotTrackName = trackName == null ? "<none>" : trackName;
			sender.sendMessage("Track: " + gotTrackName);
			sender.sendMessage("Config reloaded.");
		}
		return true;
	}

	public PRXAPI getAPI() {
		return this.api;
	}

	public EasyLuckPerms getEzLuckPerms() {
		return this.ezLuckPerms;
	}

	public String getTrackName() {
		return this.trackName;
	}

	public boolean isResetPlayerGroupOnPrestige() {
		return this.resetPlayerGroupOnPrestige;
	}

	public String getResetPlayerGroupName() {
		return this.resetPlayerGroupName;
	}
	
}
