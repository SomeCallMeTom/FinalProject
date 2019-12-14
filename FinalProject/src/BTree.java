import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BTree<K extends Comparable<K>, V> {

  private class Node {
    private Node parent;
    private ArrayList<K> keys;
    private ArrayList<V> values;
    private ArrayList<Node> children;
    private boolean isLeaf;

    Node(List<K> keys, List<V> values) {
      parent = null;
      keys = new ArrayList<K>();
      values = new ArrayList<V>();
      children = new ArrayList<Node>();
      isLeaf = true;

      keys.addAll(keys);
      values.addAll(values);
    }

    Node() {
      parent = null;
      keys = new ArrayList<K>();
      values = new ArrayList<V>();
      children = new ArrayList<Node>();
      isLeaf = true;
    }

    void insert(K key, V value, int position) {
      keys.add(position, key);
      values.add(position, value);
    }

  }

  private Node root;
  private int branchingFactor;

  public BTree(int branchingFactor) {
    this.branchingFactor = branchingFactor;
    root = null;
  }

  /**
   * Function to traverse all nodes in tree
   * @return string in the form K1:V1,...Kn:Vn
   */
  public String traverse() {
    String out = "";
    if (!root.equals(null)) {
      out = traverse(root);
    }
    return out;
  }

  
  /**
   * private recursive method to return the contents of the tree in string
   * @param current node for which to return all values and children values
   * @return string in the form K1:V1,...Kn:Vn
   */
  private String traverse(BTree<K, V>.Node current) {
    String out = "";
    int i;
    
    for (i = 0; i < current.keys.size(); i++) {
      //traverse children
      if (!current.isLeaf) {
        traverse(current.children.get(i));
      }
      //add key value pair
      out = out + current.keys.get(i) + " : " + current.values.get(i) + ",";
    }
    //traverse last child
    if(!current.isLeaf) {
      traverse(current.children.get(i));
    }
    out = Optional.ofNullable(out)
        .filter(sStr -> sStr.length() != 0)
        .map(sStr -> sStr.substring(0,sStr.length()-1))
        .orElse(out);
    
    return out;
  }
  
  /**
   * @param key
   * @return the list of all values that match the key, returns empty list if no keys match
   */
  public List<V> find(K key) throws IllegalArgumentException{
    if(key.equals(null)) {
      throw new IllegalArgumentException("Cannot perform find on null");
    }
    if(root.equals(null)) {
      return new ArrayList<V>();
    } else {
      return find(key, root);
    }
  }
  /**
   * @param key the key for which values you wish to return
   * @param current the current node on which search is being performed
   * @return a list of the values which match this key in the subtree of current
   */
  private List<V> find(K key, Node current) {
    ArrayList<V> out = new ArrayList<V>();
    int i=0;
    //skip nodes that are less than
    while(i<current.keys.size() && key.compareTo(current.keys.get(i))<0) {
      i++;
    }
    //if current key is greater than or equal to key, check children, add if equal
    while(i<current.keys.size() && key.compareTo(current.keys.get(i))>= 0) {
      if(!current.isLeaf) {
        out.addAll(find(key,current.children.get(i)));
      }
      if(current.keys.get(i).equals(key)) {
        out.add(current.values.get(i));
      }
      i++;
    }
    //check last child if key is equal
    if(!current.isLeaf && key.equals(current.keys.get(i))) {
      out.addAll(find(key,current.children.get(i)));
    }
   return out;
  }

  public void insert(K key, V value) throws IllegalArgumentException {
    if (key.equals(null)) {
      throw new IllegalArgumentException("Arguement key to insert can't be null");
    }

    insert(key, value, root);

  }

  private Node split(Node current) {
    Node temp = new Node();
    for (int i = 0; i < this.branchingFactor / 2; i++) {
      temp.children.add(i, current.children.remove(i));
      temp.keys.add(i, current.keys.remove(i));
      temp.values.add(i, current.values.remove(i));

      // TODO promote mid key & value
    }
    return temp;
  }

  private void insert(K key, V value, Node current) {

    if (current.children.isEmpty()) { // leaf
      for (int i = 0; i < current.keys.size(); i++) {
        int comparison = current.keys.get(i).compareTo(key);
        if (comparison >= 0) {
          current.insert(key, value, i);
          // TODO check for resize
          return;
        } // end loop through keys
      }
      current.insert(key, value, current.keys.size());
      // TODO check for resize
      return;
    } // end leaf
    else {// non leaf
      for (int i = 0; i < current.keys.size(); i++) {
        int comparison = current.keys.get(i).compareTo(key);
        if (comparison >= 0) {
          insert(key, value, current.children.get(i));
          return;
        } // end loop through keys

      }
    }
  }
}

