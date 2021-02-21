
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RecipeSearch {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("File to read: ");
        final String file = sc.nextLine();

        final List<Recipe> recipes = readRecipesFromFile(file);
        System.out.println("Commands");
        System.out.println("list - lists the recipes");
        System.out.println("stop - stops the program");
        System.out.println("find name - searches recipes by name");
        System.out.println("find cooking time - searches recipes by cooking time");
        System.out.println("find ingredient - searches recipes by ingredient");

        while (true) {
            System.out.println("Enter command: ");
            final String input = sc.nextLine();

            if ("list".equalsIgnoreCase(input)) {
                for (Recipe recipe : recipes) {
                    System.out.println(recipe);
                }
            }

            if ("stop".equalsIgnoreCase(input)) {
                break;
            }

            if ("find name".equalsIgnoreCase(input)) {
                System.out.println("Searched word: ");
                final String query = sc.nextLine();
                for (Recipe recipe : recipes) {
                    if (recipe.getName().contains(query)) {
                        System.out.println(recipe);
                    }
                }
            }

            if ("find cooking time".equalsIgnoreCase(input)) {
                System.out.println("Max cooking time: ");
                final int query = Integer.parseInt(sc.nextLine());
                for (Recipe recipe : recipes) {
                    if (recipe.getCookTime() <= query) {
                        System.out.println(recipe);
                    }
                }
            }

            if ("find ingredient".equalsIgnoreCase(input)) {
                System.out.println("Ingredient: ");
                final String query = sc.nextLine();
                for (Recipe recipe : recipes) {
                    for (String ingredient : recipe.getIngredients()) {
                        if (Objects.equals(ingredient, query)) {
                            System.out.println(recipe);
                        }
                    }
                }
            }
        }

    }

    public static List<Recipe> readRecipesFromFile(String filename) {
        final List<Recipe> recipes = new ArrayList<>();
        final List<String> recipeParts = new ArrayList<>();

        try (Scanner sc = new Scanner(Paths.get(filename))) {
            while (sc.hasNextLine()) {
                final String line = sc.nextLine();
                if (!line.isEmpty()) {
                    recipeParts.add(line);
                } else {
                    assignRecipeParts(recipes, recipeParts);
                }
            }
            if (!recipeParts.isEmpty()) {
                assignRecipeParts(recipes, recipeParts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;

    }

    private static void assignRecipeParts(List<Recipe> recipes, List<String> recipeParts) {
        String recipeName;
        int cookTime;
        recipeName = recipeParts.get(0);
        cookTime = Integer.parseInt(recipeParts.get(1));
        recipeParts.remove(0);
        recipeParts.remove(0);
        recipes.add(new Recipe(recipeName, cookTime, new ArrayList<>(recipeParts)));
        recipeParts.clear();
    }

}
