package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.dao.StudentDao;
import com.zhiyixingnan.dao.TeacherDao;
import com.zhiyixingnan.dao.TutorDao;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.domain.Teacher;
import com.zhiyixingnan.service.ITeacherService;
import com.zhiyixingnan.service.client.ProblemClient;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ITeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher>
    implements ITeacherService {

  private final StudentDao studentDao;
  private final TeacherDao teacherDao;
  private final TutorDao tutorDao;
  private final AdministratorDao administratorDao;
  private final ProblemClient problemClient;

  @Lazy
  public ITeacherServiceImpl(
      StudentDao studentDao,
      TeacherDao teacherDao,
      TutorDao tutorDao,
      AdministratorDao administratorDao,
      ProblemClient problemClient) {
    this.studentDao = studentDao;
    this.teacherDao = teacherDao;
    this.tutorDao = tutorDao;
    this.administratorDao = administratorDao;
    this.problemClient = problemClient;
  }

  @Override
  public Boolean isExistTeacher(String id, String password) {
    LambdaQueryWrapper<Teacher> lambdaQueryWrapper = Wrappers.<Teacher>lambdaQuery();
    lambdaQueryWrapper
        .eq(Teacher::getDeleted, 0)
        .and(i -> i.eq(Teacher::getId, id).eq(Teacher::getPassword, password));
    if (teacherDao.selectOne(lambdaQueryWrapper) == null) return false;
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean deleteProblem(String problemId) {
    Problem problem = problemClient.getProblemByIdAndDeleted0(problemId);
    problem.setDeleted(1);
    problemClient.updateProblem(problem);
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean bulkDeleteProblem(List<String> ids) {
    if (ids.isEmpty()) return false;
    for (String problemId : ids) {
      if (problemClient.getProblemByIdAndDeleted0(problemId) == null) {
        continue;
      }
      Problem problem = problemClient.getProblemByIdAndDeleted0(problemId);
      problem.setDeleted(1);
      problemClient.updateProblem(problem);
    }
    return true;
  }
}
