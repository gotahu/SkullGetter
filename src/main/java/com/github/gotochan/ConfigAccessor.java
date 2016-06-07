package com.github.gotochan;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAccessor
{
	private final String fileName;
	private final JavaPlugin plugin;
	
	private File configFile;
	private FileConfiguration fileConfiguration;
	
	@SuppressWarnings("deprecation")
	public ConfigAccessor(JavaPlugin plugin, String fileName)
	{
		if ( plugin == null )
		{
			throw new IllegalArgumentException("Plugin Cannot be null");
		}
		
		if ( !plugin.isInitialized() )
		{
			throw new IllegalArgumentException("Plugin must be Initialized");
		}
		
		this.plugin = plugin;
		this.fileName = fileName;
		File dataFolder = plugin.getDataFolder();
		
		if ( dataFolder == null )
		{
			throw new IllegalStateException();
		}
		
		this.configFile = new File(plugin.getDataFolder(), fileName);
	}
	
	public void reloadConfig()
	{
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
	}
	
	public FileConfiguration getConfig()
	{
		if ( fileConfiguration == null )
		{
			this.reloadConfig();
		}
		return fileConfiguration;
	}
	
	public void saveConfig()
	{
		if(fileConfiguration == null || configFile == null)
		{
			return;
		}
		else
		{
			try
			{
				getConfig().save(configFile);
			}
			catch(IOException ex)
			{
				plugin.getLogger().info("Could not save config to " + configFile);
				ex.printStackTrace();
			}
		}
	}
	
	public void saveDefaultConfig()
	{
		if (!configFile.exists())
		{
			SkullUtility.copyFileFromJar(SkullGetter.instance.getPluginJarFile(),
					configFile,
					fileName);
		}
	}
}
