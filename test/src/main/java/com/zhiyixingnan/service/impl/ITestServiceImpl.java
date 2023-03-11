package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.TestDao;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.TestDetail;
import com.zhiyixingnan.service.ITestService;
import com.zhiyixingnan.service.client.UserClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ITestServiceImpl extends ServiceImpl<TestDao, TestDetail> implements ITestService {

  private final TestDao testDao;
  private final UserClient userClient;

  @Lazy
  public ITestServiceImpl(TestDao testDao, UserClient userClient) {
    this.testDao = testDao;
    this.userClient = userClient;
  }

  @Override
  public TestDetail getStudent(String id) {

    //    RestTemplate restTemplate = new RestTemplate();

    //    Student student =
    //        restTemplate.getForObject("http://localhost:8101/students/test/" + id, Student.class);

    Student student = userClient.getUserById("202108030102");

    return new TestDetail(student, null);
  }
}
