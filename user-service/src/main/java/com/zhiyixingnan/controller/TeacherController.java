package com.zhiyixingnan.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhiyixingnan.service.ITeacherService;
import com.zhiyixingnan.utils.JsonResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

  private final ITeacherService iTeacherService;

  @Lazy
  public TeacherController(ITeacherService iTeacherService) {
    this.iTeacherService = iTeacherService;
  }

  /**
   * @param jsonObject: a * @retur JsonResult
   * @author ZJ
   * @description TODO [教师]删除题目(题库管理) json数据包含problemId
   * @date 2023/3/14 15:05
   */
  @RequestMapping(value = "/deleteProblem", method = RequestMethod.DELETE)
  public JsonResult deleteProblem(@RequestBody JSONObject jsonObject) {
    Boolean flag = iTeacherService.deleteProblem(jsonObject.getString("problemId"));
    if (flag) {
      return JsonResult.success("删除成功");
    }
    return JsonResult.failed("删除失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]批量删除题目(题库管理) json数据包含ids
   * @date 2023/3/14 15:31
   */
  @RequestMapping(value = "/bulkDeleteProblem", method = RequestMethod.DELETE)
  public JsonResult bulkDeleteProblem(@RequestBody JSONObject jsonObject) {
    Boolean flag =
        iTeacherService.bulkDeleteProblem(jsonObject.getJSONArray("ids").toJavaList(String.class));
    if (flag) {
      return JsonResult.success("删除成功");
    }
    return JsonResult.failed("删除失败");
  }
}
