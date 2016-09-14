package com.xpg.modules.web.${moduleName};

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.ext.sql.Cnd;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.Map;

/**
 * <p> ${table.desc} </p>
 * @author slai@xtremeprog.com
 * @since ${.now}
 */
@SuppressWarnings("serial")
<#list table.primaryKeys as key>
@TableBind(tableName="${table.tableName}", alias="", pkName = "${key.columnName}")
public class ${table.className} extends Model<${table.className}> {

    public static final ${table.className} me = new ${table.className}();

    /*分页列表*/
    public Page<${table.className}> getByPage(Map<String, String[]> params, int pageNumber, int pageSize) {
        Cnd cnd = Cnd.queryToCnd(params, ${table.className}.class, "").build();
        return ${table.className}.me.paginate(pageNumber, pageSize, "select * ", "from ${table.tableName}" + cnd.getSql(), cnd.getParas());
    }

    /*根据id查询*/
    public ${table.className} getById(${key.javaType} ${key.javaProperty}) {
        String sql = "SELECT * FROM ${table.tableName} WHERE ${key.columnName}=?";
        return findFirst(sql, ${key.javaProperty});
    }
</#list>

}