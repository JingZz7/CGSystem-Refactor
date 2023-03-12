package com.zhiyixingnan.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhiyixingnan.domain.Administrator;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Teacher;
import com.zhiyixingnan.domain.Tutor;
import com.zhiyixingnan.service.IAdministratorService;
import com.zhiyixingnan.service.IStudentService;
import com.zhiyixingnan.service.ITeacherService;
import com.zhiyixingnan.service.ITutorService;
import com.zhiyixingnan.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

  private final IStudentService iStudentService;
  private final IAdministratorService iAdministratorService;
  private final ITeacherService iTeacherService;
  private final ITutorService iTutorService;

  @Lazy
  public LoginController(
      IStudentService iStudentService,
      IAdministratorService iAdministratorService,
      ITeacherService iTeacherService,
      ITutorService iTutorService) {
    this.iStudentService = iStudentService;
    this.iAdministratorService = iAdministratorService;
    this.iTeacherService = iTeacherService;
    this.iTutorService = iTutorService;
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO 登录 jsonObject数据包含"id"和"password"即可
   * @date 2023/3/12 21:47
   */
  @RequestMapping(value = "/logins", method = RequestMethod.POST)
  //  @GetMapping("/{id}/{password}")
  public JsonResult logins(@RequestBody JSONObject jsonObject) {
    //    lqw.eq(Student::getDeleted        , 0)
    //            .and(i -> i.eq(Student::getId, id).eq(Student::getPassword, password));
    if (iStudentService.isExistStudent(
        jsonObject.getString("id"), jsonObject.getString("password")))
      return JsonResult.success(
          iStudentService.getOne(
              new LambdaQueryWrapper<Student>().eq(Student::getId, jsonObject.getString("id"))),
          "学生登录成功");
    else if (iTeacherService.isExistTeacher(
        jsonObject.getString("id"), jsonObject.getString("password")))
      return JsonResult.success(
          iTeacherService.getOne(
              new LambdaQueryWrapper<Teacher>().eq(Teacher::getId, jsonObject.getString("id"))),
          "教师登录成功");
    else if (iAdministratorService.isExistAdministrator(
        jsonObject.getString("id"), jsonObject.getString("password")))
      return JsonResult.success(
          iAdministratorService.getOne(
              new LambdaQueryWrapper<Administrator>()
                  .eq(Administrator::getId, jsonObject.getString("id"))),
          "管理员登录成功");
    else if (iTutorService.isExistTutor(
        jsonObject.getString("id"), jsonObject.getString("password")))
      return JsonResult.success(
          iTutorService.getOne(
              new LambdaQueryWrapper<Tutor>().eq(Tutor::getId, jsonObject.getString("id"))),
          "助教登录成功");
    return JsonResult.failed("登录失败");
  }
}
