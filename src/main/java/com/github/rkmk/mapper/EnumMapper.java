package com.github.rkmk.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class EnumMapper implements ResultSetMapper<Enum> {

    private Class<? extends Enum> type;

    public EnumMapper(Class<? extends Enum> type) {
        this.type = type;
    }

    @Override
    public Enum map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        String value = r.getString(1);
        return (value != null) ? Enum.valueOf(type, value) : null;
    }
}
