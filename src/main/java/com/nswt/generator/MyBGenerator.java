package com.nswt.generator;

/**
 * @User SimonKing
 * @Date 16/11/19.
 * @Time 22:23.
 * @Mail yuhongkai@nswt.com.cn
 */
public class MyBGenerator {

    public MyBGenerator(){}

    //<!--指定特定数据库的jdbc驱动jar包的位置 -->
    public String classPathEntry_location = "";

    public String context_id="hktestdb";

    public String JDBCCLASS = "";

    public String JDBCCONNECTIONURL = "";

    public String JDBCUSER = "";

    public String JDBCPASSWORD = "";

    public String PO_PACKAGE = "";

    public String PO_OUT_PATH = "";

    public final String PO_ENABLESUBPACKAGES ="enableSubPackages";

    public String po_enableSubPackages = "true";

    public final String PO_TRIMSTRINGS="trimStrings";

    public String po_trimStrings = "true";

    public String MAPPER_PACKAGE = "";

    public String MAPPER_OUT_PATH = "";

    public final String MAPPER_ENABLESUBPACKAGES = "enableSubPackages";

    public String mapper_enableSubPackages = "true";

    public String DAO_PACKAGE = "";

    public String DAO_OUT_PATH = "";

    public final String DAO_ENABLESUBPACKAGES = "enableSubPackages";

    public String dao_enableSubPackages = "true";

    public final String dao_type="XMLMAPPER";

    public String TABLE_NAME = "";

    public String SCHEMA = "";

    public String GENKEY_COLUMN = "";

    public String GENKEY_SQLSTATEMENT = "";

    public String GENKEY_IDENTITY = "";

    public final String COLUMNRENAMINGRULE_SEARCHSTRING = "_";

    public final String COLUMNRENAMINGRULE_REPLACESTRING = "";

    public String getClassPathEntry_location() {
        return classPathEntry_location;
    }

    public void setClassPathEntry_location(String classPathEntry_location) {
        this.classPathEntry_location = classPathEntry_location;
    }

    public String getContext_id() {
        return context_id;
    }

    public void setContext_id(String context_id) {
        this.context_id = context_id;
    }


    public String getJDBCCLASS() {
        return JDBCCLASS;
    }

    public void setJDBCCLASS(String JDBCCLASS) {
        this.JDBCCLASS = JDBCCLASS;
    }

    public String getJDBCCONNECTIONURL() {
        return JDBCCONNECTIONURL;
    }

    public void setJDBCCONNECTIONURL(String JDBCCONNECTIONURL) {
        this.JDBCCONNECTIONURL = JDBCCONNECTIONURL;
    }

    public String getJDBCUSER() {
        return JDBCUSER;
    }

    public void setJDBCUSER(String JDBCUSER) {
        this.JDBCUSER = JDBCUSER;
    }

    public String getJDBCPASSWORD() {
        return JDBCPASSWORD;
    }

    public void setJDBCPASSWORD(String JDBCPASSWORD) {
        this.JDBCPASSWORD = JDBCPASSWORD;
    }

    public String getPO_PACKAGE() {
        return PO_PACKAGE;
    }

    public void setPO_PACKAGE(String PO_PACKAGE) {
        this.PO_PACKAGE = PO_PACKAGE;
    }

    public String getPO_OUT_PATH() {
        return PO_OUT_PATH;
    }

    public void setPO_OUT_PATH(String PO_OUT_PATH) {
        this.PO_OUT_PATH = PO_OUT_PATH;
    }

    public String getPO_ENABLESUBPACKAGES() {
        return PO_ENABLESUBPACKAGES;
    }

    public String getPo_enableSubPackages() {
        return po_enableSubPackages;
    }

    public void setPo_enableSubPackages(String po_enableSubPackages) {
        this.po_enableSubPackages = po_enableSubPackages;
    }

    public String getPO_TRIMSTRINGS() {
        return PO_TRIMSTRINGS;
    }

    public String getPo_trimStrings() {
        return po_trimStrings;
    }

    public void setPo_trimStrings(String po_trimStrings) {
        this.po_trimStrings = po_trimStrings;
    }

    public String getMAPPER_PACKAGE() {
        return MAPPER_PACKAGE;
    }

    public void setMAPPER_PACKAGE(String MAPPER_PACKAGE) {
        this.MAPPER_PACKAGE = MAPPER_PACKAGE;
    }

    public String getMAPPER_OUT_PATH() {
        return MAPPER_OUT_PATH;
    }

    public void setMAPPER_OUT_PATH(String MAPPER_OUT_PATH) {
        this.MAPPER_OUT_PATH = MAPPER_OUT_PATH;
    }

    public String getMAPPER_ENABLESUBPACKAGES() {
        return MAPPER_ENABLESUBPACKAGES;
    }

    public String getMapper_enableSubPackages() {
        return mapper_enableSubPackages;
    }

    public void setMapper_enableSubPackages(String mapper_enableSubPackages) {
        this.mapper_enableSubPackages = mapper_enableSubPackages;
    }

    public String getDAO_PACKAGE() {
        return DAO_PACKAGE;
    }

    public void setDAO_PACKAGE(String DAO_PACKAGE) {
        this.DAO_PACKAGE = DAO_PACKAGE;
    }

    public String getDAO_OUT_PATH() {
        return DAO_OUT_PATH;
    }

    public void setDAO_OUT_PATH(String DAO_OUT_PATH) {
        this.DAO_OUT_PATH = DAO_OUT_PATH;
    }

    public String getDAO_ENABLESUBPACKAGES() {
        return DAO_ENABLESUBPACKAGES;
    }

    public String getDao_enableSubPackages() {
        return dao_enableSubPackages;
    }

    public void setDao_enableSubPackages(String dao_enableSubPackages) {
        this.dao_enableSubPackages = dao_enableSubPackages;
    }

    public String getDao_type() {
        return dao_type;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getSCHEMA() {
        return SCHEMA;
    }

    public void setSCHEMA(String SCHEMA) {
        this.SCHEMA = SCHEMA;
    }

    public String getGENKEY_COLUMN() {
        return GENKEY_COLUMN;
    }

    public void setGENKEY_COLUMN(String GENKEY_COLUMN) {
        this.GENKEY_COLUMN = GENKEY_COLUMN;
    }

    public String getGENKEY_SQLSTATEMENT() {
        return GENKEY_SQLSTATEMENT;
    }

    public void setGENKEY_SQLSTATEMENT(String GENKEY_SQLSTATEMENT) {
        this.GENKEY_SQLSTATEMENT = GENKEY_SQLSTATEMENT;
    }

    public String getGENKEY_IDENTITY() {
        return GENKEY_IDENTITY;
    }

    public void setGENKEY_IDENTITY(String GENKEY_IDENTITY) {
        this.GENKEY_IDENTITY = GENKEY_IDENTITY;
    }

    public String getCOLUMNRENAMINGRULE_SEARCHSTRING() {
        return COLUMNRENAMINGRULE_SEARCHSTRING;
    }

    public String getCOLUMNRENAMINGRULE_REPLACESTRING() {
        return COLUMNRENAMINGRULE_REPLACESTRING;
    }
}
