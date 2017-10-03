/**
 * Created by Jaspar on 25.08.2017.
 */

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Lightbulb;

import java.util.concurrent.CompletableFuture;

//Vitrine
public class GlassCabinetMock extends GlassCabinet {
    private        boolean on       = false;
    private static int     ON_CODE  = 1397077;
    private static int     OFF_CODE = 1397076;

    private HomekitCharacteristicChangeCallback subscribeCallback = null;

    public GlassCabinetMock() {
        super();
    }

    @Override
    public int getId() {
        return 33;
    }

    @Override
    public String getLabel() {
        return "Vitrine";
    }

    @Override
    public void identify() {
        System.out.println("Identifying glass cabinet");
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
        System.out.println("Vitrine light on is " + b);
        on = b;
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