package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.dao.StudentDao;
import com.zhiyixingnan.dao.TeacherDao;
import com.zhiyixingnan.dao.TutorDao;
import com.zhiyixingnan.domain.Administrator;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Teacher;
import com.zhiyixingnan.domain.Tutor;
import com.zhiyixingnan.service.IAdministratorService;
import com.zhiyixingnan.service.client.ClassClient;
import com.zhiyixingnan.utils.PageUtils;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class IAdministratorServiceImpl extends ServiceImpl<AdministratorDao, Administrator>
    implements IAdministratorService {

  private final StudentDao studentDao;
  private final TeacherDao teacherDao;
  private final TutorDao tutorDao;
  private final AdministratorDao administratorDao;
  private final ClassClient classClient;

  @Lazy
  public IAdministratorServiceImpl(
      StudentDao studentDao,
      TeacherDao teacherDao,
      TutorDao tutorDao,
      AdministratorDao administratorDao,
      ClassClient classClient) {
    this.studentDao = studentDao;
    this.teacherDao = teacherDao;
    this.tutorDao = tutorDao;
    this.administratorDao = administratorDao;
    this.classClient = classClient;
  }

  public HashMap<Object, Object> getAccountByTypeStudent(
      HashMap<Object, Object> map, Student student, String type) {
    map.put("pkStudentId", student.getPkStudentId());
    map.put("id", student.getId());
    map.put("name", student.getName());
    map.put("password", student.getPassword());
    map.put("phone", student.getPhone());
    map.put("email", student.getEmail());
    map.put("classId", student.getClassId());
    map.put("deleted", student.getDeleted());
    map.put("type", type);
    return map;
  }

  public HashMap<Object, Object> getAccountByTypeTeacher(
      HashMap<Object, Object> map, Teacher teacher, String type) {
    map.put("pkTeacherId", teacher.getPkTeacherId());
    map.put("id", teacher.getId());
    map.put("name", teacher.getName());
    map.put("password", teacher.getPassword());
    map.put("phone", teacher.getPhone());
    map.put("email", teacher.getEmail());
    map.put("deleted", teacher.getDeleted());
    map.put("type", type);
    return map;
  }

  public HashMap<Object, Object> getAccountByTypeTutor(
      HashMap<Object, Object> map, Tutor tutor, String type) {
    map.put("pkTutorId", tutor.getPkTutorId());
    map.put("id", tutor.getId());
    map.put("name", tutor.getName());
    map.put("password", tutor.getPassword());
    map.put("phone", tutor.getPhone());
    map.put("email", tutor.getEmail());
    map.put("type", type);
    return map;
  }

  @Override
  public Boolean isExistAdministrator(String id, String password) {
    LambdaQueryWrapper<Administrator> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(Administrator::getId, id).eq(Administrator::getPassword, password);
    if (administratorDao.selectOne(lambdaQueryWrapper) == null) return false;
    return true;
  }

  @Override
  public Object administratorGetAccountList(int currentPage, int pageSize) {
    List<Student> students =
        studentDao.selectList(
            new LambdaQueryWrapper<Student>().eq(Student::getDeleted, 0)); // deleted为1的表示已被逻辑删除了
    List<Teacher> teachers =
        teacherDao.selectList(
            new LambdaQueryWrapper<Teacher>().eq(Teacher::getDeleted, 0)); // deleted为1的表示已被逻辑删除了
    List<Tutor> tutors = tutorDao.selectList(null);
    List<HashMap<String, String>> list = new ArrayList<>();
    for (Student student : students) {
      HashMap<String, String> map = new HashMap<>();
      map.put("pkStudentId", student.getPkStudentId());
      map.put("type", "student");
      map.put("id", student.getId());
      map.put("name", student.getName());
      map.put("className", classClient.getClassByClassId(student.getClassId()).getName());
      map.put("phone", student.getPhone());
      map.put("email", student.getEmail());
      list.add(map);
    }
    for (Teacher teacher : teachers) {
      HashMap<String, String> map = new HashMap<>();
      map.put("pkStudentId", teacher.getPkTeacherId());
      map.put("type", "teacher");
      map.put("id", teacher.getId());
      map.put("name", teacher.getName());
      map.put("phone", teacher.getPhone());
      map.put("email", teacher.getEmail());
      list.add(map);
    }
    for (Tutor tutor : tutors) {
      HashMap<String, String> map = new HashMap<>();
      map.put("pkStudentId", tutor.getPkTutorId());
      map.put("type", "tutor");
      map.put("id", tutor.getId());
      map.put("name", tutor.getName());
      map.put("phone", tutor.getPhone());
      map.put("email", tutor.getEmail());
      list.add(map);
    }

    return list;
  }

  @Override
  public PageInfo<?> getAccountByType(String type, int currentPage, int pageSize) {
    if (type.equals("student")) {
      List<Student> students =
          studentDao.selectList(Wrappers.<Student>lambdaQuery().eq(Student::getDeleted, 0));
      List<Object> list = new ArrayList<>();
      for (Student student : students) {
        HashMap<Object, Object> map = new HashMap<>();
        map = getAccountByTypeStudent(map, student, type);
        list.add(map);
      }
      return PageUtils.pageObject(list, currentPage, pageSize);
    } else if (type.equals("teacher")) {
      List<Teacher> teachers =
          teacherDao.selectList(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getDeleted, 0));
      List<Object> list = new ArrayList<>();
      for (Teacher teacher : teachers) {
        HashMap<Object, Object> map = new HashMap<>();
        map = getAccountByTypeTeacher(map, teacher, type);
        list.add(map);
      }
      return PageUtils.pageObject(list, currentPage, pageSize);
    } else if (type.equals("tutor")) {
      List<Tutor> tutors = tutorDao.selectList(null);
      List<Object> list = new ArrayList<>();
      for (Tutor tutor : tutors) {
        HashMap<Object, Object> map = new HashMap<>();
        map = getAccountByTypeTutor(map, tutor, type);
        list.add(map);
      }
      return PageUtils.pageObject(list, currentPage, pageSize);
    }
    List<Object> objects = new ArrayList<>();
    List<Student> students =
        studentDao.selectList(Wrappers.<Student>lambdaQuery().eq(Student::getDeleted, 0));
    List<Teacher> teachers =
        teacherDao.selectList(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getDeleted, 0));
    List<Tutor> tutors = tutorDao.selectList(null);
    for (Student student : students) {
      HashMap<Object, Object> map = new HashMap<>();
      map = getAccountByTypeStudent(map, student, type);
      objects.add(map);
    }
    for (Teacher teacher : teachers) {
      HashMap<Object, Object> map = new HashMap<>();
      map = getAccountByTypeTeacher(map, teacher, type);
      objects.add(map);
    }
    for (Tutor tutor : tutors) {
      HashMap<Object, Object> map = new HashMap<>();
      map = getAccountByTypeTutor(map, tutor, type);
      objects.add(map);
    }
    return PageUtils.pageObject(objects, currentPage, pageSize);
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean editAccount(
      String id, String password, String email, String phone, String className)
      throws InterruptedException {

    LambdaQueryWrapper<Student> lambdaQueryWrapper1 =
        Wrappers.<Student>lambdaQuery().eq(Student::getId, id);
    LambdaQueryWrapper<Teacher> lambdaQueryWrapper2 =
        Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id);
    LambdaQueryWrapper<Tutor> lambdaQueryWrapper3 =
        Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id);

    Student student = studentDao.selectOne(lambdaQueryWrapper1);
    Teacher teacher = teacherDao.selectOne(lambdaQueryWrapper2);
    Tutor tutor = tutorDao.selectOne(lambdaQueryWrapper3);

    if (student != null) {
      student.setPassword(password);
      student.setEmail(email);
      student.setPhone(phone);
      student.setClassId(classClient.getClassByClassName(className).getId());
      studentDao.updateById(student);
      return true;
    } else if (teacher != null) {
      teacher.setPassword(password);
      teacher.setEmail(email);
      teacher.setPhone(phone);
      teacherDao.updateById(teacher);
      return true;
    } else if (tutor != null) {
      tutor.setPassword(password);
      tutor.setEmail(email);
      tutor.setPhone(phone);
      tutorDao.updateById(tutor);
      return true;
    }
    return false;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean addAccount(
      String type,
      String id,
      String name,
      String password,
      String email,
      String phone,
      String className)
      throws InterruptedException {

    if (studentDao.selectOne(
                Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0))
            != null
        || teacherDao.selectOne(
                Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0))
            != null
        || tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) != null)
      return false;

    if (type.equals("student")) {

      if (classClient.getClassByClassName(className) == null) {
        return false;
      }

      if (studentDao.selectOne(
              Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 1))
          != null) {

        Student student =
            studentDao.selectOne(
                Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 1));
        student.setName(name);
        student.setPassword(password);
        student.setEmail(email);
        student.setPhone(phone);
        student.setDeleted(0);
        student.setClassId(classClient.getClassByClassName(className).getId());
        studentDao.updateById(student);
        return true;
      }

      Student student = new Student();
      student.setId(id);
      student.setName(name);
      student.setPassword(password);
      student.setEmail(email);
      student.setPhone(phone);
      student.setDeleted(0);
      student.setClassId(classClient.getClassByClassName(className).getId());
      studentDao.insert(student);
      return true;
    } else if (type.equals("teacher")) {

      if (teacherDao.selectOne(
              Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 1))
          != null) {
        Teacher teacher =
            teacherDao.selectOne(
                Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 1));
        teacher.setName(name);
        teacher.setPassword(password);
        teacher.setEmail(email);
        teacher.setPhone(phone);
        teacher.setDeleted(0);
        teacherDao.updateById(teacher);
        return true;
      }

      Teacher teacher = new Teacher();
      teacher.setId(id);
      teacher.setName(name);
      teacher.setPassword(password);
      teacher.setEmail(email);
      teacher.setPhone(phone);
      teacher.setDeleted(0);
      teacherDao.insert(teacher);
      return true;
    } else if (type.equals("tutor")) {
      Tutor tutor = new Tutor();
      tutor.setId(id);
      tutor.setName(name);
      tutor.setPassword(password);
      tutor.setEmail(email);
      tutor.setPhone(phone);
      tutorDao.insert(tutor);
      return true;
    }

    return false;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean deleteAccount(String id) throws InterruptedException {
    Student student = studentDao.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, id));
    Teacher teacher = teacherDao.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id));
    Tutor tutor = tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));

    if (student == null && teacher == null && tutor == null) {
      return false;
    }

    if (student != null) {
      studentDao.delete(Wrappers.<Student>lambdaQuery().eq(Student::getId, id));
    } else if (teacher != null) {
      teacherDao.delete(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id));
    } else {
      tutorDao.delete(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
    }
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean bulkDeleteAccount(List<String> ids) throws InterruptedException {
    for (String id : ids) {
      // 三个都为空，返回false，学生的deleted已经为1或者教师的deleted已经为1，返回false
      if ((studentDao.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, id)) == null
              && teacherDao.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id))
                  == null
              && tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) == null)
          || (studentDao.selectOne(
                      Wrappers.<Student>lambdaQuery()
                          .eq(Student::getId, id)
                          .eq(Student::getDeleted, 1))
                  != null
              || teacherDao.selectOne(
                      Wrappers.<Teacher>lambdaQuery()
                          .eq(Teacher::getId, id)
                          .eq(Teacher::getDeleted, 1))
                  != null)) {
        return false;
      }

      // 学生和教师进行逻辑删除，助教进行物理删除
      if (studentDao.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, id)) != null) {
        Student student =
            studentDao.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, id));
        student.setDeleted(1);
        studentDao.updateById(student);
      }
      if (teacherDao.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id)) != null) {
        Teacher teacher =
            teacherDao.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id));
        teacher.setDeleted(1);
        teacherDao.updateById(teacher);
      }
      if (tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) != null) {
        tutorDao.delete(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
      }
    }
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean resetPassword(String id) throws InterruptedException {
    if (studentDao.selectOne(
            Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0))
        != null) {
      Student student =
          studentDao.selectOne(
              Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0));
      student.setPassword("123456");
      studentDao.updateById(student);
      return true;
    } else if (teacherDao.selectOne(
            Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0))
        != null) {
      Teacher teacher =
          teacherDao.selectOne(
              Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0));
      teacher.setPassword("123456");
      teacherDao.updateById(teacher);
      return true;
    } else if (tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) != null) {
      Tutor tutor = tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
      tutor.setPassword("123456");
      tutorDao.updateById(tutor);
      return true;
    }
    return false;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean bulkResetPasswords(List<String> ids) throws InterruptedException {
    for (String id : ids) {
      if (studentDao.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, id)) != null) {
        Student student =
            studentDao.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, id));
        student.setPassword("123456");
        studentDao.updateById(student);
      }

      if (teacherDao.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id)) != null) {
        Teacher teacher =
            teacherDao.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id));
        teacher.setPassword("123456");
        teacherDao.updateById(teacher);
      }

      if (tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) != null) {
        Tutor tutor = tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
        tutor.setPassword("123456");
        tutorDao.updateById(tutor);
      }
    }
    return true;
  }

  @Override
  public PageInfo<?> getAccountById(String id, int currentPage, int pageSize) {
    if (studentDao.selectOne(
            Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0))
        != null) {
      List<Student> students =
          studentDao.selectList(
              Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0));

      return PageUtils.pageStudent(students, currentPage, pageSize);
    }

    if (teacherDao.selectOne(
            Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0))
        != null) {
      List<Teacher> teachers =
          teacherDao.selectList(
              Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0));
      return PageUtils.pageTeacher(teachers, currentPage, pageSize);
    }

    if (tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) != null) {
      List<Tutor> tutors = tutorDao.selectList(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
      return PageUtils.pageTutor(tutors, currentPage, pageSize);
    }
    // 返回一个空的List ?
    return new PageInfo<List<?>>(new ArrayList<>());
  }

  @Override
  public PageInfo<?> getAccountByName(String name, int currentPage, int pageSize) {
    // 模糊查询
    List<Object> objects = new ArrayList<>();
    // 查找包含name的学生
    if (studentDao.selectList(
            Wrappers.<Student>lambdaQuery().eq(Student::getDeleted, 0).like(Student::getName, name))
        != null) {
      List<Student> students =
          studentDao.selectList(
              Wrappers.<Student>lambdaQuery()
                  .eq(Student::getDeleted, 0)
                  .like(Student::getName, name));

      for (Student student : students) {
        objects.add(student);
      }
    }
    // 查询包含name的教师
    if (teacherDao.selectList(
            Wrappers.<Teacher>lambdaQuery().eq(Teacher::getDeleted, 0).like(Teacher::getName, name))
        != null) {
      List<Teacher> teachers =
          teacherDao.selectList(
              Wrappers.<Teacher>lambdaQuery()
                  .eq(Teacher::getDeleted, 0)
                  .like(Teacher::getName, name));

      for (Teacher teacher : teachers) {
        objects.add(teacher);
      }
    }
    // 查询包含name的助教
    if (tutorDao.selectList(Wrappers.<Tutor>lambdaQuery().like(Tutor::getName, name)) != null) {
      List<Tutor> tutors =
          tutorDao.selectList(Wrappers.<Tutor>lambdaQuery().like(Tutor::getName, name));
      for (Tutor tutor : tutors) objects.add(tutor);
    }
    // 不为空，开始分页操作
    if (!objects.isEmpty()) {
      return PageUtils.pageObject(objects, currentPage, pageSize);
    }
    // 如果object为空，返回一个空List
    return new PageInfo<List<?>>(new ArrayList<>());
  }

  @Override
  public List<HashMap<String, String>> displayPersonalInformation(String id) {
    HashMap<String, String> map = new HashMap<>();
    Administrator administrator =
        administratorDao.selectOne(
            Wrappers.<Administrator>lambdaQuery().eq(Administrator::getId, id));
    map.put("id", id);
    map.put("name", administrator.getName());
    map.put("email", administrator.getEmail());
    map.put("phone", administrator.getPhone());
    List<HashMap<String, String>> list = new ArrayList<>();
    list.add(map);
    return list;
  }

  @Override
  public Boolean isAdministratorExist(String id) {
    if (administratorDao.selectOne(
            Wrappers.<Administrator>lambdaQuery().eq(Administrator::getId, id))
        != null) {
      return true;
    }
    return false;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean modifyPhoneAndEmailById(String id, String phone, String email)
      throws InterruptedException {
    Boolean flag = isAdministratorExist(id);
    if (!flag) {
      return false;
    }
    Administrator administrator =
        administratorDao.selectOne(
            Wrappers.<Administrator>lambdaQuery().eq(Administrator::getId, id));
    administrator.setPhone(phone);
    administrator.setEmail(email);
    administratorDao.updateById(administrator);
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean modifyPasswordById(String id, String password) throws InterruptedException {
    Boolean flag = isAdministratorExist(id);
    if (!flag) {
      return false;
    }
    Administrator administrator =
        administratorDao.selectOne(
            Wrappers.<Administrator>lambdaQuery().eq(Administrator::getId, id));
    administrator.setPassword(password);
    administratorDao.updateById(administrator);
    return true;
  }
}
