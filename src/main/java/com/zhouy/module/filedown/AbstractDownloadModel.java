package com.zhouy.module.filedown;


import com.zhouy.module.filedown.util.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * 文件下载模型抽象类,简化文件管理操作,将http操作封装成透明
 *
 * @author:zhouy,date:20170706
 * @Version 1.0
 */
public abstract class AbstractDownloadModel {
    protected String filePath = null;

    /**
     * 在实现下载方法中 需要自己标记是否执行删除
     */
    protected boolean deleteFlag = false;

    public AbstractDownloadModel() {
    }

    /**
     * 通过生成文件获取路径执行下载
     *
     * @return
     */
    public final boolean down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        execute(null);
        boolean flag = FileUtil.downLoadOfFile(request, response, new File(filePath), this.getFileName());
        //判断那种情况下是需要删除文件
        if (flag == true) {
            //下载完成删除文件
            if (deleteFlag) {
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
            }
        } else {
            throw new Exception("文件下载失败");
        }
        return flag;
    }

    /**
     * 通过传入参数生成文件并设置文件路径
     *
     * @param params
     */
    private void execute(Map<String, String> params) {
        try {
            filePath = generateFile(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 文件下载时显示名字
     * @return
     */
    protected abstract String getFileName();

    /**
     * 通过自己规则生成file
     * @param params
     * @return string 文件生成路径
     * @throws Exception
     */
    protected abstract String generateFile(Map<String, String> params) throws Exception;
}
