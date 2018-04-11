package com.projects.ricefactory.service;

import com.projects.ricefactory.dto.RiceType;

import java.util.List;

/**
 * Created by hearlapati on 2/28/17.
 */
public interface RiceTypeDao {

    public RiceType createRiceType(RiceType riceType) throws Exception;

    public RiceType updateRiceType(RiceType riceType) throws Exception;

    public RiceType getRiceTypeByInternalName(String internalName);

    public RiceType getRiceTypeById(Long riceTypeId);

    public List<RiceType> getAllRiceTypes();

    public RiceType getRiceTypeByDisplayName(String displayName);

}
