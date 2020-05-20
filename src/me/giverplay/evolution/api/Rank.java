package me.giverplay.evolution.api;

public class Rank
{
	public Rank(String nome, String prefixo, boolean ultimo, double money, int min_level, String proximo)
	{
		this.nome = nome;
		this.prefixo = prefixo;
		this.ultimo = ultimo;
		this.money = money;
		this.min_level = min_level;
		this.proximo = proximo;
		this.ultimo = ultimo;
	}
	
	private String nome, prefixo, proximo;
	private int min_level;
	private boolean ultimo;
	private double money;
	
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
	
	public double getCost()
	{
		return this.money;
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
