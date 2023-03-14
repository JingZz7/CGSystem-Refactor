package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.CommentStudent;

import java.util.PrimitiveIterator;

public interface ICommentService extends IService<CommentStudent> {

  /**
   * @param studentId:
   * @param problemId:
   * @param description: a * @return Boolea
   * @author ZJ
   * @description TODO [学生]评论
   * @date 2023/3/14 11:05
   */
  public Boolean studentComment(String studentId, String problemId, String description) throws InterruptedException;
}
