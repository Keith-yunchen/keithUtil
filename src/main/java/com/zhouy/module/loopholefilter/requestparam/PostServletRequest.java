package com.zhouy.module.loopholefilter.requestparam;


import com.zhouy.module.poiexcel.util.StringUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 处理post请求的 json格式数据解析
 * author:zhouy,date:2017/2/16.
 */
public class PostServletRequest extends HttpServletRequestWrapper {
    private String body = null;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public PostServletRequest(HttpServletRequest request, String body) {
        super(request);
        this.body = body;
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream inputStream = null;
        if (StringUtil.isNotEmpty(body)) {
            inputStream = new PostServletInputStream(body);
        }
        return inputStream;
    }


    @Override
    public BufferedReader getReader() throws IOException {
        String enc = getCharacterEncoding();
        if (enc == null) {
            enc = "UTF-8";
        }
        return new BufferedReader(new InputStreamReader(getInputStream(), enc));
    }
}
