package com.zhiyixingnan.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.domain.ProblemDescription;
import com.zhiyixingnan.service.IProblemDescriptionService;
import com.zhiyixingnan.service.IProblemStudentService;
import com.zhiyixingnan.utils.JsonResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/show_problem")
public class ShowController {

  private final IProblemStudentService iProblemStudentService;
  private final IProblemDescriptionService iProblemDescriptionService;

  @Lazy
  public ShowController(
      IProblemStudentService iProblemStudentService,
      IProblemDescriptionService iProblemDescriptionService) {
    this.iProblemStudentService = iProblemStudentService;
    this.iProblemDescriptionService = iProblemDescriptionService;
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
        iProblemStudentService
            .getProblemList(jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize"))
            .getList(),
        iProblemStudentService
            .getProblemList(jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [学生]获取题目列表(刷题推荐)aop分页增强 json数据包含id、currentPage、pageSize
   * @date 2023/3/13 16:45
   */
  @RequestMapping(value = "/getProblemsList", method = RequestMethod.POST)
  public JsonResult getProblemsList(@RequestBody JSONObject jsonObject) {
    return JsonResult.success(
        iProblemStudentService.getProblemsList(
            jsonObject.getString("id"),
            jsonObject.getInteger("currentPage"),
            jsonObject.getInteger("pageSize")),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [学生]获取题目列表(收藏夹) json数据包含studentId、currentPage、pageSize
   * @date 2023/3/13 17:24
   */
  @RequestMapping(value = "/getFavoriteProblemList", method = RequestMethod.POST)
  public JsonResult getFavoriteProblemList(@RequestBody JSONObject jsonObject) {
    PageInfo<Problem> page =
        iProblemStudentService.getFavoriteProblemList(
            jsonObject.getString("studentId"),
            jsonObject.getInteger("currentPage"),
            jsonObject.getInteger("pageSize"));
    return JsonResult.successes(page.getList(), page.getTotal(), "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO 获取题目详细信息
   * @date 2023/3/16 20:14
   */
  @RequestMapping(value = "/getProblemDescription", method = RequestMethod.POST)
  public JsonResult getProblemDescription(@RequestBody JSONObject jsonObject) {
    return JsonResult.success(
        iProblemDescriptionService.getOne(
            Wrappers.<ProblemDescription>lambdaQuery()
                .eq(ProblemDescription::getProblemId, jsonObject.getString("problemId"))));
  }
}
