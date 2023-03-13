package com.zhiyixingnan.controller;

import com.zhiyixingnan.domain.Favorite;
import com.zhiyixingnan.service.IFavoriteService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

  private final IFavoriteService iFavoriteService;

  @Lazy
  public FavoriteController(IFavoriteService iFavoriteService) {
    this.iFavoriteService = iFavoriteService;
  }

  @RequestMapping(value = "/getProblemsByStudentId/{id}", method = RequestMethod.GET)
  public List<Favorite> getProblemsByStudentId(@PathVariable("id") String id) {
    return iFavoriteService.getProblemsByStudentId(id);
  }
}
