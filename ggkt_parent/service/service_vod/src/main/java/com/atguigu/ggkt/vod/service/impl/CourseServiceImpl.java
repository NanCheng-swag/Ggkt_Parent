package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.model.vod.CourseDescription;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.mapper.CourseMapper;
import com.atguigu.ggkt.vod.service.CourseDescriptionService;
import com.atguigu.ggkt.vod.service.CourseService;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/25 19:52
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;


    @Override
    public Map<String, Object> findCoursePage(Page<Course> coursePage, CourseQueryVo courseQueryVo) {

        //1.获取条件值
        String title = courseQueryVo.getTitle();
        Long teacherId = courseQueryVo.getTeacherId();
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectParentId = courseQueryVo.getSubjectParentId();

        //2.数据库获取
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id",teacherId);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        Page<Course> pageResult = this.baseMapper.selectPage(coursePage, wrapper);
        long totalCount = pageResult.getTotal();
        long totalPage = pageResult.getPages();
        long current = pageResult.getCurrent();
        long size = pageResult.getSize();
        List<Course> list = pageResult.getRecords();

        //3.封装讲师和分类名称
        list.stream().forEach((course)->{
            this.getTeacherAndSubject(course);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",list);
        return map;
    }

    public Course getTeacherAndSubject(Course course) {
        Long teacherId = course.getTeacherId();
        Teacher teacher = teacherService.getById(teacherId);
        if(teacher != null) {
            course.getParam().put("teachername",teacher.getName());
        }
        Subject subject = subjectService.getById(course.getSubjectParentId());
        if(subject!=null) {
            subject.getParam().put("subjectParentTitle",subject.getTitle());
        }
        Subject sub = subjectService.getById(course.getSubjectId());
        if(sub!=null) {
            sub.getParam().put("subjectTitle",sub.getTitle());
        }
        return course;
    }

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {

        //保存课程描述信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        this.baseMapper.insert(course);

        //保存课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setCourseId(course.getId());
        this.courseDescriptionService.save(courseDescription);

        //返回课程id
        return course.getId();
    }

    @Override
    public CourseFormVo getCourseById(Long id) {
        Course course = this.baseMapper.selectById(id);
        if(course==null) {
            return null;
        }
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course,courseFormVo);
        CourseDescription description = this.courseDescriptionService.getById(course.getId());
        if(description!=null) {
            courseFormVo.setDescription(description.getDescription());
        }
        return courseFormVo;
    }

    @Override
    public void updateCourse(CourseFormVo courseFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        this.baseMapper.updateById(course);

        CourseDescription description = new CourseDescription();
        description.setDescription(courseFormVo.getDescription());
        description.setCourseId(course.getId());
        this.courseDescriptionService.updateById(description);
    }


}
