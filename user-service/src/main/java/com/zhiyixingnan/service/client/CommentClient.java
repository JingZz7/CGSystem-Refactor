package com.zhiyixingnan.service.client;

import com.zhiyixingnan.domain.CommentStudent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("comment-service")
public interface CommentClient {

  @RequestMapping("/comment/getCommentList")
  public List<CommentStudent> getCommentList();

  @RequestMapping(value = "/comment/getCommentByProblemId/{id}")
  public List<CommentStudent> getCommentByProblemId(@PathVariable("id") String id);

  @RequestMapping(value = "/comment/getCommentByStudentId/{id}")
  public CommentStudent getCommentByStudentId(@PathVariable("id") String id);
}
