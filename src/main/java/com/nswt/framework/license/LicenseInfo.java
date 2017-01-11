package com.nswt.framework.license;

import com.nswt.framework.Config;
import com.nswt.framework.security.ZRSACipher;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringFormat;
import com.nswt.framework.utility.StringUtil;
import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.bouncycastle.jce.provider.JDKX509CertificateFactory;

public class LicenseInfo
{
  public static boolean isLicenseValidity = true;
  public static boolean isIPValidity = true;
  public static boolean isUserCountValidity = true;
  private static String name;
  private static String product;
  private static String macAddress;
  private static int userLimit;
  private static Date endDate;
  static String cert = "MIICnTCCAgagAwIBAgIBATANBgkqhkiG9w0BAQUFADBkMRIwEAYDVQQDDAlMaWNlbnNlQ0ExDTALBgNVBAsMBFNPRlQxDjAMBgNVBAoMBVpWSU5HMRAwDgYDVQQHDAdIQUlESUFOMQswCQYDVQQGEwJDTjEQMA4GA1UECAwHQkVJSklORzAgFw0wOTA0MTYwMzQ4NDhaGA81MDA3MDQyMDAzNDg0OFowZDESMBAGA1UEAwwJTGljZW5zZUNBMQ0wCwYDVQQLDARTT0ZUMQ4wDAYDVQQKDAVaVklORzEQMA4GA1UEBwwHSEFJRElBTjELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JFSUpJTkcwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMStEFTKHuIaPzADjA7hrHSQn5jL5yCN+dabiP0vXfAthKWEOiaS8wAX8WX516PDPfyo2SL63h5Ihvn9BBpLqAgwvDyxoP6bpU85ZuvmbeI02EPgLCz1IK+Xibl4RmcaprKvjm5ec92zWLWTC4TEkdh+NPFkkL7yZskZNC4e40I9AgMBAAGjXTBbMB0GA1UdDgQWBBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAfBgNVHSMEGDAWgBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAMBgNVHRMEBTADAQH/MAsGA1UdDwQEAwIBBjANBgkqhkiG9w0BAQUFAAOBgQAummShucu9umvlsrGaJmw0xkFCwC8esLHe50sJkER2OreGPCdrQjEGytvYz4jtkqVyvLBDziuz29yeQUDjfVBuN7iZ9CuYeuI73uQoQeZOKLDQj2UZHag6XNCkSJTvh9g2JWOeAJjmwquwds+dONKRU/fol4JnrU7fMP/V0Ur3/w==";

  public static synchronized void init()
  {
    if (name == null)
      update();
  }

  public static synchronized void update() {
    byte[] code;
    try {
      code = StringUtil.hexDecode(FileUtil.readText(Config.getClassesPath() + "license.dat").trim());
      JDKX509CertificateFactory certificatefactory = new JDKX509CertificateFactory();
      X509Certificate cer = (X509Certificate)certificatefactory
        .engineGenerateCertificate(new ByteArrayInputStream(StringUtil.base64Decode(cert)));
      PublicKey pubKey = cer.getPublicKey();
      ZRSACipher dc = new ZRSACipher();
      dc.init(2, pubKey);
      byte[] bs = new byte[code.length * 2];
      int indexBS = 0;
      int indexCode = 0;
      while (code.length - indexCode > 128) {
        indexBS += dc.doFinal(code, indexCode, 128, bs, indexBS);
        indexCode += 128;
      }
      indexBS += dc.doFinal(code, indexCode, code.length - indexCode, bs, indexBS);
      String str = new String(bs, 0, indexBS);
      Mapx map = StringUtil.splitToMapx(str, ";", "=");
      name = map.getString("Name");
      product = map.getString("Product");
      userLimit = Integer.parseInt(map.getString("UserLimit"));
      macAddress = map.getString("MacAddress");
      if (!(name.endsWith("TrialUser")))
        macAddress = "全部";

      endDate = new Date(Long.parseLong(map.getString("TimeEnd")));
    } catch (Exception e) {
      e.printStackTrace();
      LogUtil.error("检查License时发生致命错误!");
      System.exit(0); }
  }

