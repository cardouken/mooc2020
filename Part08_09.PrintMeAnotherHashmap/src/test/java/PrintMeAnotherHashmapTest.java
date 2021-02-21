
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Arrays;
import java.util.HashMap;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("08-09")
public class PrintMeAnotherHashmapTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void printValuesTest() throws Throwable {
        Reflex.reflect(Program.class).staticMethod("printValues").returningVoid().taking(HashMap.class).requirePublic();
        HashMap<String, Book> testHashmap = makeAHashMap();
        Reflex.reflect(Program.class).staticMethod("printValues").returningVoid().taking(HashMap.class).invoke(testHashmap);

        String koodi = "HashMap<String, Book> hashmap = new HashMap<>();\n"
                + "hashmap.put(\"sense\", new Book(\"Sensibility\", 1811, \"...\"));\n"
                + "hashmap.put(\"prejudice\", new Book(\"pride\", 1813, \"....\"));\n"
                + "hashmap.put(\"happy\", new Book(\"Don't let the pigeon drive the bus\", 2003, \"....\"));\n"
                + "printValues(hashmap);";

        for (String s : Arrays.asList("Sensibility", "pride", "Don't let the pigeon drive the bus", "1811", "1813", "2003")) {
            assertTrue("Output not correct. Try:\n" + koodi, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("sense", "prejudice", "happy")) {
            assertFalse("Output not correct. Try:\n" + koodi, io.getSysOut().contains(s));
        }
    }

    @Test
    public void printValueIfNameContainsTest2() throws Throwable {
        Reflex.reflect(Program.class).staticMethod("printValueIfNameContains").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, Book> testHashmap = makeAHashMap();
        Reflex.reflect(Program.class).staticMethod("printValueIfNameContains").returningVoid().taking(HashMap.class, String.class).invoke(testHashmap, "ide");
         String codeToTest = "HashMap<String, Book> hashmap = new HashMap<>();\n"
                + "hashmap.put(\"sense\", new Book(\"Sensibility\", 1811, \"...\"));\n"
                + "hashmap.put(\"prejudice\", new Book(\"pride\", 1813, \"....\"));\n"
                + "hashmap.put(\"happy\", new Book(\"Don't let the pigeon drive the bus\", 2003, \"....\"));\n"
                + "printValueIfNameContains(hashmap, \"ide\");";

        for (String s : Arrays.asList("pride", "1813")) {
            assertTrue("Output not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("sense", "prejudice", "happy", "ensibility", "Don't let the pigeon drive the bus", "1811", "2003")) {
            assertFalse("Output not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }
    }

    @Test
    public void printValueIfNameContains2() throws Throwable {
        Reflex.reflect(Program.class).staticMethod("printValueIfNameContains").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, Book> testHashmap = makeAHashMap();
        Reflex.reflect(Program.class).staticMethod("printValueIfNameContains").returningVoid().taking(HashMap.class, String.class).invoke(testHashmap, "p");

        String codeToTest = "HashMap<String, Book> hashmap = new HashMap<>();\n"
                + "hashmap.put(\"sense\", new Book(\"Sensibility\", 1811, \"...\"));\n"
                + "hashmap.put(\"prejudice\", new Book(\"pride\", 1813, \"....\"));\n"
                + "hashmap.put(\"happy\", new Book(\"Don't let the pigeon drive the bus\", 2003, \"....\"));\n"
                + "printValueIfNameContains(hashmap, \"p\");";

        for (String s : Arrays.asList("pride", "Don't let the pigeon drive the bus", "1813", "2003")) {
            assertTrue("Output not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("Sensibility", "1811", "sense", "prejudice", "happy")) {
            assertFalse("Output not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }
    }

    private static HashMap<String, Book> makeAHashMap() {
        HashMap<String, Book> hashmap = new HashMap<>();
        hashmap.put("sense", new Book("Sensibility", 1811, "..."));
        hashmap.put("prejudice", new Book("pride", 1813, "...."));
        hashmap.put("happy", new Book("Don't let the pigeon drive the bus", 2003, "...."));

        return hashmap;
    }

}
