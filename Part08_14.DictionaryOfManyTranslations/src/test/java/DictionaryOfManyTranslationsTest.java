
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import java.lang.reflect.Field;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

@Points("08-14")
public class DictionaryOfManyTranslationsTest {

    String klassName = "DictionaryOfManyTranslations";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classIsPublic() {
        assertTrue("The class " + klassName + " must be public, so it should be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void noExtraVariables() {
        sanityCheck(klassName, 3, " really nothing except one object variable to store the translations");
    }

    @Test
    public void emptyConstructor() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Define for the class " + s(klassName) + " a public constructor: "
                + "public " + s(klassName) + "()", ctor.isPublic());
        String e = "the error was caused by the code new DictionaryOfManyTranslations();";
        ctor.withNiceError(e).invoke();
    }

    public Object create() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        return ctor.invoke();
    }


    /*
     *
     */
    @Test
    public void addMethod() throws Throwable {
        String method = "add";

        Object object = create();

        assertTrue("create for the class " + klassName + " the method public void " + method + "(String word, String translation) ",
                klass.method(object, method)
                        .returningVoid().taking(String.class, String.class).isPublic());

        String e = "\nThe code that caused the error:  DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");";

        klass.method(object, method)
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("monkey", "apina");
    }

    @Test
    public void translateMethod() throws Throwable {
        String method = "translate";

        Object object = create();

        assertTrue("create for the class " + klassName + " the method public ArrayList<String> " + method + "(String word)",
                klass.method(object, method)
                        .returning(ArrayList.class)
                        .taking(String.class)
                        .isPublic());

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("monkey", "apina");

        String e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.translate(\"monkey\");\n";

        ArrayList answer = new ArrayList();
        answer.add("apina");

        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("monkey"));
    }

    @Test
    public void translateMethodWordNotAdded() throws Throwable {
        String method = "translate";

        Object object = create();

        String e = "\nThe code that caused the error: \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.translate(\"monkey\");\n";

        assertNotNull(e, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("monkey"));
    }

    @Test
    public void translateMethodTwoTranslations() throws Throwable {
        String method = "translate";

        Object object = create();

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("monkey", "apina");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("monkey", "apfe");

        String e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.add(\"monkey\",\"apfe\");\n"
                + "d.translate(\"monkey\");\n";

        ArrayList answer = new ArrayList();
        answer.add("apina");
        answer.add("apfe");

        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("monkey"));
    }

    @Test
    public void translateMethodManyWords() throws Throwable {
        String method = "translate";

        Object object = create();

        String e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.add(\"monkey\",\"apfe\");\n"
                + "d.add(\"cheese\",\"juusto\");\n"
                + "d.add(\"milk\",\"maito\");\n"
                + "d.translate(\"monkey\");\n";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("monkey", "apina");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("monkey", "apfe");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("cheese", "juusto");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("milk", "maito");

        ArrayList answer = new ArrayList();
        answer.add("apina");
        answer.add("apfe");

        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("monkey"));

        e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.add(\"monkey\",\"apfe\");\n"
                + "d.add(\"cheese\",\"juusto\");\n"
                + "d.add(\"milk\",\"maito\");\n"
                + "d.translate(\"cheese\");\n";

        answer = new ArrayList();
        answer.add("juusto");
        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("cheese"));

        e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.add(\"monkey\",\"apfe\");\n"
                + "d.add(\"cheese\",\"juusto\");\n"
                + "d.add(\"milk\",\"maito\");\n"
                + "d.translate(\"potato\");\n";

        assertNotNull(e, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("potato"));
    }

    public void testAddingOneTranslation() throws Throwable {
        Object dictionary = createInstance();
        ArrayList<String> translations = new ArrayList<String>();
        translations.add("sana");

        testTranslation(dictionary, "word", translations);
    }

    @Test
    public void testAddingManyTranslations() throws Throwable {
        Object dictionary = createInstance();
        ArrayList<String> translations = new ArrayList<String>();
        translations.add("sana");

        testTranslation(dictionary, "word", translations);

        translations.add("ord");
        translations.add("translation1");
        translations.add("translation2");

        testTranslation(dictionary, "word", translations);
    }

    @Test
    public void testAddingManyWords() throws Throwable {
        Object dictionary = createInstance();
        ArrayList<String> translations = new ArrayList<String>();
        translations.add("sana");
        translations.add("ord");
        translations.add("translation1");
        translations.add("translation2");

        testTranslation(dictionary, "word", translations);

        ArrayList<String> translations2 = new ArrayList<String>();
        translations2.add("viidakko");
        translations2.add("vidakkol");
        translations2.add("translation3");
        translations2.add("translation4");

        testTranslation(dictionary, "jungle", translations2);
    }

    /*
     *
     */
    @Test
    public void removeMethod() throws Throwable {
        String method = "remove";

        Object objecct = create();

        assertTrue("create for the class " + klassName + " the method public String " + method + "(String word) ",
                klass.method(objecct, method)
                        .returningVoid()
                        .taking(String.class)
                        .isPublic());
    }

    @Test
    public void removeExisting() throws Throwable {

        Object object = create();

        String e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.remove(\"monkey\");\n"
                + "d.translate(\"monkey\");";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("monkey", "apina");

        klass.method(object, "remove")
                .returningVoid().taking(String.class).withNiceError(e).invoke("monkey");

        assertNotNull(e, klass.method(object, "translate")
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("monkey"));
    }

    public void removeIfMany() throws Throwable {
        String method = "remove";

        Object object = create();

        String e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.add(\"monkey\",\"apfe\");\n"
                + "d.remove(\"monkey\");\n"
                + "d.translate(\"monkey\");";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("monkey", "apina");

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("monkey", "apfe");

        klass.method(object, "posita")
                .returningVoid().taking(String.class).withNiceError(e).invoke("monkey");

        assertEquals(e, null, klass.method(object, "translate")
                .returning(Set.class).taking(String.class).withNiceError(e).invoke("monkey"));
    }

    @Test
    public void manyWordsTranslationsAndRemovals() throws Throwable {
        String method = "translate";

        Object object = create();

        String e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.add(\"monkey\",\"apfe\");\n"
                + "d.add(\"cheese\",\"juusto\");\n"
                + "d.remove(\"monkey\");"
                + "d.add(\"milk\",\"maito\");\n"
                + "d.translate(\"monkey\");\n";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("monkey", "apina");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("monkey", "apfe");
        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("cheese", "juusto");
        klass.method(object, "remove")
                .returningVoid().taking(String.class).withNiceError(e).invoke("monkey");

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("milk", "maito");

        assertNotNull(e, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("monkey"));

        e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.add(\"monkey\",\"apfe\");\n"
                + "d.add(\"cheese\",\"juusto\");\n"
                + "d.remove(\"monkey\");"
                + "d.add(\"milk\",\"maito\");\n"
                + "d.translate(\"cheese\");\n";

        ArrayList answer = new ArrayList();
        answer.add("juusto");
        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("cheese"));

        e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.add(\"monkey\",\"apfe\");\n"
                + "d.add(\"cheese\",\"juusto\");\n"
                + "d.remove(\"monkey\");\n"
                + "d.add(\"milk\",\"maito\");\n"
                + "d.add(\"monkey\",\"apna\");\n"
                + "d.translate(\"monkey\");\n";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(e).invoke("monkey", "apna");

        answer = new ArrayList();
        answer.add("apna");
        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("monkey"));
    }

    public void removeNonexistent() throws Throwable {
        String method = "remove";

        Object object = create();

        String e = "\nThe code that caused the error:  \n"
                + "DictionaryOfManyTranslations d = new DictionaryOfManyTranslations();\n"
                + "d.add(\"monkey\",\"apina\");\n"
                + "d.remove(\"cream\");\n"
                + "d.translate(\"monkey\");";

        klass.method(object, "add")
                .returningVoid().taking(String.class, String.class).invoke("monkey", "apina");

        klass.method(object, "remove")
                .returningVoid().taking(String.class).withNiceError(e).invoke("cream");

        ArrayList answer = new ArrayList();
        answer.add("apina");
        answer.add("apfe");

        assertEquals(e, answer, klass.method(object, method)
                .returning(ArrayList.class).taking(String.class).withNiceError(e).invoke("monkey"));
    }

    @Test
    public void testAddingAndRemovingMultipleWords() throws Throwable {
        Object dictionary = createInstance();
        ArrayList<String> translations = new ArrayList<String>();
        translations.add("sana");
        translations.add("ord");
        translations.add("translation1");
        translations.add("translation2");

        testTranslation(dictionary, "word", translations);

        ArrayList<String> kaannokset2 = new ArrayList<String>();
        kaannokset2.add("viidakko");
        kaannokset2.add("vidakkol");
        kaannokset2.add("translation3");
        kaannokset2.add("translation4");

        testTranslation(dictionary, "jungle", kaannokset2);

        testRemoval(dictionary, "word");

        testRemoval(dictionary, "jungle");
    }

    @Test
    public void testNonexistentWord() throws Throwable {
        Object dictionary = createInstance();
        testNonexistentWord(dictionary, "nonexistentword");

        ArrayList<String> translations2 = new ArrayList<String>();
        translations2.add("viidakko");
        translations2.add("vidakkol");
        translations2.add("translation3");
        translations2.add("translation4");

        testTranslation(dictionary, "jungle", translations2);

        testRemoval(dictionary, "word");

        testNonexistentWord(dictionary, "nonexistentword2");
    }

    private void testNonexistentWord(Object dictionary, String word) throws Throwable {
        ArrayList<String> translations = Reflex.reflect(klassName).method("translate").returning(ArrayList.class).taking(String.class).invokeOn(dictionary, word);
        Assert.assertTrue("The dictionary was asked to translate \"" + word + "\", "
                + " which hadn't been added. Should have returned an empty list, but the returned list of translations was: "
                + translations, translations == null || translations.isEmpty());
    }

    private void testTranslation(Object dictionary, String word, ArrayList<String> translations) throws Throwable {
        for (String translation : translations) {
            Reflex.reflect(klassName).method("add").returningVoid().taking(String.class, String.class).invokeOn(dictionary, word, translation);
        }

        ArrayList<String> returnedValues = Reflex.reflect(klassName).method("translate").returning(ArrayList.class).taking(String.class).invokeOn(dictionary, word);

        if (returnedValues == null) {
            Assert.fail("The word \"" + word + "\" "
                    + " was added to the dictionary with the translations: " 
                    + translations + ", but the translate() method "
                    + " returns null for that word.");
            return;
        }

        Assert.assertTrue("The word \"" + word + "\" "
                + " was added to the dictionary with the translations: " + translations + ", but "
                + "the list of returned translations was: " + returnedValues,
                returnedValues.containsAll(translations));
    }

    private void testRemoval(Object dictionary, String word) throws Throwable {
        Reflex.reflect(klassName).method("remove").returningVoid().taking(String.class).invokeOn(dictionary, word);

        ArrayList<String> translations = Reflex.reflect(klassName).method("translate").returning(ArrayList.class).taking(String.class).invokeOn(dictionary, word);

        Assert.assertTrue("The word \"" + word + "\", "
                + " was removed from the dictionary, but the returned list of translations wasn't null or empty: "
                + translations, translations == null || translations.isEmpty());
    }

    private Object createInstance() {
        String nameOfClass = "DictionaryOfManyTranslations";
        ClassRef<?> classs;
        try {
            classs = Reflex.reflect(nameOfClass);
        } catch (Throwable t) {
            Assert.fail("The class " + nameOfClass + " does not exist. You must create that class in this exercise.");
            return null;
        }

        Object instance;
        try {
            instance = classs.constructor().takingNoParams().invoke();
        } catch (Throwable t) {
            Assert.fail("The class " + nameOfClass + " does not have a non-parameterized constructor.");
            return null;
        }

        return instance;
    }

    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }

    private void sanityCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("you don't need \"static variables\", remove from the class " + s(klassName) + " the variable " + field(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("the visibility of all the object variables shoudl be private, but the class " + s(klassName) + " contained: " + field(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("the class " + s(klassName) + " only needs " + m + ", remove the extra ones", var <= n);
        }
    }

    private String field(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "").replace("java.io.", "");
    }
}
