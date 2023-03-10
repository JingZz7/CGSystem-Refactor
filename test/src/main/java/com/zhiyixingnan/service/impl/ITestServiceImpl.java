package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.TestDao;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Test;
import com.zhiyixingnan.domain.TestDetail;
import com.zhiyixingnan.service.ITestService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ITestServiceImpl extends ServiceImpl<TestDao, TestDetail> implements ITestService {

  private final TestDao testDao;

  @Lazy
  public ITestServiceImpl(TestDao testDao) {
    this.testDao = testDao;
  }

  @Override
  public TestDetail getStudent(String id) {

    RestTemplate restTemplate = new RestTemplate();

    Student student =
        restTemplate.getForObject("http://localhost:8101/students/test/" + id, Student.class);

    return new TestDetail(student, null);
  }
}
