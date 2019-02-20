package com.suwy.coder.destination;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author laisy
 * @date 2019-02-20
 * @description
 */
@Entity
@Table(name = "AREA_DEPT")
public class AreaDept{
    private String PARENTID;
    private int ID;
    private String NAME;
}