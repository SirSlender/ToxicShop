package io.github.toxicbyte.toxicShop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class tsListener implements Listener
{
	private String prefix;
	private toxicShop plugin;
	private Shop shop;
	public tsListener(toxicShop plugin)
	{
		this.plugin = plugin;
		prefix = plugin.getPrefix();
	}
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		for (String shops : plugin.getConfig().getConfigurationSection("PlayerShop." + e.getPlayer().getName() + ".shops").getKeys(false))
		{
			if (e.getFrom
					().getBlockX() != e.getTo().getBlockX() 
					|| e.getFrom().getBlockY() != e.getTo().getBlockY() 
					|| e.getFrom().getBlockZ() != e.getTo().getBlockZ())
			{
				Cuboid c = new Cuboid(
						new Location(
						plugin.getServer().getWorld(plugin.getConfig().getString("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".coords" + ".world"))
						, plugin.getConfig().getInt("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".coords" + ".minX")
						, plugin.getConfig().getInt("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".coords" + ".minY")
						, plugin.getConfig().getInt("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".coords" + ".minZ")
						), 
						new Location(
						plugin.getServer().getWorld(plugin.getConfig().getString("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".coords" + ".world"))
						, plugin.getConfig().getInt("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".coords" + ".maxX")
						, plugin.getConfig().getInt("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".coords" + ".maxY")
						, plugin.getConfig().getInt("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".coords" + ".maxZ")));
				if (c.contains(e.getTo()))
				{
					if (plugin.timeOut.containsKey(e.getPlayer()) && plugin.timeOut.get(e.getPlayer()))
					{
						
					}
					else
					{
						if (plugin.getConfig().get("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".buy") != null)
						{
							Inventory inv = plugin.getServer().createInventory(null, 54, ChatColor.GREEN + "Buy");
							inv.setContents((ItemStack[]) plugin.getConfig().get("PlayerShops." + e.getPlayer().getName() + ".shops." + shops + ".buy"));
							Shop shop = new Shop(shops, c, inv);
							plugin.inShop.put(e.getPlayer(), shop);
							shop.openMenu(e.getPlayer());
						}
						
					}
				}
				else
				{
					if (plugin.timeOut.containsKey(e.getPlayer()) && plugin.timeOut.get(e.getPlayer()))
					{
						plugin.timeOut.remove(e.getPlayer());
					}
				}
			}
		}
	}
	@EventHandler
	public void onPlayerSelect(PlayerInteractEvent e)
	{
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK 
				&& e.getPlayer().hasPermission("toxicshop.select") 
				&& e.getPlayer().getItemInHand().getType() == Material.WOOD_SPADE)
		{
			plugin.setLoc2(e.getClickedBlock().getLocation());
			e.setCancelled(true);
			e.getPlayer().sendMessage(prefix + "Second location set!");
		}
		else if(e.getAction() == Action.LEFT_CLICK_BLOCK
				&& e.getPlayer().hasPermission("toxicshop.select") 
				&& e.getPlayer().getItemInHand().getType() == Material.WOOD_SPADE)
		{
			plugin.setLoc1(e.getClickedBlock().getLocation());
			e.setCancelled(true);
			e.getPlayer().sendMessage(prefix + "First location set!");
		}
	}
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e)
	{
		if (e.getWhoClicked() instanceof Player)
		{
			Player player = (Player) e.getWhoClicked();
			if (player.hasPermission("toxicshop.use") && plugin.inShop.containsKey(player))
			{
				if (ChatColor.stripColor(e.getInventory().getName()) == "Menu")
				{
					if (e.getCursor().getType() == Material.WOOL && ChatColor.stripColor(e.getCursor().getItemMeta().getDisplayName()) == "Sell")
					{
						shop.openSell(player);
					}
					if (e.getCursor().getType() == Material.WOOL && ChatColor.stripColor(e.getCursor().getItemMeta().getDisplayName()) == "Buy")
					{
						shop.openBuy(player);
						
					}
				}
				else if(ChatColor.stripColor(e.getInventory().getName()) == "Buy")
				{
					
				}
				else if(ChatColor.stripColor(e.getInventory().getName()) == "Sell")
				{
					
				}
				else
				{
					
				}
			}
		}
	}
	@EventHandler
	public void onCloseInv(InventoryCloseEvent e)
	{
		if(plugin.inShop.containsKey(e.getPlayer()))
		{
			plugin.inShop.remove(e.getPlayer());
		}
	}
}
