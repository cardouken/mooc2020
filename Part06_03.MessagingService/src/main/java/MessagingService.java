import java.util.ArrayList;

public class MessagingService {

    private final ArrayList<Message> messages = new ArrayList<>();

    public void add(Message message) {
        if (message.messageLengthValid()) {
            messages.add(message);
        }
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
