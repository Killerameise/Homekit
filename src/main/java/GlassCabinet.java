/**
 * Created by Jaspar on 25.08.2017.
 */

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Lightbulb;
import com.beowulfe.hap.accessories.Switch;

import java.util.concurrent.CompletableFuture;

//Vitrine
public class GlassCabinet implements Lightbulb {
    private        boolean on       = false;
    private static int     ON_CODE  = 1397077;
    private static int     OFF_CODE = 1397076;

    private HomekitCharacteristicChangeCallback subscribeCallback = null;

    public GlassCabinet() {
        super();
    }

    @Override
    public int getId() {
        return 15;
    }

    @Override
    public String getLabel() {
        return "Vitrine";
    }

    @Override
    public void identify() {
        System.out.println("Identifying glass cabinet");
        RFSender.sendMultiple(ON_CODE);
        RFSender.sendMultiple(OFF_CODE);
    }

    @Override
    public String getSerialNumber() {
        return "0000-0004";
    }

    @Override
    public String getModel() {
        return "Version 0.1";
    }

    @Override
    public String getManufacturer() {
        return "Jaspar Mang";
    }


    @Override
    public CompletableFuture<Boolean> getLightbulbPowerState() {
        return CompletableFuture.completedFuture(on);
    }

    @Override
    public CompletableFuture<Void> setLightbulbPowerState(boolean b) throws Exception {
        on = b;
        if (b) {
            RFSender.sendMultiple(ON_CODE);
        } else {
            RFSender.sendMultiple(OFF_CODE);
        }
        if (subscribeCallback != null) {
            subscribeCallback.changed();
        }

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void subscribeLightbulbPowerState(HomekitCharacteristicChangeCallback homekitCharacteristicChangeCallback) {
        this.subscribeCallback = homekitCharacteristicChangeCallback;
    }

    @Override
    public void unsubscribeLightbulbPowerState() {
        this.subscribeCallback = null;
    }

    public boolean isOn() {
        return on;
    }
}