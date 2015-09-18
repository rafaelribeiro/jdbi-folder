package com.github.rkmk;

import java.util.HashMap;
import java.util.Map;

import com.github.rkmk.mapper.FieldHelper;
import com.google.common.base.Predicate;

public class CustomPredicate<T> implements Predicate<T> {

    private final Map<String, Object> whereClause;

    public CustomPredicate(Map<String, Object> whereClause) {

        this.whereClause = whereClause;
    }

	@Override
	public boolean apply(T t) {
		Map<String, Object> objectMap = new HashMap<>();
        for (String field : whereClause.keySet()) {
            objectMap.put(field, FieldHelper.accessField(field, t));
        }
        return objectMap.size() == whereClause.size() && objectMap.equals(whereClause);
	}
}
