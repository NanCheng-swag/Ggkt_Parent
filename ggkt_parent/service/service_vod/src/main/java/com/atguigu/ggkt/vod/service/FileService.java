package com.atguigu.ggkt.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/25 16:16
 */
public interface FileService {

    String upload(MultipartFile file) throws IOException;
}
