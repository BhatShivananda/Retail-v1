package com.app.retail.dao;

import org.springframework.core.GenericTypeResolver;

import com.datastax.driver.mapping.MappingManager;

/**
 * @param <ET> entity datatype for crud dao operations
 * @param <AT> accessor interface type for complex queries
 */
public class BaseAccessorDao<ET, AT> extends BaseCrudDao<ET> {

  protected final AT accessor;

  @SuppressWarnings("unchecked")
  public BaseAccessorDao(MappingManager mappingManager) {
    super(mappingManager);
    Class<?>[] typeArgs = GenericTypeResolver.resolveTypeArguments(getClass(), BaseAccessorDao.class);
    Class<AT> accessorClass = (Class<AT>) typeArgs[1];
    this.accessor = mappingManager.createAccessor(accessorClass);
  }

}
