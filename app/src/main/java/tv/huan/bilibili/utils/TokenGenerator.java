/**
 * Created by IntelliJ IDEA.
 * User: Nuke
 * Date: 2009-6-17
 * Time: 12:06:12
 * To change this template use File | Settings | File Templates.
 */
package tv.huan.bilibili.utils;


import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * 方法generateToken用于生成token
 * 方法generateAccessUrl用于生成带有token的播放url
 * 用法：
 * tokenGenerator = new TokenGenerator("the secret word",30L);
 * TokenInfo tokenInfo = new TokenInfo();
 * tokenInfo.setUserId("0001");
 * tokenInfo.setUserName("username");
 * tokenInfo.setSerialNumber("9900000");
 * tokenInfo.setAuthKey("oss_auth_key");
 * tokenInfo.setContentName("contentName");
 * AdInfo[] adInfo = new AdInfo[1];
 * adInfo[0]=new AdInfo();
 * adInfo[0].setInsertTime(-1);//片尾广告
 * adInfo[0].setAdUri("/to/ad/media/url");
 * String playrl = generateAccessUrl("http://host/prefix",
 * "/to/media/relative/url",
 * "111.111.111.111",
 * tokenInfo,adInfo);
 */
public class TokenGenerator {

    private static final String TAG = TokenGenerator.class.getSimpleName() + "--ShanYuHai--";
    private Cipher cipher;

    private SecureRandom random;

    private static long tokenTTLStartPoint; //令牌有效期的计算起点，秒为单位，应该为2000年1月1日0点到1970年1月1日0点的秒数

    private long tokenTTL; //令牌生存期，以秒为单位

    public final static byte[] KEY_MASK = {0x02, 0x4F, 0x60, 0x33, 0x5D, 0x3C,
            0x2B, 0x0B};

    public final static byte[] IV = {0x09, 0x41, 0x63, 0x30, 0x5D, 0x3B, 0x21,
            0x08};

    static {
        Calendar start = Calendar.getInstance();
        start.clear();
        start.set(2000, 0, 1, 0, 0, 0);
        tokenTTLStartPoint = start.getTimeInMillis() / 1000;
    }


    /**
     *
     */
    public TokenGenerator() {
        super();
    }

    /**
        * 按指定的密钥字符串和token生存期，创建一个TokenGenerator。
        *
        * @param chiave   用来加密的密钥字符串，是EPG与Streaming
        *                 Server之间共享的，必须是英文字符或GBK字符。chiave会与keyMask做异或，得出加密用的key。
        */
       public TokenGenerator(String chiave) {
           this(chiave, -1, Cipher.ENCRYPT_MODE);
       }

    /**
     * 按指定的密钥字符串和token生存期，创建一个TokenGenerator。
     *
     * @param chiave   用来加密的密钥字符串，是EPG与Streaming
     *                 Server之间共享的，必须是英文字符或GBK字符。chiave会与keyMask做异或，得出加密用的key。
     * @param tokenTTL 令牌的生命期，秒为单位。生成的token中，validateTime是当前时间加上tokenTTL构成的。
     */
    public TokenGenerator(String chiave, long tokenTTL) {
        this(chiave, tokenTTL, Cipher.ENCRYPT_MODE);
    }


    public TokenGenerator(String chiave, long tokenTTL, int mode) {
        try {
            // 将密码字符串与KEY_MASK异或
            byte[] keyBytes = new byte[KEY_MASK.length];
            System.arraycopy(KEY_MASK, 0, keyBytes, 0, KEY_MASK.length);
            byte[] chiaveBytes = chiave.getBytes("GBK");
            for (int i = 0; i < chiaveBytes.length && i < keyBytes.length; i++) {
                keyBytes[i] ^= chiaveBytes[i];
            }

            DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

            cipher = Cipher.getInstance("DES/CFB/NoPadding");
            cipher
                    .init(mode, secretKey, new IvParameterSpec(
                            IV));

            random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed((new Date()).getTime());

            this.tokenTTL = tokenTTL;

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 解密数据
     * @param data
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException 
     */
    public TokenInfo decrypt(byte[] data) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        byte[] result = cipher.doFinal(data);
        byte[] input = new byte[data.length - 25];
        System.arraycopy(result, 25, input, 0, input.length);
        String str = new String(input,"GBK");
        String[] arr = str.split("\n");
        TokenInfo token = new TokenInfo();
        token.setUserId(arr[0]);
        return token;
    }

    public long limitTime(byte[] data) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
		byte[] result = cipher.doFinal(data);
		byte[] mima = new byte[4];
		System.arraycopy(result, 5, mima, 0, mima.length);
		long l = bytesToLong(mima);
		return l;
	}
    
