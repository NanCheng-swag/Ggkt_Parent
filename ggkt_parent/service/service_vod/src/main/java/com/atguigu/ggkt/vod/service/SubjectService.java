package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/25 17:01
 */
public interface SubjectService extends IService<Subject> {

    List<Subject> subjectList(long id);

    void importData(MultipartFile file);

    void exportData(HttpServletResponse response);
}
