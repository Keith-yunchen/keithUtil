package com.zhouy.module.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明参数类型 目前只能识别 boolean，number，string，string[]三种类型
 *
 * @author:zhouy,date:20170417
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamType {
	Type ptype() default Type.STRING;

	public enum Type{BOOLEAN,NUMBER,STRING,STRING_ARRAY};
}
