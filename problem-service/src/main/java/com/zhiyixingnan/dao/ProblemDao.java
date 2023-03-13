package com.zhiyixingnan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.domain.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemDao extends BaseMapper<Problem> {}
