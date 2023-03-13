package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.Student;
import java.util.List;

public interface IStudentService extends IService<Student> {
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
   * @param id:  * @return String
   * @author ZJ
   * @description TODO 获取随机验证码(忘记密码)
   * @date 2023/3/13 10:02
   */
  public String getCaptchaById(String id);

  /**
   * @param id:
   * @param password:  * @return Boolean
   * @author ZJ
   * @description TODO 忘记密码
   * @date 2023/3/13 10:09
   */
  public Boolean forgotPassword(String id, String password) throws InterruptedException;
}
