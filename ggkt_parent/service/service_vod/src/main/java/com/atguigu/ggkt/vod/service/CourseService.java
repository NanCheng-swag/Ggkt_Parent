package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/25 19:52
 */
public interface CourseService extends IService<Course> {

    Map<String, Object> findCoursePage(Page<Course> coursePage, CourseQueryVo courseQueryVo);

    Long saveCourseInfo(CourseFormVo courseFormVo);

    CourseFormVo getCourseById(Long id);

    void updateCourse(CourseFormVo courseFormVo);
}
