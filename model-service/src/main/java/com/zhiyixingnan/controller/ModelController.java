package com.zhiyixingnan.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhiyixingnan.domain.ModelOutputKnowledge;
import com.zhiyixingnan.domain.ModelOutputScore;
import com.zhiyixingnan.service.IModelOutputKnowledgeService;
import com.zhiyixingnan.service.IModelOutputScoreService;
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
  private final IModelOutputScoreService iModelOutputScoreService;

  @Lazy
  public ModelController(
      IModelOutputKnowledgeService iModelOutputKnowledgeService,
      IModelOutputScoreService iModelOutputScoreService) {
    this.iModelOutputKnowledgeService = iModelOutputKnowledgeService;
    this.iModelOutputScoreService = iModelOutputScoreService;
  }

  @RequestMapping(value = "/getModelOutputKnowledgeById/{id}", method = RequestMethod.GET)
  public List<ModelOutputKnowledge> getModelOutputKnowledgeById(@PathVariable("id") String id) {
    return iModelOutputKnowledgeService.getModelOutputKnowledgeById(id);
  }

  @RequestMapping(value = "/getModelKnowledgeListByStudentId/{id}", method = RequestMethod.GET)
  public List<ModelOutputKnowledge> getModelKnowledgeListByStudentId(
      @PathVariable("id") String id) {
    return iModelOutputKnowledgeService.list(
        Wrappers.<ModelOutputKnowledge>lambdaQuery().eq(ModelOutputKnowledge::getStudentId, id));
  }

  @RequestMapping(value = "/getModelOutputScoreList", method = RequestMethod.GET)
  public List<ModelOutputScore> getModelOutputScoreList() {
    return iModelOutputScoreService.list(null);
  }

  @RequestMapping(value = "/getModelScoreByStudentId/{id}", method = RequestMethod.GET)
  public ModelOutputScore getModelScoreByStudentId(@PathVariable("id") String id) {
    return iModelOutputScoreService.getOne(
        Wrappers.<ModelOutputScore>lambdaQuery().eq(ModelOutputScore::getStudentId, id));
  }
}
