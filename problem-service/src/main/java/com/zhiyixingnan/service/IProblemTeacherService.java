package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.domain.Problem;

public interface IProblemTeacherService extends IService<Problem> {

  /**
   * @param problemId:
   * @param currentPage:
   * @param pageSize: a * @return PageIno<?>
   * @author ZJ
   * @description TODO [教师]根据id查询问题(题库管理)
   * @date 2023/3/14 14:40
   */
  public PageInfo<?> getProblemById(String problemId, int currentPage, int pageSize);

  /**
   * @param problemName:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<?>
   * @author ZJ
   * @description TODO [教师]根据名称查询问题(题库管理)
   * @date 2023/3/14 14:46
   */
  public PageInfo<?> getProblemListByName(String problemName, int currentPage, int pageSize);

  /**
   * @param difficulty:
   * @param currentPage:
   * @param pageSize:  * @return PageInfo<Problem>
   * @author ZJ
   * @description TODO [教师]根据难度查询(题库管理)
   * @date 2023/3/14 14:49
   */
  public PageInfo<Problem> getListByDifficulty(String difficulty, int currentPage, int pageSize);
}
