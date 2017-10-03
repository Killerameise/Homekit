/**
 * Created by Jaspar on 25.08.2017.
 */

import com.beowulfe.hap.HomekitRoot;
import com.beowulfe.hap.HomekitServer;

import java.net.InetAddress;

public class Main {

    private static final int PORT = 20012;

    public static void main(String[] args) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            InetAddress in = InetAddress.getByName("192.168.5.108");
            HomekitServer homekit = new HomekitServer(in, PORT);

            HomekitRoot bridge = homekit.createBridge(new HomekitAuth(), "Test", "Jaspar Mang2", "V0.1", "0000-0001");
            //bridge.addAccessory(new TestLockMechanism());
            //bridge.addAccessory(new TestLockMechanismJAMMED());
            //bridge.addAccessory(new TestLockMechanismUNSECURE());
            /*InetAddress in = InetAddress.getByName("192.168.5.111");
            HomekitServer homekit = new HomekitServer(in, PORT);

            HomekitRoot bridge = homekit.createBridge(new HomekitAuth(), "Raspi Bridge", "Jaspar Mang", "V0.1", "0000-0001");

            bridge.addAccessory(new RadioControlledSwitch());
            GlassCabinet glassCabinet = new GlassCabinet();
            bridge.addAccessory(glassCabinet);
            bridge.addAccessory(new IrrigationSystem());
            bridge.addAccessory(new SystemInfoAccessory());*/
            bridge.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}