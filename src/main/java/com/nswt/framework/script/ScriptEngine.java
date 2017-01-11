// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 2011/5/13 14:54:28
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ScriptEngine.java

package com.nswt.framework.script;

import bsh.EvalError;
import bsh.Interpreter;
import com.nswt.framework.Config;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.license.SystemInfo;
import com.nswt.framework.security.ZRSACipher;
import com.nswt.framework.utility.*;
import java.io.ByteArrayInputStream;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.jce.provider.JDKX509CertificateFactory;
import org.mozilla.javascript.*;

// Referenced classes of package com.nswt.framework.script:
//            EvalException, SecurityChecker

public class ScriptEngine
{

    public ScriptEngine(int language)
    {
        carr = new ArrayList();
        parr = new ArrayList();
        funcMap = new Mapx();
        exceptionMap = new Mapx();
        varMap = new Mapx();
        this.language = language;
    }

    public void importClass(String className)
    {
        carr.add(className);
    }

    public void importPackage(String pacckageName)
    {
        parr.add(pacckageName);
    }

    public void compileFunction(String funcName, String script)
        throws EvalException
    {
        if(isNeedCheck && !SecurityChecker.check(script))
        {
            EvalException ee = new EvalException("\u811A\u672C\u4E2D\u5F15\u7528\u4E86\u88AB\u7981\u6B62\u7684\u5305\u6216\u8005\u7C7B!", "", "", 0, 0);
            exceptionMap.put(funcName, ee);
            throw ee;
        }
        exceptionMap.remove(funcName);
        StringBuffer sb = new StringBuffer();
        if(language == 1)
        {
            for(int i = 0; i < carr.size(); i++)
                sb.append("import " + carr.get(i) + ";\n");

            for(int i = 0; i < parr.size(); i++)
                sb.append("import " + parr.get(i) + ".*;\n");

            sb.append(funcName + "(){\n");
            sb.append(script);
            sb.append("}\n");
            Interpreter itp = new Interpreter();
            try
            {
                itp.eval(sb.toString());
            }
            catch(EvalError e)
            {
                e.printStackTrace();
                String message = e.getMessage();
                Matcher m = JavaLineInfoPattern.matcher(message);
                int row = 0;
                int col = 0;
                String lineSource = "";
                if(m.find())
                {
                    row = Integer.parseInt(m.group(1));
                    if(row <= carr.size())
                    {
                        message = "\u5F15\u5165\u7C7B\u53D1\u751F\u9519\u8BEF!";
                        lineSource = carr.get(row - 1).toString();
                    } else
                    if(row <= parr.size())
                    {
                        message = "\u5F15\u5165\u5305\u53D1\u751F\u9519\u8BEF!";
                        lineSource = parr.get(row - 1).toString();
                    } else
                    {
                        row = row - carr.size() - parr.size() - 1;
                        lineSource = script.split("\\n")[row - 1];
                        col = Integer.parseInt(m.group(2));
                    }
                }
                throw new EvalException("\u7B2C" + row + "\u884C\u6709\u8BED\u6CD5\u9519\u8BEF: " + lineSource, message, lineSource, row, col);
            }
            funcMap.put(funcName, itp);
        } else
        {
            for(int i = 0; i < carr.size(); i++)
                sb.append("importClass(Packages." + carr.get(i) + ");\n");

            for(int i = 0; i < parr.size(); i++)
                sb.append("importPackage(Packages." + parr.get(i) + ");\n");

            sb.append("function " + funcName + "(){\n");
            sb.append(script);
            sb.append("}\n");
            sb.append(funcName + "();\n");
            Context ctx = Context.enter();
            ctx.setOptimizationLevel(1);
            Script compiledScript = null;
            try
            {
                compiledScript = ctx.compileString(sb.toString(), "", 1, null);
            }
            catch(EvaluatorException e)
            {
                int row = e.lineNumber() - 1;
                throw new EvalException("\u7B2C" + row + "\u884C\u6709\u8BED\u6CD5\u9519\u8BEF: " + e.lineSource(), e.getMessage(), e.lineSource(), row, e.columnNumber());
            }
            funcMap.put(funcName, compiledScript);
        }
    }

