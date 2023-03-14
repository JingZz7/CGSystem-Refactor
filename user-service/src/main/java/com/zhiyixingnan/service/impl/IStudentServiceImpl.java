package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.dao.StudentDao;
import com.zhiyixingnan.dao.TeacherDao;
import com.zhiyixingnan.dao.TutorDao;
import com.zhiyixingnan.domain.Administrator;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Teacher;
import com.zhiyixingnan.domain.Tutor;
import com.zhiyixingnan.service.IStudentService;
import com.zhiyixingnan.utils.GetCaptcha;
import com.zhiyixingnan.utils.MailUtils;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class IStudentServiceImpl extends ServiceImpl<StudentDao, Student>
    implements IStudentService {

  private final StudentDao studentDao;
  private final TeacherDao teacherDao;
  private final TutorDao tutorDao;
  private final AdministratorDao administratorDao;

  @Lazy
  public IStudentServiceImpl(
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
  public Student selectOneStudentByIdAndDeleted(String id) {
    return studentDao.selectOne(
        new LambdaQueryWrapper<Student>().eq(Student::getId, id).eq(Student::getDeleted, 0));
  }

  public Teacher selectOneTeacherByIdAndDeleted(String id) {
    return teacherDao.selectOne(
        new LambdaQueryWrapper<Teacher>().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0));
  }

  public Tutor selectOneTutorById(String id) {
    return tutorDao.selectOne(new LambdaQueryWrapper<Tutor>().eq(Tutor::getId, id));
  }

  public Administrator selectOneAdministratorById(String id) {
    return administratorDao.selectOne(
        new LambdaQueryWrapper<Administrator>().eq(Administrator::getId, id));
  }

  @Override
  public Boolean isExistStudent(String id, String password) {
    LambdaQueryWrapper<Student> lqw = Wrappers.<Student>lambdaQuery();
    lqw.eq(Student::getDeleted, 0)
        .and(i -> i.eq(Student::getId, id).eq(Student::getPassword, password));
    return studentDao.selectOne(lqw) != null;
  }

  @Override
  public String getCaptchaById(String id) {

    if (selectOneStudentByIdAndDeleted(id) == null
        && selectOneTeacherByIdAndDeleted(id) == null
        && selectOneTutorById(id) == null
        && selectOneAdministratorById(id) == null) {
      return "学号错误";
    }

    String code = new GetCaptcha().getCode(6);

    if (selectOneStudentByIdAndDeleted(id) != null)
      new MailUtils()
          .sendMail(selectOneStudentByIdAndDeleted(id).getEmail(), "验证码为：" + code, "CGSystem验证码");
    else if (selectOneTeacherByIdAndDeleted(id) != null)
      new MailUtils()
          .sendMail(selectOneTeacherByIdAndDeleted(id).getEmail(), "验证码为：" + code, "CGSystem验证码");
    else if (selectOneTutorById(id) != null)
      new MailUtils()
          .sendMail(selectOneTutorById(id).getEmail(), "验证码为：" + code, "CGSystem" + "验证码");
    else
      new MailUtils()
          .sendMail(selectOneAdministratorById(id).getEmail(), "验证码为：" + code, "CGSystem验证码");
    return code;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean forgotPassword(String id, String password) throws InterruptedException {
    if (selectOneStudentByIdAndDeleted(id) == null
        && selectOneTeacherByIdAndDeleted(id) == null
        && selectOneTutorById(id) == null
        && selectOneAdministratorById(id) == null) return false;

    if (selectOneStudentByIdAndDeleted(id) != null) {
      Student student = selectOneStudentByIdAndDeleted(id);
      student.setPassword(password);
      studentDao.updateById(student);
      return true;
    } else if (selectOneTeacherByIdAndDeleted(id) != null) {
      Teacher teacher = selectOneTeacherByIdAndDeleted(id);
      teacher.setPassword(password);
      teacherDao.updateById(teacher);
      return true;
    } else if (selectOneTutorById(id) != null) {
      Tutor tutor = selectOneTutorById(id);
      tutor.setPassword(password);
      tutorDao.updateById(tutor);
      return true;
    } else {
      Administrator administrator = selectOneAdministratorById(id);
      administrator.setPassword(password);
      administratorDao.updateById(administrator);
      return true;
    }
  }
}
