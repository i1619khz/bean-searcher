package com.ejlchina.searcher.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于注解一个 SearchBean
 * v3.0.0 后该注解可以缺省，缺省时根据 {@link com.ejlchina.searcher.DbMapping } 自动映射数据库表
 * @author Troy.Zhou @ 2017-03-20
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SearchBean {

	/**
	 * 指定数据源
	 * @since v3.0.0
	 * @return 数据源
	 */
	String dataSource() default "";

	/**
	 * 参与检索的数据库表名，例如:
	 * users u, user_role ur, roles r
	 * v3.0.0 后可空，为空时以类名映射表名
	 * @return tables
	 */
	String tables() default "";
	
	/**
	 * 参与检索的数据表的连接条件，例如：
	 * u.id = ur.user_id and ur.role_id = r.id 
	 * @return join condition
	 * */
	String joinCond() default "";	
	
	/**
	 * 分组字段，例如
	 * u.id,r.name
	 * @return group information
	 */
	String groupBy() default "";
	
	/**
	 * 是否 distinct 结果
	 * @return distinct
	 * */
	boolean distinct() default false;

	/**
	 * 字段未加 @DbField 时，指定它自动映射到那张表（只有在 tables 属性不空时起作用，当 tables 为空时，则自动映射到默认的那张表）
	 * 如果 autoMapping 为空，则表示未被 @DbField 注解的字段不需要映射
	 * @since v3.0.0
	 * @return 自动映射的表名 或 别名
	 */
	String autoMapTo() default "";

}

