package com.github.rkmk.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FieldMapperFactory<T> {
    public T getValue(ResultSet rs, int index, Class<?> type) throws SQLException;
    public Boolean accepts(Class<?> type);

    public T getNullOrValue(Class<?> type, ResultSet rs, T value) throws SQLException;

}
