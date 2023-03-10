package com.zhiyixingnan.controller;

import com.zhiyixingnan.domain.TestDetail;
import com.zhiyixingnan.service.ITestService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testtt")
public class TestController {

  private final ITestService iTestService;

  @Lazy
  public TestController(ITestService iTestService) {
    this.iTestService = iTestService;
  }

  @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
  public TestDetail testtt(@PathVariable String id) {
    return iTestService.getStudent(id);
  }
}
