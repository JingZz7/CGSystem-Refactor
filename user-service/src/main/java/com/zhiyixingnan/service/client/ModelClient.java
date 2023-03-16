package com.zhiyixingnan.service.client;

import com.zhiyixingnan.domain.ModelOutputKnowledge;
import com.zhiyixingnan.domain.ModelOutputScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("model-service")
public interface ModelClient {

  @RequestMapping(value = "/model/getModelKnowledgeListByStudentId/{id}")
  public List<ModelOutputKnowledge> getModelKnowledgeListByStudentId(@PathVariable("id") String id);

  @RequestMapping(value = "/model/getModelOutputScoreList")
  public List<ModelOutputScore> getModelOutputScoreList();

  @RequestMapping(value = "/model/getModelScoreByStudentId/{id}")
  public ModelOutputScore getModelScoreByStudentId(@PathVariable("id") String id);
}
