import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JokeManager {

    private final List<String> jokes = new ArrayList<>();

    public JokeManager() {
    }

    public void addJoke(String joke) {
        this.jokes.add(joke);
    }

    public String drawJoke() {
        if (jokes.isEmpty()) {
            return "Jokes are in short supply.";
        }
        return jokes.get(new Random().nextInt(jokes.size()));
    }

    public void printJokes() {
        for (String joke : jokes) {
            System.out.println(joke);
        }
    }
}
