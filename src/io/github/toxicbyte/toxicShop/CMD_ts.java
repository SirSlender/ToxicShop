package io.github.toxicbyte.toxicShop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_ts implements CommandExecutor 
{
	private toxicShop plugin;
	private String prefix;
	public CMD_ts(toxicShop plugin)
	{
		this.plugin = plugin;
		prefix = plugin.getPrefix();
	}
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) 
	{
		if(cs instanceof Player && cmd.getName().equalsIgnoreCase("shop"))
		{
			final Player player = (Player) cs;
			if(args.length == 2)
			{
				if(args[0].equalsIgnoreCase("create"))
				{
					if(player.hasPermission("toxicshop.create"))
					{
						String name = args[1];
						if (plugin.getLoc1() != null && plugin.getLoc2() != null)
						{
							Cuboid c = new Cuboid(plugin.getLoc1(), plugin.getLoc2());
							if (plugin.getConfig().getConfigurationSection("Shops").getKeys(false).contains(player.getName()))
							{
								plugin.getConfig().set("Shops." + name + ".coords" + ".minX", c.getLowerX());
								plugin.getConfig().set("Shops." + name + ".coords" + ".minY", c.getLowerY());
								plugin.getConfig().set("Shops." + name + ".coords" + ".minZ", c.getLowerZ());
								plugin.getConfig().set("Shops." + name + ".coords" + ".maxX", c.getUpperX());
								plugin.getConfig().set("Shops." + name + ".coords" + ".maxX", c.getUpperY());
								plugin.getConfig().set("Shops." + name + ".coords" + ".maxX", c.getUpperZ());
								plugin.getConfig().set("Shops." + name + ".coords" + ".world", c.getWorld().getName());
								plugin.getConfig().set("Shops." + name + ".buy", null);
								plugin.getConfig().set("Shops." + name + ".sell", null);
								plugin.saveConfig();
								player.sendMessage("Shop created!");
							}
							else
							{
								plugin.getConfig().set("Shops." + name + ".coords" + ".minX", c.getLowerX());
								plugin.getConfig().set("Shops." + name + ".coords" + ".minY", c.getLowerY());
								plugin.getConfig().set("Shops." + name + ".coords" + ".minZ", c.getLowerZ());
								plugin.getConfig().set("Shops." + name + ".coords" + ".maxX", c.getUpperX());
								plugin.getConfig().set("Shops." + name + ".coords" + ".maxX", c.getUpperY());
								plugin.getConfig().set("Shops." + name + ".coords" + ".maxX", c.getUpperZ());
								plugin.getConfig().set("Shops." + name + ".coords" + ".world", c.getWorld().getName());
								plugin.getConfig().set("Shops." + name + ".buy", null);
								plugin.getConfig().set("Shops." + name + ".sell", null);
								plugin.saveConfig();
								player.sendMessage("Shop created!");
							}
						}
					}
					else
					{
						player.sendMessage(prefix + "You don't have permission to perform this action!");
					}
				}
				else if(args[0].equalsIgnoreCase("del"))
				{
					if(player.hasPermission("toxicshop.delete"))
					{
						String name = args[1];
						if(plugin.getConfig().getConfigurationSection("Shops").getKeys(false).contains(name))
						{
							plugin.getConfig().set("Shops." + name, null);
							plugin.saveConfig();
							player.sendMessage(prefix + "Shop deleted!");
						}
						else
						{
							player.sendMessage(prefix + "That shop doesn't exist!");
						}
					}
					else
					{
						player.sendMessage(prefix + "You don't have permission to perform this action!");
					}
				}
				else
				{
					player.sendMessage(prefix + "Correct usage: /shop help");
				}
			}
			else if(args.length == 1)
			{
				if (args[0].equalsIgnoreCase("help"))
				{
					
				}
				else
				{
					player.sendMessage("Correct usage: /shop help");
				}
			}
			else
			{
				
			}
		}
		return false;
	}

}
