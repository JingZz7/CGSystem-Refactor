package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.dao.StudentDao;
import com.zhiyixingnan.dao.TeacherDao;
import com.zhiyixingnan.dao.TutorDao;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Teacher;
import com.zhiyixingnan.service.IStudentService;
import com.zhiyixingnan.service.ITeacherService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ITeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher>
    implements ITeacherService {

  private final StudentDao studentDao;
  private final TeacherDao teacherDao;
  private final TutorDao tutorDao;
  private final AdministratorDao administratorDao;

  @Lazy
  public ITeacherServiceImpl(
      StudentDao studentDao,
      TeacherDao teacherDao,
      TutorDao tutorDao,
      AdministratorDao administratorDao) {
    this.studentDao = studentDao;
    this.teacherDao = teacherDao;
    this.tutorDao = tutorDao;
    this.administratorDao = administratorDao;
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
}
