import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BTreeTest {
  private BTree<Integer, Integer> btree;

  @Before
  public void setUp() throws Exception {
    this.btree = new BTree<Integer, Integer>(7);
  }

  @After
  public void tearDown() throws Exception {
    this.btree = null;
  }

  @Test
  public final void test00_addNull() {
    try {
      btree.insert(null, null);
      fail("test00 failed IllegalArugmentException expected");
    } catch (IllegalArgumentException e) {
      // test passes
    } catch (Exception e) {
      fail("test00 faile - unexpeced exception" + e);
    }

    try {
      btree.insert(null, 1);
      fail("test00 failed IllegalArugmentException expected");
    } catch (IllegalArgumentException e) {
      // test passes
    } catch (Exception e) {
      fail("test00 faile - unexpeced exception" + e);
    }
  }

  @Test
  public final void test01_insert() {
    int[] data = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    for (int i : data) {
      btree.insert(i, i);
    }
    assertEquals(
        "test01 failed, expected 0:0, 1:1, 2:2, 3:3, 4:4, 5:5, 6:6, 7:7, 8:8, 9:9, 10:10, 11:11, 12:12. returns: "
            + btree.traverse(),
        "0:0, 1:1, 2:2, 3:3, 4:4, 5:5, 6:6, 7:7, 8:8, 9:9, 10:10, 11:11, 12:12", btree.traverse());

    btree = new BTree<Integer, Integer>(7);
    for (int i : data) {
      btree.insert(7, i);
    }

    assertEquals(
        "test01 failed, expected 7:0, 7:1, 7:2, 7:3, 7:4, 7:5, 7:6, 7:7, 7:8, 7:9, 7:10, 7:11, 7:12. returns: "
            + btree.traverse(),
        "7:0, 7:1, 7:2, 7:3, 7:4, 7:5, 7:6, 7:7, 7:8, 7:9, 7:10, 7:11, 7:12", btree.traverse());


  }

  @Test
  public final void test02_find() {
    List<Integer> expected = new ArrayList<Integer>();

    assertEquals("test02 failed, expected empty list, returned: " + btree.find(1), expected,
        btree.find(1));

    int[] data = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    for (int i : data) {
      btree.insert(i, i);
    }
    
    assertEquals("test02 failed, expected empty list, returned: " + btree.find(13), expected,
        btree.find(13));
    
    expected.add(1);
    
    assertEquals("test02 failed, expected empty list, returned: " + btree.find(1), expected,
        btree.find(1));
    
    btree = new BTree<Integer, Integer>(7);
    expected = new ArrayList<Integer>();
    for (int i : data) {
      btree.insert(7, i);
      expected.add(i);
    }
    
    assertEquals("test02 failed, expected empty list, returned: " + btree.find(7), expected,
        btree.find(7));
    

  }

}
