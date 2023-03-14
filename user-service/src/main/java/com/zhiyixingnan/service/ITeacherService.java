package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.Teacher;

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
   * @param ids:  * @return Boolean
   * @author ZJ
   * @description TODO [教师]批量删除题目(题库管理)
   * @date 2023/3/14 15:28
   */
  public Boolean bulkDeleteProblem(List<String> ids);
}
