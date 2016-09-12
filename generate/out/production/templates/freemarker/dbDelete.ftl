package ${basePackage}.${moduleName};

import com.gizwits.jdbc.IDbBusiness;
import com.gizwits.jdbc.sql.Action;
import com.gizwits.jdbc.sql.DeleteAction;
import com.xpg.pay.plugin.BusinessInit;
import com.xpg.pay.plugin.service.DbService;

/**
 * <p> 删除${table.desc} </p>
 * @author slai@xtremeprog.com
 * @since ${.now}
 */
public class Delete${table.className} extends IDbBusiness<DbService> {

    @Override
    public BusinessInit getBase() {
        return BusinessInit.init("delete${table.className}DB", "删除${table.desc}", "");
    }

    @Override
    public Action[] getActions() {
        return new Action[] {
            new DeleteAction() {

                @Override
                public String table() {
                    return "${table.tableName}";
                }

                @Override
                protected String[] where() {
                    return new String[] {
                    <#list table.primaryKeys as key>
                        "${key.columnName} = {${key.javaProperty}}"
                     </#list>
                     };
                }
            }
        };
    }
}