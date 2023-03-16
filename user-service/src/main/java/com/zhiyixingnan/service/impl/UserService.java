package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.dao.StudentDao;
import com.zhiyixingnan.dao.TeacherDao;
import com.zhiyixingnan.dao.TutorDao;
import com.zhiyixingnan.domain.Administrator;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Teacher;
import com.zhiyixingnan.domain.Tutor;
import com.zhiyixingnan.service.IAdministratorService;
import com.zhiyixingnan.service.IStudentService;
import com.zhiyixingnan.service.ITeacherService;
import com.zhiyixingnan.service.ITutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private StudentDao studentDao;

  @Autowired private IStudentService iStudentService;

  @Autowired private TeacherDao teacherDao;

  @Autowired private ITeacherService iTeacherService;

  @Autowired private TutorDao tutorDao;

  @Autowired private ITutorService iTutorService;

  @Autowired private AdministratorDao administratorDao;

  @Autowired private IAdministratorService iAdministratorService;

  /**
   * @param id:
   * @return String
   * @author ZJ
   * @description TODO 获取密码
   * @date 2022/12/5 14:59
   */
  public String getPasswordById(String id) {
    if (iStudentService.isStudentExist(id)) {
      return studentDao
          .selectOne(
              Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0))
          .getPassword();
    }
    if (iTeacherService.isTeacherExist(id)) {
      return teacherDao
          .selectOne(
              Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0))
          .getPassword();
    }
    if (iTutorService.isTutorExist(id)) {
      return tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)).getPassword();
    }
    if (iAdministratorService.isAdministratorExist(id)) {
      return administratorDao
          .selectOne(Wrappers.<Administrator>lambdaQuery().eq(Administrator::getId, id))
          .getPassword();
    }
    return null;
  }
}
