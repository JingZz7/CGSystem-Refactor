package com.zhiyixingnan.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.service.IProblemService;
import com.zhiyixingnan.utils.JsonResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query")
public class QueryController {

  private final IProblemService iProblemService;

  @Lazy
  public QueryController(IProblemService iProblemService) {
    this.iProblemService = iProblemService;
  }

  /**
   * @param id:  * @return Problem
   * @author ZJ
   * @description TODO 通过id查找问题，Deleted必须为0
   * @date 2023/3/14 11:30
   */
  @RequestMapping(value = "/getProblemByIdAndDeleted0/{id}", method = RequestMethod.GET)
  public Problem getProblemByIdAndDeleted0(@PathVariable("id") String id) {
    return iProblemService.getProblemByIdAndDeleted0(id);
  }

  /**
   * @param jsonObject: a * @retur JsonResult
   * @author ZJ
   * @description TODO [学生]根据id查询问题(刷题推荐) json数据包含problemId、currentPage、pageSize
   * @date 2023/3/14 10:16
   */
  @RequestMapping(value = "/getProblemById", method = RequestMethod.POST)
  public JsonResult getProblemById(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iProblemService
            .getProblemById(
                jsonObject.getString("problemId"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iProblemService
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
   * @description TODO [学生]根据难度查询(刷题推荐)aop分页增强 json数据包含id、difficulty、currentPage、pageSize
   * @date 2023/3/14 10:22
   */
  @RequestMapping(value = "/getProblemsByDifficulty", method = RequestMethod.POST)
  public JsonResult getProblemsByDifficulty(@RequestBody JSONObject jsonObject) {
    return JsonResult.success(
        iProblemService.getProblemsByDifficulty(
            jsonObject.getString("id"),
            jsonObject.getString("difficulty"),
            jsonObject.getInteger("currentPage"),
            jsonObject.getInteger("pageSize")),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [学生]根据id查询问题(收藏夹) json数据包含studentId、problemId、currentPage、pageSize
   * @date 2023/3/14 10:36
   */
  @RequestMapping(value = "/getProblemInFavoriteById", method = RequestMethod.POST)
  public JsonResult getProblemInFavoriteById(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iProblemService
            .getProblemInFavoriteById(
                jsonObject.getString("studentId"),
                jsonObject.getString("problemId"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iProblemService
            .getProblemInFavoriteById(
                jsonObject.getString("studentId"),
                jsonObject.getString("problemId"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [学生]根据名称查询问题(收藏夹) json数据包含studentId、problemName、currentPage、pageSize
   * @date 2023/3/14 10:42
   */
  @RequestMapping(value = "/getProblemByName", method = RequestMethod.POST)
  public JsonResult getProblemByName(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iProblemService
            .getProblemByName(
                jsonObject.getString("studentId"),
                jsonObject.getString("problemName"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iProblemService
            .getProblemByName(
                jsonObject.getString("studentId"),
                jsonObject.getString("problemName"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }
}
