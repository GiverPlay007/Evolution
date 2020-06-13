package me.giverplay.evolution.api.comando;

import org.bukkit.command.CommandSender;

public abstract class Comando
{
	private String name, usage;
	private ComandoType type;
	
	public Comando(String name, ComandoType type, String usage)
	{
		this.name = name;
		this.type = type;
		this.usage = usage;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public ComandoType getType()
	{
		return this.type;
	}
	
	public String getUsage()
	{
		return "§cUtilize " + this.usage;
	}
	
	public abstract void execute(CommandSender sender, String[] args);
}
