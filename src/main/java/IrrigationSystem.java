/**
 * Created by Jaspar on 25.08.2017.
 */

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Switch;
import com.pi4j.io.gpio.*;

import java.util.concurrent.CompletableFuture;

public class IrrigationSystem implements Switch {
    private static int                                 PIN_NUMBER        = 1;
    private static int                                 ON_CODE           = 1381717;
    private static int                                 OFF_CODE          = 1381716;
    private        HomekitCharacteristicChangeCallback subscribeCallback = null;
    final GpioController       gpio;
    final GpioPinDigitalOutput pin;
    Thread thread;
    private boolean isRunning = false;

    public IrrigationSystem() {
        super();
        gpio = GpioFactory.getInstance();
        pin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(PIN_NUMBER), "IrrigationSystem");
        pin.setShutdownOptions(true, PinState.LOW);
    }

    @Override
    public int getId() {
        return 12;
    }

    @Override
    public String getLabel() {
        return "Bewässerungssystem";
    }

    @Override
    public void identify() {
    }

    @Override
    public String getSerialNumber() {
        return "0000-0002";
    }

    @Override
    public String getModel() {
        return "Version 0.1";
    }

    @Override
    public String getManufacturer() {
        return "Jaspar Mang";
    }

    private boolean getPinState() {
        return pin.getState() == PinState.HIGH;
    }

    @Override
    public CompletableFuture<Boolean> getSwitchState() {
        return CompletableFuture.completedFuture(getPinState());
    }

    @Override
    public CompletableFuture<Void> setSwitchState(boolean b) throws Exception {
        if (!isRunning && b) {
            isRunning = true;
            thread = new Thread(() -> {
                RFSender.sendMultiple(ON_CODE);
                pin.setState(true);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pin.setState(false);
                RFSender.sendMultiple(OFF_CODE);
                isRunning = false;
            });
            thread.start();
            if (subscribeCallback != null) {
                subscribeCallback.changed();
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void subscribeSwitchState(HomekitCharacteristicChangeCallback homekitCharacteristicChangeCallback) {
        subscribeCallback = homekitCharacteristicChangeCallback;
    }

    @Override
    public void unsubscribeSwitchState() {
        subscribeCallback = null;
    }
}