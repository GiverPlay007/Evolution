package me.giverplay.evolution.utils;

public final class TimeUtils {
  private TimeUtils() { }

  public static String format(int seconds) {
    int min = seconds / 60;
    int sec = seconds % 60;

    StringBuilder sb = new StringBuilder();
    sb.append(min);
    sb.append(':');

    if(sec < 10) {
      sb.append(0);
    }

    sb.append(sec);
    return sb.toString();
  }
}
