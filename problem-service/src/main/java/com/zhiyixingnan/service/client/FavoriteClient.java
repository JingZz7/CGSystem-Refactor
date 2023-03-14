package com.zhiyixingnan.service.client;

import com.zhiyixingnan.domain.Favorite;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@FeignClient("favorite-service")
public interface FavoriteClient {

  @RequestMapping("/favorite/getProblemsByStudentId/{id}")
  public List<Favorite> getProblemsByStudentId(@PathVariable("id") String id);

  @RequestMapping(value = "/getProblemInFavoriteById/{studentId}/{problemId}")
  public Favorite getProblemInFavoriteById(
      @PathVariable("studentId") String studentId, @PathVariable("problemId") String problemId);
}
