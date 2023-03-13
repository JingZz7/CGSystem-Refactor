package com.zhiyixingnan.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhiyixingnan.service.IProblemService;
import com.zhiyixingnan.utils.JsonResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/show_problem")
public class ShowController {

  private final IProblemService iProblemService;

  @Lazy
  public ShowController(IProblemService iProblemService) {
    this.iProblemService = iProblemService;
  }

  /**
   * @param jsonObject: a * @retur JsonResult
   * @author ZJ
   * @description TODO [学生]获取题目列表(刷题推荐)
   * @date 2023/3/13 10:27
   */
  @RequestMapping(value = "/getProblemList", method = RequestMethod.POST)
  public JsonResult getProblemList(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iProblemService
            .getProblemList(jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize"))
            .getList(),
        iProblemService
            .getProblemList(jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }
}
