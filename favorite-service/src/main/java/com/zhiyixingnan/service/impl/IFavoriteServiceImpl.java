package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.FavoriteDao;
import com.zhiyixingnan.domain.Favorite;
import com.zhiyixingnan.service.IFavoriteService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class IFavoriteServiceImpl extends ServiceImpl<FavoriteDao, Favorite>
    implements IFavoriteService {

  private final FavoriteDao favoriteDao;

  @Lazy
  public IFavoriteServiceImpl(FavoriteDao favoriteDao) {
    this.favoriteDao = favoriteDao;
  }

  @Override
  public List<Favorite> getProblemsByStudentId(String id) {
    return new ArrayList<Favorite>(
        favoriteDao.selectList(Wrappers.<Favorite>lambdaQuery().eq(Favorite::getStudentId, id)));
  }

  @Override
  public Favorite getProblemInFavoriteById(String studentId, String problemId) {
    return favoriteDao.selectOne(
        Wrappers.<Favorite>lambdaQuery()
            .eq(Favorite::getStudentId, studentId)
            .eq(Favorite::getProblemId, problemId));
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean collectProblem(String studentId, String problemId) throws InterruptedException {
    if (favoriteDao.selectOne(
            Wrappers.<Favorite>lambdaQuery()
                .eq(Favorite::getStudentId, studentId)
                .eq(Favorite::getProblemId, problemId))
        != null) {
      return false;
    }
    favoriteDao.insert(new Favorite(studentId, problemId));
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean cancelCollectedProblem(String studentId, String problemId)
      throws InterruptedException {
    if (favoriteDao.selectOne(
            Wrappers.<Favorite>lambdaQuery()
                .eq(Favorite::getStudentId, studentId)
                .eq(Favorite::getProblemId, problemId))
        == null) return false;
    favoriteDao.delete(
        Wrappers.<Favorite>lambdaQuery()
            .eq(Favorite::getStudentId, studentId)
            .eq(Favorite::getProblemId, problemId));
    return true;
  }

  @GlobalTransactional(rollbackFor = Exception.class)
  @Override
  public Boolean bulkDeleteCollectedProblem(String studentId, List<Integer> ids)
      throws InterruptedException {
    if (studentId == null || ids == null) {
      return false;
    }

    for (Integer id : ids) {
      favoriteDao.delete(
          Wrappers.<Favorite>lambdaQuery()
              .eq(Favorite::getStudentId, studentId)
              .eq(Favorite::getProblemId, id));
    }
    return true;
  }
}
