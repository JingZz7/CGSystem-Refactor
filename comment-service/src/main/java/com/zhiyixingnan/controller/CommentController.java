package com.zhiyixingnan.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhiyixingnan.service.ICommentService;
import com.zhiyixingnan.utils.JsonResult;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

  private final ICommentService iCommentService;

  @Lazy
  public CommentController(ICommentService iCommentService) {
    this.iCommentService = iCommentService;
  }

  /**
   * @param jsonObject: a * @retur JsonResult
   * @author ZJ
   * @description TODO [学生]评论 json数据包含studentId、problemId、description
   * @date 2023/3/14 11:32
   */
  @RequestMapping(value = "/studentComment", method = RequestMethod.POST)
  public JsonResult studentComment(@RequestBody JSONObject jsonObject) throws InterruptedException {
    Boolean flag =
        iCommentService.studentComment(
            jsonObject.getString("studentId"),
            jsonObject.getString("problemId"),
            jsonObject.getString("description"));

    if (flag) {
      return JsonResult.success("评论成功");
    }
    return JsonResult.failed("评论失败");
  }
}
