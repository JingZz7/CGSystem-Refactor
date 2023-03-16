package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.domain.Student;

public interface IProblemStudentService extends IService<Problem> {

  /**
   * @param id:  * @return Problem
   * @author ZJ
   * @description TODO 通过id查找问题，Deleted必须为0
   * @date 2023/3/14 11:25
   */
  public Problem getProblemByIdAndDeleted0(String id);

  /**
   * @param id:  * @return Problem
   * @author ZJ
   * @description TODO 通过id查找问题，Deleted必须为1
   * @date 2023/3/15 17:49
   */
  public Problem getProblemByIdAndDeleted1(String id);

  /**
   * @param currentPage:
   * @param pageSize: a * @return PaeInfo<Problem>
   * @author ZJ
   * @description TODO [学生]获取题目列表
   * @date 2023/3/13 10:28
   */
  public PageInfo<Problem> getProblemList(int currentPage, int pageSize);

  /**
   * @param id:
   * @param currentPage:
   * @param pageSize: * @return Object
   * @author ZJ
   * @description TODO [学生]获取题目列表(刷题推荐)
   * @date 2023/3/13 16:42
   */
  public Object getProblemsList(String id, int currentPage, int pageSize);

  /**
   * @param studentId:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<Problem>
   * @author ZJ
   * @description TODO [学生]获取题目列表(收藏夹)
   * @date 2023/3/13 17:09
   */
  public PageInfo<Problem> getFavoriteProblemList(String studentId, int currentPage, int pageSize);

  /**
   * @param problemId:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<?>
   * @author ZJ
   * @description TODO [学生]根据id查询问题(刷题推荐)
   * @date 2023/3/14 10:12
   */
  public PageInfo<?> getProblemById(String problemId, int currentPage, int pageSize);

  /**
   * @param id:
   * @param difficulty:
   * @param currentPage:
   * @param pageSize: a * @retur Object
   * @author ZJ
   * @description TODO [学生]根据难度查询(刷题推荐)
   * @date 2023/3/14 10:17
   */
  public Object getProblemsByDifficulty(
      String id, String difficulty, int currentPage, int pageSize);

  /**
   * @param studentId:
   * @param problemId:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<Problem>
   * @author ZJ
   * @description TODO [学生]根据id查询问题(收藏夹)
   * @date 2023/3/14 10:33
   */
  public PageInfo<Problem> getProblemInFavoriteById(
      String studentId, String problemId, int currentPage, int pageSize);

  /**
   * @param studentId:
   * @param problemName:
   * @param currentPage:
   * @param pageSize: a * @retur PageInfo<Problem>
   * @author ZJ
   * @description TODO [学生]根据名称查询问题(收藏夹)
   * @date 2023/3/14 10:39
   */
  public PageInfo<Problem> getProblemByName(
      String studentId, String problemName, int currentPage, int pageSize);
}
