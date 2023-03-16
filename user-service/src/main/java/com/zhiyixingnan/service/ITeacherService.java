package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.domain.Teacher;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface ITeacherService extends IService<Teacher> {

  /**
   * @param id:
   * @param password: * @return Boolean
   * @author ZJ
   * @description TODO 教师是否存在
   * @date 2023/3/12 21:36
   */
  public Boolean isExistTeacher(String id, String password);

  /**
   * @param problemId: a * return Boolean
   * @author ZJ
   * @description TODO [教师]删除题目(题库管理)
   * @date 2023/3/14 14:52
   */
  public Boolean deleteProblem(String problemId);

  /**
   * @param ids: * @return Boolean
   * @author ZJ
   * @description TODO [教师]批量删除题目(题库管理)
   * @date 2023/3/14 15:28
   */
  public Boolean bulkDeleteProblem(List<String> ids);

  /**
   * @param id:
   * @param name:
   * @param difficulty:
   * @param label: * @return Boolean
   * @author ZJ
   * @description TODO [教师]编辑题目(题库管理)
   * @date 2023/3/15 17:43
   */
  public Boolean editProblem(String id, String name, String difficulty, String label)
      throws InterruptedException;

  /**
   * @param id:
   * @param name:
   * @param label:
   * @param difficulty: * @return Boolean
   * @author ZJ
   * @description TODO [教师]添加题目
   * @date 2023/3/15 17:46
   */
  public Boolean addProblem(String id, String name, String label, String difficulty)
      throws InterruptedException;

  /**
   * @param currentPage:
   * @param pageSize: * @return PageInfo<?>
   * @author ZJ
   * @description TODO [教师]获取账户列表(账户管理)
   * @date 2023/3/15 17:55
   */
  public PageInfo<?> teacherGetAccountList(int currentPage, int pageSize);

  /**
   * @param id:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<?>
   * @author ZJ
   * @description TODO [教师]根据工号查询(账户管理)
   * @date 2023/3/15 17:59
   */
  public PageInfo<?> teacherGetAccountById(String id, int currentPage, int pageSize);

  /**
   * @param name:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<?>
   * @author ZJ
   * @description TODO [教师]根据姓名查询(账户管理)
   * @date 2023/3/15 18:03
   */
  public PageInfo<?> teacherGetAccountByName(String name, int currentPage, int pageSize);

  /**
   * @param type:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<?>
   * @author ZJ
   * @description TODO [教师]根据类型查询(账户管理)
   * @date 2023/3/15 18:05
   */
  public PageInfo<?> teacherGetAccountByType(String type, int currentPage, int pageSize);

  /**
   * @param type:
   * @param id:
   * @param name:
   * @param password:
   * @param email:
   * @param phone: a * @return Bolean
   * @author ZJ
   * @description TODO [教师]添加账户(账户管理)
   * @date 2023/3/15 18:07
   */
  public Boolean teacherAddAccount(
      String type, String id, String name, String password, String email, String phone)
      throws InterruptedException;

  /**
   * @param id: * @return Boolean
   * @author ZJ
   * @description TODO [教师]删除账户(账户管理)
   * @date 2023/3/15 18:10
   */
  public Boolean deleteAccount(String id) throws InterruptedException;

  /**
   * @param ids: * @return Boolean
   * @author ZJ
   * @description TODO [教师]批量删除账户(账户管理)
   * @date 2023/3/15 18:13
   */
  public Boolean teacherBulkDeleteAccount(List<String> ids) throws InterruptedException;

  /**
   * @param id: * @return Boolean
   * @author ZJ
   * @description TODO [教师]重置密码(账户管理)
   * @date 2023/3/15 18:15
   */
  public Boolean teacherResetPassword(String id) throws InterruptedException;

  /**
   * @param ids: * @return Boolean
   * @author ZJ
   * @description TODO [教师]批量重置密码(账户管理)
   * @date 2023/3/15 18:17
   */
  public Boolean teacherBulkResetPassword(List<String> ids) throws InterruptedException;

  /**
   * @param currentPage:
   * @param pageSize: * @return PageInfo<HashMap<String,String>>
   * @author ZJ
   * @description TODO [教师]获取评论列表(查看评论)
   * @date 2023/3/15 18:20
   */
  public PageInfo<HashMap<String, String>> teacherGetReviewList(int currentPage, int pageSize);

  /**
   * @param problemId:
   * @param currentPage:
   * @param pageSize: a * @retrn PageInfo<HashMap<String,String>>
   * @author ZJ
   * @description TODO [教师]根据问题id搜索评论(查看评论)
   * @date 2023/3/15 18:27
   */
  public PageInfo<HashMap<String, String>> teacherGetReviewByProblemId(
      String problemId, int currentPage, int pageSize);

  /**
   * @param id: * @return List<HashMap<String,String>>
   * @author ZJ
   * @description TODO [教师]查看具体评论信息(查看评论)
   * @date 2023/3/16 14:00
   */
  public List<HashMap<String, String>> teacherViewDetailReview(String id);

  /**
   * @param : * @return List<Integer>
   * @author ZJ
   * @description TODO [教师]获取成绩分布
   * @date 2023/3/16 14:45
   */
  public List<Integer> gradeDistribution();

  /**
   * @param className: * @return List<Integer>
   * @author ZJ
   * @description TODO [教师]根据班级获取成绩分布
   * @date 2023/3/16 15:40
   */
  public List<Integer> gradeDistributionByClass(String className);

  /**
   * @param id: * @return List<BigDecimal>
   * @author ZJ
   * @description TODO [教师]获取学生知识点成绩扇形图
   * @date 2023/3/16 15:46
   */
  public List<BigDecimal> getKnowledgePointGrade(String id);

  /**
   * @param id: a * @rturn List<HashMap<String,String>>
   * @author ZJ
   * @description TODO [教师]展示个人信息(个人中心)
   * @date 2023/3/16 19:56
   */
  public List<HashMap<String, String>> displayPersonalInformation(String id);

  /**
   * @param id: * @return Boolean
   * @author ZJ
   * @description TODO [教师]是否存在
   * @date 2023/3/16 20:00
   */
  public Boolean isTeacherExist(String id);

  /**
   * @param id:
   * @param phone:
   * @param email: * @return Boolean
   * @author ZJ
   * @description TODO [教师]修改电话和邮箱(个人中心)
   * @date 2023/3/16 20:04
   */
  public Boolean modifyPhoneAndEmailById(String id, String phone, String email) throws InterruptedException;

  /**
   * @param id:
   * @param password:  * @return Boolean
   * @author ZJ
   * @description TODO [教师]修改密码(个人中心)
   * @date 2023/3/16 20:11
   */
  public Boolean modifyPasswordById(String id, String password) throws InterruptedException;
}
