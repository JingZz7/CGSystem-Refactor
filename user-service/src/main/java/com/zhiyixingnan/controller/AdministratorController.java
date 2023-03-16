package com.zhiyixingnan.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhiyixingnan.service.IAdministratorService;
import com.zhiyixingnan.utils.JsonResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {

  private final IAdministratorService iAdministratorService;

  @Lazy
  public AdministratorController(IAdministratorService iAdministratorService) {
    this.iAdministratorService = iAdministratorService;
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]获取用户列表(账户管理) json数据包含currentPage、pageSize
   * @date 2023/3/16 14:17
   */
  @RequestMapping(value = "/administratorGetAccountList", method = RequestMethod.POST)
  public JsonResult administratorGetAccountList(@RequestBody JSONObject jsonObject) {
    return JsonResult.success(
        iAdministratorService.administratorGetAccountList(
            jsonObject.getInteger("currentPage"), jsonObject.getInteger("pageSize")),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]分类查找(账户管理) json数据包含type、currentPage、pageSize
   * @date 2023/3/16 14:21
   */
  @RequestMapping(value = "/getAccountByType", method = RequestMethod.POST)
  public JsonResult getAccountByType(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iAdministratorService
            .getAccountByType(
                jsonObject.getString("type"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iAdministratorService
            .getAccountByType(
                jsonObject.getString("type"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]编辑账户(账户管理) json数据中包含id、password、email、phone、className
   * @date 2023/3/16 14:25
   */
  @RequestMapping(value = "/editAccount", method = RequestMethod.PUT)
  public JsonResult editAccount(@RequestBody JSONObject jsonObject) throws InterruptedException {
    Boolean flag =
        iAdministratorService.editAccount(
            jsonObject.getString("id"),
            jsonObject.getString("password"),
            jsonObject.getString("email"),
            jsonObject.getString("phone"),
            jsonObject.getString("className"));

    if (flag) return JsonResult.success("修改成功");
    return JsonResult.failed("修改失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]添加账户(账户管理) json数据包含type、id、name、password、email、phone date、className
   * @date 2023/3/16 14:29
   */
  @RequestMapping(value = "/addAccount", method = RequestMethod.POST)
  public JsonResult addAccount(@RequestBody JSONObject jsonObject) throws InterruptedException {
    Boolean flag =
        iAdministratorService.addAccount(
            jsonObject.getString("type"),
            jsonObject.getString("id"),
            jsonObject.getString("name"),
            jsonObject.getString("password"),
            jsonObject.getString("email"),
            jsonObject.getString("phone"),
            jsonObject.getString("className"));
    if (flag) {
      return JsonResult.success("添加成功");
    }
    return JsonResult.failed("添加失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]删除账号(账户管理) json数据包含id
   * @date 2023/3/16 14:32
   */
  @RequestMapping(value = "/deleteAccount", method = RequestMethod.DELETE)
  public JsonResult deleteAccount(@RequestBody JSONObject jsonObject) throws InterruptedException {
    Boolean flag = iAdministratorService.deleteAccount(jsonObject.getString("id"));

    if (flag) return JsonResult.success("删除成功");
    return JsonResult.failed("删除失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]批量删除账号(账户管理) json数据包含ids数组
   * @date 2023/3/16 14:35
   */
  @RequestMapping(value = "/bulkDeleteAccount", method = RequestMethod.DELETE)
  public JsonResult bulkDeleteAccount(@RequestBody JSONObject jsonObject)
      throws InterruptedException {
    List<String> ids = jsonObject.getJSONArray("ids").toJavaList(String.class);
    Boolean flag = iAdministratorService.bulkDeleteAccount(ids);
    if (flag) return JsonResult.success("删除成功");
    return JsonResult.failed("删除失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]重置密码(账户管理) json数据包含id
   * @date 2023/3/16 14:37
   */
  @RequestMapping(value = "/resetPasswords", method = RequestMethod.PUT)
  public JsonResult resetPasswords(@RequestBody JSONObject jsonObject) throws InterruptedException {
    Boolean flag = iAdministratorService.resetPassword(jsonObject.getString("id"));
    if (flag) {
      return JsonResult.success("重置成功");
    }
    return JsonResult.failed("重置失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]批量重置密码(账户管理) json数据包含ids数组和password
   * @date 2023/3/16 14:39
   */
  @RequestMapping(value = "/bulkResetPasswords", method = RequestMethod.PUT)
  public JsonResult bulkResetPasswords(@RequestBody JSONObject jsonObject)
      throws InterruptedException {
    List<String> ids = jsonObject.getJSONArray("ids").toJavaList(String.class);
    Boolean flag = iAdministratorService.bulkResetPasswords(ids);

    if (flag) {
      return JsonResult.success("修改成功");
    }
    return JsonResult.failed("修改失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]根据id查询账号(账户管理) json数据包含id、currentPage、pageSize
   * @date 2023/3/16 14:41
   */
  @RequestMapping(value = "/getAccountById", method = RequestMethod.POST)
  public JsonResult getAccountById(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iAdministratorService
            .getAccountById(
                jsonObject.getString("id"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iAdministratorService
            .getAccountById(
                jsonObject.getString("id"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [管理员]根据姓名查询账号(账户管理) json数据包含name、currentPage、pageSize
   * @date 2023/3/16 14:43
   */
  @RequestMapping(value = "/getAccountByName", method = RequestMethod.POST)
  public JsonResult getAccountByName(@RequestBody JSONObject jsonObject) {
    return JsonResult.successes(
        iAdministratorService
            .getAccountByName(
                jsonObject.getString("name"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getList(),
        iAdministratorService
            .getAccountByName(
                jsonObject.getString("name"),
                jsonObject.getInteger("currentPage"),
                jsonObject.getInteger("pageSize"))
            .getTotal(),
        "获取成功");
  }
}
