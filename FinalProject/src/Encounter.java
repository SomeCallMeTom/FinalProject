import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * @author darlingt
 * represent an encounter between monsters and players
 */
public class Encounter {
  ArrayList<Monster> monsters = new ArrayList<Monster>();
  ArrayList<Player> players = new ArrayList<Player>();
  EnumMap<Difficulty, Integer[]> thresholds = new EnumMap<Difficulty, Integer[]>(Difficulty.class);

  public Encounter() {
    thresholds.put(Difficulty.EASY, new Integer[] {25, 50, 75, 125, 250, 300, 350, 450, 550, 600,
        800, 1000, 1100, 1250, 1400, 1600, 2000, 2100, 2400, 2800});
    thresholds.put(Difficulty.MEDIUM, new Integer[] {50, 100, 150, 250, 500, 600, 750, 900, 1100,
        1200, 1600, 2000, 2200, 2500, 2800, 3200, 3900, 4200, 4900, 5700});
    thresholds.put(Difficulty.HARD, new Integer[] {75, 150, 225, 375, 750, 900, 1100, 1400, 1600,
        1900, 2400, 3000, 3400, 3800, 4300, 4800, 5900, 6300, 7300, 8500});
    thresholds.put(Difficulty.DEADLY, new Integer[] {100, 200, 400, 500, 1100, 1400, 1700, 2100,
        2400, 2800, 3600, 4500, 5100, 5700, 6400, 7200, 8800, 9500, 10900, 12700});
  }

  /**
   * add a monster to the encounter
   * @param monster
   */
  public void addMonster(Monster monster) {
    monsters.add(monster);
  }

  /**
   * @return the list of monsters in the encounter
   */
  public List<Monster> getMonsters() {
    return monsters;
  }

  /**
   * @param monster monster to remove
   */
  public void removeMonster(Monster monster) {
    for (Monster cur : monsters) {
      if (cur.equals(monster)) {
        monsters.remove(cur);
        return;
      }
    }
  }

  /**
   * @param level of the player to add
   */
  public void addPlayer(Integer level) {
    players.add(new Player(level));
  }

  /**
   * @param level of the player to remove
   */
  public void removePlayer(Integer level) {
    for (Player player : players) {
      if (player.getLevel() == level) {
        players.remove(player);
        return;
      }
    }
  }

  /**
   * @return list of players in the encounter
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * @param difficulty for the encounter
   * @return XP minimum threshold for the difficulty
   */
  public Integer getXPThreshold(Difficulty dif) {
    Integer groupThreshold = 0;
    for (Player cur : players) {
      groupThreshold += getXPThreshold(dif, cur);
    }
    return groupThreshold;
  }

  /**
   * @param dif  difficulty of the encounter
   * @param play player for to get the threshold
   * @return XP minimum threshold for the difficulty
   */
  private Integer getXPThreshold(Difficulty dif, Player play) {
    return thresholds.get(dif)[play.getLevel() - 1];
  }

  /**
   * @return XP to reward the players
   */
  public Integer getXPRewarded() {
    Integer out = 0;
    for (Monster cur : monsters) {
      out += cur.getXP();
    }
    return out;
  }

  /**
   * @return the encounter difficulty in XP
   */
  public Integer getXPDiffuculty() {
    Double multiplier = 0.0;
    Integer monCount = this.monsters.size();
    Integer playCount = this.players.size();

    if (monCount == 1 && playCount < 3) {
      multiplier = 1.5;
    } else if (monCount == 2 && playCount < 3) {
      multiplier = 2.0;
    } else if (monCount < 7 && playCount < 3) {
      multiplier = 2.5;
    } else if (monCount < 11 && playCount < 3) {
      multiplier = 3.0;
    } else if (monCount < 15 && playCount < 3) {
      multiplier = 4.0;
    } else if (monCount > 14 && playCount < 3) {
      multiplier = 5.0;
    } else if (monCount == 1 && playCount < 6) {
      multiplier = 1.0;
    } else if (monCount == 2 && playCount < 6) {
      multiplier = 1.5;
    } else if (monCount < 7 && playCount < 6) {
      multiplier = 2.0;
    } else if (monCount < 11 && playCount < 6) {
      multiplier = 2.5;
    } else if (monCount < 15 && playCount < 6) {
      multiplier = 3.0;
    } else if (monCount > 14 && playCount < 6) {
      multiplier = 4.0;
    } else if (monCount == 1 && playCount > 5) {
      multiplier = 0.5;
    } else if (monCount == 2 && playCount > 5) {
      multiplier = 1.0;
    } else if (monCount < 7 && playCount > 5) {
      multiplier = 1.5;
    } else if (monCount < 11 && playCount > 5) {
      multiplier = 2.0;
    } else if (monCount < 15 && playCount > 5) {
      multiplier = 2.5;
    } else if (monCount > 14 && playCount > 5) {
      multiplier = 3.0;
    }
    return (int) (getXPRewarded() * multiplier);
  }


}
