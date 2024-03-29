package com.github.zamponimarco.elytrabooster.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class PlayerBoostEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean cancelled;
	private Player player;

	public PlayerBoostEvent(ElytraBooster plugin, Player player, Boost boost) {
		
		this.player = player;

		BukkitRunnable boostProcess = new BukkitRunnable() {

			double tempVelocity = boost.getInitialVelocity();
			double d = Math.pow((boost.getFinalVelocity() / boost.getInitialVelocity()),
					(1.0 / boost.getBoostDuration()));

			int counter = 0;

			@Override
			public void run() {

				if (counter == boost.getBoostDuration()) {
					plugin.getStatusMap().replace(player, false);
					this.cancel();
				} else if (!player.isGliding()) {
					plugin.getStatusMap().remove(player);
					this.cancel();
				}

				sendProgressMessage(player, boost, counter);
				boost.getTrail().spawnTrail(player);
				player.setVelocity(player.getLocation().getDirection().normalize().multiply(tempVelocity));

				counter++;
				tempVelocity *= d;
			}
		};

		plugin.getStatusMap().replace(player, true);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 20, 1);
		boostProcess.runTaskTimer(plugin, 0, 1);
	}

	/**
	 * Sends to the player the progress bar in the action bar
	 * 
	 * @param player
	 * @param portal
	 * @param counter
	 */
	private void sendProgressMessage(Player player, Boost boost, int counter) {
		int progress = (int) Math.floor((counter / (double) boost.getBoostDuration()) * 30);

		StringBuilder sb = new StringBuilder("");
		sb.append("&a");
		sb.append(MessagesUtil.repeat(30 - progress, "|"));
		sb.append("&c");
		sb.append(MessagesUtil.repeat(progress, "|"));

		player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
				new ComponentBuilder(
						MessagesUtil.color(MessagesUtil.color(String.format("&2Boost &6[%s&6]", sb.toString()))))
								.create());
	}

	/**
	 * Returns the Handler List of the event
	 */
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	/**
	 * Returns whether the event is cancelled
	 */
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets the cancellation of the event
	 */
	@Override
	public void setCancelled(boolean isCancelled) {
		cancelled = isCancelled;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
