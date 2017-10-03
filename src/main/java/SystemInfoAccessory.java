import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.TemperatureSensor;
import com.pi4j.system.SystemInfo;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Jaspar Mang on 28.09.17.
 */
public class SystemInfoAccessory implements TemperatureSensor {
    private        HomekitCharacteristicChangeCallback subscribeCallback = null;

    @Override
    public int getId() {
        return 33;
    }

    @Override
    public String getLabel() {
        return "Raspberry PI SystemInfo";
    }

    @Override
    public void identify() {
        System.out.println("Identifying SystemInfo");
    }

    @Override
    public String getSerialNumber() {
        return "0000-0006";
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
    public CompletableFuture<Double> getCurrentTemperature() {
        float temp = -9.9F;
        try {
            temp = SystemInfo.getCpuTemperature();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture((double) temp);
    }

    @Override
    public void subscribeCurrentTemperature(
            final HomekitCharacteristicChangeCallback homekitCharacteristicChangeCallback) {
        this.subscribeCallback = homekitCharacteristicChangeCallback;
    }

    @Override
    public void unsubscribeCurrentTemperature() {
        this.subscribeCallback = null;
    }

    @Override
    public double getMinimumTemperature() {
        return -10.0;
    }

    @Override
    public double getMaximumTemperature() {
        return 200.0;
    }


}
