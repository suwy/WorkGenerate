package com.suwy.coder.mapper;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @author laisy
 * @date 2019/2/20
 * @description
 */
public class MapperUtil {
        private DatabaseMetaData databaseMetaData;
        Connection connection;

        public MapperUtil(String dbName, String username, String password) throws SQLException {
            connection = ConnectionUtil.getConn(dbName, username, password);
            databaseMetaData = connection.getMetaData();
        }

        /**
         * 只是文件名（不包含扩展名）
         * @param fileName 文件名称
         * @param dir 文件所处的目录
         * @param mainContent 文件的主要内容
         * @param isLombok 是否包含lombok常见的一些方法？包含：不包含
         */
        public void createFile(String tableName, String fileName, String dir, String packagePath, String mainContent, boolean isLombok) throws Exception {
            if (fileName == null || fileName.trim().equals("") || dir == null || dir.trim().equals("")){
                throw new Exception("传入参数不正确");
            }
            /**
             * 1.首先检测是否有这个文件夹路径
             * 2.如果不存在则新建
             */
            File fileDir = new File(dir);
            if (!fileDir.exists()){
                fileDir.setWritable(true);
                fileDir.mkdirs();
            }

            /**
             * 如果没有出错，那么创建文件，这里统一创建java文件
             */
            File javaFile = new File(fileDir, fileName+".java");
            javaFile.createNewFile();

            /**
             * 向文件中输送内容
             * 使用
             */
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(javaFile)));
            dataOutputStream.writeBytes("package " + packagePath + ";\n");
            dataOutputStream.writeBytes("\n");
            if (isLombok){
                dataOutputStream.writeBytes(
                        "import lombok.*;\n\n"+
                                "@Data\n" +
                                "@ToString\n" +
                                "@Builder\n" +
                                "@AllArgsConstructor\n" +
                                "@NoArgsConstructor\n");
            } else {
                dataOutputStream.writeBytes(
                        "import javax.persistence.Column;\n" +
                                "import javax.persistence.Entity;\n" +
                                "import javax.persistence.Table;\n\n" +
                                "/**\n" +
                                " * @author laisy\n" +
                                " * @date " + LocalDate.now() +"\n" +
                                " * @description\n" +
                                " */\n@Entity\n@Table(name = \""+tableName+"\")\n");
            }
            dataOutputStream.writeBytes("public class " + fileName + "{\n");
            dataOutputStream.writeBytes(mainContent);
            dataOutputStream.writeBytes("}");
            dataOutputStream.close();
        }

        /**
         * 构造对应的字符串。
         * @param map
         * @return
         */
        public String generateString(Map<String, String> map){
            String result = "";
            Set set = map.entrySet();
            /**
             * 对map进行遍历
             */
            for(Iterator iter = set.iterator(); iter.hasNext();)
            {
                Map.Entry entry = (Map.Entry)iter.next();
                String key = (String)entry.getKey();

                //把字段的下划线去掉，下划线后一位变为大写
                StringBuilder sb = new StringBuilder(key);
                for (int i = 1; i < key.length(); i++) {
                    if (i+1 < key.length() && key.substring(i,i+1).equals("_")){
                        sb.replace(i, i+2, key.substring(i+1,i+2).toUpperCase());
                    }
                }

                String value = (String)entry.getValue();
                System.out.println("key: " + key + " sb: " + sb +" :" + value);
                result += "    private " + value + " " + sb + ";\n";
            }
            return result;
        }



        /**
         * 返回字段名称和属性
         * @param tableName
         * @return
         */
        public Map<String, String> getFiledAndType(String tableName) throws SQLException {
            if (tableName == null || tableName.trim().equals("")){return null;}

            ResultSet resultSet = connection.prepareStatement("select * from " + tableName).executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //得到所有的列名信息
            int count = resultSetMetaData.getColumnCount();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 1; i <= count; i++) {
                String type = resultSetMetaData.getColumnTypeName(i);
                type = typeTransform(type);
                map.put(resultSetMetaData.getColumnName(i), type);
            }
            return map;
        }

        /**
         * 得到所有表的名称
         * @return 含有所有表名的列表
         */
        public List<String> getTableNames() throws SQLException {
            List<String> tables = new ArrayList<String>();
            ResultSet resultSet = databaseMetaData.getTables(null, null, "%", null);
            while (resultSet.next()){
                String tableName = resultSet.getString("TABLE_NAME");
                tables.add(tableName);
                System.out.println(tableName);
            }
            return tables;
        }

        /**
         * 类型转换: 目前针对常见的几个类型
         * @return
         */
        public String typeTransform(String sqlType){
            if (sqlType == null || sqlType.trim().equals("")){
                return null;
            }
            sqlType = sqlType.toLowerCase();
            if (matchFloatNumber(sqlType)){
                return "double";
            }else if (matchInt(sqlType)){
                return "int";
            }else if (matchString(sqlType)){
                return "String";
            }
            return "String";
        }

        public boolean match(String target, String[] datas) {
            if (target == null || "".equals(target.trim()) || datas.length == 0){
                return false;
            }
            for (String a:datas) {
                if (a.equals(target)){
                    return true;
                }
            }
            return false;
        }

        public boolean matchString(String target){
            String[] datas = {"char", "varchar", "date", "text", "timestamp", "datetime", "tinytext", "longtext"};
            return match(target, datas);
        }

        public boolean matchFloatNumber(String target){
            String[] datas = {"float", "double"};
            return match(target, datas);
        }

        public boolean matchInt(String target){
            String[] datas = {"int", "tinyint"};
            return match(target, datas);
        }

}