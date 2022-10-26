package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/25 19:52
 */
@Api(tags = "课程管理接口")
@RestController
@RequestMapping("/admin/vod/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("课程列表")
    @GetMapping("/{page}/{limit}")
    public Result courseList(@PathVariable("page") Long page,
                            @PathVariable("limit") Long limit,
                            CourseQueryVo courseQueryVo) {
        Page<Course> coursePage = new Page<>(page, limit);
        Map<String,Object> map = this.courseService.findCoursePage(coursePage,courseQueryVo);
        return Result.ok(map);
    }

    @ApiOperation("新增课程")
    @PostMapping("/save")
    public Result save(@RequestBody CourseFormVo courseFormVo) {
        Long courseId = this.courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }

    @ApiOperation("获取课程")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Long id) {
        CourseFormVo couseFormVo = this.courseService.getCourseById(id);
        return Result.ok(couseFormVo);
    }

    @ApiOperation("修改课程")
    @PutMapping("/update")
    public Result updateCourse(@RequestBody CourseFormVo courseFormVo) {
        this.courseService.updateCourse(courseFormVo);
        return Result.ok();
    }
}
