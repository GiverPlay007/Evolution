package me.giverplay.evolution.api;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.base.Preconditions;

import io.netty.util.internal.ThreadLocalRandom;
import me.giverplay.evolution.Evolution;
import net.milkbowl.vault.chat.Chat;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@SuppressWarnings("deprecation")
public class Formater 
{	
	private static final NavigableMap<Long, String> suffixes = new TreeMap<> ();
	
	static 
	{
		suffixes.put(1_000L, "k");
		suffixes.put(1_000_000L, "M");
		suffixes.put(1_000_000_000L, "B");
		suffixes.put(1_000_000_000_000L, "T");
		suffixes.put(1_000_000_000_000_000L, "Q");
		suffixes.put(1_000_000_000_000_000_000L, "S");
	}
	
	public static ChatColor getColorFromPlayer(Player p)
	{
		PermissionUser user = PermissionsEx.getUser(p);
		PermissionGroup group = user.getGroups()[0];
		String prefix = group.getPrefix();
		String chatcolor = ChatColor.getLastColors(prefix.replace("&", "§")).toString().replace("§", "");
		return ChatColor.getByChar(chatcolor);
	}
	
	public static ChatColor getColorFromPlayer(String p)
	{
		String prefix = getPrefix(p);
		String chatcolor = ChatColor.getLastColors(prefix.replace("&", "§")).toString().replace("§", "");
		return ChatColor.getByChar(chatcolor);
	}
	
	public static ChatColor getColorFromString(String prefix)
	{
		String chatcolor = ChatColor.getLastColors(prefix.replace("&", "§")).toString().replace("§", "");
		return ChatColor.getByChar(chatcolor);
	}
	
	public static String format(long value) 
	{
		//Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
		if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
		if (value < 0) return "-" + format(-value);
		if (value < 1000) return Long.toString(value); //deal with easy case
		
		Entry<Long, String> e = suffixes.floorEntry(value);
		Long divideBy = e.getKey();
		String suffix = e.getValue();
		
		long truncated = value / (divideBy / 10); //the number part of the output times 10
		boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
		return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
	}
	
	public static String value(double value) 
	{
		boolean isWholeNumber = value == Math.round(value);
		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
		formatSymbols.setDecimalSeparator('.');
		String pattern = isWholeNumber ? "###,###" : "###,##0";
		DecimalFormat df = new DecimalFormat(pattern, formatSymbols);
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(value);
	}
	
	public static boolean isInt(String s)
	{
		try 
		{
			Integer.parseInt(s);
			return true;
		} 
		catch (Exception e)
		{
			return false;
		}
	}
	
	public static String formatTime(long time)
	{
		String format = "";
		long hours = TimeUnit.MILLISECONDS.toHours(time);
		long hoursInMillis = TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(time - hoursInMillis);
		long minutesInMillis = TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(time - (hoursInMillis + minutesInMillis));
		int days = (int) (time / 86400000L);
		
		if (hours > 0L)
		{
			if (days > 0) 
			{
				time -= TimeUnit.DAYS.toMillis(days);
				hours = TimeUnit.MILLISECONDS.toHours(time - minutesInMillis);
				format = days + " dias, " + hours + (hours > 1L ? " horas" : " hora");
				return format;
			}
			format = hours + (hours > 1L ? " horas" : " hora");
		}
		
		if (minutes > 0L)
		{
			if ((seconds > 0L) && (hours > 0L)) 
			{
				format = format + ", ";
			}
			else if (hours > 0L)
			{
				format = format + " e ";
			}
			
			format = format + minutes + (minutes > 1L ? " minutos" : " minuto");
		}
		
		if (seconds > 0L)
		{
			if ((hours > 0L) || (minutes > 0L)) 
			{
				format = format + " e ";
			}
			
			format = format + seconds + (seconds > 1L ? " segundos" : " segundo");
		}
		
		if (format.equals(""))
		{
			long rest = time / 100L;
			if (rest == 0L)
			{
				rest = 1L;
			}
			format = "0." + rest + " segundo";
		}
		
		if (days > 0)
		{
			format = days + " dias";
		}
		
		return format;
	}
	
	public static void tocarAll(Sound sound, float value) 
	{
		try 
		{
			for (Player a : Bukkit.getOnlinePlayers()) 
			{
				a.playSound(a.getLocation(), sound, 10, value);
			}
		}
		catch (Exception e) 
		{
			
		}
	}
	
	public static String serializeLoc(Location l) 
	{
		return l.getWorld().getName() + ";" + l.getBlockX() + ";" + l.getBlockY() + ";" + l.getBlockZ() + ";" + l.getPitch() + ";" + l.getYaw();
	}
	
	public static Location deserializeLoc(String l) 
	{
		Location loc = new Location(Bukkit.getWorld(l.split(";")[0]), Double.parseDouble(l.split(";")[1]), Double.parseDouble(l.split(";")[2]), Double.parseDouble(l.split(";")[3]));
		loc.setPitch(Float.parseFloat(l.split(";")[4]));
		loc.setYaw(Float.parseFloat(l.split(";")[5]));
		return loc.clone().add(0.5, 0, 0.5);
	}
	
	public static String serializeLocBlock(Location l)
	{
		return l.getWorld().getName() + ";" + l.getBlockX() + ";" + l.getBlockY() + ";" + l.getBlockZ();
	}
	
	public static Location deserializeLocBlock(String l)
	{
		Location loc = new Location(Bukkit.getWorld(l.split(";")[0]), Double.parseDouble(l.split(";")[1]), Double.parseDouble(l.split(";")[2]), Double.parseDouble(l.split(";")[3]));
		return loc;
	}
	
	public static Location getRandomLocation(Location loc1, Location loc2)
	{
		Preconditions.checkArgument(loc1.getWorld() == loc2.getWorld());
		double minX = Math.min(loc1.getX(), loc2.getX());
		double minY = Math.min(loc1.getY(), loc2.getY());
		double minZ = Math.min(loc1.getZ(), loc2.getZ());
		
		double maxX = Math.max(loc1.getX(), loc2.getX());
		double maxY = Math.max(loc1.getY(), loc2.getY());
		double maxZ = Math.max(loc1.getZ(), loc2.getZ());
		
		return new Location(loc1.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ)).clone().add(0.5, 0, 0.5);
	}
	
	public static double randomDouble(double min, double max) 
	{
		return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
	}
	
	public static void giveItem(Player p, ItemStack item)
	{
		if (p.getInventory().firstEmpty() == -1) 
		{
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					p.getWorld().dropItemNaturally(p.getLocation(), item);
					p.sendMessage("§cSeu inventário está cheio, o item caiu no chão.");
				}
			}.runTask(Evolution.getInstance());
		}
		else 
		{
			p.getInventory().addItem(item);
		}
	}
	
	public static String getPrefix(String player)
	{
		String prefix = "";
		
		try 
		{
			RegisteredServiceProvider<Chat> chatclazz = Bukkit.getServicesManager().getRegistration(Chat.class);
			
			if (chatclazz != null) 
			{
				Chat chat = chatclazz.getProvider();
				if (chat != null) 
				{
					try 
					{
						prefix = chat.getPlayerPrefix("world", player).replace("&", "§");
					}
					catch (Exception e)
					{
						prefix = "";
					}
				}
			}
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
		
		return prefix.replace("[Membro] ", "");
	}
	
	public static void playSoundGlobal(Sound sound, float i, float t) 
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.playSound(p.getLocation(), sound, i, t);
		}
	}
}
