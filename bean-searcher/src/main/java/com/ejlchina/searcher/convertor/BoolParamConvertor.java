package com.ejlchina.searcher.convertor;

import com.ejlchina.searcher.ParamResolver;
import com.ejlchina.searcher.bean.DbType;
import com.ejlchina.searcher.util.StringUtils;

/**
 * [String | Number to Boolean] 参数值转换器
 *
 * @author Troy.Zhou @ 2022-06-14
 * @since v3.8.0
 */
public class BoolParamConvertor implements ParamResolver.Convertor {

    private String[] falseValues = new String[] { "0", "OFF", "FALSE", "N", "NO", "F" };

    @Override
    public boolean supports(DbType dbType, Class<?> valueType) {
        return dbType == DbType.BOOL && (
                String.class == valueType || Number.class.isAssignableFrom(valueType) || Boolean.class == valueType
        );
    }

    @Override
    public Object convert(DbType dbType, Object value) {
        if (value instanceof Boolean) {
            return value;
        }
        if (value instanceof String) {
            String s = (String) value;
            if (StringUtils.isBlank(s)) {
                return null;
            }
            for (String f : falseValues) {
                if (s.equals(f)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        }
        return null;
    }

    public String[] getFalseValues() {
        return falseValues;
    }

    public void setFalseValues(String[] falseValues) {
        this.falseValues = falseValues;
    }

}
