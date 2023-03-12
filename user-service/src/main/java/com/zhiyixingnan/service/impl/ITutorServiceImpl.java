package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.dao.StudentDao;
import com.zhiyixingnan.dao.TeacherDao;
import com.zhiyixingnan.dao.TutorDao;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Tutor;
import com.zhiyixingnan.service.IStudentService;
import com.zhiyixingnan.service.ITutorService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ITutorServiceImpl extends ServiceImpl<TutorDao, Tutor> implements ITutorService {

  private final StudentDao studentDao;
  private final TeacherDao teacherDao;
  private final TutorDao tutorDao;
  private final AdministratorDao administratorDao;

  @Lazy
  public ITutorServiceImpl(
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
  public Boolean isExistTutor(String id, String password) {
    LambdaQueryWrapper<Tutor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(Tutor::getId, id).eq(Tutor::getPassword, password);
    if (tutorDao.selectOne(lambdaQueryWrapper) == null) return false;
    return true;
  }
}
