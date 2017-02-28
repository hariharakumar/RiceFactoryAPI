package com.projects.ricefactory.service;

import com.projects.ricefactory.dto.RiceType;

/**
 * Created by hearlapati on 2/28/17.
 */
public interface RiceTypeDao {

    public RiceType createRiceType(RiceType riceType) throws Exception;

    public RiceType updateRiceType(RiceType riceType) throws Exception;

    public RiceType getRiceTypeByInternalName(String internalName);

    public RiceType getRiceTypeById(String riceTypeId);

}
