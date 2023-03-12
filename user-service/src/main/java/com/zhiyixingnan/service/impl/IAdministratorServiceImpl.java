package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.dao.StudentDao;
import com.zhiyixingnan.dao.TeacherDao;
import com.zhiyixingnan.dao.TutorDao;
import com.zhiyixingnan.domain.Administrator;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.service.IAdministratorService;
import com.zhiyixingnan.service.IStudentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class IAdministratorServiceImpl extends ServiceImpl<AdministratorDao, Administrator>
    implements IAdministratorService {

  private final StudentDao studentDao;
  private final TeacherDao teacherDao;
  private final TutorDao tutorDao;
  private final AdministratorDao administratorDao;

  @Lazy
  public IAdministratorServiceImpl(
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
  public Boolean isExistAdministrator(String id, String password) {
    LambdaQueryWrapper<Administrator> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(Administrator::getId, id).eq(Administrator::getPassword, password);
    if (administratorDao.selectOne(lambdaQueryWrapper) == null) return false;
    return true;
  }
}
