
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Arrays;
import java.util.HashMap;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("08-08")
public class PrintMeMyHashmapTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void printKeys() throws Throwable {
        Reflex.reflect(Program.class).staticMethod("printKeys").returningVoid().taking(HashMap.class).requirePublic();
        HashMap<String, String> testHashMap = hm("a", "b", "c", "d", "e", "f");
        Reflex.reflect(Program.class).staticMethod("printKeys").returningVoid().taking(HashMap.class).invoke(testHashMap);

        String codeToTest = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"a\", \"b\");\n"
                + "hm.put(\"c\", \"d\");\n"
                + "hm.put(\"e\", \"f\");\n"
                + "printKeys(hm);";

        for (String s : Arrays.asList("a", "c", "e")) {
            assertTrue("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("b", "d", "f")) {
            assertFalse("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }
    }

    @Test
    public void printKeysWhereTest1() throws Throwable {
        Reflex.reflect(Program.class).staticMethod("printKeysWhere").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, String> data = hm("a", "b", "c", "d", "e", "f");
        Reflex.reflect(Program.class).staticMethod("printKeysWhere").returningVoid().taking(HashMap.class, String.class).invoke(data, "a");

        String codeToTest = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"a\", \"b\");\n"
                + "hm.put(\"c\", \"d\");\n"
                + "hm.put(\"e\", \"f\");\n"
                + "printKeysWhere(hm, \"a\");";

        for (String s : Arrays.asList("a")) {
            assertTrue("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("b", "d", "f", "c", "e")) {
            assertFalse("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }
    }

    @Test
    public void printKeysWhereTest2() throws Throwable {
        Reflex.reflect(Program.class).staticMethod("printKeysWhere").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, String> data = hm("abcd", "jkl", "def", "mno", "ghi", "pqr");
        Reflex.reflect(Program.class).staticMethod("printKeysWhere").returningVoid().taking(HashMap.class, String.class).invoke(data, "d");

        String codeToTest = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"abcd\", \"jkl\");\n"
                + "hm.put(\"def\", \"mno\");\n"
                + "hm.put(\"ghi\", \"pqr\");\n"
                + "printKeysWhere(hm, \"a\");";

        for (String s : Arrays.asList("abcd", "def")) {
            assertTrue("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("ghi", "jkl", "mno", "pqr")) {
            assertFalse("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }
    }

    @Test
    public void printValuesOfKeysWhereTest1() throws Throwable {
        Reflex.reflect(Program.class).staticMethod("printValuesOfKeysWhere").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, String> data = hm("abcd", "jkl", "def", "mno", "ghi", "pqr");
        Reflex.reflect(Program.class).staticMethod("printValuesOfKeysWhere").returningVoid().taking(HashMap.class, String.class).invoke(data, "a");

        String codeToTest = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"abcd\", \"jkl\");\n"
                + "hm.put(\"def\", \"mno\");\n"
                + "hm.put(\"ghi\", \"pqr\");\n"
                + "printValuesOfKeysWhere(hm, \"a\");";

        for (String s : Arrays.asList("jkl")) {
            assertTrue("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("abcd", "def", "ghi", "mno", "pqr")) {
            assertFalse("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }
    }

    @Test
    public void printValuesOfKeysWhereTest2() throws Throwable {
        Reflex.reflect(Program.class).staticMethod("printValuesOfKeysWhere").returningVoid().taking(HashMap.class, String.class).requirePublic();
        HashMap<String, String> data = hm("abcd", "jkl", "def", "mno", "ghi", "pqr");
        Reflex.reflect(Program.class).staticMethod("printValuesOfKeysWhere").returningVoid().taking(HashMap.class, String.class).invoke(data, "d");

        String codeToTest = "HashMap<String, String> hm = new HashMap<>();\n"
                + "hm.put(\"abcd\", \"jkl\");\n"
                + "hm.put(\"def\", \"mno\");\n"
                + "hm.put(\"ghi\", \"pqr\");\n"
                + "printValuesOfKeysWhere(hm, \"a\");";

        for (String s : Arrays.asList("jkl", "mno")) {
            assertTrue("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }

        for (String s : Arrays.asList("abcd", "def", "ghi", "pqr")) {
            assertFalse("Output was not correct. Try:\n" + codeToTest, io.getSysOut().contains(s));
        }
    }

    private static HashMap<String, String> hm(String... data) {
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < data.length; i += 2) {
            map.put(data[i], data[i + 1]);
        }

        return map;
    }

}