    /**
     * 生成可以传送给Streaming Server的访问token。
     * <p/>
     * <pre>
     *  token是经DES/CFB/NoPadding算法，用密钥加密过的。加密前的数据是这样的：
     *  校验字节 1Bytes 使得所有字节累加起来的和为零
     *  随机数 4Bytes
     *  过期时间 4Bytes 从2000年1月1日0点算起的秒数，低位在前
     *  是对ip+url的MD5摘要 16Bytes
     *  用户ID ASCII字符串，长度不定，以\x0结束
     * </pre>
     *
     * @param userIp      用户IP
     * @param relativeUrl 所访问的媒体文件相对URL
     * @param tokenInfo   token所包含的各信息元素。
     * @return 十六进制字符串表示的token。如果发生错误，则返回""。
     */
    public String generateToken(String userIp, String relativeUrl, TokenInfo tokenInfo) {
        String result = "";

        try {
            //随机数
            byte[] randomData = new byte[4];
            random.nextBytes(randomData);

            //过期时间
            Date now = new Date();
            byte[] validateData = longTo4Bytes(now.getTime() / 1000
                    - tokenTTLStartPoint + tokenTTL);
            Log.e(TAG,"validateData==" + validateData) ;
            // ip+url的MD5摘要（16字节长）
            StringBuffer ipurl = new StringBuffer();
            if (userIp != null) {
                ipurl.append(userIp);
            }
            if (relativeUrl != null) {
                ipurl.append(relativeUrl);
            }
            Md5 md5 = new Md5(ipurl.toString(), "GBK");
            md5.processString();
            byte[] hashOfIpAndURL = md5.getDigest();

            //用户ID + "\n" + serialNumber + "\n" + authKey + "\n" + userName + "\n" + contentName
            StringBuffer inputUserData = new StringBuffer();
            inputUserData.append(tokenInfo.getUserId()).append('\n');
            //inputUserData.append(tokenInfo.getRelativeUrl());
            //ad
            byte[] userData = inputUserData.toString().getBytes("GBK");

            //合并数据，准备加密
            byte[] input = new byte[1 + randomData.length + validateData.length
                    + hashOfIpAndURL.length + userData.length + 1];
            int n = 1;
            System.arraycopy(randomData, 0, input, n, randomData.length);
            n += randomData.length;
            System.arraycopy(validateData, 0, input, n, validateData.length);
            n += validateData.length;
            System
                    .arraycopy(hashOfIpAndURL, 0, input, n,
                            hashOfIpAndURL.length);
            n += hashOfIpAndURL.length;
            System.arraycopy(userData, 0, input, n, userData.length);
            n += userData.length;
            input[n] = 0;
            input[0] = 0;
            for (int i = 1; i < input.length; i++) {
                input[0] -= input[i];
            }
            result = encrypt(input);

        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }


    /**
     * convert the byte array into an hexadecimal string
     *
     * @param b
     * @return
     */
    private String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 加密指定的数据，并将结果用16进制字符串返回。
     *
     * @param data
     * @return @throws
     *         IllegalStateException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private String encrypt(byte[] data) throws IllegalStateException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] result = cipher.doFinal(data);
        String output = byteArrayToHexString(result);
        return output;
    }

    /**
     * 将长整型数转换成字节数组（8字节，低位在前）。
     *
     * @param l 假定不为负
     * @return
     */
    private byte[] longTo8Bytes(long l) {
        return longToBytes(l, 8);
    }

    /**
     * 将长整型数转换成字节数组（4字节，低位在前）。
     *
     * @param l 假定不为负
     * @return
     */
    private byte[] longTo4Bytes(long l) {
        return longToBytes(l, 4);
    }

    /**
     * 将长整型数转换成字节数组（n字节，低位在前）。
     *
     * @param l 假定不为负
     * @param n 字节数组的大小
     * @return 如果n太小，则抛弃l的高位
     */
    private byte[] longToBytes(long l, int n) {
        byte[] result = new byte[n];
        for (int i = 0; i < n; i++) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }
    private long bytesToLong(byte[] bb) {
        long l=( (((long) bb[3] & 0xff) << 24) 
                | (((long) bb[2] & 0xff) << 16) 
                | (((long) bb[1] & 0xff) << 8) | (((long) bb[0] & 0xff) << 0)); 
        return l;
    }
    /**
     * 生成可以传送给Streaming Server的访问token。
     * <p/>
     * <pre>
     *  token是经DES/CFB/NoPadding算法，用密钥加密过的。加密前的数据是这样的：
     *  校验字节 1Bytes 使得所有字节累加起来的和为零
     *  随机数 4Bytes
     *  过期时间 4Bytes 从2000年1月1日0点算起的秒数，低位在前
     *  是对ip+url的MD5摘要 16Bytes
     *  用户ID ASCII字符串，长度不定，以\x0结束
     * </pre>
     *
     * @param userIp      用户IP
     * @param relativeUrl 所访问的媒体文件相对URL
     * @param tokenInfo   token所包含的各信息元素。
     * @return 十六进制字符串表示的token。如果发生错误，则返回""。
     */
    public String generateTokenCnd2(String userIp, String relativeUrl, TokenInfo tokenInfo) {
        String result = "";

        try {
            //随机数
            byte[] randomData = new byte[4];
            random.nextBytes(randomData);

            //过期时间
            Date now = new Date();
            byte[] validateData = longTo4Bytes(now.getTime() / 1000
                    - tokenTTLStartPoint + tokenTTL);

            // ip+url的MD5摘要（16字节长）
            StringBuffer ipurl = new StringBuffer();
            if (userIp != null) {
                ipurl.append(userIp);
            }
            if (relativeUrl != null) {
                ipurl.append(relativeUrl);
            }
            Md5 md5 = new Md5(ipurl.toString(), "GBK");
            md5.processString();
            byte[] hashOfIpAndURL = md5.getDigest();

            //用户ID + "\n" + serialNumber + "\n" + authKey + "\n" + userName + "\n" + contentName
            StringBuffer inputUserData = new StringBuffer();
            inputUserData.append(tokenInfo.getUserId()).append('\n');
            inputUserData.append(tokenInfo.getSeq()).append('\n');
            inputUserData.append(tokenInfo.getOssSeq()).append('\n');
            inputUserData.append(tokenInfo.getUserName()).append('\n');
            inputUserData.append(tokenInfo.getContentName());
            inputUserData.append(tokenInfo.getRelativeUrl());
            //ad
            byte[] userData = inputUserData.toString().getBytes("GBK");

            //合并数据，准备加密
            byte[] input = new byte[1 + randomData.length + validateData.length
                    + hashOfIpAndURL.length + userData.length + 1];
            int n = 1;
            System.arraycopy(randomData, 0, input, n, randomData.length);
            n += randomData.length;
            System.arraycopy(validateData, 0, input, n, validateData.length);
            n += validateData.length;
            System
                    .arraycopy(hashOfIpAndURL, 0, input, n,
                            hashOfIpAndURL.length);
            n += hashOfIpAndURL.length;
            System.arraycopy(userData, 0, input, n, userData.length);
            n += userData.length;
            input[n] = 0;
            input[0] = 0;
            for (int i = 1; i < input.length; i++) {
                input[0] -= input[i];
            }
            result = encrypt(input);

        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
//    public String generateAccessUrl(String prefix, String relativeUrl, String userIp, TokenInfo tokenInfo, AdInfo[] adInfo,boolean booltoken) {
//        StringBuffer sb = new StringBuffer();
//        if (StringUtils.isBlank(prefix)||relativeUrl.startsWith("http")||relativeUrl.startsWith("rtsp")) {
//        	sb.append(relativeUrl);
//        }else{
//        	sb.append(prefix).append(relativeUrl);
//        }
//
//        if(booltoken){
//             String token = generateTokenCnd2(userIp, relativeUrl, tokenInfo);
//             sb.append("?token=").append(token);
//        }
//        if (adInfo != null) {
//            for (int i = 0; i < adInfo.length; i++) {
//                int insertTime = adInfo[i].getInsertTime();
//                String uri = adInfo[i].getAdUri();
//                if(booltoken){
//                    sb.append("&ts=").append(insertTime);
//                }else{
//                    sb.append("?ts=").append(insertTime);
//                    booltoken = true;
//                }
//                if (uri.startsWith("/")) {
//                    sb.append("&uri=").append(uri);
//                } else {
//                    sb.append("&url=").append(uri);
//                }
//            }
//        }
//        return sb.toString();
//    }
    
//    public static void main(String[] args) throws Exception {
//    	System.out.println("请输入需要解密的token串");
//    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//    	String token = br.readLine();
//    	byte[] data = Hex.decodeHex(token.toCharArray());
//    	TokenInfo info = new TokenGenerator(PageAttributeDef.SEC_KEY,-1, Cipher.DECRYPT_MODE).decrypt(data);
//    	System.out.println("解密成功");
//    	System.out.println(info.getUserId());
//    	System.out.println("解析详细字段");
//    	System.out.println(info.getUserId().replaceAll("\\|", "\n"));
//    	System.exit(0);
//    }

}
