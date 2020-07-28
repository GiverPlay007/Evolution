package me.giverplay.evolution.api;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class RankNovo
{
	public RankNovo(String nome, String prefixo, boolean ultimo, List<ItemStack> req, int min_level, String proximo)
	{
		this.nome = nome;
		this.prefixo = prefixo;
		this.ultimo = ultimo;
		this.req = req;
		this.min_level = min_level;
		this.proximo = proximo;
		this.ultimo = ultimo;
	}
	
	private String nome, prefixo, proximo;
	private int min_level;
	private boolean ultimo;
	private List<ItemStack> req;
	
	public String getName()
	{
		return this.nome;
	}
	
	public String getPrefix()
	{
		return this.prefixo;
	}
	
	public boolean isLastRank()
	{
		return this.ultimo;
	}
	
	public List<ItemStack> getCost()
	{
		return this.req;
	}
	
	public int getMinLevel()
	{
		return this.min_level;
	}
	
	public String getNextRank()
	{
		return this.proximo;
	}
}
