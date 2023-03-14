package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.dao.FavoriteDao;
import com.zhiyixingnan.domain.Favorite;

import java.util.List;

public interface IFavoriteService extends IService<Favorite> {

  public List<Favorite> getProblemsByStudentId(String id);

  public Favorite getProblemInFavoriteById(String studentId, String problemId);

  /**
   * @param studentId:
   * @param problemId:  * @return Boolean
   * @author ZJ
   * @description TODO [学生]收藏题目
   * @date 2023/3/14 10:44
   */
  public Boolean collectProblem(String studentId, String problemId) throws InterruptedException;

  /**
   * @param studentId:
   * @param problemId:  * @return Boolean
   * @author ZJ
   * @description TODO [学生]取消收藏
   * @date 2023/3/14 10:51
   */
  public Boolean cancelCollectedProblem(String studentId, String problemId) throws InterruptedException;

  /**
   * @param studentId:
   * @param ids:  * @return Boolean
   * @author ZJ
   * @description TODO [学生]批量删除收藏夹的题目
   * @date 2023/3/14 10:54
   */
  public Boolean bulkDeleteCollectedProblem(String studentId, List<Integer> ids) throws InterruptedException;
}
