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
}
