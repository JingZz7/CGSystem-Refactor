package com.zhiyixingnan.service.client;

import com.zhiyixingnan.domain.Classes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("class-service")
public interface ClassClient {

  @RequestMapping(value = "/class/getClassByClassId/{id}")
  public Classes getClassByClassId(@PathVariable("id") String id);

  @RequestMapping(value = "/class/getClassByClassName/{name}")
  public Classes getClassByClassName(@PathVariable("name") String name);
}