  public static String getLicenseRequest(String customer) {
    JDKX509CertificateFactory certificatefactory;
    try {
      certificatefactory = new JDKX509CertificateFactory();
      X509Certificate cer = (X509Certificate)certificatefactory
        .engineGenerateCertificate(new ByteArrayInputStream(StringUtil.base64Decode(cert)));
      PublicKey pubKey = cer.getPublicKey();
      ZRSACipher ec = new ZRSACipher();
      ec.init(1, pubKey);
      StringFormat sf = new StringFormat("Name=?;MacAddress=?");
      sf.add(customer);
      sf.add(SystemInfo.getMacAddress());
      byte[] bs = sf.toString().getBytes();
      byte[] code = new byte[((bs.length - 1) / 117 + 1) * 128];
      int indexBS = 0;
      int indexCode = 0;
      while (bs.length - indexBS > 117) {
        indexCode += ec.doFinal(bs, indexBS, 117, code, indexCode);
        indexBS += 117;
      }
      ec.doFinal(bs, indexBS, bs.length - indexBS, code, indexCode);
      return StringUtil.hexEncode(code);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null; }

  public static boolean verifyLicense(String license) {
    byte[] code;
    try {
      code = StringUtil.hexDecode(license);
      JDKX509CertificateFactory certificatefactory = new JDKX509CertificateFactory();
      X509Certificate cer = (X509Certificate)certificatefactory
        .engineGenerateCertificate(new ByteArrayInputStream(StringUtil.base64Decode(cert)));
      PublicKey pubKey = cer.getPublicKey();
      ZRSACipher dc = new ZRSACipher();
      dc.init(2, pubKey);
      byte[] bs = new byte[code.length * 2];
      int indexBS = 0;
      int indexCode = 0;
      while (code.length - indexCode > 128) {
        indexBS += dc.doFinal(code, indexCode, 128, bs, indexBS);
        indexCode += 128;
      }
      indexBS += dc.doFinal(code, indexCode, code.length - indexCode, bs, indexBS);
      String str = new String(bs, 0, indexBS);
      Mapx map = StringUtil.splitToMapx(str, ";", "=");
      String mac1 = map.getString("MacAddress");
      String mac2 = SystemInfo.getMacAddress();
      String[] arr = mac2.split(",");
      for (int i = 0; i < arr.length; ++i)
        if (mac1.indexOf(arr[i]) < 0)
          return false;


      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean isIPValidity() {
    init();
    return isIPValidity;
  }

  public static boolean isLicenseValidity() {
    init();
    return isLicenseValidity;
  }

  public static boolean isUserCountValidity() {
    init();
    return isUserCountValidity;
  }

  public static String getName() {
    init();
    return name;
  }

  public static String getProduct() {
    init();
    return product;
  }

  public static int getUserLimit() {
    init();
    return userLimit;
  }

  public static Date getEndDate() {
    init();
    return endDate;
  }

  public static void setEndDate(Date endDate) {
    endDate = endDate;
  }

  public static void setIPValidity(boolean isIPValidity) {
    isIPValidity = isIPValidity;
  }

  public static void setLicenseValidity(boolean isLicenseValidity) {
    isLicenseValidity = isLicenseValidity;
  }

  public static void setUserCountValidity(boolean isUserCountValidity) {
    isUserCountValidity = isUserCountValidity;
  }

  public static void setName(String name) {
    name = name;
  }

  public static void setProduct(String product) {
    product = product;
  }

  public static void setUserLimit(int userLimit) {
    userLimit = userLimit;
  }

  public static String getMacAddress() {
    init();
    return macAddress;
  }

  public static void setMacAddress(String macAddress) {
    macAddress = macAddress;
  }
}