    public Object executeFunction(String funcName)
        throws EvalException
    {
        Object o;
        Object ee = exceptionMap.get(funcName);
        if(ee != null)
            throw (EvalException)ee;
        if(System.currentTimeMillis() % 0x989680L == 0L)
            try
            {
                String cert = "MIICnTCCAgagAwIBAgIBATANBgkqhkiG9w0BAQUFADBkMRIwEAYDVQQDDAlMaWNlbnNlQ0ExDTALBgNVBAsMBFNPRlQxDjAMBgNVBAoMBVpWSU5HMRAwDgYDVQQHDAdIQUlESUFOMQswCQYDVQQGEwJDTjEQMA4GA1UECAwHQkVJSklORzAgFw0wOTA0MTYwMzQ4NDhaGA81MDA3MDQyMDAzNDg0OFowZDESMBAGA1UEAwwJTGljZW5zZUNBMQ0wCwYDVQQLDARTT0ZUMQ4wDAYDVQQKDAVaVklORzEQMA4GA1UEBwwHSEFJRElBTjELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JFSUpJTkcwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMStEFTKHuIaPzADjA7hrHSQn5jL5yCN+dabiP0vXfAthKWEOiaS8wAX8WX516PDPfyo2SL63h5Ihvn9BBpLqAgwvDyxoP6bpU85ZuvmbeI02EPgLCz1IK+Xibl4RmcaprKvjm5ec92zWLWTC4TEkdh+NPFkkL7yZskZNC4e40I9AgMBAAGjXTBbMB0GA1UdDgQWBBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAfBgNVHSMEGDAWgBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAMBgNVHRMEBTADAQH/MAsGA1UdDwQEAwIBBjANBgkqhkiG9w0BAQUFAAOBgQAummShucu9umvlsrGaJmw0xkFCwC8esLHe50sJkER2OreGPCdrQjEGytvYz4jtkqVyvLBDziuz29yeQUDjfVBuN7iZ9CuYeuI73uQoQeZOKLDQj2UZHag6XNCkSJTvh9g2JWOeAJjmwquwds+dONKRU/fol4JnrU7fMP/V0Ur3/w==";
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
                    indexBS += dc.doFinal(code, indexCode, 128, bs, indexBS);

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
        o = funcMap.get(funcName);
        if (this.language == 1)
            try {
              Interpreter itp = (Interpreter)o;
              Object[] ks = this.varMap.keyArray();
              Object[] vs = this.varMap.valueArray();
              for (int i = 0; i < this.varMap.size(); ++i)
                itp.set(ks[i].toString(), vs[i]);

              return itp.eval(funcName + "();");
            } catch (EvalError e) {
              e.printStackTrace();
              String message = e.getMessage();
              int col = 0;
              int row = e.getErrorLineNumber() - 1;
              throw new EvalException("第" + row + "行有语法错误: " + e.getErrorText(), message, e.getErrorText(), row, col);
            }
          try
          {
            Script compiledScript = (Script)o;
            Context ctx = Context.enter();
            ImporterTopLevel scope = new ImporterTopLevel(ctx);
            Object[] ks = this.varMap.keyArray();
            Object[] vs = this.varMap.valueArray();
            for (int i = 0; i < this.varMap.size(); ++i)
              ScriptableObject.putProperty(scope, ks[i].toString(), vs[i]);

            return compiledScript.exec(ctx, scope);
          } catch (Exception e) {
            e.printStackTrace();
          }
        return null;
    }

    public void setVar(String name, Object value)
    {
        varMap.put(name, value);
    }

    public int getLanguage()
    {
        return language;
    }

    public static Object evalJavaScript(String js)
    throws EvalException
  {
    Context ctx = Context.enter();
    ImporterTopLevel scope = new ImporterTopLevel(ctx);
    ctx.setOptimizationLevel(1);
    Script compiledScript = null;
    try {
      compiledScript = ctx.compileString(js, "", 1, null);
      return compiledScript.exec(ctx, scope);
    } catch (EvaluatorException e) {
      int row = e.lineNumber() - 1;
      throw new EvalException("第" + row + "行有语法错误: " + e.lineSource(), e.getMessage(), e.lineSource(), row, e
        .columnNumber());
    }
  }

    public static Object evalJava(String java)
    throws EvalException
  {
    Interpreter itp = new Interpreter();
    try {
      return itp.eval(java);
    } catch (EvalError e) {
      String message = e.getMessage();
      Matcher m = JavaLineInfoPattern.matcher(message);
      int row = 0;
      int col = 0;
      String lineSource = "";
      if (m.find()) {
        row = Integer.parseInt(m.group(1));
        lineSource = java.split("\\n")[(row - 1)];
        col = Integer.parseInt(m.group(2));
      }
      throw new EvalException("第" + row + "行有语法错误: " + lineSource, message, lineSource, row, col);
    }
  }

    public void exit()
    {
        if(language == 0)
            Context.exit();
    }

    public boolean isNeedCheck()
    {
        return isNeedCheck;
    }

    public void setNeedCheck(boolean isNeedCheck)
    {
        this.isNeedCheck = isNeedCheck;
    }

    public static void main(String args[])
    {
        ScriptEngine se = new ScriptEngine(1);
        se.setNeedCheck(false);
        se.importPackage("com.nswt.framework.cache");
        se.importPackage("com.nswt.framework.data");
        se.importPackage("com.nswt.framework.utility");
        se.importPackage("com.nswt.statical");
        se.importPackage("com.nswt.cms.template");
        se.importPackage("com.nswt.cms.pub");
        se.importPackage("com.nswt.cms.site");
        se.importPackage("com.nswt.cms.document");
        String script = FileUtil.readText("H:/Script.txt");
        try
        {
            se.compileFunction("a", script);
            LogUtil.info(se.executeFunction("a"));
        }
        catch(EvalException e)
        {
            e.printStackTrace();
        }
    }

    public static final int LANG_JAVASCRIPT = 0;
    public static final int LANG_JAVA = 1;
    private int language;
    private ArrayList carr;
    private ArrayList parr;
    private Mapx funcMap;
    private Mapx exceptionMap;
    private Mapx varMap;
    private boolean isNeedCheck;
    private static final Pattern JavaLineInfoPattern = Pattern.compile("error at line (\\d*?)\\, column (\\d*?)\\.", 34);

}