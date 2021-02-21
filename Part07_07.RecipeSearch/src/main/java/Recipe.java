import java.text.MessageFormat;
import java.util.List;

public class Recipe {

    private String name;
    private int cookTime;
    private List<String> ingredients;

    public Recipe(String name, int cookTime, List<String> ingredients) {
        this.name = name;
        this.cookTime = cookTime;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public Recipe setName(String name) {
        this.name = name;
        return this;
    }

    public int getCookTime() {
        return cookTime;
    }

    public Recipe setCookTime(int cookTime) {
        this.cookTime = cookTime;
        return this;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public Recipe setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}, cooking time: {1}", name, cookTime);
    }
}
