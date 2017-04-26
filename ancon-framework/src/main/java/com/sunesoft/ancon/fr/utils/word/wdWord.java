package com.sunesoft.ancon.fr.utils.word;

import com.aspose.words.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Word 操作辅助类。
 */
public class wdWord {
    /**
     * 根据模板输出文件到指定位置。
     *
     * @param holder   占位符。
     * @param savePath 存储地址。
     * @return 是否成功。
     */
    public static boolean exportWordByTemplate(WordPlaceHolder holder, String savePath) {
        try {
            //设置授权，去除 Aspose 的水印
            InputStream is = wdWord.class.getClassLoader().getResourceAsStream("license.xml");
            License asposeLicense = new License();
            asposeLicense.setLicense(is);
            Document doc = new Document(holder.getTempFilePath());
            //插入文本
            for (Map.Entry<String, String> holderEntry : holder.getTextHolderMap().entrySet()) {
                doc.getRange().replace(Pattern.compile(holderEntry.getKey()), holderEntry.getValue());
            }

            //插入图片
            for (Map.Entry<String, List<String>> holderEntry : holder.getImageHolderMap().entrySet()) {
                doc.getRange().replace(Pattern.compile(holderEntry.getKey()), new ReplaceImage(holderEntry.getValue()), true);
            }
            doc.save(savePath, SaveFormat.DOC);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据模板输出文件到指定位置。
     *
     * @param files    多文件：key值对应压缩包中对应的文件路径，value值对应文件占位符。
     * @param savePath 存储地址。
     * @return 是否成功。
     */
    public static boolean exportWordPackageByTemplate(Map<String, WordPlaceHolder> files, String savePath) {
        try {
            //设置授权，去除 Aspose 的水印
            InputStream is = wdWord.class.getClassLoader().getResourceAsStream("license.xml");
            License asposeLicense = new License();
            asposeLicense.setLicense(is);
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(savePath));
            int i = 0;
            for (Map.Entry<String, WordPlaceHolder> file : files.entrySet()) {
                i++;
                zos.putNextEntry(new ZipEntry(file.getKey()));

                Document doc = new Document(file.getValue().getTempFilePath());

                //插入文本
                for (Map.Entry<String, String> holderEntry : file.getValue().getTextHolderMap().entrySet()) {
                    doc.getRange().replace(Pattern.compile(holderEntry.getKey()), holderEntry.getValue());
                }

                //插入图片
                for (Map.Entry<String, List<String>> holderEntry : file.getValue().getImageHolderMap().entrySet()) {
                    doc.getRange().replace(Pattern.compile(holderEntry.getKey()), new ReplaceImage(holderEntry.getValue()), true);
                }
                doc.save(zos, SaveFormat.DOC);
                zos.closeEntry();
            }
            zos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据模板向浏览器端输出 Word。
     *
     * @param holder   占位符。
     * @param response 服务器响应上下文。
     * @param fileName 导出的文件名称。
     * @return 是否成功。
     */
    public static boolean exportServletWordByTemplate(WordPlaceHolder holder, HttpServletResponse response, String fileName) {
        try {
            //设置授权，去除 Aspose 的水印
            InputStream is = wdWord.class.getClassLoader().getResourceAsStream("license.xml");
            License asposeLicense = new License();
            asposeLicense.setLicense(is);
            Document doc = new Document(holder.getTempFilePath());

            //插入文本
            for (Map.Entry<String, String> holderEntry : holder.getTextHolderMap().entrySet()) {
                doc.getRange().replace(Pattern.compile(holderEntry.getKey()), holderEntry.getValue());
            }

            //插入图片
            for (Map.Entry<String, List<String>> holderEntry : holder.getImageHolderMap().entrySet()) {
                doc.getRange().replace(Pattern.compile(holderEntry.getKey()), new ReplaceImage(holderEntry.getValue()), true);
            }

            OutputStream fos = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
            doc.save(fos, SaveFormat.DOC);
            is.close();
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据模板向浏览器端输出 Word 包。
     *
     * @param files    多文件：key值对应压缩包中对应的文件路径，value值对应文件占位符。
     * @param response 服务器响应上下文。
     * @param fileName 导出的文件名称。
     * @return 是否成功。
     */
    public static boolean exportServletWordPackageByTemplate(Map<String, WordPlaceHolder> files, HttpServletResponse response, String fileName) {
        try {
            //设置授权，去除 Aspose 的水印
            InputStream is = wdWord.class.getClassLoader().getResourceAsStream("license.xml");
            License asposeLicense = new License();
            asposeLicense.setLicense(is);
            //写入输出流，同时压缩文件。
            OutputStream fos = response.getOutputStream();
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (Map.Entry<String, WordPlaceHolder> file : files.entrySet()) {
                zos.putNextEntry(new ZipEntry(file.getKey()));

                Document doc = new Document(file.getValue().getTempFilePath());

                //插入文本
                for (Map.Entry<String, String> holderEntry : file.getValue().getTextHolderMap().entrySet()) {
                    doc.getRange().replace(Pattern.compile(holderEntry.getKey()), holderEntry.getValue());
                }

                //插入图片
                for (Map.Entry<String, List<String>> holderEntry : file.getValue().getImageHolderMap().entrySet()) {
                    doc.getRange().replace(Pattern.compile(holderEntry.getKey()), new ReplaceImage(holderEntry.getValue()), true);
                }
                doc.save(zos, SaveFormat.DOC);
                zos.closeEntry();
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
            is.close();
            zos.close();
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
