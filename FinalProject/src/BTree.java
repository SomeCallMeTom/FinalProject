import java.util.ArrayList;
import java.util.List;

public class BTree<K extends Comparable<K>, V> {

  private class Node {
    private Node parent;
    private ArrayList<K> keys;
    private ArrayList<V> values;
    private ArrayList<Node> children;

    Node(List<K> keys, List<V> values) {
      parent = null;
      keys = new ArrayList<K>();
      values = new ArrayList<V>();
      children = new ArrayList<Node>();

      keys.addAll(keys);
      values.addAll(values);
    }

    Node() {
      parent = null;
      keys = new ArrayList<K>();
      values = new ArrayList<V>();
      children = new ArrayList<Node>();
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
    root = new Node();
  }

  public void insert(K key, V value) throws IllegalArgumentException {
    if (key == null ) {
      throw new IllegalArgumentException("Arguement key to insert can't be null");
    }

    insert(key, value, root);

  }
  private Node split(Node current) {
    Node temp = new Node();
    for(int i =0; i<this.branchingFactor/2;i++) {
      temp.children.add(i, current.children.remove(i));
      temp.keys.add(i,current.keys.remove(i));
      temp.values.add(i,current.values.remove(i));
      
      //TODO promote mid key & value 
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

