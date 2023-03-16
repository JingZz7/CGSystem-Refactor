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

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]编辑题目(题库管理) json数据包含id、name、difficulty、label
   * @date 2023/3/15 17:45
   */
  @RequestMapping(value = "/editProblem", method = RequestMethod.PUT)
  public JsonResult editProblem(@RequestBody JSONObject jsonObject) throws InterruptedException {
    Boolean flag =
        iTeacherService.editProblem(
            jsonObject.getString("id"),
            jsonObject.getString("name"),
            jsonObject.getString("difficulty"),
            jsonObject.getString("label"));
    if (flag) {
      return JsonResult.success("编辑成功");
    }
    return JsonResult.failed("编辑失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]添加题目(题库管理) json数据包含id、name、label、difficulty
   * @date 2023/3/15 17:54
   */
  @RequestMapping(value = "/addProblem", method = RequestMethod.POST)
  public JsonResult addProblem(@RequestBody JSONObject jsonObject) throws InterruptedException {
    Boolean flag =
        iTeacherService.addProblem(
            jsonObject.getString("id"),
            jsonObject.getString("name"),
            jsonObject.getString("label"),
            jsonObject.getString("difficulty"));
    if (flag) {
      return JsonResult.success("添加成功");
    }
    return JsonResult.failed("添加失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]获取账户列表(账户管理) json数据包含currentPage、pageSize
   * @date 2023/3/15 17:58
   */
  @RequestMapping(value = "/teacherGetAccountList", method = RequestMethod.POST)
  public JsonResult teacherGetAccountList(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iTeacherService
            .teacherGetAccountList(
                jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize"))
            .getList(),
        iTeacherService
            .teacherGetAccountList(
                jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]根据工号查询(账户管理) json数据包含id、currentPage、pageSize
   * @date 2023/3/15 18:02
   */
  @RequestMapping(value = "/teacherGetAccountById", method = RequestMethod.POST)
  public JsonResult teacherGetAccountById(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iTeacherService
            .teacherGetAccountById(
                jsonObject.getString("id"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iTeacherService
            .teacherGetAccountById(
                jsonObject.getString("id"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]根据姓名查询(账户管理) json数据包含name、currentPage、pageSize
   * @date 2023/3/15 18:05
   */
  @RequestMapping(value = "/teacherGetAccountByName", method = RequestMethod.POST)
  public JsonResult teacherGetAccountByName(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iTeacherService
            .teacherGetAccountByName(
                jsonObject.getString("name"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iTeacherService
            .teacherGetAccountByName(
                jsonObject.getString("name"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]根据类型查询(账户管理) json数据包含type、currentPage、pageSize
   * @date 2023/3/15 18:07
   */
  @RequestMapping(value = "/teacherGetAccountByType", method = RequestMethod.POST)
  public JsonResult teacherGetAccountByType(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iTeacherService
            .teacherGetAccountByType(
                jsonObject.getString("type"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iTeacherService
            .teacherGetAccountByType(
                jsonObject.getString("type"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "查找成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]添加账户(账户管理) json数据包含type、id、name、password、email、phone
   * @date 2023/3/15 18:09
   */
  @RequestMapping(value = "/teacherAddAccount", method = RequestMethod.POST)
  public JsonResult teacherAddAccount(@RequestBody JSONObject jsonObject)
      throws InterruptedException {
    Boolean flag =
        iTeacherService.teacherAddAccount(
            jsonObject.getString("type"),
            jsonObject.getString("id"),
            jsonObject.getString("name"),
            jsonObject.getString("password"),
            jsonObject.getString("email"),
            jsonObject.getString("phone"));
    if (flag) {
      return JsonResult.success("添加成功");
    }
    return JsonResult.failed("添加失败");
  }

  /**
   * @param jsonObject: a * return JsonResult
   * @author ZJ
   * @description TODO [教师]删除账户(账户管理) json数据包含id
   * @date 2023/3/15 18:12
   */
  @RequestMapping(value = "/teacherDeleteAccount", method = RequestMethod.DELETE)
  public JsonResult teacherDeleteAccount(@RequestBody JSONObject jsonObject)
      throws InterruptedException {
    Boolean flag = iTeacherService.deleteAccount(jsonObject.getString("id"));
    if (flag) {
      return JsonResult.success("删除成功");
    }
    return JsonResult.failed("删除失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]批量删除账户(账户管理) json数据包含ids的数组
   * @date 2023/3/15 18:14
   */
  @RequestMapping(value = "/teacherBulkDeleteAccount", method = RequestMethod.DELETE)
  public JsonResult teacherBulkDeleteAccount(@RequestBody JSONObject jsonObject)
      throws InterruptedException {
    Boolean flag =
        iTeacherService.teacherBulkDeleteAccount(
            jsonObject.getJSONArray("ids").toJavaList(String.class));
    if (flag) {
      return JsonResult.success("删除成功");
    }
    return JsonResult.failed("删除失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]重置密码(账户管理) json数据包含id
   * @date 2023/3/15 18:16
   */
  @RequestMapping(value = "/teacherResetPassword", method = RequestMethod.PUT)
  public JsonResult teacherResetPassword(@RequestBody JSONObject jsonObject)
      throws InterruptedException {
    Boolean flag = iTeacherService.teacherResetPassword(jsonObject.getString("id"));
    if (flag) {
      return JsonResult.success("重置成功");
    }
    return JsonResult.failed("重置失败");
  }

  /**
   * @param jsonObject: a * return JsonResult
   * @author ZJ
   * @description TODO [教师]批量重置密码(账户管理) json数据包含ids数组
   * @date 2023/3/15 18:19
   */
  @RequestMapping(value = "/teacherBulkResetPassword", method = RequestMethod.PUT)
  public JsonResult teacherBulkResetPassword(@RequestBody JSONObject jsonObject)
      throws InterruptedException {
    Boolean flag =
        iTeacherService.teacherBulkResetPassword(
            jsonObject.getJSONArray("ids").toJavaList(String.class));
    if (flag) return JsonResult.success("重置成功");
    return JsonResult.failed("重置失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]获取评论列表(查看评论) json数据包含currentPage、pageSize
   * @date 2023/3/15 18:26
   */
  @RequestMapping(value = "teacherGetReviewList", method = RequestMethod.POST)
  public JsonResult teacherGetReviewList(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iTeacherService
            .teacherGetReviewList(
                jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize"))
            .getList(),
        iTeacherService
            .teacherGetReviewList(
                jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]根据问题id搜索评论(查看评论) json数据包含problemId、currentPage、pageSize
   * @date 2023/3/15 18:33
   */
  @RequestMapping(value = "/teacherGetReviewByProblemId", method = RequestMethod.POST)
  public JsonResult teacherGetReviewByProblemId(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iTeacherService
            .teacherGetReviewByProblemId(
                jsonObject.getString("problemId"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iTeacherService
            .teacherGetReviewByProblemId(
                jsonObject.getString("problemId"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]查看具体评论信息(查看评论) json数据包含评论的id
   * @date 2023/3/16 14:05
   */
  @RequestMapping(value = "/teacherViewDetailReview", method = RequestMethod.POST)
  public JsonResult teacherViewDetailReview(@RequestBody JSONObject jsonObject) {
    return JsonResult.success(
        iTeacherService.teacherViewDetailReview(jsonObject.getString("id")), "获取成功");
  }

  /**
   * @param : * @return JsonResult
   * @author ZJ
   * @description TODO [教师]获取成绩分布 无参
   * @date 2023/3/16 14:51
   */
  @RequestMapping(value = "/gradeDistribution", method = RequestMethod.POST)
  public JsonResult gradeDistribution() {
    return JsonResult.success(iTeacherService.gradeDistribution(), "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]根据班级获取成绩分布 json数据包含className
   * @date 2023/3/16 15:45
   */
  @RequestMapping(value = "/gradeDistributionByClass", method = RequestMethod.POST)
  public JsonResult gradeDistributionByClass(@RequestBody JSONObject jsonObject) {
    return JsonResult.success(
        iTeacherService.gradeDistributionByClass(jsonObject.getString("className")), "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [教师]获取学生知识点成绩扇形图 json数据包含id
   * @date 2023/3/16 19:49
   */
  @RequestMapping(value = "/getKnowledgePointGrade", method = RequestMethod.POST)
  public JsonResult getKnowledgePointGrade(@RequestBody JSONObject jsonObject) {
    return JsonResult.success(
        iTeacherService.getKnowledgePointGrade(jsonObject.getString("id")), "获取成功");
  }
}
