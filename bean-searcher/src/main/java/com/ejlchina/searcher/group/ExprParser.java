package com.ejlchina.searcher.group;

/**
 * Group 表达式 解析器
 * @author Troy.Zhou @ 2022-02-21
 * @since v3.5.0
 */
public interface ExprParser {

    /**
     * 解析
     * @return Group
     */
    Group<String> parse();

    /**
     * 工厂
     */
    interface Factory {

        /**
         * 创建一个解析器
         * @param expr 表达式
         * @return ExprParser
         */
        ExprParser create(String expr);

    }

}
