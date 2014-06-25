package io.github.toxicbyte.toxicShop;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class toxicShop extends JavaPlugin
{
	public HashMap<Player, Shop> inShop = new HashMap<Player, Shop>();
	public HashMap<Player, Boolean> timeOut = new HashMap<Player, Boolean>();
	private Location loc1 = null;
	private Location loc2 = null;
	private String prefix;
	public void onEnable()
	{
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(new tsListener(this), this);
		prefix = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("Prefix"));			
		this.getCommand("shop").setExecutor(new CMD_ts(this));
	}
	public String getPrefix()
	{
		return prefix;
	}
	public void setLoc1(Location loc)
	{
		loc1 = loc;
	}
	public void setLoc2(Location loc)
	{
		loc2 = loc;
	}
	public Location getLoc1()
	{
		return loc1;
	}
	public Location getLoc2()
	{
		return loc2;
	}
}
