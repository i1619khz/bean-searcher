package com.ejlchina.searcher;

import com.ejlchina.searcher.dialect.Dialect;
import com.ejlchina.searcher.dialect.DialectSensor;
import com.ejlchina.searcher.implement.DialectWrapper;
import com.ejlchina.searcher.operator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字段运算符池（支持的运算符都在这里）
 * @author Troy.Zhou @ 2022-01-19
 * @since v3.3.0
 */
public class FieldOpPool extends DialectWrapper {

    private List<FieldOp> fieldOps;

    private final Map<Object, FieldOp> cache = new ConcurrentHashMap<>();


    public FieldOpPool(List<FieldOp> fieldOps) {
        this.fieldOps = Objects.requireNonNull(fieldOps);
    }

    public FieldOpPool() {
        fieldOps = new ArrayList<>();
        fieldOps.add(new Equal());
        fieldOps.add(new NotEqual());
        fieldOps.add(new GreaterThan());
        fieldOps.add(new GreaterEqual());
        fieldOps.add(new LessThan());
        fieldOps.add(new LessEqual());
        fieldOps.add(new Between());
        fieldOps.add(new NotBetween());
        fieldOps.add(new Contain());
        fieldOps.add(new StartWith());
        fieldOps.add(new EndWith());
        fieldOps.add(new OrLike());
        fieldOps.add(new InList());
        fieldOps.add(new NotIn());
        fieldOps.add(new IsNull());
        fieldOps.add(new NotNull());
        fieldOps.add(new Empty());
        fieldOps.add(new NotEmpty());
    }


    public FieldOp getFieldOp(Object key) {
        if (key == null) {
            return null;
        }
        FieldOp fOp = cache.get(key);
        if (fOp == null && key instanceof FieldOp) {
            fOp = cache.get(((FieldOp) key).name());
        }
        if (fOp != null) {
            return fOp;
        }
        for (FieldOp op: fieldOps) {
            if (isMatch(op, key)) {
                if (key instanceof FieldOp) {
                    // 防止用户对同一个运算符 new 了很多次导致 cache 膨胀
                    cache.put(((FieldOp) key).name(), op);
                } else {
                    cache.put(key, op);
                }
                return op;
            }
        }
        return null;
    }

    private boolean isMatch(FieldOp op, Object key) {
        if (key instanceof FieldOp) {
            return op.sameTo((FieldOp) key);
        }
        if (key instanceof String) {
            return op.isNamed((String) key);
        }
        if (key instanceof Class) {
            return op.getClass() == key;
        }
        return false;
    }

    public List<FieldOp> getFieldOps() {
        return fieldOps;
    }

    public synchronized void setFieldOps(List<FieldOp> fieldOps) {
        this.fieldOps = Objects.requireNonNull(fieldOps);
        updateAllOpDialect();
    }

    public synchronized void addFieldOp(FieldOp fieldOp) {
        if (fieldOp != null) {
            this.fieldOps.add(fieldOp);
            updateOpDialect(fieldOp);
        }
    }

    @Override
    public synchronized void setDialect(Dialect dialect) {
        if (dialect != null) {
            super.setDialect(dialect);
            updateAllOpDialect();
        }
    }

    private void updateAllOpDialect() {
        for (FieldOp op : fieldOps) {
            updateOpDialect(op);
        }
    }

    private void updateOpDialect(FieldOp op) {
        if (op instanceof DialectSensor) {
            Dialect dialect = getDialect();
            if (dialect != null) {
                ((DialectSensor) op).setDialect(dialect);
            }
        }
    }

}
