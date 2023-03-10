package com.zhiyixingnan.controller;

import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.service.IStudentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

  private final IStudentService iStudentService;

  @Lazy
  public StudentController(IStudentService iStudentService) {
    this.iStudentService = iStudentService;
  }

  @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
  public Student test(@PathVariable String id) {
    return iStudentService.selectOneStudentByIdAndDeleted(id);
  }
}
