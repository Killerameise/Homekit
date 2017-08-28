/**
 * Created by Jaspar on 25.08.2017.
 * <p>
 * Created by Jaspar on 25.08.2017.
 */
/**
 * Created by Jaspar on 25.08.2017.
 */

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Switch;
import com.pi4j.io.gpio.*;

import java.util.concurrent.CompletableFuture;

public class MockSwitch implements Switch {

    private HomekitCharacteristicChangeCallback subscribeCallback = null;
    final GpioController gpio;
    final GpioPinDigitalOutput pin;

    public MockSwitch() {
        super();
        gpio = GpioFactory.getInstance();
        pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED");
        pin.setShutdownOptions(true, PinState.LOW);
    }

    @Override
    public int getId() {
        return 11;
    }

    @Override
    public String getLabel() {
        return "Test Switch";
    }

    @Override
    public void identify() {
        System.out.println("Identifying switch");
    }

    @Override
    public String getSerialNumber() {
        return "none";
    }

    @Override
    public String getModel() {
        return "none";
    }

    @Override
    public String getManufacturer() {
        return "none";
    }

    /*
    @Override
    public CompletableFuture<Boolean> getLightbulbPowerState() {
        return CompletableFuture.completedFuture(powerState);
    }

    @Override
    public CompletableFuture<Void> setLightbulbPowerState(boolean powerState)
            throws Exception {
        this.powerState = powerState;
        if (subscribeCallback != null) {
            subscribeCallback.changed();
        }
        System.out.println("The lightbulb is now "+(powerState ? "on" : "off"));
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void subscribeLightbulbPowerState(
            HomekitCharacteristicChangeCallback callback) {
        this.subscribeCallback = callback;
    }

    @Override
    public void unsubscribeLightbulbPowerState() {
        this.subscribeCallback = null;
    }

    */


    private boolean getPinState() {
        return pin.getState() == PinState.HIGH;
    }

    @Override
    public CompletableFuture<Boolean> getSwitchState() {
        return CompletableFuture.completedFuture(getPinState());
    }

    @Override
    public CompletableFuture<Void> setSwitchState(boolean b) throws Exception {
        pin.setState(b);
        if (subscribeCallback != null) {
            subscribeCallback.changed();
        }
        System.out.println("The switch is now " + (getPinState() ? "on" : "off"));
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