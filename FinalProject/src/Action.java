/**
 * 
 */

/**
 * @author darlingt
 *
 */
public class Action {


    private String name;
    private String description;
    private String damageDice;
    private Integer attackBonus;
    private Integer damageBonus;
  
  /**
   * Creates an action with a name and description
   * @param name
   * @param description
   */
  public Action(String name, String description) {
    this.name = name;
    this.description = description;
    
  }
  
  /**
   * Creates an action with a name, description, attackBonus, and damageDice
   * @param name
   * @param description
   * @param attackBonus
   * @param damageDice
   */
  public Action(String name, String description,Integer attackBonus, String damageDice) {
    this.name = name;
    this.description = description;
    this.attackBonus = attackBonus;
    this.damageDice = damageDice;
  }
  
  /**
   * Creates an action with a name, description, attackBonus, damageDice, and damageBonus
   * @param name
   * @param description
   * @param attackBonus
   * @param damageDice
   * @param damageBonus
   */
  public Action(String name, String description,Integer attackBonus, String damageDice, Integer damageBonus) {
    this.name = name;
    this.description = description;
    this.attackBonus = attackBonus;
    this.damageDice = damageDice;
    this.damageBonus = damageBonus;
  }
  /**
 * @return the name
 */
public String getName() {
  return name;
}

/**
 * @return the description
 */
public String getDescription() {
  return description;
}

/**
 * @return the damageDice
 */
public String getDamageDice() {
  return damageDice;
}

/**
 * @return the attackBonus
 */
public Integer getAttackBonus() {
  return attackBonus;
}

/**
 * @return the damageBonus
 */
public Integer getDamageBonus() {
  return damageBonus;
}

}
