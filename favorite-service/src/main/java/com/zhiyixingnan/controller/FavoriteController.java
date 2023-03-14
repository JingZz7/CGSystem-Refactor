package com.zhiyixingnan.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhiyixingnan.domain.Favorite;
import com.zhiyixingnan.service.IFavoriteService;
import com.zhiyixingnan.utils.JsonResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

  private final IFavoriteService iFavoriteService;

  @Lazy
  public FavoriteController(IFavoriteService iFavoriteService) {
    this.iFavoriteService = iFavoriteService;
  }

  @RequestMapping(value = "/getProblemsByStudentId/{id}", method = RequestMethod.GET)
  public List<Favorite> getProblemsByStudentId(@PathVariable("id") String id) {
    return iFavoriteService.getProblemsByStudentId(id);
  }

  @RequestMapping(
      value = "/getProblemInFavoriteById/{studentId}/{problemId}",
      method = RequestMethod.GET)
  public Favorite getProblemInFavoriteById(
      @PathVariable("studentId") String studentId, @PathVariable("problemId") String problemId) {
    return iFavoriteService.getProblemInFavoriteById(studentId, problemId);
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [学生]收藏题目 json数据包含studentId,problemId
   * @date 2023/3/14 10:50
   */
  @RequestMapping(value = "/collectProblem", method = RequestMethod.POST)
  public JsonResult collectProblem(@RequestBody JSONObject jsonObject) throws InterruptedException {
    if (iFavoriteService.collectProblem(
        jsonObject.getString("studentId"), jsonObject.getString("problemId")))
      return JsonResult.success("收藏成功");
    return JsonResult.failed("收藏失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [学生]取消收藏 json数据包含studentId,problemId
   * @date 2023/3/14 10:53
   */
  @RequestMapping(value = "/cancelCollectedProblem", method = RequestMethod.DELETE)
  public JsonResult cancelCollectedProblem(@RequestBody JSONObject jsonObject)
      throws InterruptedException {
    if (iFavoriteService.cancelCollectedProblem(
        jsonObject.getString("studentId"), jsonObject.getString("problemId")))
      return JsonResult.success("取消收藏成功");
    return JsonResult.failed("取消收藏失败");
  }

  /**
   * @param jsonObject: * @return JsonResult
   * @author ZJ
   * @description TODO [学生]批量删除收藏夹的题目 json数据包含studentId字段和ids的list集合
   * @date 2023/3/14 10:55
   */
  @RequestMapping(value = "/bulkDeleteCollectedProblem", method = RequestMethod.DELETE)
  public JsonResult bulkDeleteCollectedProblem(@RequestBody JSONObject jsonObject)
      throws InterruptedException {
    if (jsonObject == null) return JsonResult.validateFailed("删除失败");
    iFavoriteService.bulkDeleteCollectedProblem(
        jsonObject.getString("studentId"),
        jsonObject.getJSONArray("ids").toJavaList(Integer.class));
    return JsonResult.success("删除成功");
  }
}
