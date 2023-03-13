package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.dao.FavoriteDao;
import com.zhiyixingnan.domain.Favorite;

import java.util.List;

public interface IFavoriteService extends IService<Favorite> {

  public List<Favorite> getProblemsByStudentId(String id);
}
