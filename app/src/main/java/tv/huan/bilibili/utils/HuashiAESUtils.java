package tv.huan.bilibili.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class HuashiAESUtils {
    public static final String AES_KEY = "!@#$%^&*FJGFJT78";


    /**
     * 使用默认的KEY进行解密
     *
     * @param cipherText 使用默认key加过密的密文
     * @return 解密后的明文
     */
    public static String Decrypt(String cipherText) {
        return Decrypt(cipherText, AES_KEY);
    }

    /**
     * 使用默认的KEY进行加密
     *
     * @param plaintText 需要加密的明文
     * @return 加密后的密文
     */
    public static String Encrypt(String plaintText) {
        return Encrypt(plaintText, AES_KEY);
    }

    /**
     * 使用指定的KEY进行解密
     *
     * @param cipherText 需要解密的密文
     * @param key        解密的key
     * @return 解密后的明文
     * @throws Exception
     */
    public static String Decrypt(String cipherText, String key) {
        String plainText = null;
        try {
            if (key == null || key.length() != 16) {
                LogUtil.log("The length of the key must be 12 byte!!!!!!");
            } else {
                byte[] raw = key.getBytes("ASCII");
                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
                byte[] encrypted1 = hex2byte(cipherText);
                byte[] original = cipher.doFinal(encrypted1);
                plainText = new String(original);
            }
        } catch (Exception e) {
            System.out.print("Decrypt Exception:{}" + e);
        }
        return plainText;

    }

    /**
     * 使用指定的KEY进行加密
     *
     * @param plaintText 需要加密的明文
     * @param key        用于加密的key
     * @return 加密后的密文
     * @throws Exception
     */
    public static String Encrypt(String plaintText, String key) {
        if (key == null || key.length() != 16) {
            LogUtil.log("The length of the key must be 16 characters!!!!!!");
        } else {
            try {
                byte[] raw = key.getBytes("ASCII");
                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
                byte[] encrypted = cipher.doFinal(plaintText.getBytes());
                return byte2hex(encrypted).toLowerCase();
            } catch (Exception e) {
                LogUtil.log("Encrypt Exception:{}");
            }
        }
        return null;
    }

    private static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public static void main(String[] args) {
        //String encryptResult = getToken("123789", "1r39df456j8wet45", "AES/ECB/ZeroBytePadding");
        String encryptResult = Encrypt("53402000104", AES_KEY);
        System.out.print("加密后{}" + encryptResult);
        //解密 aa8aeeb7e94a82310806b702d2f32998解密后
        String decryptResult = Decrypt("aa8aeeb7e94a82310806b702d2f32998", AES_KEY);
        System.out.print("解密后{}" + decryptResult);

    }
}
