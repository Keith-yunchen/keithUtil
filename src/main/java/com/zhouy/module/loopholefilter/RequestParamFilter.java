package com.zhouy.module.loopholefilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.zhouy.module.loopholefilter.requestparam.GetServletRequest;
import com.zhouy.module.loopholefilter.requestparam.PostServletRequest;
import com.zhouy.module.poiexcel.util.StringUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 请求参数处理拦截器
 *
 * @Copyright 2017 ShenZhen DSE Corporation
 * @Company:深圳东深电子股份有限公司
 * @author:zhouy,date:20170406
 * @Version 1.0
 */
public class RequestParamFilter implements Filter {

    private String validateflag = null;//判断是否需要做跨站点请求处理标记
    private List<String> referenList = Lists.newCopyOnWriteArrayList();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        validateflag = filterConfig.getInitParameter("validateflag");
        String validateReferenIp = filterConfig.getInitParameter("validateReferen");
        if (validateflag == null) {
            validateflag = "true";
        }
        //初始化referen参数
        if (StringUtil.isNotEmpty(validateReferenIp)) {
            referenList = Arrays.asList(validateReferenIp.split(","));
        } else {
            referenList = new CopyOnWriteArrayList<String>();
        }
        return;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if ("true".equals(validateflag)) {
            //处理跨站点请求伪造 10.44.31.* 10.100.9.*
            String referer = ((HttpServletRequest) request).getHeader("Referer");
            boolean flag = false;
            for (String property : referenList) {
                if(referer != null && referer.contains(property)){
                    flag = true;
                    break;
                }
            }
            if (!flag && referer!=null) {//需要拦截处理
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.flushBuffer();
                return;
            }
        }
        String method = ((HttpServletRequest) request).getMethod();
        Map<String, String[]> paramMap = null;
        String paramStr = null;
        boolean isJsonStr = true;
        if (method.equals("POST")) {//处理post请求的json参数
            String enctype = request.getContentType();
            if (StringUtil.isNotEmpty(enctype) && enctype.contains("application/json")) {
                String body = null;
                try {
                    body = getBody((HttpServletRequest) request);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServletException();
                }
                if (!body.startsWith("{") || !body.endsWith("}")) {
                    isJsonStr = false;
                }
                if (body != null && body.length() > 0 && isJsonStr) {
                    paramMap = JSON.parseObject(body, Map.class, Feature.IgnoreNotMatch);
                } else {
                    paramStr = body;
                }
            } else {
                paramMap = ((HttpServletRequest) request).getParameterMap();
            }
        } else {
            paramMap = ((HttpServletRequest) request).getParameterMap();
        }
        //过滤特殊字符、处理sql、html特殊字符 解决sql注入以及跨站脚本攻击漏洞
        //TODO 版本迁移导致方法要重写
//        Map<String, Object> afterParam = dealParam(paramMap);
        Map temp = null;
        Map<String, Object> afterParam = dealParam(temp);
        if (afterParam != null || !isJsonStr) {
            if (method.equals("POST")) {
                String afterBody = null;
                if (isJsonStr) {
                    afterBody = JSON.toJSONString(afterParam, SerializerFeature.WriteMapNullValue);
                } else {
                    afterBody = dealParam(paramStr);
                }
                if (afterBody != null && afterBody.trim().length() > 0) {
                    request = this.getRequest(request, afterBody);//重置post请求参数
                }
            } else {
                request = new GetServletRequest((HttpServletRequest) request, temp);
            }
        }
        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {
        return;
    }

    /**
     * 解析post提交的json格式参数
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getBody(HttpServletRequest request) throws Exception {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
        body = stringBuilder.toString();
        return body;
    }

    /**
     * 将post解析过后的request进行封装改写
     *
     * @param request
     * @param body
     * @return
     */
    private ServletRequest getRequest(ServletRequest request, String body) {
        String enctype = request.getContentType();
        if (StringUtils.isNotEmpty(enctype) && enctype.contains("application/json")) {
            return new PostServletRequest((HttpServletRequest) request, body);
        }
        return request;
    }

    /**
     * 处理请求参数
     *
     * @param mapParam
     */
    private Map<String, Object> dealParam(Map<String, Object> mapParam) {
        Map<String, Object> resutl = new HashMap<String, Object>();
        if (mapParam != null && !mapParam.isEmpty()) {
            Set<String> keys = mapParam.keySet();
            for (String key : keys) {
                Object value = mapParam.get(key);
                if (value instanceof String) {
                    String value3 = this.dealParam((String) value);
                    resutl.put(key, value3);
                } else if (value instanceof Boolean) {
                    resutl.put(key, value);
                } else if (value instanceof BigDecimal) {
                    resutl.put(key, value);
                } else if (value instanceof String[]) {
                    String[] values = (String[]) value;
                    for (int i = 0; i < values.length; i++) {
                        String str = values[i];
                        values[i] = this.dealParam(str);
                    }
                    resutl.put(key, values);
                } else {
                    resutl.put(key, value);
                }
            }
        }
        return resutl;
    }

    private String dealParam(String str) {
        String value1 = HtmlUtils.htmlEscape(str);
//        String value2 = StringEscapeUtils.escapeJavaScript(value1);
        String value3 = StringEscapeUtils.escapeJava(value1);
        return value3;
    }

}
