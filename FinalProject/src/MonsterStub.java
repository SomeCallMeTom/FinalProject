import java.util.EnumMap;
import java.util.Map;

public class MonsterStub {
  
  protected int jsonID;
  protected String slug;
  protected String name;
  protected String type;
  protected float CR;
  protected int XP;
  protected EnumMap<AbilityScore, Integer> abilityScores;
  private String docTitle;
  
  public MonsterStub(String slug, Integer jsonID,String Name, float cR, int xP,String Type, String DocTitle) {
    this.abilityScores = new EnumMap<AbilityScore, Integer>(AbilityScore.class);
    this.name = Name;
    this.slug = slug;
    this.jsonID = jsonID;
    this.CR = cR;
    this.XP = xP;
    this.type= Type;
    this.docTitle = DocTitle;
  }
  
  /**
   * @return the xP
   */
  public int getXP() {
    return XP;
  }


  /**
   * @return the cR
   */
  public float getCR() {
    return CR;
  }


  /**
   * @return the slug
   */
  public String getSlug() {
    return slug;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the abilityScores
   */
  public Map<AbilityScore, Integer> getAbilityScores() {
    return abilityScores;
  }

  /**
   * @param abilityScores the abilityScores to set
   */
  public void setAbilityScore(AbilityScore label, Integer score) throws IllegalArgumentException {
    if (score < 1 || score > 30) {
      throw new IllegalArgumentException(
          "Error on Monster" + slug + ", Ability Score must be between 1 & 30,");
    }
    this.abilityScores.put(label, score);
  }
  
  /**
   * @param label ability for which you need the score
   * @return
   */
  public Integer getAbilityScore(AbilityScore label) {
    return this.abilityScores.get(label);
  }
  
  /**
   * @return the jsonID
   */
  public int getJsonID() {
    return jsonID;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @return the docTitle
   */
  public String getDocTitle() {
    return docTitle;
  }

  
}
