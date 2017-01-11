package com.nswt.framework.orm;

import com.nswt.framework.Config;
import com.nswt.framework.utility.FileUtil;
import java.io.File;
import java.util.List;
import org.dom4j.*;
import org.dom4j.io.SAXReader;



public class CRUDGenerator
{
    class SchemaColumn
    {

        public String ID;
        public String Name;
        public String Code;
        public String Comment;
        public String DataType;
        public int Length;
        public boolean Mandatory;
        public boolean isPrimaryKey;

        SchemaColumn()
        {
        }
    }


    private String fileName;
    private String outputDir;
    private String namespace;
    private String aID;
    private Namespace nso;
    private Namespace nsc;
    private Namespace nsa;

    public CRUDGenerator()
    {
        aID = "ID";
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public void setOutputDir(String dir)
    {
        outputDir = dir;
    }

    public void generate()
        throws Exception
    {
        File f = new File(fileName);
        if(!f.exists())
        {
            throw new RuntimeException("\u6587\u4EF6\u4E0D\u5B58\u5728");
        }
        SAXReader reader = new SAXReader(false);
        Document doc = reader.read(f);
        Element root = doc.getRootElement();
        nso = root.getNamespaceForPrefix("o");
        nsc = root.getNamespaceForPrefix("c");
        nsa = root.getNamespaceForPrefix("a");
        Element rootObject = root.element(new QName("RootObject", nso));
        Element children = rootObject.element(new QName("Children", nsc));
        Element model = children.element(new QName("Model", nso));
        if(model.attributeValue("ID") == null)
        {
            if(model.attributeValue("Id") != null)
            {
                aID = "Id";
            } else
            {
                throw new RuntimeException("ID\u5C5E\u6027\u540D\u79F0\u672A\u5B9A\uFF0CPDM\u7248\u672C\u4E0D\u6B63\u786E");
            }
        }
        List tables = model.element(new QName("Tables", nsc)).elements();
        for(int i = 0; i < tables.size(); i++)
        {
            try
            {
                generateOneTable((Element)tables.get(i));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    private void generateOneTable(Element table)
    {
        String tableName = table.elementText(new QName("Name", nsa));
        String tableCode = table.elementText(new QName("Code", nsa));
        String tableComment = table.elementText(new QName("Comment", nsa));
        Element eColumns = table.element(new QName("Columns", nsc));
        if(eColumns == null)
        {
            System.out.println("Error:\u6CA1\u6709\u4E3A\u8868" + tableCode + "\u5B9A\u4E49\u5217!");
            return;
        }
        List columns = eColumns.elements();
        SchemaColumn scs[] = new SchemaColumn[columns.size()];
        for(int i = 0; i < columns.size(); i++)
        {
            SchemaColumn sc = new SchemaColumn();
            Element column = (Element)columns.get(i);
            sc.ID = column.attributeValue(aID);
            sc.Name = column.elementText(new QName("Name", nsa));
            sc.Code = column.elementText(new QName("Code", nsa));
            sc.Comment = column.elementText(new QName("Comment", nsa));
            sc.DataType = column.elementText(new QName("DataType", nsa));
            String length = column.elementText(new QName("Length", nsa));
            try
            {
                if(length != null && !length.equals(""))
                {
                    sc.Length = Integer.parseInt(length);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            String mandatory = column.elementText(new QName("Mandatory", nsa));
            if(mandatory == null || mandatory.equals("") || mandatory.equals("0"))
            {
                sc.Mandatory = false;
            } else
            {
                sc.Mandatory = true;
            }
            scs[i] = sc;
        }

        Element primaryKey = table.element(new QName("PrimaryKey", nsc));
        String keyRef = null;
        if(primaryKey != null)
        {
            primaryKey = primaryKey.element(new QName("Key", nso));
            if(primaryKey != null)
            {
                keyRef = primaryKey.attributeValue("Ref");
            }
        }
        if(keyRef != null)
        {
            List keys = table.element(new QName("Keys", nsc)).elements();
            boolean keyFlag = false;
            for(int i = 0; i < keys.size(); i++)
            {
                Element key = (Element)keys.get(i);
                if(!keyRef.equals(key.attributeValue(aID)))
                {
                    continue;
                }
                List keyColumns = key.element(new QName("Key.Columns", nsc)).elements();
                for(int j = 0; j < keyColumns.size(); j++)
                {
                    String columnID = ((Element)keyColumns.get(j)).attributeValue("Ref");
                    for(int k = 0; k < scs.length; k++)
                    {
                        if(scs[k].ID.equals(columnID))
                        {
                            scs[k].isPrimaryKey = true;
                        }
                    }

                }

                keyFlag = true;
                break;
            }

            if(!keyFlag)
            {
                System.out.println("\u8868" + tableCode + "\u672A\u627E\u5230\u4E3B\u952E!");
            }
        }
        if(!checkCode(tableCode, "\u8868\u4EE3\u7801"))
        {
            return;
        }
        StringBuffer sb = new StringBuffer();
        StringBuffer dsb = new StringBuffer();
        StringBuffer hsb = new StringBuffer();
        StringBuffer svsb = new StringBuffer();
        StringBuffer gvsb = new StringBuffer();
        StringBuffer csb = new StringBuffer();
        StringBuffer isb = new StringBuffer();
        StringBuffer insertsb = new StringBuffer();
        StringBuffer updatesb = new StringBuffer();
        StringBuffer pksb = new StringBuffer();
        sb.append("package " + namespace + ";\n\n");
        isb.append("import com.nswt.framework.data.DataColumn;\n");
        isb.append("import com.nswt.framework.orm.Schema;\n");
        isb.append("import com.nswt.framework.orm.SchemaSet;\n");
        isb.append("import com.nswt.framework.orm.SchemaColumn;\n");
        hsb.append("\tpublic static final SchemaColumn[] _Columns = new SchemaColumn[] {\n");
        svsb.append("\tpublic void setV(int i, Object v) {\n");
        gvsb.append("\tpublic Object getV(int i) {\n");
        insertsb.append("\tprotected static final String _InsertAllSQL = \"insert into " + tableCode + " values(");
        updatesb.append("\tprotected static final String _UpdateAllSQL = \"update " + tableCode + " set ");
        pksb.append(" where ");
        boolean dateFlag = false;
        boolean blobFlag = false;
        boolean firstPKFlag = true;
        for(int i = 0; i < scs.length; i++)
        {
            String code = scs[i].Code;
            if(i == 0)
            {
                insertsb.append("?");
                updatesb.append(code + "=?");
            } else
            {
                insertsb.append(",?");
                updatesb.append("," + code + "=?");
            }
            if(scs[i].isPrimaryKey)
            {
                if(firstPKFlag)
                {
                    pksb.append(code + "=?");
                    firstPKFlag = false;
                } else
                {
                    pksb.append(" and " + code + "=?");
                }
            }
            if(!checkCode(code, "\u8868" + tableCode + "\u7684\u5B57\u6BB5"))
            {
                return;
            }
            String dataType = scs[i].DataType;
            if(dataType == null || dataType.equals(""))
            {
                System.out.println("Error:\u8868" + tableCode + "\u7684\u5B57\u6BB5" + code + "\u7684\u6570\u636E\u7C7B\u578B\u672A\u5B9A\u4E49!");
                return;
            }
            String type = dataType.toLowerCase().trim();
            String ctype = null;
            String vtype = null;
            if(type.startsWith("nvarchar") || type.startsWith("varchar") || type.startsWith("char") || type.startsWith("nchar") || type.startsWith("long varchar") || type.startsWith("ntext") || type.startsWith("text"))
            {
                type = "String";
                ctype = "STRING";
                vtype = type;
            } else
            if(type.startsWith("int") || type.startsWith("bit") || type.startsWith("smallint"))
            {
                type = "int";
                ctype = "INTEGER";
                vtype = "Integer";
            } else
            if(type.startsWith("long") || type.startsWith("bigint"))
            {
                type = "long";
                ctype = "LONG";
                vtype = "Long";
            } else
            if(type.startsWith("float"))
            {
                type = "float";
                ctype = "FLOAT";
                vtype = "Float";
            } else
            if(type.startsWith("double") || type.startsWith("decimal") || type.startsWith("number"))
            {
                type = "double";
                ctype = "DOUBLE";
                vtype = "Double";
            } else
            if(type.startsWith("blob") || type.startsWith("clob"))
            {
                type = "Blob";
                ctype = "BLOB";
                vtype = type;
                blobFlag = true;
            } else
            if(type.startsWith("date") || type.startsWith("time"))
            {
                type = "Date";
                ctype = "DATETIME";
                vtype = type;
                dateFlag = true;
            } else
            {
                System.out.println(tableCode + "\uFF1A\u4E0D\u652F\u6301\u7684\u6570\u636E\u7C7B\u578B" + type);
                return;
            }
            dsb.append("\tprivate " + vtype + " " + scs[i].Code + ";\n\n");
            csb.append("\t/**\n");
            csb.append("\t* \u83B7\u53D6\u5B57\u6BB5" + code + "\u7684\u503C\uFF0C\u8BE5\u5B57\u6BB5\u7684<br>\n");
            csb.append("\t* \u5B57\u6BB5\u540D\u79F0 :" + scs[i].Name + "<br>\n");
            csb.append("\t* \u6570\u636E\u7C7B\u578B :" + scs[i].DataType + "<br>\n");
            csb.append("\t* \u662F\u5426\u4E3B\u952E :" + scs[i].isPrimaryKey + "<br>\n");
            csb.append("\t* \u662F\u5426\u5FC5\u586B :" + scs[i].Mandatory + "<br>\n");
            if(scs[i].Comment != null)
            {
                csb.append("\t* \u5907\u6CE8\u4FE1\u606F :<br>\n");
                splitComment(csb, scs[i].Comment, "\t");
            }
            csb.append("\t*/\n");
            csb.append("\tpublic " + type + " get" + code + "() {\n");
            if(vtype.equals("Float") || vtype.equals("Integer") || vtype.equals("Long") || vtype.equals("Double"))
            {
                csb.append("\t\tif(" + code + "==null){return 0;}\n");
                csb.append("\t\treturn " + code + "." + type + "Value();\n");
            } else
            {
                csb.append("\t\treturn " + code + ";\n");
            }
            csb.append("\t}\n\n");
            csb.append("\t/**\n");
            csb.append("\t* \u8BBE\u7F6E\u5B57\u6BB5" + code + "\u7684\u503C\uFF0C\u8BE5\u5B57\u6BB5\u7684<br>\n");
            csb.append("\t* \u5B57\u6BB5\u540D\u79F0 :" + scs[i].Name + "<br>\n");
            csb.append("\t* \u6570\u636E\u7C7B\u578B :" + scs[i].DataType + "<br>\n");
            csb.append("\t* \u662F\u5426\u4E3B\u952E :" + scs[i].isPrimaryKey + "<br>\n");
            csb.append("\t* \u662F\u5426\u5FC5\u586B :" + scs[i].Mandatory + "<br>\n");
            if(scs[i].Comment != null)
            {
                csb.append("\t* \u5907\u6CE8\u4FE1\u606F :<br>\n");
                splitComment(csb, scs[i].Comment, "\t");
            }
            csb.append("\t*/\n");
            String tCode = code.substring(0, 1).toLowerCase() + code.substring(1);
            csb.append("\tpublic void set" + code + "(" + type + " " + tCode + ") {\n");
            if(vtype.equals("Float") || vtype.equals("Integer") || vtype.equals("Long") || vtype.equals("Double"))
            {
                csb.append("\t\t this." + code + " = new " + vtype + "(" + tCode + ");\n");
            } else
            {
                csb.append("\t\t this." + code + " = " + tCode + ";\n");
            }
            csb.append("    }\n\n");
            hsb.append("\t\tnew SchemaColumn(\"" + code + "\", DataColumn." + ctype + ", " + i + ", " + scs[i].Length + " , " + scs[i].Mandatory + " , " + scs[i].isPrimaryKey + ")");
            if(i < scs.length - 1)
            {
                hsb.append(",\n");
            } else
            {
                hsb.append("\n");
            }
            if(vtype.equals("Float") || vtype.equals("Integer") || vtype.equals("Long") || vtype.equals("Double"))
            {
                svsb.append("\t\tif (i == " + i + "){if(v==null){" + code + " = null;}else{" + code + " = new " + vtype + "(v.toString());}}\n");
            } else
            {
                svsb.append("\t\tif (i == " + i + "){" + code + " = (" + vtype + ")v;}\n");
            }
            gvsb.append("\t\tif (i == " + i + "){return " + code + ";}\n");
        }

        if(dateFlag)
        {
            isb.append("import java.util.Date;\n");
        }
        if(blobFlag)
        {
            isb.append("import com.nswt.framework.orm.Blob;\n");
        }
        isb.append("\n");
        sb.append(isb);
        sb.append("/**\n");
        sb.append(" * \u8868\u540D\u79F0\uFF1A" + tableName);
        sb.append("<br>\n * \u8868\u4EE3\u7801\uFF1A" + tableCode);
        if(tableComment != null)
        {
            sb.append("<br>\n * \u8868\u5907\u6CE8\uFF1A<br>\n" + tableComment);
            splitComment(sb, tableComment, "");
        }
        sb.append("<br>\n */\n");
        sb.append("public class " + tableCode + "Schema extends Schema {\n");
        sb.append(dsb);
        hsb.append("\t};\n\n");
        hsb.append("\tpublic static final String _TableCode = \"" + tableCode + "\";\n\n");
        hsb.append("\tpublic static final String _NameSpace = \"" + namespace + "\";\n\n");
        insertsb.append(")\";\n\n");
        updatesb.append("");
        updatesb.append(pksb);
        updatesb.append("\";\n\n");
        hsb.append(insertsb);
        hsb.append(updatesb);
        hsb.append("\tprotected static final String _DeleteSQL = \"delete from " + tableCode + " " + pksb.toString() + "\";\n\n");
        hsb.append("\tprotected static final String _FillAllSQL = \"select * from " + tableCode + " " + pksb.toString() + "\";\n\n");
        hsb.append("\tpublic " + tableCode + "Schema(){\n");
        hsb.append("\t\tTableCode = _TableCode;\n");
        hsb.append("\t\tNameSpace = _NameSpace;\n");
        hsb.append("\t\tColumns = _Columns;\n");
        hsb.append("\t\tInsertAllSQL = _InsertAllSQL;\n");
        hsb.append("\t\tUpdateAllSQL = _UpdateAllSQL;\n");
        hsb.append("\t\tDeleteSQL = _DeleteSQL;\n");
        hsb.append("\t\tFillAllSQL = _FillAllSQL;\n");
        hsb.append("\t\tHasSetFlag = new boolean[" + scs.length + "];\n");
        hsb.append("\t}\n\n");
        hsb.append("\tprotected Schema newInstance(){\n");
        hsb.append("\t\treturn new " + tableCode + "Schema();\n");
        hsb.append("\t}\n\n");
        hsb.append("\tprotected SchemaSet newSet(){\n");
        hsb.append("\t\treturn new " + tableCode + "Set();\n");
        hsb.append("\t}\n\n");
        hsb.append("\tpublic " + tableCode + "Set query() {\n");
        hsb.append("\t\treturn query(null, -1, -1);\n");
        hsb.append("\t}\n\n");
        hsb.append("\tpublic " + tableCode + "Set query(String wherePart) {\n");
        hsb.append("\t\treturn query(wherePart, -1, -1);\n");
        hsb.append("\t}\n\n");
        hsb.append("\tpublic " + tableCode + "Set query(int pageSize, int pageIndex) {\n");
        hsb.append("\t\treturn query(null, pageSize, pageIndex);\n");
        hsb.append("\t}\n\n");
        hsb.append("\tpublic " + tableCode + "Set query(String wherePart , int pageSize, int pageIndex){\n");
        hsb.append("\t\tSchemaSet set = querySet(wherePart , pageSize , pageIndex);\n");
        hsb.append("\t\t" + tableCode + "Set newSet = new " + tableCode + "Set();\n");
        hsb.append("\t\tnewSet.set(set);\n");
        hsb.append("\t\treturn newSet;\n");
        hsb.append("\t}\n\n");
        svsb.append("\t}\n\n");
        gvsb.append("\t\treturn null;\n");
        gvsb.append("\t}\n\n");
        sb.append(hsb);
        sb.append(svsb);
        sb.append(gvsb);
        sb.append(csb);
        sb.append("}");
        FileUtil.writeText(outputDir + "/" + tableCode + "Schema.java", sb.toString());
        generateSet(tableCode);
    }

    private void generateSet(String tableCode)
    {
        StringBuffer sb = new StringBuffer(1000);
        sb.append("package " + namespace + ";\n\n");
        sb.append("import " + namespace + "." + tableCode + "Schema;\n");
        sb.append("import com.nswt.framework.orm.SchemaSet;\n\n");
        sb.append("public class " + tableCode + "Set extends SchemaSet {\n");
        sb.append("\tpublic " + tableCode + "Set() {\n");
        sb.append("\t\tTableCode = " + tableCode + "Schema._TableCode;\n");
        sb.append("\t\tColumns = " + tableCode + "Schema._Columns;\n");
        sb.append("\t\tNameSpace = " + tableCode + "Schema._NameSpace;\n");
        sb.append("\t\tInsertAllSQL = " + tableCode + "Schema._InsertAllSQL;\n");
        sb.append("\t\tUpdateAllSQL = " + tableCode + "Schema._UpdateAllSQL;\n");
        sb.append("\t\tFillAllSQL = " + tableCode + "Schema._FillAllSQL;\n");
        sb.append("\t\tDeleteSQL = " + tableCode + "Schema._DeleteSQL;\n");
        sb.append("\t}\n\n");
        sb.append("\tprotected SchemaSet newInstance(){\n");
        sb.append("\t\treturn new " + tableCode + "Set();\n");
        sb.append("\t}\n\n");
        sb.append("\tpublic boolean add(" + tableCode + "Schema aSchema) {\n");
        sb.append("\t\treturn super.add(aSchema);\n");
        sb.append("\t}\n\n");
        sb.append("\tpublic boolean add(" + tableCode + "Set aSet) {\n");
        sb.append("\t\treturn super.add(aSet);\n");
        sb.append("\t}\n\n");
        sb.append("\tpublic boolean remove(" + tableCode + "Schema aSchema) {\n");
        sb.append("\t\treturn super.remove(aSchema);\n");
        sb.append("\t}\n\n");
        sb.append("\tpublic " + tableCode + "Schema get(int index) {\n");
        sb.append("\t\t" + tableCode + "Schema tSchema = (" + tableCode + "Schema) super.getObject(index);\n");
        sb.append("\t\treturn tSchema;\n");
        sb.append("\t}\n\n");
        sb.append("\tpublic boolean set(int index, " + tableCode + "Schema aSchema) {\n");
        sb.append("\t\treturn super.set(index, aSchema);\n");
        sb.append("\t}\n\n");
        sb.append("\tpublic boolean set(" + tableCode + "Set aSet) {\n");
        sb.append("\t\treturn super.set(aSet);\n");
        sb.append("\t}\n");
        sb.append("}\n ");
        FileUtil.writeText(outputDir + "/" + tableCode + "Set.java", sb.toString());
    }

    private void splitComment(StringBuffer sb, String comment, String tab)
    {
        String a[] = comment.split("\n");
        for(int i = 0; i < a.length; i++)
        {
            if(!a[i].trim().equals(""))
            {
                sb.append(tab);
                sb.append(a[i].trim());
                sb.append("<br>\n");
            }
        }

    }

    private boolean checkCode(String code, String msgPrefix)
    {
        char ca[] = code.toCharArray();
        for(int i = 0; i < ca.length; i++)
        {
            if(i == 0)
            {
                if(!Character.isJavaIdentifierStart(ca[i]))
                {
                    System.out.println(msgPrefix + code + "\u4E0D\u662F\u5408\u9002\u7684Java\u6807\u5FD7\u540D");
                    return false;
                }
            } else
            if(!Character.isJavaIdentifierPart(ca[i]))
            {
                System.out.println(msgPrefix + code + "\u4E0D\u662F\u5408\u9002\u7684Java\u6807\u5FD7\u540D");
                return false;
            }
        }

        return true;
    }

    public static void main(String args[])
    {
        String prefix = Config.getContextRealPath();
        prefix = prefix.substring(0, prefix.length() - 2);
        prefix = prefix.substring(0, prefix.lastIndexOf("/") + 1);
        String pdmprefix = prefix + "DB/" + "nswtp";
        String javapath = prefix + "Java/com/nswt/schema";
        SchemaGenerator og = new SchemaGenerator();
        og.setFileName(pdmprefix + ".pdm");
        og.setOutputDir(javapath);
        og.setNamespace("com.nswt.schema");
        try
        {
            og.generate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }
}
