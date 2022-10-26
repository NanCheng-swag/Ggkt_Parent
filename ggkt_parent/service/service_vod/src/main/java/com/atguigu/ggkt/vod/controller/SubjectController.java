package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/25 17:00
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping("/admin/vod/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("查询下一层的课程分类")
    @GetMapping("/getChildSubject/{id}")
    public Result getChildrenSubject(@PathVariable("id") long id) {
        List<Subject> list = this.subjectService.subjectList(id);
        return Result.ok(list);
    }

    @ApiOperation("课程分类导入")
    @GetMapping("/importData")
    public void importData(MultipartFile file) {
        this.subjectService.importData(file);
    }

    @ApiOperation("课程分类导出")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        this.subjectService.exportData(response);
    }
}
