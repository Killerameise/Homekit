/**
 * Created by Jaspar on 25.08.2017.
 */

import com.beowulfe.hap.HomekitAuthInfo;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * This is a simple implementation that should never be used in actual production. The mac, salt, and privateKey
 * are being regenerated every time the application is started. The user store is also not persisted. This means pairing
 * needs to be re-done every time the app restarts.
 *
 * @author Andy Lintner
 */
public class HomekitAuth implements HomekitAuthInfo {

    private static final String PIN = "031-45-154";

    private final String mac = "7e:e3:7e:86:5e:5";
    private final BigInteger salt = new BigInteger("90317919186174212269431819641008825859");
    private final byte[] privateKey = javax.xml.bind.DatatypeConverter.parseBase64Binary("A391EDC1F9653A220CA68F0A5EB3CF291F8B23FE5DF93E76FADA277283694F13");
    private Map<String, byte[]> userKeyMap = new HashMap<>();
    private Preferences prefs;

    public HomekitAuth() throws InvalidAlgorithmParameterException {
        prefs = Preferences.userNodeForPackage(this.getClass());

        System.out.println("Auth info is generated each time the sample application is started. Pairings are not persisted.");
        System.out.println("The PIN for pairing is " + PIN);
    }

    @Override
    public String getPin() {
        return PIN;
    }

    @Override
    public String getMac() {
        return mac;
    }

    @Override
    public BigInteger getSalt() {
        return salt;
    }

    @Override
    public byte[] getPrivateKey() {
        return privateKey;
    }

    @Override
    public void createUser(String username, byte[] publicKey) {
        prefs.putByteArray(username, publicKey);
        System.out.println("Added pairing for " + username);
    }

    @Override
    public void removeUser(String username) {
        prefs.remove(username);
        System.out.println("Removed pairing for " + username);
    }

    @Override
    public byte[] getUserPublicKey(String username) {
        return prefs.getByteArray(username, null);
    }

}