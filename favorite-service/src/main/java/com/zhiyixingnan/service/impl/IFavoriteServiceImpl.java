package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.FavoriteDao;
import com.zhiyixingnan.domain.Favorite;
import com.zhiyixingnan.service.IFavoriteService;
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
}
