package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.domain.Student;

public interface IProblemService extends IService<Problem> {

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
   * @param pageSize:  * @return PageInfo<Problem>
   * @author ZJ
   * @description TODO [学生]获取题目列表(收藏夹)
   * @date 2023/3/13 17:09
   */
  public PageInfo<Problem> getFavoriteProblemList(String studentId, int currentPage, int pageSize);
}
