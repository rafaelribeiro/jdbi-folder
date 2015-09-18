package com.github.rkmk.helper;

import static com.github.rkmk.mapper.FieldHelper.set;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.github.rkmk.mapper.FieldHelper;

public class FieldWrapper {

    private Class<?> classType;
    private Field field;
    private String nameSpace;
    public static String rootClassNameSpace = "";

    public FieldWrapper(Class<?> classType, Field field, String nameSpace) {
        this.classType = classType;
        this.field = field;
        this.nameSpace = nameSpace;
    }

    public Field getField() {
        return field;
    }

    public Class<?> getFieldType() {
        return field.getType();
    }

    public void setValue(Object value, HashMap<String, Object> instanceMap) {
        Object object = instanceMap.get(nameSpace);
        if(object == null) {
            object = FieldHelper.getInstance(classType);
            instanceMap.put(nameSpace, object);
        }
        set(field, object, value);
    }
}
