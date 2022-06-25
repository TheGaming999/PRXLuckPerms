package me.prxluckperms;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.prisonranksx.api.PRXAPI;
import me.prisonranksx.events.AsyncAutoPrestigeEvent;
import me.prisonranksx.events.AsyncAutoRankupEvent;
import me.prisonranksx.events.AsyncPrestigeMaxEvent;
import me.prisonranksx.events.AsyncRankRegisterEvent;
import me.prisonranksx.events.AsyncRankupMaxEvent;
import me.prisonranksx.events.PrestigeUpdateEvent;
import me.prisonranksx.events.RankUpdateEvent;
import me.prxluckperms.listeners.IRankUpdateListener;
import me.prxluckperms.listeners.PrestigeUpdateListener;
import me.prxluckperms.listeners.RankUpdateListener;
import me.prxluckperms.listeners.RankUpdateListenerServer;
import me.prxluckperms.listeners.RankUpdateListenerTrack;
import me.prxluckperms.listeners.RankUpdateListenerTrackServer;

public class PRXLuckPerms extends JavaPlugin implements Listener {

	private String trackName;
	private boolean resetPlayerGroupOnPrestige;
	private String resetPlayerGroupName;
	private String serverName;
	private IRankUpdateListener rankUpdateListener;
	private PrestigeUpdateListener prestigeUpdateListener;
	private PRXAPI api;

	@Override
	public void onEnable() {
		if (EZLuckPerms.getLuckPerms() == null) getLogger().severe("LuckPerms is not installed in your server!");
		api = new PRXAPI();
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		loadData();
		registerListeners();
		getLogger().info("PRXLuckPerms is on!");
	}

	public void loadData() {
		trackName = getConfig().getString("track", "");
		if (trackName.isEmpty()) trackName = null;
		resetPlayerGroupOnPrestige = getConfig().getBoolean("reset-player-group-on-prestige", false);
		resetPlayerGroupName = getConfig().getString("reset-player-group-name", "");
		if (resetPlayerGroupName.isEmpty()) resetPlayerGroupName = this.getAPI().getDefaultRank();
		serverName = getConfig().getString("server", "");
		if (serverName.isEmpty()) serverName = null;
	}

	public void registerListeners() {
		if (trackName == null) {
			if (serverName == null)
				rankUpdateListener = new RankUpdateListener(this);
			else
				rankUpdateListener = new RankUpdateListenerServer(this);
		} else {
			if (serverName == null)
				rankUpdateListener = new RankUpdateListenerTrack(this);
			else
				rankUpdateListener = new RankUpdateListenerTrackServer(this);
		}
		if (resetPlayerGroupOnPrestige) prestigeUpdateListener = new PrestigeUpdateListener(this);
	}

	public void unregisterListeners() {
		RankUpdateEvent.getHandlerList().unregister(rankUpdateListener);
		AsyncRankupMaxEvent.getHandlerList().unregister(rankUpdateListener);
		AsyncAutoRankupEvent.getHandlerList().unregister(rankUpdateListener);
		AsyncRankRegisterEvent.getHandlerList().unregister(rankUpdateListener);
		PrestigeUpdateEvent.getHandlerList().unregister(prestigeUpdateListener);
		AsyncPrestigeMaxEvent.getHandlerList().unregister(prestigeUpdateListener);
		AsyncAutoPrestigeEvent.getHandlerList().unregister(prestigeUpdateListener);
	}

	@Override
	public void onDisable() {
		unregisterListeners();
		getLogger().info("PRXLuckPerms is off!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isOp()) return true;
		if (cmd.getLabel().equalsIgnoreCase("prxluckperms")) {
			sender.sendMessage("Reloading...");
			reloadConfig();
			unregisterListeners();
			loadData();
			registerListeners();
			String retrievedTrackName = trackName == null ? "<none>" : trackName;
			String retrievedServerName = serverName == null ? "<none>" : serverName;
			sender.sendMessage("Track: " + retrievedTrackName);
			sender.sendMessage("Server: " + retrievedServerName);
			sender.sendMessage("Config reloaded.");
		}
		return true;
	}

	public PRXAPI getAPI() {
		return api;
	}

	public String getTrackName() {
		return trackName;
	}

	public boolean isResetPlayerGroupOnPrestige() {
		return resetPlayerGroupOnPrestige;
	}

	public String getResetPlayerGroupName() {
		return resetPlayerGroupName;
	}

	public String getServerName() {
		return serverName;
	}

}
