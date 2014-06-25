package io.github.toxicbyte.toxicShop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Shop 
{
	private String name;
	private Inventory sell;
	private Inventory buy;
	private Inventory menu;
	private Cuboid loc;
	public Shop(String name, Cuboid loc, Inventory buy)
	{
		this.name = name;
		this.loc = loc;
		this.sell = Bukkit.createInventory(null, 54, ChatColor.RED + "Sell");
		this.buy = buy;
		this.menu = Bukkit.createInventory(null, 2, ChatColor.YELLOW + "Menu");
		makeMenu();
	}
	public Cuboid getArea()
	{
		return this.loc;
	}
	public Inventory getItems()
	{
		Inventory inv = null;
		return inv;
	}
	public void openSell(Player target)
	{
		target.openInventory(this.sell);
	}
	public void openBuy(Player target)
	{
		target.openInventory(this.buy);
	}
	public void openMenu(Player target)
	{
		target.openInventory(this.menu);
	}
	public void addBuy(ItemStack items, int value)
	{
		toxicShop ts = new toxicShop();
		if (ts.getConfig().get("Shops." + name + ".buy") != null)
		{
			List<String> buys = ts.getConfig().getStringList("Shops." + name + ".buy");
			buys.add(makeSave(items.getType().name(), String.valueOf(items.getAmount()), String.valueOf(value)));
		}
		else
		{   
		   List<String> buys = new ArrayList<String>();
		   buys.add(makeSave(items.getType().name(), String.valueOf(items.getAmount()), String.valueOf(value)));
		   ts.getConfig().set("Shops." + name + ".buy", buys);
		   ts.saveConfig();			
		}
	}
	public Inventory getBuy()
	{
		return this.buy;
	}
	public String getName()
	{
		return this.name;
	}
	@SuppressWarnings("deprecation")
	private void makeMenu()
	{
		ItemStack sell = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
		ItemStack buy = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
		sell.getItemMeta().setDisplayName(ChatColor.RED + "Sell");
		buy.getItemMeta().setDisplayName(ChatColor.GREEN + "Buy");
		this.menu.addItem(sell);
		this.menu.addItem(buy);
	}
	private String makeSave(String mat, String amount, String value)
	{
		return mat + "-" + amount + "-" + value;
	}
	private String getMat(String word)
	{
		return word.split("-")[0];
	}
	private int getAmount(String word)
	{
		return Integer.parseInt(word.split("-")[1]);
	}
	private int getValue(String word)
	{
		return Integer.parseInt(word.split("-")[2]);
	}
}
