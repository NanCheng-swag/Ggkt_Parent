package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.CourseDescription;
import com.atguigu.ggkt.vod.mapper.CourseDescriptionMapper;
import com.atguigu.ggkt.vod.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/25 21:29
 */
@Service
public class CourseDescriptionImpl extends ServiceImpl<CourseDescriptionMapper,CourseDescription> implements CourseDescriptionService {
}
