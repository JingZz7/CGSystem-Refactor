package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.Test;
import com.zhiyixingnan.domain.TestDetail;

public interface ITestService extends IService<TestDetail> {

  TestDetail getStudent(String id);
}
