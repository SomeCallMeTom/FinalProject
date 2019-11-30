import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

enum abilityScore{
  STR,DEX,CON,INT,WIS,CHA
}
enum speed{
  walk,swim,fly,hover,burrow
}
enum size{
  tiny,small,medium,large,huge,gargantuan
}

public class Monster {
  private class Action{
    String name;
    String description;
    String damageDice;
    Integer attackBonus;
    Integer damageBonus;
  }

  private HashMap<abilityScore, Integer> abilityScores;
  private HashMap<speed,Integer> speeds;
  private HashMap<String,String> actions;
  private HashMap<String, Integer> skills;
  private HashMap<abilityScore, Integer> savingThrows;
  private HashSet<String> languages;
  private HashSet<String> vulnerabilities;
  private HashSet<String> resistances;
  private HashSet<String> immunitites;
  private HashSet<String> senses;
  
  private int jsonID;
  private int AC;
  private int HP;
  private int XP;
  private float CR;
  private String slug;
  private String name;
  private String alignment;
  private String type;
  private String subType;
  private String hitDice;
  private size size;
  
  
  
  
  public Monster(String slug, Integer jsonID) {
    abilityScores = new HashMap<abilityScore, Integer>();
    this.slug = slug;
    this.jsonID = jsonID;
  }
  
  /**
   * @return the abilityScores
   */
  public Map<abilityScore, Integer> getAbilityScores() {
    return abilityScores;
  }
  /**
   * @param abilityScores the abilityScores to set
   */
  public void setAbilityScore(abilityScore label, Integer score) throws IllegalArgumentException {
        if (score < 1 || score > 30) {
         throw new IllegalArgumentException("Error on Monster"+slug+", Ability Score must be between 1 & 30,");
        }  
        this.abilityScores.put(label, score);
    }

  public Integer getAbilityScore(abilityScore label) {
    return this.abilityScores.get(label);
  }
  }
  
