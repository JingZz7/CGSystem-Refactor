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
}
