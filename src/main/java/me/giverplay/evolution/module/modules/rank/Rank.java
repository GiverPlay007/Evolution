package me.giverplay.evolution.module.modules.rank;

public class Rank {

  private final String name;
  private final String next;

  private final double cost;

  private final boolean isFirst;
  private final boolean isLast;

  public Rank(String name, double cost, boolean isFirst, boolean isLast, String next) {
    this.name = name;
    this.cost = cost;
    this.isFirst = isFirst;
    this.isLast = isLast;
    this.next = next;
  }

  public String getName() {
    return name;
  }

  public String getNextRank() {
    return next;
  }

  public double getCost() {
    return cost;
  }

  public boolean isFirst() {
    return isFirst;
  }

  public boolean isLast() {
    return isLast;
  }
}
