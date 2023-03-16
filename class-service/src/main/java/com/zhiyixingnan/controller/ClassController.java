package com.zhiyixingnan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhiyixingnan.domain.Classes;
import com.zhiyixingnan.service.IClassService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class")
public class ClassController {

  private final IClassService iClassService;

  @Lazy
  public ClassController(IClassService iClassService) {
    this.iClassService = iClassService;
  }

  @RequestMapping(value = "/getClassByClassId/{id}", method = RequestMethod.GET)
  public Classes getClassByClassId(@PathVariable("id") String id) {
    return iClassService.getOne(
        Wrappers.<Classes>lambdaQuery().eq(Classes::getId, id).eq(Classes::getDeleted, 0));
  }

  @RequestMapping(value = "/getClassByClassName/{name}", method = RequestMethod.GET)
  public Classes getClassByClassName(@PathVariable("name") String name) {
    return iClassService.getOne(
        Wrappers.<Classes>lambdaQuery().eq(Classes::getName, name).eq(Classes::getDeleted, 0));
  }
}
