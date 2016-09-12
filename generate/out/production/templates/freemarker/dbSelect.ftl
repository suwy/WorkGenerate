package ${basePackage}.${moduleName};

import com.gizwits.jdbc.IDbBusiness;
import com.gizwits.jdbc.sql.Action;
import com.gizwits.jdbc.sql.SelectAction;
import com.xpg.pay.plugin.BusinessInit;
import com.xpg.pay.plugin.service.DbService;

/**
 * <p> 查询${table.desc} </p>
 * @author slai@xtremeprog.com
 * @since ${.now}
 */
public class Get${table.className} extends IDbBusiness<DbService> {

    @Override
    public BusinessInit getBase() {
        return BusinessInit.init("get${table.className}DB", "查询${table.desc}", "");
    }

    @Override
    public Action[] getActions() {
        return new Action[] {

            new SelectAction() {

                @Override
                public Object select() {
                    return null;
                }

                @Override
                public String[] fromTable() {
                    return new String[] { "${table.tableName}"};
                }

                @Override
                protected String[] where() {
                    return new String[] {
                <#list table.baseColumns as column>
                    <#if (column_has_next)>
                        "${column.columnName} = {${column.javaProperty}}",
                    <#else>
                        "${column.columnName} = {${column.javaProperty}}"
                    </#if>
                </#list>
                    };
                }
            }

        };
    }
}