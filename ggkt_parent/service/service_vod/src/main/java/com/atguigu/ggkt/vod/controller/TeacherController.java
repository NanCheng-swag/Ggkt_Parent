package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/22 15:49
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public Result<List<Teacher>> findAll() {
        List<Teacher> list = this.teacherService.list();
        return Result.ok(list).message("查询数据成功");
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public Result removeTeacherById(@PathVariable("id") Integer id) {
        boolean boo = this.teacherService.removeById(id);
        if (boo) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation("条件分页查询讲师")
    @PostMapping("/findQueryPage/{page}/{limit}")
    public Result findPage(@PathVariable("page") Long current,
                        @PathVariable("limit") Long limit,
                        @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        Page<Teacher> tPage = new Page(current, limit);
        if(teacherQueryVo == null) {
            Page<Teacher> pageModel = this.teacherService.page(tPage, null);
            return Result.ok(pageModel);
        } else {
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();
            if (!StringUtils.isEmpty(name)) {
                queryWrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)) {
                queryWrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(joinDateBegin)) {
                queryWrapper.ge("join_date", joinDateBegin);
            }
            if (!StringUtils.isEmpty(joinDateEnd)) {
                queryWrapper.le("join_date", joinDateEnd);
            }
            Page<Teacher> pageModel = teacherService.page(tPage, queryWrapper);
            return Result.ok(pageModel);
        }
    }

    @ApiOperation("添加讲师")
    @PostMapping("/saveTeacher")
    public Result save(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return Result.ok(null);
    }

    @ApiOperation("根据id获取讲师")
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable("id") Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    @ApiOperation("修改讲师")
    @PutMapping("/update")
    public Result updateById(@RequestBody Teacher teacher) {
        this.teacherService.updateById(teacher);
        return Result.ok(null);
    }

    @ApiOperation("根据id列表删除讲师")
    @DeleteMapping("/batchRemove")
    public Result deleteByIds(@RequestBody List<Long> ids) {
        this.teacherService.removeByIds(ids);
        return Result.ok(null);
    }
}

