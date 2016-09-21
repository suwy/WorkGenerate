package com.xpg.modules.web.${moduleName};

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.route.ControllerBind;
import com.xpg.common.BaseTempleteController;
import com.xpg.util.allpay.Allpay;
import com.xpg.util.allpay.AllpayResult;

/**
 * <p> ${table.desc} </p>
 * @author slai@xtremeprog.com
 * @since ${.now}
 */
@ControllerBind(controllerKey = "/${moduleName}")
public class ${table.className}Controller extends BaseTempleteController<${table.className}> {

    /*分页列表*/
    @Override
    public void getByPage() {
        Page<${table.className}> page = ${table.className}.me.getByPage(getParaMap(), pageNumber(), pageSize());
        setAttr("_page", page);
        render("_page.html");
    }

    /*搜索项及表头*/
    public void index() {
        setAttr("_index", controlerUrl());
        render("_index.html");
    }

    /*内页详情*/
    public void form() {
        setAttr("_form", controlerUrl());
        render("_form.html");
    }

}