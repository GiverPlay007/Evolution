package me.giverplay.evolution_old.api;

public class PBar 
{
	public static String getProgressBar(double current, double max, int totalBars, String symbol, String completedColor, String notCompletedColor)
	{
		
		double percent = current / max;
		int progressBars = (int) (totalBars * percent);
		int leftOver = (totalBars - progressBars);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(completedColor);
		
		for (int i = 0; i < progressBars; i++) 
		{
			sb.append(symbol);
		}
		
		sb.append(notCompletedColor);
		
		for (int i = 0; i < leftOver; i++)
		{
			sb.append(symbol);
		}
		
		return sb.toString();
	}
	
	public static String getProgressBarByPercent(double percent, int totalBars, String symbol, String completedColor, String notCompletedColor)
	{
		int progressBars = (int) (totalBars * percent);
		int leftOver = (totalBars - progressBars);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(completedColor);
		
		for (int i = 0; i < progressBars; i++) 
		{
			sb.append(symbol);
		}
		
		sb.append(notCompletedColor);
		
		for (int i = 0; i < leftOver; i++) 
		{
			sb.append(symbol);
		}
		
		return sb.toString();
	}
	
	public static String getProgressBarScore(double current, double max, int totalBars, String symbol, String completedColor, String notCompletedColor, String inicio, String fim)
	{
		
		double percent = current / max;
		int progressBars = (int) (totalBars * percent);
		int leftOver = (totalBars - progressBars);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(completedColor);
		
		for (int i = 0; i < progressBars; i++) 
		{
			sb.append(symbol);
		}
		
		sb.append(notCompletedColor);
		
		for (int i = 0; i < leftOver; i++) 
		{
			sb.append(symbol);
		}
		
		return inicio + sb.toString() + fim;
	}
}
