import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Monster extends MonsterStub {



  private EnumMap<AbilityScore, Integer> abilityScores;


  private CreatureSize size;
  private String subType;
  private String group;
  private String alignment;
  private int AC;
  private String armorDescription;
  private int HP;
  private String hitDice;
  private EnumMap<Speed, String> speeds;
  private EnumMap<AbilityScore, Integer> savingThrows;
  private int perception;
  private HashMap<String, Integer> skills;
  private HashSet<String> dmgVulnerabilities;
  private HashSet<String> dmgResistances;
  private HashSet<String> dmgImmunities;
  private HashSet<Condition> conidtionImmunities;
  private HashSet<String> senses;
  private HashSet<String> languages;
  private HashSet<Action> actions;
  private HashSet<Action> reactions;
  private String legendaryDescription;
  private HashSet<Action> legendaryActions;
  private HashSet<Action> specialAbilities;
  private String docTitle;


  public Monster(MonsterStub stub, CreatureSize size, String alignment, int AC, int HP,
      String hitDice, String[] senses) {
    super(stub.getSlug(), stub.getJsonID(), stub.getName(), stub.getCR(), stub.getXP(),
        stub.getType(), stub.getDocTitle());
    this.abilityScores = (EnumMap<AbilityScore, Integer>) stub.getAbilityScores();

    this.size = size;
    this.alignment = alignment;
    this.AC = AC;
    this.HP = HP;
    this.hitDice = hitDice;
    this.senses = new HashSet<String>();
    for(String sense:senses) {
      this.senses.add(sense);
    }

    this.speeds = new EnumMap<Speed, String>(Speed.class);

  }

  /**
   * @return the alignment
   */
  public String getAlignment() {
    return alignment;
  }

  /**
   * @return the subType
   */
  public String getSubType() {
    return subType;
  }

  /**
   * @param subType the subType to set
   */
  public void setSubType(String subType) {
    this.subType = subType;
  }

  /**
   * @return the hitDice
   */
  public String getHitDice() {
    return hitDice;
  }

  /**
   * @return the size
   */
  public CreatureSize getSize() {
    return size;
  }

  /**
   * @return the actions
   */
  public Set<Action> getActions() {
    return this.actions;
  }

  /**
   * @param actions the actions to set
   */
  public void addAction(Action action) {
    if (this.actions.equals(null)) {
      this.actions = new HashSet<Action>();
    }
    this.actions.add(action);
  }

  /**
   * @return the savingThrows
   */
  public Map<AbilityScore, Integer> getSavingThrows() {
    return savingThrows;
  }

  /**
   * @param ability
   * @param score
   */
  public void setSavingThrow(AbilityScore ability, Integer score) {
    if (this.savingThrows.equals(null)) {
      this.savingThrows = new EnumMap<AbilityScore, Integer>(AbilityScore.class);
    }
    this.abilityScores.put(ability, score);
  }

  /**
   * @return the languages
   */
  public Set<String> getLanguages() {
    return languages;
  }

  /**
   * @param languages the languages to set
   */
  public void setLanguages(String[] languages) {
    this.languages = new HashSet<String>();
    for (String language : languages) {
      this.languages.add(language);
    }
  }

  /**
   * @return the resistances
   */
  public Set<String> getResistances() {
    return dmgResistances;
  }

  /**
   * @param resistances the resistances to set
   */
  public void setResistances(String[] resistances) {
    for (String resistance : resistances) {
      this.dmgResistances.add(resistance);
    }
  }

  /**
   * @return the vulnerabilities
   */
  public Set<String> getVulnerabilities() {
    return dmgVulnerabilities;
  }

  /**
   * @param vulnerabilities the vulnerabilities to set
   */
  public void setVulnerabilities(String[] vulnerabilities) {
    this.dmgVulnerabilities = new HashSet<String>();
    for (String vulnerability : vulnerabilities) {
      this.dmgVulnerabilities.add(vulnerability);
    }
  }

  /**
   * @return the skills
   */
  public Map<String, Integer> getSkills() {
    return skills;
  }


  /**
   * @param skill
   * @param score
   */
  public void addSkill(String skill, Integer score) {
    if (this.skills.equals(null)) {
      this.skills = new HashMap<String, Integer>();
    }
    this.skills.put(skill, score);
  }

  /**
   * @return the immunities
   */
  public Set<Condition> getImmunitites() {
    return conidtionImmunities;
  }

  /**
   * @param immunitites the immunities to set
   */
  public void setImmunitites(String[] immunities) {
    this.conidtionImmunities = new HashSet<Condition>();
    for (String sImmunity : immunities) {
      this.conidtionImmunities.add(Condition.valueOf(sImmunity));
    }
  }

  /**
   * @return the senses
   */
  public Set<String> getSenses() {
    return senses;
  }

  /**
   * @param senses the senses to set
   */
  public void setSenses(String[] senses) {

    this.senses = new HashSet<String>();
    for (String sense : senses) {
      this.senses.add(sense);
    }
  }

  /**
   * @return the AC
   */
  public int getAC() {
    return AC;
  }

  
  /**
   * @return the hP
   */
  public int getHP() {
    return HP;
  }

  /**
   * @param hP the hP to set
   */
  public void setHP(int hP) {
    HP = hP;
  }

  /**
   * @return the legendaryActions
   */
  public HashSet<Action> getLegendaryActions() {
    return legendaryActions;
  }

  /**
   * @param legendaryActions the legendaryActions to set
   */
  public void addLegendaryAction(Action legendaryAction) {
    if (this.legendaryActions.equals(null)) {
      this.legendaryActions = new HashSet<Action>();
    }
    this.legendaryActions.add(legendaryAction);
  }

  /**
   * @return the legendaryDescription
   */
  public String getLegendaryDescription() {
    return legendaryDescription;
  }

  /**
   * @param legendaryDescription the legendaryDescription to set
   */
  public void setLegendaryDescription(String legendaryDescription) {
    this.legendaryDescription = legendaryDescription;
  }

  /**
   * @return the specialAbilities
   */
  public Set<Action> getSpecialAbilities() {
    return specialAbilities;
  }

  /**
   * @param specialAbility the specialAbility to add
   */
  public void addSpecialAbility(Action specialAbility) {
    if (this.specialAbilities.equals(null)) {
      this.specialAbilities = new HashSet<Action>();
    }
    this.specialAbilities.add(specialAbility);
  }

  /**
   * @return whether monster is legendary
   */
  public boolean isLegendary() {
    return !this.legendaryDescription.equals(null);
  }

  /**
   * @return the group
   */
  public String getGroup() {
    return group;
  }

  /**
   * @param group the group to set
   */
  public void setGroup(String group) {
    this.group = group;
  }

  /**
   * @return the armorDescription
   */
  public String getArmorDescription() {
    return armorDescription;
  }

  /**
   * @param armorDescription the armorDescription to set
   */
  public void setArmorDescription(String armorDescription) {
    this.armorDescription = armorDescription;
  }

  /**
   * @return the dmgImmunities
   */
  public Set<String> getDmgImmunities() {
    return dmgImmunities;
  }

  /**
   * @param dmgImmunities the dmgImmunities to set
   */
  public void setDmgImmunities(String[] dmgImmunities) {
    this.dmgImmunities = new HashSet<String>();
    for (String dmgIm : dmgImmunities) {
      this.dmgImmunities.add(dmgIm);
    }
  }

  /**
   * @return the reactions
   */
  public Set<Action> getReactions() {
    return reactions;
  }

  /**
   * @param reaction the reactions to add
   */
  public void addReaction(Action reaction) {
    if (this.reactions.equals(null)) {
      this.reactions = new HashSet<Action>();
    }
    this.reactions.add(reaction);
  }

  /**
   * @return the docTitle, where the monster was introduced
   */
  public String getDocTitle() {
    return docTitle;
  }

  /**
   * @param docTitle the docTitle to set
   */
  public void setDocTitle(String docTitle) {
    this.docTitle = docTitle;
  }

  /**
   * @return the speeds
   */
  public Map<Speed, String> getSpeeds() {
    return speeds;
  }

  /**
   * @param speeds the speeds to set
   */
  public void setSpeed(Speed type, String speed) {
    this.speeds.put(type, speed);
  }

  /**
   * @return the perception
   */
  public int getPerception() {
    return perception;
  }

  /**
   * @param perception the perception to set
   */
  public void setPerception(int perception) {
    this.perception = perception;
  }
}

