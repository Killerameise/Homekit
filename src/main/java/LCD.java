import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.Lcd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Created by Jaspar Mang on 28.09.17.
 */
public class LCD {
    final GpioLcdDisplay lcd;


    public LCD() {
        lcd = new GpioLcdDisplay(LCD_ROWS,    // number of row supported by LCD
                                 LCD_COLUMNS,       // number of columns supported by LCD
                                 RaspiPin.GPIO_11,  // LCD RS pin
                                 RaspiPin.GPIO_10,  // LCD strobe pin
                                 RaspiPin.GPIO_00,  // LCD data bit 1
                                 RaspiPin.GPIO_01,  // LCD data bit 2
                                 RaspiPin.GPIO_02,  // LCD data bit 3
                                 RaspiPin.GPIO_03); // LCD data bit 4
    }


    public final static int LCD_ROWS    = 2;
    public final static int LCD_COLUMNS = 16;
    public final static int LCD_BITS    = 4;

    public static void main(String args[]) throws InterruptedException, IOException {
        System.out.println("<--Pi4J--> Wiring Pi LCD test program");

        // setup wiringPi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }

        // initialize LCD
        int lcdHandle = Lcd.lcdInit(LCD_ROWS,     // number of row supported by LCD
                                    LCD_COLUMNS,  // number of columns supported by LCD
                                    LCD_BITS,     // number of bits used to communicate to LCD
                                    11,           // LCD RS pin
                                    10,           // LCD strobe pin
                                    0,            // LCD data bit 1
                                    1,            // LCD data bit 2
                                    2,            // LCD data bit 3
                                    3,            // LCD data bit 4
                                    0,            // LCD data bit 5 (set to 0 if using 4 bit communication)
                                    0,            // LCD data bit 6 (set to 0 if using 4 bit communication)
                                    0,            // LCD data bit 7 (set to 0 if using 4 bit communication)
                                    0);           // LCD data bit 8 (set to 0 if using 4 bit communication)


        // verify initialization
        if (lcdHandle == -1) {
            System.out.println(" ==>> LCD INIT FAILED");
            return;
        }

        // clear LCD
        Lcd.lcdClear(lcdHandle);



        while (true) {
            // write line 1 to LCD
            Lcd.lcdHome(lcdHandle);
            Lcd.lcdPosition (lcdHandle, 0, 0) ;
            Lcd.lcdPuts(lcdHandle, "Temp: " + SystemInfo.getCpuTemperature());

            // write line 2 to LCD
            Lcd.lcdPosition(lcdHandle, 0, 1);
            Lcd.lcdPuts(lcdHandle, "Mem: " + SystemInfo.getMemoryUsed());

            Arrays.toString(NetworkInfo.getIPAddresses());
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

            // update time every one second
            // write time to line 2 on LCD
            //Lcd.lcdPosition (lcdHandle, 0, 1) ;
            //Lcd.lcdPuts (lcdHandle, "--- " + formatter.format(new Date()) + " ---");
            Thread.sleep(200);
        }
    }


}
