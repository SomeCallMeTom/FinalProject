import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author darlingt Helper class, reads JSON, indexes monsters
 */

public class MonsterHelper {
  private String filename = "./monsters.json";
  private BTree<String, Integer> slugIndex;
  private BTree<String, Integer> typeIndex;
  private BTree<Float, Integer> crIndex;
  private BTree<Integer, Integer> xpIndex;

  public static void main(String args[]) {
    MonsterHelper helper = new MonsterHelper();
    try {
      Integer index = Integer.parseInt(args[0]);
      MonsterStub stub = helper.getMonsterStub(index);

      System.console().writer().println(stub.getName() + ":");
      System.console().writer().println();
      System.console().writer().println("CR: " + stub.getCR() + "    XP: " + stub.getXP());
      System.console().writer().println("type; " + stub.getType());
      System.console().writer().println("Ability Scores:");
      System.console().writer().println(stub.getAbilityScores());
      System.console().writer().println("Source: " + stub.getDocTitle());
    } catch (

    NumberFormatException e) {
      System.out.println("First Argument must be an integer between 0 and 1085");
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * initializes object with details contained in filename, must be JSONfile.
   * 
   * @param filename
   * @throws ParseException
   * @throws IOException
   * @throws FileNotFoundException
   */
  private void readMonsters(String filename)
      throws FileNotFoundException, IOException, ParseException {

    this.filename = filename;

    slugIndex = new BTree<String, Integer>(7);
    typeIndex = new BTree<String, Integer>(7);
    crIndex = new BTree<Float, Integer>(7);
    xpIndex = new BTree<Integer, Integer>(7);

    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(new FileReader(filename));
    JSONArray monsters = (JSONArray) json.get("results");

    for (int i = 0; i < monsters.size(); i++) {
      JSONObject jsonMon = (JSONObject) monsters.get(i);
      String slug = (String) jsonMon.get("slug");
      String type = (String) jsonMon.get("type");
      float CR = (float) jsonMon.get("challenge_rating");
      Integer XP = this.convertToXP(CR);
      slugIndex.insert(slug, i);
      if (type.length() > 0) {
        typeIndex.insert(type, i);
      }
      crIndex.insert(CR, i);
      xpIndex.insert(XP, i);
    }
  }

  /**
   * initializes indexes with details contained in monsters.json
   * 
   * @throws ParseException
   * @throws IOException
   * @throws FileNotFoundException
   */
  private void readMonsters() throws FileNotFoundException, IOException, ParseException {
    this.readMonsters(this.filename);
  }

  /**
   * @param index of the monster in JSON file
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   */
  public MonsterStub getMonsterStub(int index)
      throws FileNotFoundException, IOException, ParseException {
    MonsterStub mon;
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(new FileReader(filename));
    JSONArray monsters = (JSONArray) json.get("results");
    JSONObject jsonMon = (JSONObject) monsters.get(index);
    String strCR = (String) jsonMon.get("challenge_rating");
    Float CR = Float.parseFloat(strCR);
    Integer XP = this.convertToXP(CR);

    mon = new MonsterStub((String) jsonMon.get("slug"), index, (String) jsonMon.get("name"), CR, XP,
        (String) jsonMon.get("type"), (String) jsonMon.get("document__title"));
    this.jsonSetAbilityScore(jsonMon, mon, "strength");
    this.jsonSetAbilityScore(jsonMon, mon, "dexterity");
    this.jsonSetAbilityScore(jsonMon, mon, "constitution");
    this.jsonSetAbilityScore(jsonMon, mon, "intelligence");
    this.jsonSetAbilityScore(jsonMon, mon, "wisdom");
    this.jsonSetAbilityScore(jsonMon, mon, "charisma");

    return mon;
  }

  public Monster getMonster(int index) throws FileNotFoundException, IOException, ParseException {
    MonsterStub stub = this.getMonsterStub(index);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(new FileReader(filename));
    JSONArray monsters = (JSONArray) json.get("results");
    JSONObject jsonMon = (JSONObject) monsters.get(index);

    String stringSize = (String) jsonMon.get("size");
    stringSize = stringSize.toUpperCase();
    CreatureSize size = CreatureSize.valueOf(stringSize);

    String alignment = (String) jsonMon.get("alignment");
    int AC = (Integer) jsonMon.get("armor_class");
    int HP = (Integer) jsonMon.get("hit_points");
    String hitDice = (String) jsonMon.get("hit_dice");
    String sensesString = (String) jsonMon.get("senses");
    String[] senses = sensesString.split(",");

    Monster mon = new Monster(stub, size, alignment, AC, HP, hitDice, senses);

    generateSubType(jsonMon, mon);

    generateGroup(jsonMon, mon);

    generateArmorDesc(jsonMon, mon);

    generateSpeeds(jsonMon, mon);

    generateSavingThrows(jsonMon, mon);

    generatePerception(jsonMon, mon);

    generateSkills(jsonMon, mon);

    generateDmgVulnerabilites(jsonMon, mon);

    generateDmgResistances(jsonMon, mon);

    generateDmgImmunities(jsonMon, mon);

    generateCndImmunities(jsonMon, mon);

    generateLanguages(jsonMon, mon);

    generateActions(jsonMon, mon);

    generateReactions(jsonMon, mon);

    genLegendary(jsonMon, mon);

    generateSpecialAbilities(jsonMon, mon);


    return mon;
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void genLegendary(JSONObject jsonMon, Monster mon) {
    String legDesc = (String) jsonMon.get("legendary_desc");
    if (!legDesc.equals(null) && legDesc.length() > 0) {
      mon.setLegendaryDescription(legDesc);

      generateLegendayActions(jsonMon, mon);
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateLanguages(JSONObject jsonMon, Monster mon) {
    String lngString = (String) jsonMon.get("languages");
    if (!lngString.equals(null) && lngString.length() > 0) {
      mon.setLanguages(lngString.split(","));
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateCndImmunities(JSONObject jsonMon, Monster mon) {
    String cndImunStr = (String) jsonMon.get("condition_immunities");
    if (!cndImunStr.equals(null) && cndImunStr.length() > 0) {
      String[] cndImun = cndImunStr.split(",");
      mon.setVulnerabilities(cndImun);
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateDmgImmunities(JSONObject jsonMon, Monster mon) {
    String dmgImunStr = (String) jsonMon.get("damage_immunities");
    if (!dmgImunStr.equals(null) && dmgImunStr.length() > 0) {
      String[] dmgImun = dmgImunStr.split(";");
      mon.setVulnerabilities(dmgImun);
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateDmgResistances(JSONObject jsonMon, Monster mon) {
    String dmgResStr = (String) jsonMon.get("damage_resistances");
    if (!dmgResStr.equals(null) && dmgResStr.length() > 0) {
      String[] dmgRes = dmgResStr.split(";");
      mon.setVulnerabilities(dmgRes);
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateDmgVulnerabilites(JSONObject jsonMon, Monster mon) {
    String dmgVulnStr = (String) jsonMon.get("damage_vulnerabilities");
    if (!dmgVulnStr.equals(null) && dmgVulnStr.length() > 0) {
      String[] dmgVulns = dmgVulnStr.split(",");
      mon.setVulnerabilities(dmgVulns);
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateSkills(JSONObject jsonMon, Monster mon) {
    JSONObject jsonSkills = (JSONObject) jsonMon.get("skills");
    if (!jsonSkills.isEmpty()) {

      for (Object skillObj : jsonSkills.keySet()) {
        String skillStr = (String) skillObj;
        mon.addSkill(skillStr, (Integer) jsonSkills.get(skillStr));
      }
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generatePerception(JSONObject jsonMon, Monster mon) {
    Integer perception = (Integer) jsonMon.get("perception");
    if (!perception.equals(null)) {
      mon.setPerception(perception);
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateSavingThrows(JSONObject jsonMon, Monster mon) {
    String[] savingThrows =
        "strength_save,dexterity_save,constitution_save,intelligence_save,wisdom_save,charisma_save"
            .split(",");
    for (String key : savingThrows) {
      if (!jsonMon.get(key).equals(null)) {
        this.jsonSetSavingThrowScore(jsonMon, mon, key);
      }
    }
  }

  private void generateSpeeds(JSONObject jsonMon, Monster mon) {
    JSONObject jsonSpeeds = (JSONObject) jsonMon.get("speed");
    if (!jsonSpeeds.isEmpty()) {

      for (Object speedObj : jsonSpeeds.keySet()) {
        String speedStr = (String) speedObj;
        Speed speed = Speed.valueOf(speedStr);
        mon.setSpeed(speed, (String) jsonSpeeds.get(speedStr));
      }
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateArmorDesc(JSONObject jsonMon, Monster mon) {
    String armrDesc = (String) jsonMon.get("armor_desc");
    if (!armrDesc.equals(null) && armrDesc.length() > 0) {
      mon.setArmorDescription(armrDesc);
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateGroup(JSONObject jsonMon, Monster mon) {
    String group = (String) jsonMon.get("group");
    if (!group.equals(null) && group.length() > 0) {
      mon.setGroup(group);
    }
  }

  /**
   * @param jsonMon
   * @param mon
   */
  private void generateSubType(JSONObject jsonMon, Monster mon) {
    String sub = (String) jsonMon.get("subtype");
    if (!sub.equals(null) && sub.length() > 0) {
      mon.setSubType(sub);
    }
  }

  private void generateSpecialAbilities(JSONObject jsonMon, Monster mon) {
    JSONArray jsonSpecActs = (JSONArray) jsonMon.get("special_abilities");
    for (int i = 0; i < jsonSpecActs.size(); i++) {
      JSONObject jsonSpecAct = (JSONObject) jsonSpecActs.get(i);
      Action act = new Action((String) jsonSpecAct.get("name"), (String) jsonSpecAct.get("desc"));
      mon.addSpecialAbility(act);
    }
  }

  /**
   * Parses jsonMon for legendaryActions and adds them to mon
   * 
   * @param jsonMon
   * @param mon
   */
  private void generateLegendayActions(JSONObject jsonMon, Monster mon) {
    JSONArray jsonLegActs = (JSONArray) jsonMon.get("legendary_actions");
    for (int i = 0; i < jsonLegActs.size(); i++) {
      JSONObject jsonLegAct = (JSONObject) jsonLegActs.get(i);
      Action act = new Action((String) jsonLegAct.get("name"), (String) jsonLegAct.get("desc"));
      mon.addLegendaryAction(act);
    }
  }

  /**
   * Parses jsonMon for reactions, and adds them to Mon
   * 
   * @param jsonMon
   * @param mon
   */
  private void generateReactions(JSONObject jsonMon, Monster mon) {
    JSONArray jsonReactions = (JSONArray) jsonMon.get("reactions");
    for (int i = 0; i < jsonReactions.size(); i++) {
      JSONObject jsonReaction = (JSONObject) jsonReactions.get(i);
      Action act = new Action((String) jsonReaction.get("name"), (String) jsonReaction.get("desc"));
      mon.addReaction(act);
    }

  }

  /**
   * Parses jsonMon for action and adds them to mon
   * 
   * @param jsonMon
   * @param mon
   */
  private void generateActions(JSONObject jsonMon, Monster mon) {
    JSONArray jsonActions = (JSONArray) jsonMon.get("actions");
    for (int i = 0; i < jsonActions.size(); i++) {
      JSONObject jsonAction = (JSONObject) jsonActions.get(i);
      String name = (String) jsonAction.get("name");
      String desc = (String) jsonAction.get("desc");

      Integer atkBon = (Integer) jsonAction.get("attack_bonus");
      String dmgDice = (String) jsonAction.get("damage_dice");
      Integer dmgBon = (Integer) jsonAction.get("damage_bonus");

      Boolean hasAtkBon = (!atkBon.equals(null)) ? true : false;
      Boolean hasDmgDice = (!dmgDice.equals(null) && dmgDice.length() > 0) ? true : false;
      Boolean hasDmgBon = (!dmgBon.equals(null)) ? true : false;

      if (hasAtkBon && hasDmgDice && hasDmgBon) {
        Action act = new Action(name, desc, atkBon, dmgDice, dmgBon);
        mon.addAction(act);
      } else if (hasAtkBon && hasDmgDice) {
        Action act = new Action(name, desc, atkBon, dmgDice);
        mon.addAction(act);
      } else {
        Action act = new Action(name, desc);
        mon.addAction(act);
      }

    }
  }

  /**
   * @param CR a monosters challenge rating
   * @return the experience gained for facing this monster
   * @throws IllegalArgumentException if CR is not valid
   */
  private int convertToXP(float CR) throws IllegalArgumentException {
    if (CR == (float) 1 / 8) {
      return 25;
    } else if (CR == (float) 1 / 4) {
      return 50;
    } else if (CR == (float) 1 / 2) {
      return 100;
    } else {
      switch ((int) CR) {
        case 0:
          return 10;
        case 1:
          return 200;
        case 2:
          return 450;
        case 3:
          return 700;
        case 4:
          return 1100;
        case 5:
          return 1800;
        case 6:
          return 2300;
        case 7:
          return 2900;
        case 8:
          return 3900;
        case 9:
          return 5000;
        case 10:
          return 5900;
        case 11:
          return 7200;
        case 12:
          return 8400;
        case 13:
          return 10000;
        case 14:
          return 11500;
        case 15:
          return 13000;
        case 16:
          return 15000;
        case 17:
          return 18000;
        case 18:
          return 20000;
        case 19:
          return 22000;
        case 20:
          return 25000;
        case 21:
          return 33000;
        case 22:
          return 41000;
        case 23:
          return 50000;
        case 24:
          return 62000;
        case 25:
          return 75000;
        case 26:
          return 90000;
        case 27:
          return 105000;
        case 28:
          return 120000;
        case 29:
          return 135000;
        case 30:
          return 155000;

      }
    }
    throw new IllegalArgumentException("CR must be between 0 adn 30");
  }

  private void jsonSetSavingThrowScore(JSONObject jsonMon, Monster mon, String key) {

    mon.setSavingThrow(this.mapToEnum(key), (Integer) jsonMon.get(key));
  }

  private void jsonSetAbilityScore(JSONObject jsonMon, MonsterStub stub, String key) {
    Long value = (Long) jsonMon.get(key);
    stub.setAbilityScore(this.mapToEnum(key), (int) (long) value);
  }

  /**
   * @param value string value to map to an AbilityScore
   * @return proper AbilityScore, null if can't map
   */
  private AbilityScore mapToEnum(String value) {

    value = value.toLowerCase();
    if (value.contains("strength")) {
      return AbilityScore.STR;
    } else if (value.contains("dexterity")) {
      return AbilityScore.DEX;
    } else if (value.contains("constitution")) {
      return AbilityScore.CON;
    } else if (value.contains("intelligence")) {
      return AbilityScore.INT;
    } else if (value.contains("wisdom")) {
      return AbilityScore.WIS;
    } else if (value.contains("charisma")) {
      return AbilityScore.CHA;
    } else {
      return null;
    }
  }
}
