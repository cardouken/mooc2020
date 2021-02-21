
import java.util.HashMap;
import java.util.Map;

public class Nicknames {

    public static void main(String[] args) {
        final Map<String, String> nickNameMap = new HashMap<>();

        nickNameMap.put("matthew", "matt");
        nickNameMap.put("michael", "mix");
        nickNameMap.put("arthur", "artie");

        System.out.println(nickNameMap.get("matthew"));
    }

}
