package com.github.rkmk.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class FieldMapperAbstractFactory <T> implements FieldMapperFactory<T>{
	
	public T getNullOrValue(Class<?> type, ResultSet rs, T value) throws SQLException {
		return rs.wasNull() && !type.isPrimitive() ? null : value ;
	}
}
