package com.zhiyixingnan.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.service.IProblemTeacherService;
import com.zhiyixingnan.utils.JsonResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management_query")
public class ManagementQueryController {

  private final IProblemTeacherService iProblemTeacherService;

  @Lazy
  public ManagementQueryController(IProblemTeacherService iProblemTeacherService) {
    this.iProblemTeacherService = iProblemTeacherService;
  }

  @RequestMapping(value = "/updateProblem", method = RequestMethod.POST)
  public Boolean updateProblem(@RequestBody JSONObject jsonObject) {
    return iProblemTeacherService.updateById(jsonObject.toJavaObject(Problem.class));
  }

  /**
   * @param jsonObject: a * @return JsonResult
   * @author ZJ
   * @description TODO [教师]根据id查询问题(题库管理) json数据包含problemId、currentPage、pageSize
   * @date 2023/3/14 14:44
   */
  @RequestMapping(value = "/getProblemById", method = RequestMethod.POST)
  public JsonResult getProblemById(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iProblemTeacherService
            .getProblemById(
                jsonObject.getString("problemId"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iProblemTeacherService
            .getProblemById(
                jsonObject.getString("problemId"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]根据名称查询问题(题库管理) json数据包含problemName、currentPage、pageSize
   * @date 2023/3/14 14:47
   */
  @RequestMapping(value = "/getProblemListByName", method = RequestMethod.POST)
  public JsonResult getProblemListByName(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iProblemTeacherService
            .getProblemListByName(
                jsonObject.getString("problemName"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iProblemTeacherService
            .getProblemListByName(
                jsonObject.getString("problemName"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]根据难度查询(题库管理) json数据包含difficulty、currentPage、pageSize
   * @date 2023/3/14 14:51
   */
  @RequestMapping(value = "/getListByDifficulty", method = RequestMethod.POST)
  public JsonResult getListByDifficulty(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iProblemTeacherService
            .getListByDifficulty(
                jsonObject.getString("difficulty"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iProblemTeacherService
            .getListByDifficulty(
                jsonObject.getString("difficulty"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "查询成功");
  }
}
