package com.zhiyixingnan.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONObject;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.service.IStudentService;
import com.zhiyixingnan.utils.JsonResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SentinelResource(value = "test")
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

  /**
   * @param id: * @return Student
   * @author ZJ
   * @description TODO 通过id查找单个学生，Deleted必须为0
   * @date 2023/3/14 11:21
   */
  @RequestMapping(value = "/selectOneStudentByIdAndDeleted/{id}", method = RequestMethod.GET)
  public Student selectOneStudentByIdAndDeleted(@PathVariable("id") String id) {
    return iStudentService.selectOneStudentByIdAndDeleted(id);
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [学生]获取期末成绩预测 json数据包含id
   * @date 2023/3/16 19:51
   */
  @RequestMapping(value = "/getFinalForecast", method = RequestMethod.POST)
  public JsonResult getFinalForecast(@RequestBody JSONObject jsonObject) {
    return JsonResult.success(iStudentService.getFinalForecast(jsonObject.getString("id")), "获取成功");
  }
}
