package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.atguigu.ggkt.vod.listener.SubjectListener;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/25 17:01
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper,Subject> implements SubjectService {
    @Override
    public List<Subject> subjectList(long id) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<Subject> subjects = this.baseMapper.selectList(queryWrapper);
        for (Subject subject : subjects) {
            Long subjectId = subject.getId();
            boolean boo = isChildren(subjectId);
            subject.setHasChildren(boo);
        }
        return subjects;
    }

    public boolean isChildren(long subjectId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",subjectId);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        return count>0;
    }

    //引入读取的监听器
    @Autowired
    private SubjectListener subjectListener;

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),SubjectEeVo.class, subjectListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
            List<Subject> subjects = this.baseMapper.selectList(null);
            List<SubjectEeVo> list = new ArrayList<>();
            for (SubjectEeVo sub : list) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(sub,subjectEeVo);
                list.add(subjectEeVo);
            }
            EasyExcel.write(response.getOutputStream(),SubjectEeVo.class).sheet().doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GgktException(20001,"导出失败！");
        }
    }
}
