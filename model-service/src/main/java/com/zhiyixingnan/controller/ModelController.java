package com.zhiyixingnan.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zhiyixingnan.domain.ModelOutputKnowledge;
import com.zhiyixingnan.service.IModelOutputKnowledgeService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SentinelResource(value = "test")
@RequestMapping("/model")
public class ModelController {

  private final IModelOutputKnowledgeService iModelOutputKnowledgeService;

  @Lazy
  public ModelController(IModelOutputKnowledgeService iModelOutputKnowledgeService) {
    this.iModelOutputKnowledgeService = iModelOutputKnowledgeService;
  }

  @RequestMapping(value = "/getModelOutputKnowledgeById/{id}", method = RequestMethod.GET)
  public List<ModelOutputKnowledge> getModelOutputKnowledgeById(@PathVariable("id") String id) {
    return iModelOutputKnowledgeService.getModelOutputKnowledgeById(id);
  }
}
