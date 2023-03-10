package com.zhiyixingnan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Test;
import com.zhiyixingnan.domain.TestDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDao extends BaseMapper<TestDetail> {}
