package com.zhouy.module.aop;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;

/**
 * 启用spring的aop处理参数类型校验 持续完善
 *
 * @author:zhouy,date:20170414
 * @Version 1.0
 */
//@Component
//@Aspect
public class RequestParamAop {
	private static Logger logger = Logger.getLogger(RequestParamAop.class);

	@Pointcut("execution(* xxx.*(..))")
	public void pointcut(){}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Exception,Throwable {
		Object result = null;
		try {
			Object[] args = joinPoint.getArgs();
			if(args != null && args.length>0){
				boolean invalidate = true;
				//注入点获取所有请求参数
				for(int i=0;i<args.length;i++){
					//内部循环会重新赋值，若出现参数异常，跳出循环
					if(!invalidate){
						break;
					}
					Object obj = args[i];
					//httpServletRequest对象
					if(obj instanceof ServletRequest){
						continue;
					//根据参数类型做出不同处理
					}else{
						//字符串
						if(obj instanceof String){
							args[i] = dealParam((String)obj);
							//字符串数组
						}else if(obj instanceof String[]){
							String[] array = (String[]) obj;
							for(int j = 0;j<array.length;j++){
								String temp = array[j];
								array[j] = dealParam(temp);
							}
							args[i] = array;
						}else{//自定义请求参数封装对象
							if(obj == null){
								continue;
							}
							Field[] fields = obj.getClass().getDeclaredFields();
							//自定义对象反射获取自定义注解 ，携带注解参数需要处理 例如：数字型参数不能参入字符串
							for(Field field : fields){
								field.setAccessible(true);
								ParamType ptype = field.getAnnotation(ParamType.class);
								//自定义请求封装对象 不给定注解的情况，默认不做处理
								if(null == ptype){
									continue;
								}
								if(ParamType.Type.BOOLEAN==ptype.ptype()){
									continue;
								}else if(ParamType.Type.NUMBER==ptype.ptype()){
									invalidate = validateInteger(String.valueOf(field.get(obj)));
									if(!invalidate){
										break;
									}
								}else if(ParamType.Type.STRING==ptype.ptype()){
									continue;
								}else if(ParamType.Type.STRING_ARRAY==ptype.ptype()){
									continue;
								}else{
									continue;
								}
							}
						}
					}
				}
				if(!invalidate){
					throw new Exception("参数类型错误");
				}
				result = joinPoint.proceed(args);
			}else {
				result = joinPoint.proceed();
			}
			return result;
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e);
			throw e;
		}
	}


	/**
	 * 校验sql注入
	 * @param value
	 * @return
	 */
	private boolean validateSql(String value){

		return true;
	}

	//校验html 跨站请求
	private boolean validateHtml(String value){

		return true;
	}

	//校验js 跨站脚本攻击
	private boolean validateScript(String value){

		return true;
	}

	//参数类型验证
	private boolean validateInteger(String value){
		boolean isFieldValid = true;
		if(null != value && value.trim().length()>0){
			try {
				Integer.parseInt(value);
			} catch (Exception e) {
				isFieldValid = false;
			}
		}
		return isFieldValid;
	}

	/**
	 * 处理参数
	 * @param value
	 * @return
	 */
	private String dealParam(String value){
		String temp = StringEscapeUtils.escapeHtml4( value);
		temp = StringEscapeUtils.escapeJava(temp);
		temp = StringEscapeUtils.escapeJson(temp);
		return temp;
	}

	private boolean validate(String value, boolean isNumber){
		boolean flag = true;
		flag = validateSql(value);
		if(!flag){
			return flag;
		}
		flag = validateHtml(value);
		if(!flag){
			return flag;
		}
		flag = validateScript(value);
		if(!flag){
			return flag;
		}
		if(isNumber){
			flag = validateInteger(value);
		}
		return flag;
	}
}
