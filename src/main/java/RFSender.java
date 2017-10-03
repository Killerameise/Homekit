import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by Jaspar Mang on 30.08.17.
 */
public class RFSender {

    public static void send(int code) {
        String command = "/opt/433Utils/RPi_utils/codesend " + code;
        try {
            Process child = Runtime.getRuntime().exec(command);
            while(child.isAlive());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMultiple(int code){
        IntStream.range(0, 5).forEach(
                i -> {
                    send(code);
                }
        );
    }
}
