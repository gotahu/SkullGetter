package com.github.gotochan;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullInventory
{
	private int size = 0;
	private Inventory inv = null;
	//private String[] list;

	public Inventory o_Inventory = null;
	public Inventory o_Inventory2 = null;

	public SkullInventory()
	{
		create_otherPlayerInventory();
		create_otherPlayerInventory2();
	}

	public int getInventorySize()
	{
		int players = Bukkit.getOnlinePlayers().size() + 1;

		if ( players <= 9 ) {
			size = 9;
		} else if ( players <= 18 )	{
			size = 18;
		} else if ( players <= 27 ) {
			size = 27;
		} else if ( players <= 36 )	{
			size = 36;
		} else if ( players <= 45 )	{
			size = 45;
		} else if ( players <= 54 ) {
			size = 54;
		} else if ( players <= 63 )	{
			size = 63;
		}
		return size;
	}

	public Inventory createInventory()
	{
		inv = Bukkit.createInventory(null, getInventorySize(), "SkullInventory");
		addInventory(inv);
		return this.inv;
	}

	public Inventory getInventory()
	{
		if ( inv == null )
		{
			createInventory();
			return this.inv;
		} else
			return this.inv;
	}

	public void updateInventory()
	{

	}

	private void addInventory(Inventory inventory)
	{
		int i = 0;
		for ( Player player : Bukkit.getServer().getOnlinePlayers() )
		{
			i++;
			if ( i == Bukkit.getServer().getOnlinePlayers().size() )
			{
				inv.addItem(getSkull(player));
				ItemStack emerald = new ItemStack(Material.EMERALD);
				ItemMeta meta = emerald.getItemMeta();
				meta.setDisplayName("§r§b他のスカルを見る");
				emerald.setItemMeta(meta);
				inv.addItem(emerald);
				return;
			}
			inv.addItem(getSkull(player));
		}
	}

	public ItemStack getSkull(Player player)
	{
		@SuppressWarnings("deprecation")
		ItemStack skullitem = new ItemStack(Material.SKULL_ITEM, 1, (short)0, (byte) 3);
		SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		skullMeta.setOwner(player.getName());
		skullMeta.setDisplayName(player.getName());
		skullitem.setItemMeta(skullMeta);

		return skullitem;
	}

	@SuppressWarnings("deprecation")
	public void create_otherPlayerInventory()
	{
		Inventory inv = Bukkit.createInventory(null, 54, "MobSkullInventory");

		String[] p = {
				"Notch",
				"MHF_Alex","MHF_Blaze","MHF_CaveSpider","MHF_Chicken","MHF_Cow","MHF_Creeper","MHF_Enderman",
				"MHF_Ghast","MHF_Golem","MHF_Herobrine","MHF_LavaSlime","MHF_MushroomCow","MHF_Ocelot",
				"MHF_Pig","MHF_PigZombie","MHF_Sheep","MHF_Skeleton","MHF_Slime","MHF_Spider","MHF_Squid",
				"MHF_Steve","MHF_Villager","MHF_WSkeleton","MHF_Zombie","MHF_Cactus","MHF_Cake",
				"MHF_Chest","MHF_CoconutB","MHF_CoconutG","MHF_Melon","MHF_OakLog","MHF_Present1","MHF_Present2",
				"MHF_Pumpkin","MHF_TNT","MHF_TNT2",
				"MHF_ArrowUp","MHF_ArrowDown","MHF_ArrowLeft","MHF_ArrowRight","MHF_Exclamation","MHF_Question"
		};

		int i = 0;
		for ( String name : p )
		{
			i++;
			if (i==(p.length-1)) {
				ItemStack next = new ItemStack(Material.DIAMOND_AXE);
				ItemMeta next_meta = next.getItemMeta();
				next_meta.setDisplayName("§b§lNext");
				next.setItemMeta(next_meta);
				inv.addItem(getSkullOffline(Bukkit.getOfflinePlayer(name)));
				inv.addItem(next);
				this.o_Inventory = inv;
				return;
			}
			inv.addItem(getSkullOffline(Bukkit.getOfflinePlayer(name)));
		}
		this.o_Inventory = inv;
		return;
	}

	public ItemStack getSkullOffline(OfflinePlayer offlinePlayer)
	{
		@SuppressWarnings("deprecation")
		ItemStack skullitem = new ItemStack(Material.SKULL_ITEM, 1, (short)0, (byte) 3);
		SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		skullMeta.setOwner(offlinePlayer.getName());
		skullMeta.setDisplayName(offlinePlayer.getName());
		skullitem.setItemMeta(skullMeta);

		return skullitem;
	}

	@SuppressWarnings("deprecation")
	public void create_otherPlayerInventory2()
	{
		Inventory inv = Bukkit.createInventory(null, 54, "MobSkullInventory2");
		String[] p = {
				"mescovic","Ace","moosify","eto","Tereneckla","annayirb","IronBrin","acissejxd","AllTheDiamond",
				"ptktnt","metalhedd","teachdaire","StackedGold","CameronGoldRush","loiwiol","Cobble","_Rience",
				"klingon","Cakers","Trish13","Thypthon","Benlisted","terryxu","Eternal - TNT Block","Spe",
				"emack0714","ThaBrick","BrickInTheHead","Praxis8","rugofluk","JL2579","Cynaps_","Sameopet",
				"RedstoneMakerMe","haohanklliu","luke4891","Numba_one_Stunna","Njham","ingo897","Jellyfish",
				"ChazOfftopic","107295","b4url82","Bendablob","Baker93"
		};
		int i = 0;
		for ( String name : p )
		{
			i++;
			if ( i==(p.length-1) )
			{
				ItemStack next = new ItemStack(Material.GOLD_AXE);
				ItemMeta next_meta = next.getItemMeta();
				next_meta.setDisplayName("§b§lBack");
				next.setItemMeta(next_meta);
				inv.addItem(getSkullOffline(Bukkit.getOfflinePlayer(name)));
				inv.addItem(next);

				this.o_Inventory2 = inv;
				return;
			}
			inv.addItem(getSkullOffline(Bukkit.getOfflinePlayer(name)));
		}
		this.o_Inventory2 = inv;
		return;
	}
}
