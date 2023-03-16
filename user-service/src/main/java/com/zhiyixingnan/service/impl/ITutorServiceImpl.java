package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.dao.StudentDao;
import com.zhiyixingnan.dao.TeacherDao;
import com.zhiyixingnan.dao.TutorDao;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Tutor;
import com.zhiyixingnan.service.IStudentService;
import com.zhiyixingnan.service.ITutorService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

  @Override
  public List<HashMap<String, String>> displayPersonalInformation(String id) {
    HashMap<String, String> map = new HashMap<>();
    Tutor tutor = tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
    map.put("id", id);
    map.put("name", tutor.getName());
    map.put("email", tutor.getEmail());
    map.put("phone", tutor.getPhone());
    List<HashMap<String, String>> list = new ArrayList<>();
    list.add(map);
    return list;
  }

  @Override
  public Boolean isTutorExist(String id) {
    if (tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) != null) {
      return true;
    }
    return false;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean modifyPhoneAndEmailById(String id, String phone, String email) {
    Boolean flag = isTutorExist(id);
    if (!flag) {
      return false;
    }
    Tutor tutor = tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
    tutor.setPhone(phone);
    tutor.setEmail(email);
    tutorDao.updateById(tutor);
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean modifyPasswordById(String id, String password) throws InterruptedException {
    Boolean flag = isTutorExist(id);
    if (!flag) {
      return false;
    }
    Tutor tutor = tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
    tutor.setPassword(password);
    tutorDao.updateById(tutor);
    return true;
  }
}
