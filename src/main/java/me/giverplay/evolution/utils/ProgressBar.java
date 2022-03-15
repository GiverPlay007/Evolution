package me.giverplay.evolution.utils;

public final class ProgressBar {
  private ProgressBar() { }

  public static String bar(double current, double max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
    double percent = current / max;
    int progressBars = (int) (totalBars * percent);
    int leftOver = (totalBars - progressBars);

    return completedColor + symbol.repeat(progressBars) + notCompletedColor + symbol.repeat(leftOver);
  }
}
