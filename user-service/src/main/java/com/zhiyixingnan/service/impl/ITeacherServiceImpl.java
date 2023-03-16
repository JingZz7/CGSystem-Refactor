package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.dao.StudentDao;
import com.zhiyixingnan.dao.TeacherDao;
import com.zhiyixingnan.dao.TutorDao;
import com.zhiyixingnan.domain.CommentStudent;
import com.zhiyixingnan.domain.ModelOutputKnowledge;
import com.zhiyixingnan.domain.ModelOutputScore;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Teacher;
import com.zhiyixingnan.domain.Tutor;
import com.zhiyixingnan.service.ITeacherService;
import com.zhiyixingnan.service.client.CommentClient;
import com.zhiyixingnan.service.client.ModelClient;
import com.zhiyixingnan.service.client.ProblemClient;
import com.zhiyixingnan.utils.PageUtils;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class ITeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher>
    implements ITeacherService {

  private final StudentDao studentDao;
  private final TeacherDao teacherDao;
  private final TutorDao tutorDao;
  private final AdministratorDao administratorDao;
  private final ProblemClient problemClient;
  private final CommentClient commentClient;
  private final ModelClient modelClient;

  @Lazy
  public ITeacherServiceImpl(
      StudentDao studentDao,
      TeacherDao teacherDao,
      TutorDao tutorDao,
      AdministratorDao administratorDao,
      ProblemClient problemClient,
      CommentClient commentClient,
      ModelClient modelClient) {
    this.studentDao = studentDao;
    this.teacherDao = teacherDao;
    this.tutorDao = tutorDao;
    this.administratorDao = administratorDao;
    this.problemClient = problemClient;
    this.commentClient = commentClient;
    this.modelClient = modelClient;
  }

  public Student selectOneStudentByIdAndDeleted0(String id) {
    return studentDao.selectOne(
        Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0));
  }

  public Student selectOneStudentByIdAndDeleted1(String id) {
    return studentDao.selectOne(
        Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 1));
  }

  public Tutor selectOneTutorById(String id) {
    return tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
  }

  public List<Student> selectStudentByClassName(String classId) {
    return studentDao.selectList(
        Wrappers.<Student>lambdaQuery()
            .eq(Student::getClassId, classId)
            .eq(Student::getDeleted, 0));
  }

  public static BigDecimal sqrt(BigDecimal value, int scale) {
    BigDecimal num2 = BigDecimal.valueOf(2);
    int precision = 100;
    MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
    BigDecimal deviation = value;
    int cnt = 0;
    while (cnt < precision) {
      deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
      cnt++;
    }
    deviation = deviation.setScale(scale, BigDecimal.ROUND_HALF_UP);
    return deviation;
  }

  private static List<BigDecimal> customSortList(
      List<HashMap<String, BigDecimal>> platformDataStatistics, int[] index) {
    List<BigDecimal> list = new ArrayList<>();
    for (int i = 0; i < 11; ++i) {
      Collection<BigDecimal> values =
          platformDataStatistics.get(index[i]).values(); // 获取一个Hashmap中的value数组
      for (BigDecimal ii : values) {
        list.add(ii);
      }
    }
    return list;
  }

  @Override
  public Boolean isExistTeacher(String id, String password) {
    LambdaQueryWrapper<Teacher> lambdaQueryWrapper = Wrappers.<Teacher>lambdaQuery();
    lambdaQueryWrapper
        .eq(Teacher::getDeleted, 0)
        .and(i -> i.eq(Teacher::getId, id).eq(Teacher::getPassword, password));
    if (teacherDao.selectOne(lambdaQueryWrapper) == null) {
      return false;
    }
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

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean editProblem(String id, String name, String difficulty, String label)
      throws InterruptedException {
    if (problemClient.getProblemByIdAndDeleted0(id) == null) {
      return false;
    }
    Problem problem = problemClient.getProblemByIdAndDeleted0(id);
    problem.setLabel(label);
    problem.setDifficulty(difficulty.charAt(0));
    problem.setName(name);
    problemClient.updateProblem(problem);
    return true;
  }

  /**
   * @param str: * @return boolean
   * @author ZJ
   * @description TODO 判断字符串是否为数字
   * @date 2023/3/15 17:48
   */
  public static boolean isNumeric(String str) {
    for (int i = str.length(); --i >= 0; ) {
      int chr = str.charAt(i);
      if (chr < 48 || chr > 57) return false;
    }
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean addProblem(String id, String name, String label, String difficulty)
      throws InterruptedException {
    if (id.charAt(0) == '-' || id.charAt(0) == '0') { // 负数和0
      return false;
    }
    boolean flag = isNumeric(id);
    if (!flag) { // 不是数字
      return false;
    }
    if (problemClient.getProblemByIdAndDeleted0(id) != null) { // id已存在
      return false;
    }
    if (difficulty.length() > 1) { // 难度不匹配
      return false;
    }
    if (problemClient.getProblemByIdAndDeleted1(id) != null) {
      Problem problem = problemClient.getProblemByIdAndDeleted1(id);
      problem.setName(name);
      problem.setLabel(label);
      problem.setDifficulty(difficulty.charAt(0));
      problem.setKnowledgePointId("x");
      problem.setDeleted(0);
      problemClient.updateProblem(problem);
      return true;
    }
    Problem problem = new Problem();
    problem.setId(id);
    problem.setDeleted(0);
    problem.setName(name);
    problem.setLabel(label);
    problem.setDifficulty(difficulty.charAt(0));
    problem.setKnowledgePointId("x");
    problemClient.insertProblem(problem);
    return true;
  }

  @Override
  public PageInfo<?> teacherGetAccountList(int currentPage, int pageSize) {
    List<Student> students =
        studentDao.selectList(Wrappers.<Student>lambdaQuery().eq(Student::getDeleted, 0));
    List<Tutor> tutors = tutorDao.selectList(null);
    List<Object> objects = new ArrayList<>();
    for (Student student : students) {
      objects.add(student);
    }
    for (Tutor tutor : tutors) {
      objects.add(tutor);
    }
    return PageUtils.pageObject(objects, currentPage, pageSize);
  }

  @Override
  public PageInfo<?> teacherGetAccountById(String id, int currentPage, int pageSize) {
    List<Object> objects = new ArrayList<>();
    if (selectOneStudentByIdAndDeleted0(id) != null) {
      objects.add(selectOneStudentByIdAndDeleted0(id));
    }
    if (selectOneTutorById(id) != null) {
      objects.add(selectOneTutorById(id));
    }
    return PageUtils.pageObject(objects, currentPage, pageSize);
  }

  @Override
  public PageInfo<?> teacherGetAccountByName(String name, int currentPage, int pageSize) {
    List<Object> objects = new ArrayList<>();
    List<Student> students =
        studentDao.selectList(
            Wrappers.<Student>lambdaQuery()
                .eq(Student::getDeleted, 0)
                .and(i -> i.like(Student::getName, name)));
    if (!students.isEmpty()) {
      for (Student student : students) {
        objects.add(student);
      }
    }
    List<Tutor> tutors =
        tutorDao.selectList(Wrappers.<Tutor>lambdaQuery().like(Tutor::getName, name));
    if (!tutors.isEmpty()) {
      for (Tutor tutor : tutors) {
        objects.add(tutor);
      }
    }
    return PageUtils.pageObject(objects, currentPage, pageSize);
  }

  @Override
  public PageInfo<?> teacherGetAccountByType(String type, int currentPage, int pageSize) {
    if (type.equals("student")) {
      List<Student> students =
          studentDao.selectList(Wrappers.<Student>lambdaQuery().eq(Student::getDeleted, 0));
      return PageUtils.pageStudent(students, currentPage, pageSize);
    } else if (type.equals("tutor")) {
      List<Tutor> tutors = tutorDao.selectList(null);
      return PageUtils.pageTutor(tutors, currentPage, pageSize);
    }
    List<Object> objects = new ArrayList<>();
    List<Student> students =
        studentDao.selectList(Wrappers.<Student>lambdaQuery().eq(Student::getDeleted, 0));
    List<Tutor> tutors = tutorDao.selectList(null);
    for (Student student : students) {
      objects.add(student);
    }
    for (Tutor tutor : tutors) {
      objects.add(tutor);
    }
    return PageUtils.pageObject(objects, currentPage, pageSize);
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean teacherAddAccount(
      String type, String id, String name, String password, String email, String phone)
      throws InterruptedException {
    if (selectOneStudentByIdAndDeleted0(id) != null || selectOneTutorById(id) != null) {
      return false;
    }
    if (type.equals("student")) {
      if (selectOneStudentByIdAndDeleted1(id) != null) { // deleted
        Student student = selectOneStudentByIdAndDeleted1(id);
        student.setName(name);
        student.setPassword(password);
        student.setEmail(email);
        student.setPhone(phone);
        student.setDeleted(0);
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
      student.setClassId("未分配");
      studentDao.insert(student);
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
    if (studentDao.selectOne(
                Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0))
            == null
        && tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) == null) {
      return false;
    }
    if (studentDao.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, id)) != null) {
      Student student =
          studentDao.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, id));
      student.setDeleted(1);
      studentDao.updateById(student);
      return true;
    }
    tutorDao.delete(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean teacherBulkDeleteAccount(List<String> ids) throws InterruptedException {
    for (String id : ids)
      if (studentDao.selectOne(
              Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0))
          != null) {
        Student student =
            studentDao.selectOne(
                Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0));
        student.setDeleted(1);
        studentDao.updateById(student);
      } else if (tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) != null) {
        tutorDao.delete(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
      } else {
        return false;
      }
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean teacherResetPassword(String id) throws InterruptedException {
    if (studentDao.selectOne(
            Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0))
        != null) {
      Student student =
          studentDao.selectOne(
              Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0));
      student.setPassword("123456");
      studentDao.updateById(student);
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
  public Boolean teacherBulkResetPassword(List<String> ids) throws InterruptedException {
    for (String id : ids) {
      if (studentDao.selectOne(
              Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0))
          != null) {
        Student student =
            studentDao.selectOne(
                Wrappers.<Student>lambdaQuery().eq(Student::getId, id).eq(Student::getDeleted, 0));
        student.setPassword("123456");
        studentDao.updateById(student);
      } else if (tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id)) != null) {
        Tutor tutor = tutorDao.selectOne(Wrappers.<Tutor>lambdaQuery().eq(Tutor::getId, id));
        tutor.setPassword("123456");
        tutorDao.updateById(tutor);
      } else {
        return false;
      }
    }
    return true;
  }

  @Override
  public PageInfo<HashMap<String, String>> teacherGetReviewList(int currentPage, int pageSize) {
    List<HashMap<String, String>> objects = new ArrayList<>();
    List<CommentStudent> commentStudents = commentClient.getCommentList();
    for (CommentStudent commentStudent : commentStudents) {
      HashMap<String, String> map = new HashMap<>();
      String problemId = commentStudent.getProblemId();
      map.put("id", commentStudent.getPkCommentStudentId());
      map.put("dateTime", commentStudent.getDateTime());
      map.put("problemId", problemId);
      map.put("studentId", commentStudent.getStudentId());
      Problem problem = problemClient.getProblemByIdAndDeleted0(problemId);
      map.put("name", problem.getName());
      map.put("difficulty", String.valueOf(problem.getDifficulty()));
      map.put("chapter", problem.getKnowledgePointId());
      map.put("label", problem.getLabel());
      objects.add(map);
    }
    int total = objects.size();
    if (total > pageSize) {
      int toIndex = pageSize * currentPage;
      if (toIndex > total) toIndex = total;
      objects = objects.subList(pageSize * (currentPage - 1), toIndex);
    }
    com.github.pagehelper.Page<HashMap<String, String>> page = new Page<>(currentPage, pageSize);
    page.addAll(objects);
    page.setPages((total + pageSize - 1) / pageSize);
    page.setTotal(total);

    PageInfo<HashMap<String, String>> pageInfo = new PageInfo<>(page);
    return pageInfo;
  }

  @Override
  public PageInfo<HashMap<String, String>> teacherGetReviewByProblemId(
      String problemId, int currentPage, int pageSize) {

    List<CommentStudent> commentStudents = commentClient.getCommentByProblemId(problemId);
    List<HashMap<String, String>> list = new ArrayList<>();
    for (CommentStudent commentStudent : commentStudents) {
      HashMap<String, String> map = new HashMap<>();
      map.put("id", commentStudent.getPkCommentStudentId());
      map.put("problemId", problemId);
      map.put("name", problemClient.getProblemByIdAndDeleted0(problemId).getName());
      map.put("studentId", commentStudent.getStudentId());
      map.put(
          "difficulty",
          String.valueOf(problemClient.getProblemByIdAndDeleted0(problemId).getDifficulty()));
      map.put("label", problemClient.getProblemByIdAndDeleted0(problemId).getLabel());
      map.put("dateTime", commentStudent.getDateTime());
      list.add(map);
    }
    int total = list.size();
    if (total > pageSize) {
      int toIndex = pageSize * currentPage;
      if (toIndex > total) {
        toIndex = total;
      }
      list = list.subList(pageSize * (currentPage - 1), toIndex);
    }
    com.github.pagehelper.Page<HashMap<String, String>> page = new Page<>(currentPage, pageSize);
    page.addAll(list);
    page.setPages((total + pageSize - 1) / pageSize);
    page.setTotal(total);

    PageInfo<HashMap<String, String>> pageInfo = new PageInfo<>(page);
    return pageInfo;
  }

  @Override
  public List<HashMap<String, String>> teacherViewDetailReview(String id) {
    CommentStudent commentStudent = commentClient.getCommentByStudentId(id);
    HashMap<String, String> map = new HashMap<>();
    map.put("description", commentStudent.getDescription());
    List<HashMap<String, String>> list = new ArrayList<>();
    list.add(map);
    return list;
  }

  @Override
  public List<Integer> gradeDistribution() {
    List<ModelOutputScore> modelOutputScores = modelClient.getModelOutputScoreList();
    Integer[] i = new Integer[10];
    for (ModelOutputScore modelOutputScore : modelOutputScores)
      if (modelOutputScore.getExamScore().compareTo(new BigDecimal("0")) == 1
          && modelOutputScore.getExamScore().compareTo(new BigDecimal("10")) == -1) i[0]++;
      else if (modelOutputScore.getExamScore().compareTo(new BigDecimal("10")) == 1
          && modelOutputScore.getExamScore().compareTo(new BigDecimal("20")) == -1) i[1]++;
      else if (modelOutputScore.getExamScore().compareTo(new BigDecimal("20")) == 1
          && modelOutputScore.getExamScore().compareTo(new BigDecimal("30")) == -1) i[2]++;
      else if (modelOutputScore.getExamScore().compareTo(new BigDecimal("30")) == 1
          && modelOutputScore.getExamScore().compareTo(new BigDecimal("40")) == -1) i[3]++;
      else if (modelOutputScore.getExamScore().compareTo(new BigDecimal("40")) == 1
          && modelOutputScore.getExamScore().compareTo(new BigDecimal("50")) == -1) i[4]++;
      else if (modelOutputScore.getExamScore().compareTo(new BigDecimal("50")) == 1
          && modelOutputScore.getExamScore().compareTo(new BigDecimal("60")) == -1) i[5]++;
      else if (modelOutputScore.getExamScore().compareTo(new BigDecimal("60")) == 1
          && modelOutputScore.getExamScore().compareTo(new BigDecimal("70")) == -1) i[6]++;
      else if (modelOutputScore.getExamScore().compareTo(new BigDecimal("70")) == 1
          && modelOutputScore.getExamScore().compareTo(new BigDecimal("80")) == -1) i[7]++;
      else if (modelOutputScore.getExamScore().compareTo(new BigDecimal("80")) == 1
          && modelOutputScore.getExamScore().compareTo(new BigDecimal("90")) == -1) i[8]++;
      else i[9]++;

    List<Integer> list = new ArrayList<>();
    for (Integer index : i) {
      list.add(index);
    }

    return list;
  }

  @Override
  public List<Integer> gradeDistributionByClass(String className) {
    Integer[] i = new Integer[10];
    for (int index = 0; index < 10; ++index) {
      i[index] = 0;
    }

    List<Student> students = new ArrayList<>();
    if (className.equals("通信一班")) {
      students = selectStudentByClassName("080301");
    } else if (className.equals("通信二班")) {
      students = selectStudentByClassName("080302");
    } else if (className.equals("通信三班")) {
      students = selectStudentByClassName("080303");
    } else if (className.equals("通信四班")) {
      students = selectStudentByClassName("080304");
    }

    for (Student student : students) {
      if (modelClient.getModelScoreByStudentId(student.getId()) == null) {
        continue;
      }
      BigDecimal examScore =
          modelClient.getModelScoreByStudentId(student.getId()).getExamScore(); // student学生对应的成绩
      System.out.println(examScore);
      if (examScore.compareTo(new BigDecimal("0")) > -1
          && examScore.compareTo(new BigDecimal("10")) == -1) i[0]++;
      else if (examScore.compareTo(new BigDecimal("10")) > -1
          && examScore.compareTo(new BigDecimal("20")) == -1) i[1]++;
      else if (examScore.compareTo(new BigDecimal("20")) > -1
          && examScore.compareTo(new BigDecimal("30")) == -1) i[2]++;
      else if (examScore.compareTo(new BigDecimal("30")) > -1
          && examScore.compareTo(new BigDecimal("40")) == -1) i[3]++;
      else if (examScore.compareTo(new BigDecimal("40")) > -1
          && examScore.compareTo(new BigDecimal("50")) == -1) i[4]++;
      else if (examScore.compareTo(new BigDecimal("50")) > -1
          && examScore.compareTo(new BigDecimal("60")) == -1) i[5]++;
      else if (examScore.compareTo(new BigDecimal("60")) > -1
          && examScore.compareTo(new BigDecimal("70")) == -1) i[6]++;
      else if (examScore.compareTo(new BigDecimal("70")) > -1
          && examScore.compareTo(new BigDecimal("80")) == -1) i[7]++;
      else if (examScore.compareTo(new BigDecimal("80")) > -1
          && examScore.compareTo(new BigDecimal("90")) == -1) i[8]++;
      else i[9]++;
      System.out.println(examScore);
    }

    List<Integer> list = new ArrayList<>();
    for (Integer index : i) {
      list.add(index);
    }
    return list;
  }

  @Override
  public List<BigDecimal> getKnowledgePointGrade(String id) {
    List<ModelOutputKnowledge> modelOutputKnowledges =
        modelClient.getModelKnowledgeListByStudentId(id);
    List<HashMap<String, BigDecimal>> list = new ArrayList<>();
    int[] index = new int[11];
    int i = 0;
    for (ModelOutputKnowledge modelOutputKnowledge : modelOutputKnowledges) {
      HashMap<String, BigDecimal> map = new HashMap<>();
      if (modelOutputKnowledge.getKnowledgePointId().equals("1")) {
        map.put(
            "继承",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[10] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("2")) {
        map.put(
            "构造函数",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[9] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("3")) {
        map.put(
            "类与对象",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[8] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("4")) {
        map.put(
            "结构体",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[7] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("5")) {
        map.put(
            "指针",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[6] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("6")) {
        map.put(
            "函数",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[5] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("7")) {
        map.put(
            "字符串",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[4] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("8")) {
        map.put(
            "数组",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[3] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("9")) {
        map.put(
            "循环",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[2] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("10")) {
        map.put(
            "控制结构",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        list.add(map);
        index[1] = i;
      } else if (modelOutputKnowledge.getKnowledgePointId().equals("11")) {
        map.put(
            "语言基础",
            sqrt(modelOutputKnowledge.getForecast().multiply(new BigDecimal(100)), 2)
                .setScale(0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("10")));
        index[0] = i;
        list.add(map);
      }
      ++i;
    }

    return ITeacherServiceImpl.customSortList(list, index);
  }

  @Override
  public List<HashMap<String, String>> displayPersonalInformation(String id) {
    HashMap<String, String> map = new HashMap<>();
    Teacher teacher =
        teacherDao.selectOne(
            Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0));
    map.put("id", id);
    map.put("name", teacher.getName());
    map.put("email", teacher.getEmail());
    map.put("phone", teacher.getPhone());
    List<HashMap<String, String>> list = new ArrayList<>();
    list.add(map);
    return list;
  }

  @Override
  public Boolean isTeacherExist(String id) {
    if (teacherDao.selectOne(
            Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0))
        != null) {
      return true;
    }
    return false;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean modifyPhoneAndEmailById(String id, String phone, String email)
      throws InterruptedException {
    Boolean flag = isTeacherExist(id);
    if (!flag) {
      return false;
    }
    Teacher teacher =
        teacherDao.selectOne(
            Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0));
    teacher.setPhone(phone);
    teacher.setEmail(email);
    teacherDao.updateById(teacher);
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean modifyPasswordById(String id, String password) throws InterruptedException {
    Boolean flag = isTeacherExist(id);
    if (!flag) {
      return false;
    }
    Teacher teacher =
        teacherDao.selectOne(
            Wrappers.<Teacher>lambdaQuery().eq(Teacher::getId, id).eq(Teacher::getDeleted, 0));
    teacher.setPassword(password);
    teacherDao.updateById(teacher);
    return true;
  }
}
