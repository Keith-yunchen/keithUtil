package com.zhouy.module.filedown.util;

import com.google.common.base.Charsets;
import com.google.common.io.Closer;
import com.google.common.io.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

public class FileUtil {
    /**
     * 默认io缓存大小4k
     */
    public static final int BUF_DEFAULT_SIZE = 0x1000;

    public static final String USER_AGENT_IE = "IE";
    public static final String USER_AGENT_FIREFOX = "FIREFOX";
    public static final String USER_AGENT_SAFARI = "SAFARI";

    public static final String ENCODE_NAME_ISO = Charsets.ISO_8859_1.name();
    public static final String ENCODE_NAME_UTF_8 = Charsets.UTF_8.name();
    public static final String ENCODE_NAME_GB2312 = "gb2312";

    public static final String CONTENT_TYPE = "multipart/form-data";


    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @param file
     */
    public static boolean downLoadOfFile(HttpServletRequest request, HttpServletResponse response, File file) {
        return downLoadOfFile(request, response, file, file.getName(), "");
    }

    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @param file
     * @param showName 显示名称,包含扩展名称
     */
    public static boolean downLoadOfFile(HttpServletRequest request, HttpServletResponse response, File file, String showName) {
        return downLoadOfFile(request, response, file, showName, "");
    }

    /**
     * 下载文件
     *
     * @param request
     * @param response
     * @param file
     * @param showName 显示名称
     * @param ext      后缀名称
     */
    public static boolean downLoadOfFile(HttpServletRequest request, HttpServletResponse response, File file, String showName, String ext) {
        if (file == null || !file.exists() || showName == null || ext == null) return Boolean.FALSE;
        downloadSettting(request, response, showName + ext);
        return downLoadOfFIleBase(response, file);
    }

    public static boolean downLoadOfPath(HttpServletRequest request, HttpServletResponse response, String absolutePath) {
        return downLoadOfPath(request, response, absolutePath, "");
    }

    public static boolean downLoadOfPath(HttpServletRequest request, HttpServletResponse response, String absolutePath, String showName) {
        return downLoadOfPath(request, response, absolutePath, showName, "");
    }

    /**
     * 根据绝对路径下载文件
     *
     * @param request
     * @param response
     * @param absolutePath
     * @param showName
     * @param ext
     */
    public static boolean downLoadOfPath(HttpServletRequest request, HttpServletResponse response, String absolutePath, String showName, String ext) {
        File file = new File(absolutePath);
        if (!file.exists()) return Boolean.FALSE;
        return downLoadOfFile(request, response, file, showName, ext);
    }

    public static boolean downLoadOfByte(HttpServletRequest request, HttpServletResponse response, byte[] fileByte, String showName) {
        return downLoadOfByte(request, response, fileByte, showName, "");
    }

    /**
     * 根据字节码下载文件
     *
     * @param request
     * @param response
     * @param fileByte
     * @param showName
     * @param ext
     * @return
     */
    public static boolean downLoadOfByte(HttpServletRequest request, HttpServletResponse response, byte[] fileByte, String showName, String ext) {
        if (fileByte == null || showName == null || ext == null) return Boolean.FALSE;
        downloadSettting(request, response, showName + ext);
        return downLoadOfFIleBase(response, fileByte);
    }

    /**
     * 获取浏览器信息
     *
     * @param request
     * @return
     */
    private static String getBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (userAgent.contains("msie")) return USER_AGENT_IE;
        if (userAgent.contains("firefox")) return USER_AGENT_FIREFOX;
        if (userAgent.contains("safari")) return USER_AGENT_SAFARI;
        return null;
    }

    /**
     * 下载设置
     *
     * @param request
     * @param response
     * @param fileName
     */
    private static void downloadSettting(HttpServletRequest request, HttpServletResponse response, String fileName) {
        response.reset();
        String userAgent = getBrowser(request);
        response.setCharacterEncoding(ENCODE_NAME_UTF_8);
        response.setContentType(CONTENT_TYPE);
        try {
            // 解决中文乱码
            if (userAgent != null && userAgent.equals(USER_AGENT_FIREFOX)) {
                fileName = new String(fileName.getBytes(ENCODE_NAME_GB2312), ENCODE_NAME_ISO);
            } else {
                fileName = URLEncoder.encode(fileName, ENCODE_NAME_UTF_8)
                        .replaceAll("\\+", "%20").replaceAll("%28", "\\(")
                        .replaceAll("%29", "\\)").replaceAll("%3B", ";")
                        .replaceAll("%40", "@").replaceAll("%23", "\\#")
                        .replaceAll("%26", "\\&");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", String.format("attachment; filename=%s", fileName));
    }


    /**
     * 采用默认缓存大小进行下载,4k
     *
     * @param response
     * @param file
     */
    public static boolean downLoadOfFIleBase(HttpServletResponse response, File file) {
        return downLoadOfFIleBase(response, file, 0);
    }

    /**
     * 文件下载最后方法
     *
     * @param response
     * @param file
     * @param bufsize  缓存大小
     */
    private static boolean downLoadOfFIleBase(HttpServletResponse response, File file, int bufsize) {
        Closer closer = Closer.create();
        try {
            BufferedInputStream in = closer.register((BufferedInputStream) Files.asByteSource(file).openBufferedStream());
            BufferedOutputStream ou = closer.register(new BufferedOutputStream(response.getOutputStream()));
            byte[] buff = new byte[BUF_DEFAULT_SIZE];
            if (bufsize > 0) buff = new byte[bufsize];
            int len;
            while ((len = in.read(buff)) != -1) ou.write(buff, 0, len);
            closer.close();
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Boolean.FALSE;
    }

    private static boolean downLoadOfFIleBase(HttpServletResponse response, byte[] fileByte) {
        Closer closer = Closer.create();
        try {
            BufferedOutputStream ou = closer.register(new BufferedOutputStream(response.getOutputStream()));
            ou.write(fileByte);
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Boolean.FALSE;
    }
}
