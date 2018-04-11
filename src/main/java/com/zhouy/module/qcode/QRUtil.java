package com.zhouy.module.qcode;

import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.zhouy.module.qcode.qr.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码生成工具
 *
 * @Copyright 2018 ShenZhen DSE Corporation
 * @Company:深圳东深电子股份有限公司
 * @author:zhouy,date:20180313
 * @Version 1.0
 */
public class QRUtil {
    /**
     * 生成二维码图片
     */
    public static byte[] encoderQRCode(String wpcCd, int width, int height) throws Exception {
        String content = "http://qsxk.mwr.gov.cn/wtlicnece/licenceIsExist.action?licenceId=" + wpcCd;// 内容
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
//		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        BitMatrix bitMatrix = QRCodeWriter.encode(content, width, height, hints);
        BufferedImage bimage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        boolean flag = ImageIO.write(bimage, format, bos);
        if (flag) {
            byte[] data = bos.toByteArray();
            return data;
        }
        return null;
    }
}
