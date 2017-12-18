package com.gokeeper.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 图片上传地址配置
 * @author: Created by Akk_Mac
 * @Date: 2017/12/1 18:47
 */
@Data
@ConfigurationProperties(prefix = "adImage")
@Component
public class ImgUrlConfig {

    /**
     * 图片保存路径
     */
    public String savePath;

    /**
     * 图片访问URL
     */
    public String url;

}
