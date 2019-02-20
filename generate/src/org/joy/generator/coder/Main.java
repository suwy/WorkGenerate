package com.suwy.coder;

import com.suwy.coder.mapper.MapperDo;

/**
 * @author laisy
 * @date 2019/2/20
 * @description
 */
public class Main {

        public static void main(String[] args) throws Exception {
            MapperDo mapperDo = new MapperDo("jdbc:mysql://10.168.1.123:3306/cccd_pro","uapp","Fsxxb123=");
//测试生成数据库中所有表的javaBean
//            mapperDo.generateFileModels("/home/liudong/code/evaluate_bone/src/test/java/cn/edu/cqupt/mis/evaluate_bone/test", "liudong", false);
//测试生成advices一个表的javaBean
            mapperDo.generateFileModel("D:\\git\\mine\\coder\\src\\main\\java\\com\\suwy\\coder\\destination", "com.suwy.coder.destination", false,"T_SFTS_YW_BUSIINDEX");
        }
}