package com.zhiyixingnan.service.client;

import com.zhiyixingnan.domain.Problem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("problem-service")
public interface ProblemClient {

  @RequestMapping("/query/getProblemByIdAndDeleted0/{id}")
  public Problem getProblemByIdAndDeleted0(@PathVariable("id") String id);

  @RequestMapping("/management_query/updateProblem")
  public Boolean updateProblem(@RequestBody Problem problem);
}
