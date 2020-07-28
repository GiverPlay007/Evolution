package me.giverplay.evolution.comandos;

/*
public class ComandoRankup extends Comando
{
	private Evolution plugin;
	
	public ComandoRankup()
	{
		super("rankup", ComandoType.PLAYER, "/rankup");
		plugin = Evolution.getInstance();
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		PlayerManager player = plugin.getPlayer(sender.getName());
		
		if(player.getRank().isLastRank())
		{
			player.sendMessage("§eVocê já está no último rank, meu consagrado");
			return;
		}
		
		final RankNovo proximo = plugin.getRanks().get(player.getRank().getNextRank());
		
		if(player.getLevel() < proximo.getMinLevel())
		{
			player.sendMessage("\n§cVocê não possui o seguinte requisito: §fNível " + proximo.getMinLevel() + "\n");
			return;
		}
		
		if(plugin.getEconomy().getBalance(player.getPlayer()) < proximo.getCost())
		{
			player.sendMessage("\n§cVocê não possui o seguinte requisito: §f" + plugin.getRanks().get(player.getRank().getNextRank()).getCost() + " de money\n");
			return;
		}
		
		if(!player.isDeveloper())
		{
			int ni = player.getRank().getMinLevel();
			long cooldown = (ni > 80 ? 180 : (ni > 70 ? 150 : (ni > 40 ? 120 : (ni > 15 ? 60 : 40))));
			cooldown *= 1000;
			
			long tempo = System.currentTimeMillis() - player.getRankupTime();
			
			if(tempo < (cooldown))
			{
				player.sendMessage("§cAguarde mais alguns segundos antes de upar...");
				player.sendMessage("§cMais §f" + String.valueOf((tempo - cooldown ) / 1000) + " segundos");
				return;
			}
		}
		
		plugin.getEconomy().withdrawPlayer(player.getPlayer(), proximo.getCost());
		player.setRank(proximo);
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			plugin.sendAction(p, "§6" + player.getName() + " §eupou para " + proximo.getPrefix());
		}
	}
}
*/