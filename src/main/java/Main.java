/**
 * Created by Jaspar on 25.08.2017.
 */

import com.beowulfe.hap.HomekitRoot;
import com.beowulfe.hap.HomekitServer;

public class Main {

    private static final int PORT = 9123;

    public static void main(String[] args) {
        try {
            HomekitServer homekit = new HomekitServer(PORT);

            HomekitRoot bridge = homekit.createBridge(new HomekitAuth(), "Test Bridge", "TestBridge, Inc.", "G6", "111abe234");
            bridge.addAccessory(new MockLightbulb());
            //
            //
            //bridge.addAccessory(new MockOutlet());
            bridge.addAccessory(new MockSwitch());
            bridge.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}