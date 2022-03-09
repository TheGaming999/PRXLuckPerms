package me.prxluckperms.listeners;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import me.prisonranksx.events.AsyncAutoRankupEvent;
import me.prisonranksx.events.AsyncRankupMaxEvent;
import me.prisonranksx.events.RankUpdateEvent;
import me.prxluckperms.EasyLuckPerms;
import me.prxluckperms.PRXLuckPerms;

public class RankUpdateListenerTrack implements IRankUpdateListener {

	private EasyLuckPerms ezLuckPerms;
	private String trackName;
	private PRXLuckPerms plugin;

	/**
	 * Initiate the listener and register it to the plugin.
	 * @param plugin the plugin to register the listener to.
	 */
	public RankUpdateListenerTrack(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.ezLuckPerms = this.plugin.getEzLuckPerms();
		this.trackName = this.plugin.getTrackName();
	}

	@EventHandler
	public void onRankup(RankUpdateEvent e) {
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		ezLuckPerms.deletePlayerGroups(uuid, trackName).thenRunAsync(() -> {
			CompletableFuture.supplyAsync(() -> e.getRankup()).thenAcceptAsync(rankup -> {
				ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(rankup), trackName);
			});
		});
	}

	@EventHandler
	public void onAutoRankup(AsyncAutoRankupEvent e) {
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		ezLuckPerms.deletePlayerGroups(uuid, trackName).thenRunAsync(() ->
		ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(e.getRankupTo()), trackName));
	}

	@EventHandler
	public void onRankupMax(AsyncRankupMaxEvent e) {
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
			ezLuckPerms.deletePlayerGroups(uuid, trackName).thenRunAsync(() ->
			ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(e.getFinalRankup()), trackName));
		}, 1);
	}

}
