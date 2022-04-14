package com.muguang.core.utils;

import cn.hutool.core.net.url.UrlBuilder;
import com.muguang.core.entity.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransferUtils {

    private static String host;
    @Value("${host}")
    public void setHost(String str){
        host = str;
    }

    private static String port;
    @Value("${port}")
    public void setPort(String str){
        port = str;
    }

    public static void setImage(Image image){
        String fileName = image.getName();
        if (fileName != null) {
            String url = UrlBuilder.create()
                    .setScheme("http")
                    .setHost(host)
                    .setPort(Integer.parseInt(port))
                    .addPath("selectImage")
                    .addPath(fileName).toString();
            image.setUrl(url);
        }
    }
}
