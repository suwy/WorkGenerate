package com.suwy.coder.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * @author laisy
 * @date 2019/2/20
 * @description
 */
public class MapperDo {
        private String dbName;
        private String username;
        private String password;
        private String baseDir;
        private String packagePath;
        private boolean isLombok;
        MapperUtil mapperUtil;

        public MapperDo(String dbName, String username, String password) throws SQLException {
            this.dbName = dbName;
            this.username = username;
            this.password = password;
            mapperUtil = new MapperUtil(dbName, username, password);
        }

        /**
         * 创建单个文本文件
         * @param baseDir
         * @param packagePath
         * @param isLombok
         * @param tableName
         * @throws Exception
         */
        public void generateFileModel(String baseDir, String packagePath, boolean isLombok, final String tableName) throws Exception {
            List<String> list = mapperUtil.getTableNames();
            boolean isExist = list.stream().noneMatch((s) -> s.equals(tableName));
            if (isExist){
                return;
            }
            String fileName = toCamelCase(tableName);
            Map<String, String> map = mapperUtil.getFiledAndType(tableName);
            String mainContent = mapperUtil.generateString(map);
            mapperUtil.createFile(tableName, fileName, baseDir, packagePath, mainContent, isLombok);
        }

        /**
         * 创建这个数据库中所有的模板文件、
         * @param baseDir
         * @param packagePath
         * @param isLombok
         */
        public void generateFileModels( String baseDir, String packagePath, boolean isLombok) throws Exception {
            List<String> list = mapperUtil.getTableNames();
            for (String tableName:list) {
                String fileName = toCamelCase(tableName);
                Map<String, String> map = mapperUtil.getFiledAndType(tableName);
                String mainContent = mapperUtil.generateString(map);
                mapperUtil.createFile(tableName, fileName, baseDir, packagePath, mainContent, isLombok);
            }
        }

        public String toCamelCase(String s){
            if (s == null){
                return null;
            }
            /**
             * 1. 把全部转成小写
             * 2. 把下划线全部去掉
             * 3. 把下划线之后的一个字母变成大写
             * 4. 首个字母变成大写
             */
            StringBuilder sb = new StringBuilder(s.toLowerCase());
            for (int i = 1; i < sb.length(); i++) {
                if (i+1 < sb.length() && sb.substring(i,i+1).equals("_")){
                    sb.replace(i, i+2, sb.substring(i+1,i+2).toUpperCase());
                }
            }
            sb.replace(0,1, s.substring(0,1).toUpperCase());
            return sb.toString();
        }


        /**
         *------------------此处省略getter/setter方法
         **/

}