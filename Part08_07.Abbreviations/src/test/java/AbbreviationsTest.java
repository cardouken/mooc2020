
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@Points("08-07")
public class AbbreviationsTest {

    @Test
    public void hasClassAbbreviations() {
        assertTrue("Class Abbreviations not found. Make sure it is defined \"public class Abbreviations {\"...", Reflex.reflect("Abbreviations").isPublic());
    }

    @Test
    public void hasMethodAddAbbreviation() {
        Reflex.reflect("Abbreviations").method("addAbbreviation").returningVoid().taking(String.class, String.class).requirePublic();
    }

    @Test
    public void hasMethodHasAbbreviation() {
        Reflex.reflect("Abbreviations").method("hasAbbreviation").returning(boolean.class).taking(String.class).requirePublic();
    }

    @Test
    public void hasMethodFindExplanationFor() {
        Reflex.reflect("Abbreviations").method("findExplanationFor").returning(String.class).taking(String.class).requirePublic();
    }

    @Test
    public void hasConstructorWithNoParameters() {
        Reflex.reflect("Abbreviations").ctor().takingNoParams().requirePublic();
    }

    @Test
    public void addFindAndRemoveAbbreviation() throws Throwable {
        Object abbreviations = Reflex.reflect("Abbreviations").ctor().takingNoParams().invoke();
        Reflex.reflect("Abbreviations").method("addAbbreviation").returningVoid().taking(String.class, String.class).invokeOn(abbreviations, "lol", "laughing out loud");
        Reflex.reflect("Abbreviations").method("addAbbreviation").returningVoid().taking(String.class, String.class).invokeOn(abbreviations, "etc", "and so on");

        String codeToTest = "Abbreviations abbreviations = new Abbreviations();\n"
                + "abbreviations.addAbbreviation(\"lol\", \"laughing out loud\");\n"
                + "abbreviations.addAbbreviation(\"etc\", \"and so on\");\n"
                + "System.out.println(abbreviations.hasAbbreviation(\"lol\"));\n"
                + "System.out.println(abbreviations.hasAbbreviation(\"etc\"));\n"
                + "System.out.println(abbreviations.findExplanationFor(\"etc\"));";

        assertFalse("Method hasAbbreviation failed. Try:\n" + codeToTest, Reflex.reflect("Abbreviations").method("hasAbbreviation").returning(boolean.class).taking(String.class).invokeOn(abbreviations, "lmao"));
        assertTrue("Method hasAbbreviation failed. Try:\n" + codeToTest, Reflex.reflect("Abbreviations").method("hasAbbreviation").returning(boolean.class).taking(String.class).invokeOn(abbreviations, "etc"));

        assertNull("Method findExplanationFor failed. Try:\n" + codeToTest, Reflex.reflect("Abbreviations").method("findExplanationFor").returning(String.class).taking(String.class).invokeOn(abbreviations, "lmao"));
        assertEquals("Method findExplanationFor failed. Try:\n" + codeToTest, "and so on", Reflex.reflect("Abbreviations").method("findExplanationFor").returning(String.class).taking(String.class).invokeOn(abbreviations, "etc"));
    }

}
