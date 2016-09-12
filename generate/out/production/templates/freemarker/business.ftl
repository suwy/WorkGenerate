package ${basePackage}.${moduleName};

import com.xpg.pay.plugin.BusinessInit;
import com.xpg.pay.plugin.IBusiness;
import com.xpg.pay.plugin.InputObject;
import com.xpg.pay.plugin.IService;
import com.xpg.pay.plugin.OutputObject;
import com.xpg.pay.plugin.message.BusinessMessage;
import com.xpg.pay.plugin.message.MessageCenter;
import com.xpg.pay.plugin.service.${serviceName};

import java.util.ArrayList;
import java.util.List;
<#if (table.hasDateColumn)>
import java.util.Date;
</#if>

/**
 * <p> ${table.desc} </p>
 * @author slai@xtremeprog.com
 * @since ${.now}
 */
public class ${table.className} implements IBusiness<${serviceName}> {

    @Override
    public BusinessInit getBase() {
        return BusinessInit.init("${table.className}","${table.desc}",IService.CODE_SUCCESS);
    }

    @Override
    public InputObject getInputObjs() {
        return new InputObject() {{
        <#list table.baseColumns as column>
            add("${column.javaProperty}", ${column.javaType}.class,"${column.remarks}",false);
        </#list>
        }};
    }

    @Override
    public List<String> getErrorCodes() {
        return new ArrayList<String>() {{
            add(IService.CODE_LACK_PARAMS);
         }};
    }

    @Override
    public OutputObject execute(BusinessMessage msg) {
        return MessageCenter.send("dbService", "${dbName}DB", msg.getArgMap());
    }
}