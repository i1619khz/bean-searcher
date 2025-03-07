package com.ejlchina.searcher.operator;

import com.ejlchina.searcher.FieldOp;
import com.ejlchina.searcher.SqlWrapper;
import com.ejlchina.searcher.dialect.MySqlDialect;
import com.ejlchina.searcher.dialect.PostgreSqlDialect;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EndWithTestCase {

    final EndWith endWith = new EndWith();

    @Test
    public void test_01() {
        Assert.assertEquals("EndWith", endWith.name());
        Assert.assertTrue(endWith.isNamed("ew"));
        Assert.assertTrue(endWith.isNamed("EndWith"));
        Assert.assertFalse(endWith.lonely());
        System.out.println("EndWithTestCase test_01 passed");
    }

    @Test
    public void test_02() {
        endWith.setDialect(new MySqlDialect());
        StringBuilder sb = new StringBuilder();
        List<Object> params = endWith.operate(sb, new FieldOp.OpPara(
                new SqlWrapper<>("name"), false, new Object[]{ "abc" }
        ));
        Assert.assertEquals("name like ?", sb.toString());
        Assert.assertArrayEquals(new Object[] { "%abc" }, params.toArray());
        System.out.println("EndWithTestCase test_02 passed");
    }

    @Test
    public void test_03() {
        endWith.setDialect(new MySqlDialect());
        StringBuilder sb = new StringBuilder();
        List<Object> params = endWith.operate(sb, new FieldOp.OpPara(
                new SqlWrapper<>("name"), true, new Object[]{ "abc" }
        ));
        Assert.assertEquals("upper(name) like ?", sb.toString());
        Assert.assertArrayEquals(new Object[] { "%ABC" }, params.toArray());
        System.out.println("EndWithTestCase test_03 passed");
    }

    @Test
    public void test_04() {
        endWith.setDialect(new MySqlDialect());
        StringBuilder sb = new StringBuilder();
        SqlWrapper<Object> fieldSql = new SqlWrapper<>("(select name from user where id = ?)");
        fieldSql.addPara(12);
        List<Object> params = endWith.operate(sb, new FieldOp.OpPara(
                fieldSql, false, new Object[]{ "abc" }
        ));
        Assert.assertEquals("(select name from user where id = ?) like ?", sb.toString());
        Assert.assertArrayEquals(new Object[] { 12, "%abc" }, params.toArray());
        System.out.println("EndWithTestCase test_04 passed");
    }

    @Test
    public void test_05() {
        endWith.setDialect(new MySqlDialect());
        StringBuilder sb = new StringBuilder();
        SqlWrapper<Object> fieldSql = new SqlWrapper<>("(select name from user where id = ?)");
        fieldSql.addPara(12);
        List<Object> params = endWith.operate(sb, new FieldOp.OpPara(
                fieldSql, true, new Object[]{ "abc" }
        ));
        Assert.assertEquals("upper((select name from user where id = ?)) like ?", sb.toString());
        Assert.assertArrayEquals(new Object[] { 12, "%ABC" }, params.toArray());
        System.out.println("EndWithTestCase test_05 passed");
    }

    @Test
    public void test_06() {
        endWith.setDialect(new PostgreSqlDialect());
        StringBuilder sb = new StringBuilder();
        List<Object> params = endWith.operate(sb, new FieldOp.OpPara(
                new SqlWrapper<>("name"), false, new Object[]{ "abc" }
        ));
        Assert.assertEquals("name like ?", sb.toString());
        Assert.assertArrayEquals(new Object[] { "%abc" }, params.toArray());
        System.out.println("EndWithTestCase test_06 passed");
    }

    @Test
    public void test_07() {
        endWith.setDialect(new PostgreSqlDialect());
        StringBuilder sb = new StringBuilder();
        List<Object> params = endWith.operate(sb, new FieldOp.OpPara(
                new SqlWrapper<>("name"), true, new Object[]{ "abc" }
        ));
        Assert.assertEquals("name ilike ?", sb.toString());
        Assert.assertArrayEquals(new Object[] { "%abc" }, params.toArray());
        System.out.println("EndWithTestCase test_07 passed");
    }

    @Test
    public void test_08() {
        endWith.setDialect(new PostgreSqlDialect());
        StringBuilder sb = new StringBuilder();
        SqlWrapper<Object> fieldSql = new SqlWrapper<>("(select name from user where id = ?)");
        fieldSql.addPara(12);
        List<Object> params = endWith.operate(sb, new FieldOp.OpPara(
                fieldSql, false, new Object[] { "abc" }
        ));
        Assert.assertEquals("(select name from user where id = ?) like ?", sb.toString());
        Assert.assertArrayEquals(new Object[]{ 12, "%abc" }, params.toArray());
        System.out.println("EndWithTestCase test_08 passed");
    }

    @Test
    public void test_09() {
        endWith.setDialect(new PostgreSqlDialect());
        StringBuilder sb = new StringBuilder();
        SqlWrapper<Object> fieldSql = new SqlWrapper<>("(select name from user where id = ?)");
        fieldSql.addPara(12);
        List<Object> params = endWith.operate(sb, new FieldOp.OpPara(
                fieldSql, true, new Object[] { "abc" }
        ));
        Assert.assertEquals("(select name from user where id = ?) ilike ?", sb.toString());
        Assert.assertArrayEquals(new Object[]{ 12, "%abc" }, params.toArray());
        System.out.println("EndWithTestCase test_09 passed");
    }

}
