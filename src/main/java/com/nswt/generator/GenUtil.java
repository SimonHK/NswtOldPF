package com.nswt.generator;

import com.nswt.framework.Config;
import com.nswt.framework.utility.FileUtil;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @User SimonKing
 * @Date 16/11/18.
 * @Time 22:25.
 * @Mail yuhongkai@nswt.com.cn
 */
public class GenUtil {


    public static boolean generatorFileInit(MyBGenerator myBGenerator){

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<!DOCTYPE generatorConfiguration \n");
        sb.append("     PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" \n");
        sb.append("     \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n");
        sb.append("<generatorConfiguration>\n");
        sb.append("     <classPathEntry location=\"").append(myBGenerator.getClassPathEntry_location()).append("\"/>\n");
        sb.append("     <context id=\"").append(myBGenerator.getContext_id()).append("\" defaultModelType=\"flat\"   targetRuntime=\"MyBatis3Simple\">\n");
        sb.append("          <commentGenerator>\n");
        sb.append("             <property name=\"suppressDate\" value=\"false\"/>\n");
        sb.append("             <property name=\"suppressAllComments\" value=\"true\"/>\n");
        sb.append("          </commentGenerator>\n");
        sb.append("          <jdbcConnection\n");
        sb.append("             driverClass=\"").append(myBGenerator.getJDBCCLASS()).append("\"\n");
        sb.append("             connectionURL=\"").append(myBGenerator.getJDBCCONNECTIONURL()).append("\"\n");
        sb.append("             userId=\"").append(myBGenerator.getJDBCUSER()).append("\"\n");
        sb.append("             password=\"").append(myBGenerator.getJDBCPASSWORD()).append("\">\n");
        sb.append("           </jdbcConnection>\n");
        sb.append("           <!-- 生成模型的包名和位置-->\n");
        sb.append("           <javaModelGenerator targetPackage=\"").append(myBGenerator.getPO_PACKAGE()).append("\" targetProject=\"").append(myBGenerator.getPO_OUT_PATH()).append("\">\n");
        sb.append("             <property name=\"enableSubPackages\" value=\"true\"/><property name=\"trimStrings\" value=\"true\"/>\n");
        sb.append("           </javaModelGenerator>\n");
        sb.append("           <!-- 生成映射文件的包名和位置-->\n");
        sb.append("           <sqlMapGenerator targetPackage=\"").append(myBGenerator.getMAPPER_PACKAGE()).append("\" targetProject=\"").append(myBGenerator.getMAPPER_OUT_PATH()).append("\">\n");
        sb.append("             <property name=\"enableSubPackages\" value=\"true\"/>\n");
        sb.append("           </sqlMapGenerator>\n");
        sb.append("           <!-- 生成DAO的包名和位置-->\n");
        sb.append("           <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"").append(myBGenerator.getDAO_PACKAGE()).append("\" targetProject=\"").append(myBGenerator.getDAO_OUT_PATH()).append("\">\n");
        sb.append("             <property name=\"enableSubPackages\" value=\"true\"/>\n");
        sb.append("           </javaClientGenerator>\n");
        sb.append("           <table tableName=\"").append(myBGenerator.getTABLE_NAME()).append("\" schema=\"").append(myBGenerator.getSCHEMA()).append("\">\n");
        sb.append("             <generatedKey column=\"").append(myBGenerator.getGENKEY_COLUMN()).append("\" sqlStatement=\"MySql\" identity=\"true\"/>\n");
        sb.append("             <columnRenamingRule searchString=\"_\" replaceString=\"\"/>\n");
        sb.append("           </table>\n");
        sb.append("     </context>\n");
        sb.append("</generatorConfiguration>");

        return FileUtil.writeText(Config.getContextRealPath() + "WEB-INF/classes/generatorConfig.xml", sb.toString(), "UTF-8");
        //return FileUtil.writeText("/Users/lijinyan/MyWork/NSWT/creditloan2/target/classes/generatorConfig.xml", sb.toString(), "UTF-8");
    }



    public static void main(String [] args){

        MyBGenerator myBGenerator = new MyBGenerator();
        myBGenerator.setClassPathEntry_location("/Users/lijinyan/MyWork/NSWT/shushuang-project/mysql-connector-java-5.1.30-bin.jar");
        myBGenerator.setContext_id("Hk001");
        myBGenerator.setJDBCCLASS("com.mysql.jdbc.Driver");
        myBGenerator.setJDBCCONNECTIONURL("jdbc:mysql://124.200.181.126:9306/HKTest?jdbcCompliantTruncation=false&amp;characterEncoding=UTF-8&amp;allowMultiQueries=true");
        myBGenerator.setJDBCUSER("root");
        myBGenerator.setJDBCPASSWORD("root");

        myBGenerator.setPO_PACKAGE("com.hk.domain");
        myBGenerator.setPO_OUT_PATH("/Users/lijinyan/MyWork/NSWT/creditloan2/src/main/webapp/wwwroot");

        myBGenerator.setMAPPER_PACKAGE("com.hk.mapper");
        myBGenerator.setMAPPER_OUT_PATH("/Users/lijinyan/MyWork/NSWT/creditloan2/src/main/webapp/wwwroot");

        myBGenerator.setDAO_PACKAGE("com.hk.dao");
        myBGenerator.setDAO_OUT_PATH("/Users/lijinyan/MyWork/NSWT/creditloan2/src/main/webapp/wwwroot");

        myBGenerator.setTABLE_NAME("table1");
        myBGenerator.setSCHEMA("HKTest");
        myBGenerator.setGENKEY_COLUMN("idnew_table");

        if(generatorFileInit(myBGenerator)){
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            //File configFile = new File(Config.getContextRealPath() + "WEB-INF/classes/generatorConfig.xml");
            File configFile = new File("/Users/lijinyan/MyWork/NSWT/creditloan2/target/classes/generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = null;
            try {
                config = cp.parseConfiguration(configFile);
                DefaultShellCallback callback = new DefaultShellCallback(overwrite);
                MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
                myBatisGenerator.generate(null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XMLParserException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
