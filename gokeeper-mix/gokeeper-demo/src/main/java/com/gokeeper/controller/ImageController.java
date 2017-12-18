package com.gokeeper.controller;

import com.gokeeper.config.ImgUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传img
 */
@Controller
public class ImageController {

    @Autowired
    private ImgUrlConfig imgUrlConfig;

    /**
     * 图片类型
     */
    private static List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".bmp");
        fileTypes.add(".gif");
        fileTypes.add(".png");
    }

    @RequestMapping(value = "/uploadImage", produces="text/html;charset=utf-8")
    public Object uploadFile(@RequestParam(value = "imgFile") String file,
                             HttpServletRequest request, HttpServletResponse response) {
        System.out.println("文件名："+file);
        String fileName = null;

        // 获得上传路径的绝对路径地址(/upload)-->
        String realPath = imgUrlConfig.getSavePath();
        System.out.println("realPath:" + realPath);
        // 如果路径不存在，则创建该路径
        File realPathDirectory = new File(realPath);
        if (realPathDirectory == null || !realPathDirectory.exists()) {
            realPathDirectory.mkdirs();
        }
        // 重命名上传后的文件名 111112323.jpg
        fileName = System.currentTimeMillis() + "_";
        // 定义上传路径 .../upload/111112323.jpg
        String path = realPathDirectory + "\\" + fileName;

        if (GenerateImage(file, path) ){
            return imgUrlConfig.getUrl() + fileName;
        }
        /*try {
            if (file != null) {
                // 取得当前上传文件的文件名称
                String myFileName = file.getOriginalFilename();
                // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                if (myFileName.trim() != "") {
                    // 获得图片的原始名称
                    String originalFilename = file.getOriginalFilename();
                    // 获得图片后缀名称,如果后缀不为图片格式，则不上传
                    String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
                    if (!fileTypes.contains(suffix)) {
                        return "上传失败";
                    }
                    // 获得上传路径的绝对路径地址(/upload)-->
                    String realPath = imgUrlConfig.getSavePath();
                    System.out.println("realPath:" + realPath);
                    // 如果路径不存在，则创建该路径
                    File realPathDirectory = new File(realPath);
                    if (realPathDirectory == null || !realPathDirectory.exists()) {
                        realPathDirectory.mkdirs();
                    }
                    // 重命名上传后的文件名 111112323.jpg
                    fileName = System.currentTimeMillis() + "_" + originalFilename;
                    // 定义上传路径 .../upload/111112323.jpg
                    File uploadFile = new File(realPathDirectory + "\\" + fileName);
                    System.out.println("uploadFile" + uploadFile);
                    file.transferTo(uploadFile);
                    String imageContextPath = imgUrlConfig.getUrl() + fileName;
                    return imageContextPath;
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return "上传失败";
    }

    //base64字符串转化成图片
    public static boolean GenerateImage(String imgStr, String path)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null){ //图像数据为空
            return false;}
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}

