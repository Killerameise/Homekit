/**
 * Created by Jaspar on 25.08.2017.
 */

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Switch;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.util.concurrent.CompletableFuture;

public class DoorContact implements Switch {
    private boolean on = false;
    private final GlassCabinet glassCabinet;
    final GpioController       gpio;
    final GpioPinDigitalInput  doorContactSensor;
    private boolean glassCabinetPowerState;

    private HomekitCharacteristicChangeCallback subscribeCallback = null;
    public DoorContact(GlassCabinet glassCabinet) {
        super();
        this.glassCabinet = glassCabinet;
        // create gpio controller
        gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        doorContactSensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        // set shutdown state for this input pin
        doorContactSensor.setShutdownOptions(true);

        // create and register gpio pin listener
        doorContactSensor.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.HIGH){
                    if(!glassCabinetPowerState){
                        try {
                            glassCabinet.setLightbulbPowerState(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if(event.getState() == PinState.LOW){
                    glassCabinetPowerState = glassCabinet.isOn();
                    if(!glassCabinetPowerState){
                        try {
                            glassCabinet.setLightbulbPowerState(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getId() {
        return 21;
    }

    @Override
    public String getLabel() {
        return "Vitrine Tür Sensorik";
    }

    @Override
    public void identify() {
        System.out.println("Identifying switch");
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
    public CompletableFuture<Boolean> getSwitchState() {
        return CompletableFuture.completedFuture(on);
    }

    @Override
    public CompletableFuture<Void> setSwitchState(boolean b) throws Exception {
        on = b;
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

    public static void main(String args[]) throws InterruptedException {


        System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");



        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
    }
}