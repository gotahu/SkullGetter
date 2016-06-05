package com.github.gotochan;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class SkullGetter extends JavaPlugin implements Listener
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			
			public void run()
			{
				if ( SkullEventer.sInventory != null )
				{
					SkullEventer.sInventory.updateInventory();
				}
			}
		} , 0L, 10L);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	@Override
	public boolean onCommand(
			CommandSender sender,
			Command cmd,
			String label,
			String[] args)
	{
		if ( sender instanceof Player )
		{
			Player player = (Player) sender;
			
			if ( cmd.getName().equalsIgnoreCase("skullgetter") )
			{
				if ( args.length == 0 )
				{
					helpMessage(player);
					return true;
				}
				
				if ( args[0].equalsIgnoreCase("get") )
				{
					
					player.getInventory().addItem(getSkullGetter());
				}
				else if ( args[0].equalsIgnoreCase("give") )
				{
					if ( args.length == 1 )
					{
						player.sendMessage("§c[SkullGetter] プレイヤー名を指定してください!");
					}
					else if ( args.length == 2 )
					{
						Player givePlayer = Bukkit.getPlayer(args[1]);
						if ( givePlayer == null )
						{
							player.sendMessage("§c[SkullGetter] そのプレイヤー名は存在しません!");
						}
						else
						{
							givePlayer.getInventory().addItem(getSkullGetter());
						}
					}
					else
					{
						helpMessage(player);
					}
				}
				else if ( args[0].equalsIgnoreCase("help") )
				{
					helpMessage(player);
				}
				else
				{
					helpMessage(player);
				}
			}
		}
		else
		{
			sender.sendMessage("§c[SkullGetter] ゲーム内から実行してください。");
		}
		return false;
	}
	
	private void helpMessage(Player player)
	{
		String[] msg = {
				"§6/sg get - スカルゲッターをインベントリに追加します。",
				"§6/sg give <player> - スカルゲッターを指定したプレイヤーのインベントリに追加します。",
				"§6/sg help - このヘルプを参照します。",
		};
		for ( int i = 0; i < msg.length; i++ )
		{
			player.sendMessage(msg[i]);
		}
	}
	
	private ItemStack getSkullGetter()
	{
		@SuppressWarnings("deprecation")
		ItemStack skullgetter = new ItemStack(Material.SKULL_ITEM, 1, (short)0, (byte)3);
		ItemMeta skull_meta = skullgetter.getItemMeta();
		skull_meta.setDisplayName("§r§aSkullGetter");
		skullgetter.setItemMeta(skull_meta);
		return skullgetter;
	}
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event)
	{
		SkullEventer.Interact(event);
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event)
	{
		SkullEventer.InventoryClick(event);
	}
	
	
}
