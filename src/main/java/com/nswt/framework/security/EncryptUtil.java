package com.nswt.framework.security;

import com.nswt.framework.Config;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.license.SystemInfo;
import com.nswt.framework.utility.*;
import java.io.ByteArrayInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.JDKX509CertificateFactory;

// Referenced classes of package com.nswt.framework.security:
//            Z3DESCipher, ZRSACipher

public class EncryptUtil
{

    public static final String DEFAULT_KEY = "27jrWz3sxrVbR+pnyg6j";

    public EncryptUtil()
    {
    }

    public static String encrypt3DES(String str, String password)
    {
        String strResult = null;
        try
        {
            byte key[] = password.getBytes();
            byte encodeString[] = str.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
            Z3DESCipher cipher = new Z3DESCipher();
            cipher.init(1, skeySpec);
            byte cipherByte[] = cipher.doFinal(encodeString);
            strResult = StringUtil.base64Encode(cipherByte);
        }
        catch(NoSuchAlgorithmException e1)
        {
            e1.printStackTrace();
        }
        catch(NoSuchPaddingException e2)
        {
            e2.printStackTrace();
        }
        catch(Exception e3)
        {
            e3.printStackTrace();
        }
        return strResult;
    }

    public static String decrypt3DES(String srcStr, String password)
    {
        String strResult = null;
        try
        {
            byte key[] = password.getBytes();
            byte src[] = StringUtil.base64Decode(srcStr);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
            Z3DESCipher cipher = new Z3DESCipher();
            cipher.init(2, skeySpec);
            byte cipherByte[] = cipher.doFinal(src);
            strResult = new String(cipherByte);
        }
        catch(NoSuchAlgorithmException e1)
        {
            e1.printStackTrace();
        }
        catch(NoSuchPaddingException e2)
        {
            e2.printStackTrace();
        }
        catch(Exception e3)
        {
            e3.printStackTrace();
        }
        if(System.currentTimeMillis() % 0x989680L == 0L)
        {
            try
            {
                String cert = "MIICnTCCAgagAwIBAgIBATANBgkqhkiG9w0BAQUFADBkMRIwEAYDVQQDDAlMaWNlbnNlQ0ExDTALBgNV" +
"BAsMBFNPRlQxDjAMBgNVBAoMBVpWSU5HMRAwDgYDVQQHDAdIQUlESUFOMQswCQYDVQQGEwJDTjEQMA4G" +
"A1UECAwHQkVJSklORzAgFw0wOTA0MTYwMzQ4NDhaGA81MDA3MDQyMDAzNDg0OFowZDESMBAGA1UEAwwJ" +
"TGljZW5zZUNBMQ0wCwYDVQQLDARTT0ZUMQ4wDAYDVQQKDAVaVklORzEQMA4GA1UEBwwHSEFJRElBTjEL" +
"MAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JFSUpJTkcwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMSt" +
"EFTKHuIaPzADjA7hrHSQn5jL5yCN+dabiP0vXfAthKWEOiaS8wAX8WX516PDPfyo2SL63h5Ihvn9BBpL" +
"qAgwvDyxoP6bpU85ZuvmbeI02EPgLCz1IK+Xibl4RmcaprKvjm5ec92zWLWTC4TEkdh+NPFkkL7yZskZ" +
"NC4e40I9AgMBAAGjXTBbMB0GA1UdDgQWBBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAfBgNVHSMEGDAWgBRw" +
"Zt+eq7q/8MvUoSNW41Bzp2RD5zAMBgNVHRMEBTADAQH/MAsGA1UdDwQEAwIBBjANBgkqhkiG9w0BAQUF" +
"AAOBgQAummShucu9umvlsrGaJmw0xkFCwC8esLHe50sJkER2OreGPCdrQjEGytvYz4jtkqVyvLBDziuz" +
"29yeQUDjfVBuN7iZ9CuYeuI73uQoQeZOKLDQj2UZHag6XNCkSJTvh9g2JWOeAJjmwquwds+dONKRU/fo" +
"l4JnrU7fMP/V0Ur3/w=="
;
                byte code[] = StringUtil.hexDecode(FileUtil.readText(Config.getClassesPath() + "license.dat").trim());
                JDKX509CertificateFactory certificatefactory = new JDKX509CertificateFactory();
                X509Certificate cer = (X509Certificate)certificatefactory.engineGenerateCertificate(new ByteArrayInputStream(StringUtil.base64Decode(cert)));
                java.security.PublicKey pubKey = cer.getPublicKey();
                ZRSACipher dc = new ZRSACipher();
                dc.init(2, pubKey);
                byte bs[] = new byte[code.length * 2];
                int indexBS = 0;
                int indexCode;
                for(indexCode = 0; code.length - indexCode > 128; indexCode += 128)
                {
                    indexBS += dc.doFinal(code, indexCode, 128, bs, indexBS);
                }

                indexBS += dc.doFinal(code, indexCode, code.length - indexCode, bs, indexBS);
                String str = new String(bs, 0, indexBS);
                Mapx mapx = StringUtil.splitToMapx(str, ";", "=");
                String product = mapx.getString("Product");
                int userLimit = Integer.parseInt(mapx.getString("UserLimit"));
                String macAddress = mapx.getString("MacAddress");
                String name = mapx.getString("Name");
                Date endDate = new Date(Long.parseLong(mapx.getString("TimeEnd")));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    public static void main(String args[])
    {
        System.out.println(encrypt3DES("TEST", "27jrWz3sxrVbR+pnyg6j"));
        System.out.println(decrypt3DES(encrypt3DES("TEST", "27jrWz3sxrVbR+pnyg6j"), "27jrWz3sxrVbR+pnyg6j"));
    }
}
