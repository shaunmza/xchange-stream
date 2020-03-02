package info.bitrich.xchangestream.valr;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ValrDigest {

    public static final String HMAC_SHA_512 = "HmacSHA512";
    private Mac mac;

    /**
     * Constructor
     *
     * @param secretKeyBase64
     * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
     *     key is invalid).
     */
    private ValrDigest(String secretKeyBase64) {
        SecretKey secretKey = new SecretKeySpec(secretKeyBase64.getBytes(), HMAC_SHA_512);
        try {
            mac = Mac.getInstance(HMAC_SHA_512);
            mac.init(secretKey);
        } catch (InvalidKeyException var2) {
            throw new IllegalArgumentException("Invalid key for hmac initialization.", var2);
        } catch (NoSuchAlgorithmException var3) {
            throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
        }
    }

    public static info.bitrich.xchangestream.valr.ValrDigest createInstance(String secretKeyBase64) {

        return secretKeyBase64 == null ? null : new ValrDigest(secretKeyBase64);
    }

    public String digestParams(String timestamp, String verb, String path, String body) {
        byte[] digest;

        mac.update(timestamp.getBytes());
        mac.update(verb.toUpperCase().getBytes());
        mac.update(path.getBytes());
        mac.update(body.getBytes());
        digest = mac.doFinal();

        return toHexString(digest);
    }

    public static String toHexString(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
