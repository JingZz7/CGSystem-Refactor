package com.zhiyixingnan.service.client;

import com.zhiyixingnan.domain.ModelOutputKnowledge;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("model-service")
public interface ModelOutputKnowledgeClient {

  @RequestMapping("/model/getModelOutputKnowledgeById/{id}")
  public List<ModelOutputKnowledge> getModelOutputKnowledgeById(@PathVariable("id") String id);
}
