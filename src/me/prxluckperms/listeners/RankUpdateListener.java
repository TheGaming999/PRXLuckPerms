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

public class RankUpdateListener implements IRankUpdateListener {

	private EasyLuckPerms ezLuckPerms;
	private PRXLuckPerms plugin;
	
	/**
	 * Initiate the listener and register it to the plugin.
	 * @param plugin the plugin to register the listener to.
	 */
	public RankUpdateListener(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.ezLuckPerms = this.plugin.getEzLuckPerms();
	}

	@EventHandler
	public void onRankup(RankUpdateEvent e) {
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		ezLuckPerms.deletePlayerGroups(uuid, true).thenRunAsync(() -> {
			CompletableFuture.supplyAsync(() -> e.getRankup()).thenAcceptAsync(rankup -> {
				ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(rankup), true);
			});
		});
	}

	@EventHandler
	public void onAutoRankup(AsyncAutoRankupEvent e) {
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		ezLuckPerms.deletePlayerGroups(uuid, true).thenRunAsync(() ->
		ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(e.getRankupTo()), true));
	}

	@EventHandler
	public void onRankupMax(AsyncRankupMaxEvent e) {
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
			ezLuckPerms.deletePlayerGroups(uuid, true).thenRunAsync(() -> 
			ezLuckPerms.setPlayerGroup(uuid, ezLuckPerms.getGroup(e.getFinalRankup()), true));
		}, 1);
	}

}
