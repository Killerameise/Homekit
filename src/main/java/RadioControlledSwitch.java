/**
 * Created by Jaspar on 25.08.2017.
 */

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Switch;
import com.pi4j.io.gpio.*;

import java.util.concurrent.CompletableFuture;

public class RadioControlledSwitch implements Switch {
    private boolean on = false;
    private static int                                 ON_CODE           = 1394005;
    private static int                                 OFF_CODE          = 1394004;

    private HomekitCharacteristicChangeCallback subscribeCallback = null;
    public RadioControlledSwitch() {
        super();
    }

    @Override
    public int getId() {
        return 14;
    }

    @Override
    public String getLabel() {
        return "Funksteckdose 1";
    }

    @Override
    public void identify() {
        System.out.println("Identifying switch");
    }

    @Override
    public String getSerialNumber() {
        return "0000-0003";
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
    public CompletableFuture<Boolean> getSwitchState() {
        return CompletableFuture.completedFuture(on);
    }

    @Override
    public CompletableFuture<Void> setSwitchState(boolean b) throws Exception {
        on = b;
        if(b){
            RFSender.sendMultiple(ON_CODE);
        }else{
            RFSender.sendMultiple(OFF_CODE);
        }
        if (subscribeCallback != null) {
            subscribeCallback.changed();
        }

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void subscribeSwitchState(HomekitCharacteristicChangeCallback homekitCharacteristicChangeCallback) {
        this.subscribeCallback = homekitCharacteristicChangeCallback;
    }

    @Override
    public void unsubscribeSwitchState() {
        this.subscribeCallback = null;
    }
}