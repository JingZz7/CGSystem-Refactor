package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.ModelOutputKnowledge;

import java.util.List;

public interface IModelOutputKnowledgeService extends IService<ModelOutputKnowledge> {

  public List<ModelOutputKnowledge> getModelOutputKnowledgeById(String id);
}
