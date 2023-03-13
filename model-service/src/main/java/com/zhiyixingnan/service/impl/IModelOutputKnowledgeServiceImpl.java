package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.ModelOutputKnowledgeDao;
import com.zhiyixingnan.domain.ModelOutputKnowledge;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.service.IModelOutputKnowledgeService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IModelOutputKnowledgeServiceImpl
    extends ServiceImpl<ModelOutputKnowledgeDao, ModelOutputKnowledge>
    implements IModelOutputKnowledgeService {

  private final ModelOutputKnowledgeDao modelOutputKnowledgeDao;

  @Lazy
  public IModelOutputKnowledgeServiceImpl(ModelOutputKnowledgeDao modelOutputKnowledgeDao) {
    this.modelOutputKnowledgeDao = modelOutputKnowledgeDao;
  }

  @Override
  public List<ModelOutputKnowledge> getModelOutputKnowledgeById(String id) {
    return new ArrayList<>(
        modelOutputKnowledgeDao.selectList(
            Wrappers.<ModelOutputKnowledge>lambdaQuery()
                .eq(ModelOutputKnowledge::getStudentId, id)));
  }
}
