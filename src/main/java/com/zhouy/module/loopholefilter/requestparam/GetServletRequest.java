package com.zhouy.module.loopholefilter.requestparam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

/**
 * 修改get方法请求参数的转换器
 *
 * @author:zhouy,date:20170406
 * @Version 1.0
 */
public class GetServletRequest extends HttpServletRequestWrapper {

	private Map<String, String[]> params;

	/**
	 * Constructs a request object wrapping the given request.
	 *
	 * @param request
	 * @throws IllegalArgumentException if the request is null
	 */
	public GetServletRequest(HttpServletRequest request) {
		super(request);
	}

	public GetServletRequest(HttpServletRequest request, Map<String, String[]> newParams) {
		super(request);
		this.params = newParams;
	}

	@Override
	public String getParameter(String name) {
		String result = "";
		Object v = params.get(name);
		if (v == null) {
			result = null;
		} else if (v instanceof String[]) {
			String[] strArr = (String[]) v;
			if (strArr.length > 0) {
				result =  strArr[0];
			} else {
				result = null;
			}
		} else if (v instanceof String) {
			result = (String) v;
		} else {
			result =  v.toString();
		}

		return result;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return params;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return new Vector<String>(params.keySet()).elements();
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] result = null;

		Object v = params.get(name);
		if (v == null) {
			result =  null;
		} else if (v instanceof String[]) {
			result =  (String[]) v;
		} else if (v instanceof String) {
			result =  new String[] { (String) v };
		} else {
			result =  new String[] { v.toString() };
		}

		return result;
	}

}
