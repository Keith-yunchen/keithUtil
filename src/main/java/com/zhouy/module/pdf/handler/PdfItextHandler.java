package com.zhouy.module.pdf.handler;

import com.itextpdf.text.pdf.BaseFont;
import com.zhouy.module.pdf.AbstractConverterHandler;
import com.zhouy.module.pdf.bean.WordBean;
import com.zhouy.module.pdf.util.PdfUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 通过itest直接操作pdf
 *
 * @author:zhouy,date:20171013
 * @Version 1.0
 */
public class PdfItextHandler extends AbstractConverterHandler {
    @Override
    public String excuteConvert() throws Exception {
        Map params = super.getWordParams();
        //html模板路径
        String htmlPath = PdfUtil.getResourceFilePath("/template/PDF.html");
        //经过转换后的html文件路径
        String jsoupHtmlPath = PdfUtil.getFilePath() + File.separator + UUID.randomUUID() + WordBean.HTML_SUFFIX;
        OutputStream os = null;
        String pdfPath = WordBean.RELATIVE_PATH + this.getWordBean().getFileName() + "_" + UUID.randomUUID() + ".pdf";
        try {
            //给模板中的pdf设定参数
            boolean flag = this.genearteHtmlByparams(htmlPath, params, jsoupHtmlPath);
            if(flag == false){
                throw new Exception("处理html出错");
            }
            //获取模板html
            String url = new File(jsoupHtmlPath).toURI().toURL().toString();
            //开始执行html转pdf准备工作
            os = new FileOutputStream(pdfPath);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            // 解决中文支持 支持linux系统（字体固定）
            ITextFontResolver fontResolver = renderer.getFontResolver();
            //此处指定的字体 一定要在html中指定才能正确解析
            String fontPath = PdfUtil.getResourceFilePath("/template/simsun.ttc");
            fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if(null != os){
                os.close();
            }
            //删除html文件
            File file = new File(jsoupHtmlPath);
            if(file.exists()){
                file.delete();
            }
        }
        return pdfPath;
    }

    private boolean genearteHtmlByparams(String templatPath, Map params, String jpath) throws Exception {
        boolean flag = true;
        if (templatPath == null) {
            throw new Exception("模板文件不能为空");
        }
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        String content = null;
        try {
            //jsoup解析
            org.jsoup.nodes.Document doc = Jsoup.parse(new File(templatPath), "UTF-8");
            //给html替换文本 获取HTML中的table
            Elements tables = doc.getElementsByTag("table");
            Iterator<Element> tableit = tables.iterator();
            while (tableit.hasNext()) {
                Element tableElement = tableit.next();
                //如果table中的name属性值为true则需要循环添加
                String name = tableElement.attr("name");
                if ("true".equals(name)) {
                    String id = tableElement.attr("id");
                    List<Map> forParams = (List<Map>) params.get(id);
                    Element fortr = tableElement.getElementsByTag("tr").last();
                    //给模板中的循环标签追加到实际个数
                    for(int i = 1 ;i<forParams.size();i++){
                        fortr.after(fortr.html());
                    }
                    //获取所有的tr
                    Elements alltr = tableElement.getElementsByTag("tr");
                    Iterator<Element> alltrit = alltr.iterator();
                    int index = 0;
                    while (alltrit.hasNext()){
                        Map fortdparam = forParams.get(index);
                        Element alltr_el = alltrit.next();
                        Elements alltr_td = alltr_el.getElementsByTag("td");
                        Iterator<Element> alltr_td_it = alltr_td.iterator();
                        boolean flage = false;
                        while(alltr_td_it.hasNext()){
                            Element ftd = alltr_td_it.next();
                            String ownText = ftd.ownText();
                            //如果是需要进行追加值的操作 则进行赋值处理 并且将参数游标后移 如果没有做赋值操作，游标保持不变
                            if (ownText != null && ownText.startsWith("${") && ownText.endsWith("}")) {
                                //获取参数中的key值
                                String key = ownText.substring(2, ownText.length()-1);
                                String value = String.valueOf(fortdparam.get(key));
                                ftd.text(value == null ? "" : value);
                                //标记游标是否+1
                                flage = true;
                            }
                        }
                        if(flage==true){
                            index++;
                        }
                    }
                } else {
                    Elements tdelements = tableElement.getElementsByTag("td");
                    Iterator<Element> iterator = tdelements.iterator();
                    while (iterator.hasNext()) {
                        Element td = iterator.next();
                        String ownText = td.ownText();
                        if (ownText != null && ownText.startsWith("${") && ownText.endsWith("}")) {
                            String key = ownText.substring(2, ownText.length()-1);
                            String value = (String) params.get(key);
                            td.text(value == null ? "" : value);
                        }
                    }
                }
            }
            //替换完成转换成的html文本 生成完整HTML（标签合法）
            content = doc.html();
            File file = new File(jpath);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            flag = false;
            //异常 删除文件
            File file = new File(jpath);
            if(file.exists()){
                file.delete();
            }
        } finally {
            try {
                if (bw != null){
                    bw.close();
                }
                if (fos != null){
                    fos.close();
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
            content = null;
        }
        return flag;
    }

}
