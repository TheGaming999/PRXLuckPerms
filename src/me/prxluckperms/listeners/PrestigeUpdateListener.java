package me.prxluckperms.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.prisonranksx.events.AsyncAutoPrestigeEvent;
import me.prisonranksx.events.AsyncPrestigeMaxEvent;
import me.prisonranksx.events.PrestigeUpdateEvent;
import me.prxluckperms.PRXLuckPerms;
import me.prxluckperms.interfaces.GroupUpdateTrack;
import me.prxluckperms.interfaces.GroupUpdateTrackless;
import me.prxluckperms.interfaces.IGroupUpdate;

public class PrestigeUpdateListener implements Listener {

	private PRXLuckPerms plugin;
	private String trackName;
	private IGroupUpdate groupUpdater;

	/**
	 * Initiate the listener and register it to the plugin.
	 * @param plugin the plugin to register the listener to.
	 */
	public PrestigeUpdateListener(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.trackName = this.plugin.getTrackName();
		this.groupUpdater = trackName != null ? new GroupUpdateTrack(this.plugin) : 
			new GroupUpdateTrackless(this.plugin);
	}

	private void execute(UUID uuid) {
		groupUpdater.set(uuid);
	}

	@EventHandler
	public void onPrestige(PrestigeUpdateEvent e) {
		execute(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPrestigeMax(AsyncPrestigeMaxEvent e) {
		execute(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onAutoPrestige(AsyncAutoPrestigeEvent e) {
		execute(e.getPlayer().getUniqueId());
	}

}
