package com.nswt.framework.messages;

import com.nswt.framework.*;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.license.SystemInfo;
import com.nswt.framework.security.ZRSACipher;
import com.nswt.framework.utility.*;
import java.io.ByteArrayInputStream;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.bouncycastle.jce.provider.JDKX509CertificateFactory;

// Referenced classes of package com.nswt.framework.messages:
//            LongTimeTask

public class LongTimeTaskPage extends Ajax
{

    public LongTimeTaskPage()
    {
    }

    public void getInfo()
    {
        long id = 0L;
        if(StringUtil.isNotEmpty($V("TaskID")))
        {
            id = Long.parseLong($V("TaskID"));
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
                if((new QueryBuilder("select count(*) from ZDUser")).executeInt() >= userLimit)
                {
                    LogUtil.fatal("\u5DF1\u6709\u7528\u6237\u6570\u8D85\u51FALicense\u4E2D\u7684\u7528\u6237\u6570\u9650" +
"\u5236!"
);
                    System.exit(0);
                }
                if(endDate.getTime() < System.currentTimeMillis())
                {
                    LogUtil.fatal("License\u5DF1\u8FC7\u671F!");
                    System.exit(0);
                }
                if(name.indexOf("Trial") < 0 && !macAddress.equalsIgnoreCase(SystemInfo.getMacAddress()))
                {
                    LogUtil.fatal("License\u4E2D\u6307\u5B9A\u7684Mac\u5730\u5740\u4E0E\u5B9E\u9645Mac\u5730\u5740\u4E0D" +
"\u4E00\u81F4!"
);
                    System.exit(0);
                }
                product = product.toLowerCase();
                try
                {
                    Class.forName("com.nswt.oa.workflow.FlowConfig");
                    if(product.indexOf("zoa") < 0)
                    {
                        LogUtil.fatal("License\u4E2D\u6CA1\u6709ZOA\u76F8\u5173\u7684\u6807\u8BB0!");
                        System.exit(0);
                    }
                }
                catch(Exception exception) { }
                try
                {
                    Class.forName("com.nswt.cms.stat.StatUtil");
                    if(product.indexOf("nswtplatform") < 0)
                    {
                        LogUtil.fatal("License\u4E2D\u6CA1\u6709nswtplatform\u76F8\u5173\u7684\u6807\u8BB0!");
                        System.exit(0);
                    }
                }
                catch(Exception exception1) { }
                try
                {
                    Class.forName("com.nswt.portal.Portal");
                    if(product.indexOf("zportal") < 0)
                    {
                        LogUtil.fatal("License\u4E2D\u6CA1\u6709ZPortal\u76F8\u5173\u7684\u6807\u8BB0!");
                        System.exit(0);
                    }
                }
                catch(Exception exception2) { }
                try
                {
                    Class.forName("com.nswt.shop.Goods");
                    if(product.indexOf("zshop") < 0)
                    {
                        LogUtil.fatal("License\u4E2D\u6CA1\u6709ZShop\u76F8\u5173\u7684\u6807\u8BB0!");
                        System.exit(0);
                    }
                }
                catch(Exception exception3) { }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        LongTimeTask ltt = LongTimeTask.getInstanceById(id);
        if(ltt != null && ltt.isAlive())
        {
            $S("CurrentInfo", StringUtil.isNotEmpty(ltt.getCurrentInfo()) ? ((Object) (ltt.getCurrentInfo() + "...")) : "");
            $S("Messages", ltt.getMessages());
            $S("Percent", ltt.getPercent());
        } else
        {
            $S("CompleteFlag", "1");
            if(ltt != null)
            {
                String errors = ltt.getAllErrors();
                if(StringUtil.isNotEmpty(errors))
                {
                    $S("CurrentInfo", errors);
                    $S("ErrorFlag", "1");
                } else
                {
                    $S("CurrentInfo", "\u4EFB\u52A1\u5DF1\u5B8C\u6210!");
                }
            } else
            {
                $S("CurrentInfo", "\u4EFB\u52A1\u5DF1\u5B8C\u6210!");
            }
            LongTimeTask.removeInstanceById(id);
        }
    }

    public void stop()
    {
        long id = Long.parseLong($V("TaskID"));
        LongTimeTask ltt = LongTimeTask.getInstanceById(id);
        if(ltt != null)
        {
            ltt.stopTask();
        }
    }

    public void stopComplete()
    {
        long id = Long.parseLong($V("TaskID"));
        LongTimeTask ltt = LongTimeTask.getInstanceById(id);
        if(ltt == null || !ltt.isAlive())
        {
            LongTimeTask.removeInstanceById(id);
        } else
        {
            Response.setStatus(0);
        }
    }
}
