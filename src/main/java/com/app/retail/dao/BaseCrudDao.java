package com.app.retail.dao;

import static com.datastax.driver.mapping.Mapper.Option.saveNullFields;

import org.springframework.core.GenericTypeResolver;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

/**
 * BaseDao class for Daos that only need basic CRUD operations
 * 
 * @param <ET> entity datatype for crud dao operations
 */
public class BaseCrudDao<ET> {

  protected final Mapper<ET> crud;

  @SuppressWarnings("unchecked")
  public BaseCrudDao(MappingManager mappingManager) {
    this.crud =
        mappingManager.mapper((Class<ET>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseCrudDao.class));
    this.crud.setDefaultSaveOptions(saveNullFields(Boolean.FALSE));
  }

}
