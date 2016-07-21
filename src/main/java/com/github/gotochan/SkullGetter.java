package com.github.gotochan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class SkullGetter extends JavaPlugin implements Listener
{

	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy econ = null;
	public static Permission perms = null;
	public static Chat chat = null;

	public static SkullGetter instance;

	@Override
	public void onEnable()
	{
		instance = this;
		SkullConfigrable sGetter = new SkullConfigrable(this);
		sGetter.loadConfig();
		if ( SkullConfigrable.isEnableEconomy )
		{
			if (!setupEconomy() ) {
				log.severe(String.format("[%s] - Vaultが導入されていません!", getDescription().getName()));
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
			setupPermissions();
			setupChat();
		}
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

			if ( !(player.hasPermission("sg.admin")) )
			{
				player.sendMessage("§c[SkullGetter] 権限がありません!");
				return true;
			}

			if ( cmd.getName().equalsIgnoreCase("skullgetter") )
			{
				if ( args.length == 0 )
				{
					helpMessage(player);
					return true;
				}

				if ( args[0].equalsIgnoreCase("get") )
				{
					player.sendMessage("§6[SkullGetter] SkullGetterを取得しました。");
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
							player.sendMessage("§a[SkullGetter] " + givePlayer.getName() + " さんにSkullGetterを与えました。");
							givePlayer.getInventory().addItem(getSkullGetter());
							givePlayer.sendMessage("§6[SkullGetter] SkullGetterを取得しました。");
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
				else if ( args[0].equalsIgnoreCase("reload") )
				{
					SkullConfigrable sConfigrable = new SkullConfigrable(this);
					sConfigrable.loadConfig();
					player.sendMessage("§e[SkullGetter] Configをリロードしました。");
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
				"§6§l/sg get §r§6- スカルゲッターをインベントリに追加します。",
				"§6§l/sg give <player> §r§6- スカルゲッターを指定したプレイヤーのインベントリに追加します。",
				"§6§l/sg help §r§6- このヘルプを参照します。",
				"§6§l/sg reload §r§6- Configをリロードします。"
		};
		for (String element : msg) {
			player.sendMessage(element);
		}
	}

	private ItemStack getSkullGetter()
	{
		@SuppressWarnings("deprecation")
		ItemStack skullgetter = new ItemStack(Material.SKULL_ITEM, 1, (short)0, (byte)3);
		ItemMeta skull_meta = skullgetter.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("§6右クリックで使用");
		skull_meta.setLore(lore);
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

	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event)
	{
		SkullEventer.InventoryDrag(event);
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null)
			return false;
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
			return false;
		econ = rsp.getProvider();
		return econ != null;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public File getPluginJarFile()
	{
		return this.getFile();
	}

}
