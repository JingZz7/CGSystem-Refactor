package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.Student;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface IStudentService extends IService<Student> {
  /**
   * @param id: * @return Student
   * @author ZJ
   * @description TODO 通过id查找单个学生，Deleted必须为0
   * @date 2023/3/14 11:19
   */
  public Student selectOneStudentByIdAndDeleted(String id);

  /**
   * @param id:
   * @param password: * @return Boolean
   * @author ZJ
   * @description TODO 学生是否存在
   * @date 2023/3/12 21:36
   */
  public Boolean isExistStudent(String id, String password);

  /**
   * @param id: * @return String
   * @author ZJ
   * @description TODO 获取随机验证码(忘记密码)
   * @date 2023/3/13 10:02
   */
  public String getCaptchaById(String id);

  /**
   * @param id:
   * @param password: * @return Boolean
   * @author ZJ
   * @description TODO 忘记密码
   * @date 2023/3/13 10:09
   */
  public Boolean forgotPassword(String id, String password) throws InterruptedException;

  /**
   * @param id: * @return BigDecimal
   * @author ZJ
   * @description TODO [学生]获取期末成绩预测
   * @date 2023/3/16 19:49
   */
  public BigDecimal getFinalForecast(String id);

  /**
   * @param id: * @return List<HashMap<String,String>>
   * @author ZJ
   * @description TODO [学生]展示个人信息(个人中心)
   * @date 2023/3/16 19:53
   */
  public List<HashMap<String, String>> displayPersonalInformation(String id);

  /**
   * @param id: * @return Boolean
   * @author ZJ
   * @description TODO [学生]是否存在
   * @date 2023/3/16 20:00
   */
  public Boolean isStudentExist(String id);

  /**
   * @param id:
   * @param phone:
   * @param email: * @return Boolean
   * @author ZJ
   * @description TODO [学生]修改电话和邮箱(个人中心)
   * @date 2023/3/16 20:04
   */
  public Boolean modifyPhoneAndEmailById(String id, String phone, String email)
      throws InterruptedException;

  /**
   * @param id:
   * @param password: * @return Boolean
   * @author ZJ
   * @description TODO [学生]修改密码(个人中心)
   * @date 2023/3/16 20:10
   */
  public Boolean modifyPasswordById(String id, String password) throws InterruptedException;
}
