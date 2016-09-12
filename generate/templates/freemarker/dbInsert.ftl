package ${basePackage}.${moduleName};

import com.gizwits.jdbc.IDbBusiness;
import com.gizwits.jdbc.sql.Action;
import com.gizwits.jdbc.sql.InsertAction;
import com.xpg.pay.plugin.BusinessInit;
import com.xpg.pay.plugin.service.DbService;

/**
 * <p> 添加${table.desc} </p>
 * @author slai@xtremeprog.com
 * @since ${.now}
 */
public class Add${table.className} extends IDbBusiness<DbService> {

    @Override
    public BusinessInit getBase() {
        return BusinessInit.init("add${table.className}DB", "添加${table.desc}", "");
    }

    @Override
    public Action[] getActions() {
        return new Action[]{
            new InsertAction() {

                @Override
                public String table() {
                    return "${table.tableName}";
                }
            }
        };
    }
}