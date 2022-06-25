package me.prxluckperms.listeners;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import me.prisonranksx.events.AsyncAutoRankupEvent;
import me.prisonranksx.events.AsyncRankRegisterEvent;
import me.prisonranksx.events.AsyncRankupMaxEvent;
import me.prisonranksx.events.RankUpdateEvent;
import me.prxluckperms.EZLuckPerms;
import me.prxluckperms.PRXLuckPerms;

public class RankUpdateListener implements IRankUpdateListener {

	private PRXLuckPerms plugin;

	/**
	 * Initiate the listener and register it to the plugin.
	 * 
	 * @param plugin the plugin to register the listener to.
	 */
	public RankUpdateListener(PRXLuckPerms plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onRankRegister(AsyncRankRegisterEvent e) {
		if (e.isCancelled()) return;
		UUID uuid = e.getUniqueId();
		EZLuckPerms.deletePlayerGroups(uuid, true).thenRunAsync(() -> {
			CompletableFuture.supplyAsync(() -> e.getRankPath().getRankName()).thenAcceptAsync(rankup -> {
				EZLuckPerms.setPlayerGroup(uuid, EZLuckPerms.getGroup(rankup), true);
			});
		});
	}

	@Override
	@EventHandler
	public void onRankup(RankUpdateEvent e) {
		if (e.isCancelled()) return;
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		EZLuckPerms.deletePlayerGroups(uuid, true).thenRunAsync(() -> {
			CompletableFuture.supplyAsync(() -> e.getRankup()).thenAcceptAsync(rankup -> {
				EZLuckPerms.setPlayerGroup(uuid, EZLuckPerms.getGroup(rankup), true);
			});
		});
	}

	@Override
	@EventHandler
	public void onAutoRankup(AsyncAutoRankupEvent e) {
		if (e.isCancelled()) return;
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		EZLuckPerms.deletePlayerGroups(uuid, true)
				.thenRunAsync(() -> EZLuckPerms.setPlayerGroup(uuid, EZLuckPerms.getGroup(e.getRankupTo()), true));
	}

	@Override
	@EventHandler
	public void onRankupMax(AsyncRankupMaxEvent e) {
		if (e.isCancelled()) return;
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
			EZLuckPerms.deletePlayerGroups(uuid, true)
					.thenRunAsync(
							() -> EZLuckPerms.setPlayerGroup(uuid, EZLuckPerms.getGroup(e.getFinalRankup()), true));
		}, 1);
	}

}
