package me.giverplay.evolution.comandos;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.PBar;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoReiniciar extends Comando
{
  // Codigo Original por GuiHSilva
  public ComandoReiniciar()
  {
    super("reiniciar", ComandoType.GERAL, "/reiniciar [tempo] [s/m] <motivo>");
    Evolution.getInstance().setRestarting(false);
    Evolution.getInstance().setRestarting(false);
  }

  private BukkitTask task;

  @Override
  public void execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("evolution.reiniciar"))
    {
      sender.sendMessage("§cSem permissão");
      return;
    }

    if (args.length==1)
    {
      if (args[0].equalsIgnoreCase("cancel") || args[0].equalsIgnoreCase("cancelar")
              || args[0].equalsIgnoreCase("c"))
      {
        if (Evolution.getInstance().isRestarting())
        {
          task.cancel();
          for (Player p : Bukkit.getOnlinePlayers())
          {
            Evolution.getInstance().sendAction(p, "§eA reinicialização foi cancelada!");
          }

          Evolution.getInstance().setRestarting(false);
          Evolution.getInstance().setBlockAllRestart(false);

          Bukkit.broadcastMessage(" ");
          Bukkit.broadcastMessage(" §cReinicio cancelado!");
          Bukkit.broadcastMessage(" ");

          for (Player p : Bukkit.getOnlinePlayers())
          {
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 3);
          }

          return;
        }
        else
        {
          sender.sendMessage("§cO servidor não esta reiniciando...");
          return;
        }
      }
    }

    if (args.length < 2)
    {
      sender.sendMessage(getUsage());
      return;
    }
    else
    {
      if (Evolution.getInstance().isRestarting())
      {
        sender.sendMessage("§cO servidor já esta reiniciando... Para cancelar use §f/reiniciar cancelar");
        return;
      }

      if (args.length==2)
      {
        int i;

        try
        {
          i = Integer.parseInt(args[0]);
        } catch (Exception e)
        {
          sender.sendMessage("§cInsira apenas numeros INTEIROS!");
          return;
        }

        if (i < 0)
        {
          sender.sendMessage("§cO tempo não pode ser negativo!");
          return;
        }

        if (args[1].equalsIgnoreCase("s") || args[1].contains("second") || args[0].contains("segundo"))
        {
          if (i <= 35)
          {
            sender.sendMessage("§cO tempo deve maior que 35 segundos!");
            return;
          }

          if (i > 1200)
          {
            sender.sendMessage("§cO tempo não pode ser maior que 20 minutos!");
            return;
          }

          reiniciarServidor(i, null);
        }

        if (args[1].equalsIgnoreCase("m") || args[1].contains("minute") || args[0].contains("minuto"))
        {
          if ((i * 60) > 1200)
          {
            sender.sendMessage("§cO tempo não pode ser maior que 20 minutos!");
            return;
          }

          reiniciarServidor(i * 60, null);
        }
      }
      else if (args.length > 2)
      {
        String msg = "";

        for (int i = 2; i < args.length; i++)
        {
          msg = msg + args[i] + " ";
        }

        int i;

        try
        {
          i = Integer.parseInt(args[0]);
        } catch (Exception e)
        {
          sender.sendMessage("§cO tempo deve ser em números inteiros!");
          return;
        }

        if (i < 0)
        {
          sender.sendMessage("§cO tempo não pode ser negativo!");
          return;
        }

        if (args[1].equalsIgnoreCase("s") || args[1].contains("second") || args[0].contains("segundo"))
        {
          if (i < 35)
          {
            sender.sendMessage("§cO tempo deve maior que 35 segundos!");
            return;
          }

          if (i > 1200)
          {
            sender.sendMessage("§cO tempo não pode ser maior que 20 minutos!");
            return;
          }

          reiniciarServidor(i, msg);
        }

        if (args[1].equalsIgnoreCase("m") || args[1].contains("minute") || args[0].contains("minuto"))
        {
          if ((i * 60) > 1200)
          {
            sender.sendMessage("§cO tempo não pode ser maior que 20 minutos!");
            return;
          }

          reiniciarServidor(i * 60, msg);
        }
      }
    }
  }

  private void reiniciarServidor(int i, String motivo)
  {
    Evolution.getInstance().setRestarting(true);

    long time = System.currentTimeMillis();
    long newtime = time + (i * 1000);

    int seconds = (int) ((newtime - System.currentTimeMillis()) / 1000);
    long minutes = (((newtime - System.currentTimeMillis()) / 1000) / 60);
    String tempo = "";

    if (minutes > 0)
    {
      tempo = minutes + " minuto" + (minutes > 0 ? "s":"");
    }
    else
    {
      tempo = seconds + " segundos ";
    }

    Bukkit.broadcastMessage(" ");
    Bukkit.broadcastMessage(" §aReiniciando em §f" + tempo);

    if (motivo!=null)
    {
      Bukkit.broadcastMessage(" §aMotivo: §f" + motivo.replaceAll("&", "§"));
    }

    Bukkit.broadcastMessage(" ");

    for (Player p : Bukkit.getOnlinePlayers())
    {
      p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 3);
    }

    task = new BukkitRunnable()
    {
      @Override
      public void run()
      {
        int a = (int) ((newtime - System.currentTimeMillis()) / 1000);
        int percent = (int) ((newtime - System.currentTimeMillis()) / 1000) * 100 / 100;
        if (percent==0 || a==0 || percent < 0 || a < 0)
        {
          for (Player p : Bukkit.getOnlinePlayers())
          {
            Evolution.getInstance().sendAction(p, "§eReinicializando o servidor!!");
          }

          task.cancel();

          for (World w : Bukkit.getWorlds())
          {
            w.save();
          }

          Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "save-all");
          Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kickall");

          new BukkitRunnable()
          {
            @Override
            public void run()
            {
              Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "stop");
            }
          }.runTaskLater(Evolution.getInstance(), 20);
        }
        else
        {
          if (percent==10 || percent==5 || percent==4 || percent==3 || percent==2 || percent==1
                  || percent==30)
          {
            Title t = new Title(" ", "§aReiniciando em " + percent + " segundos", 0, 2, 2);

            for (Player p : Bukkit.getOnlinePlayers())
            {
              t.send(p);
              p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, -0);
            }

            Evolution.getInstance().setBlockAllRestart(true);
          }

          int x = percent - i;

          if (x < 0)
          {
            x = x * -1;
          }

          int seconds = (int) ((newtime - System.currentTimeMillis()) / 1000);
          long minutes = (((newtime - System.currentTimeMillis()) / 1000) / 60);
          String tempo = "";

          if (minutes > 0)
          {
            tempo = minutes + "m ";
          }
          else
          {
            tempo = seconds + "s ";
          }

          for (Player p : Bukkit.getOnlinePlayers())
          {
            Evolution.getInstance().sendAction(p, "§eReiniciar: " + PBar.getProgressBar(x, i, 50, "|", "§a", "§f") + "§f " + tempo + "");
          }
        }
      }
    }.runTaskTimer(Evolution.getInstance(), 0, 20);
  }
}
