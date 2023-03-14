package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.CommentDao;
import com.zhiyixingnan.domain.CommentStudent;
import com.zhiyixingnan.service.ICommentService;
import com.zhiyixingnan.service.client.ProblemClient;
import com.zhiyixingnan.service.client.StudentClient;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ICommentServiceImpl extends ServiceImpl<CommentDao, CommentStudent>
    implements ICommentService {

  private final CommentDao commentDao;
  private final StudentClient studentClient;
  private final ProblemClient problemClient;

  @Lazy
  public ICommentServiceImpl(
      CommentDao commentDao, StudentClient studentClient, ProblemClient problemClient) {
    this.commentDao = commentDao;
    this.studentClient = studentClient;
    this.problemClient = problemClient;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean studentComment(String studentId, String problemId, String description)
      throws InterruptedException {
    if (studentClient.selectOneStudentByIdAndDeleted(studentId) == null
        || problemClient.getProblemByIdAndDeleted0(problemId) == null) return false;

    CommentStudent commentStudent = new CommentStudent();
    commentStudent.setStudentId(studentId);
    commentStudent.setProblemId(problemId);
    commentStudent.setDescription(description);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    java.util.Date date = new Date();
    String dateTime = df.format(date);
    commentStudent.setDateTime(dateTime);
    commentDao.insert(commentStudent);
    return true;
  }
}
