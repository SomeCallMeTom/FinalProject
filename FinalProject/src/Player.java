
public class Player {

  private Integer level;

  public Player(Integer level) throws IllegalArgumentException {
    if (level < 1 || level > 20) {
      throw new IllegalArgumentException("Player level must be between 1 & 20.");
    }
    this.level = level;
  }
  
  public Integer getLevel() {
    return level;
  }
}

