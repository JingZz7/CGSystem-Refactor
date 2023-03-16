package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.ModelOutputKnowledgeDao;
import com.zhiyixingnan.dao.ModelOutputScoreDao;
import com.zhiyixingnan.domain.ModelOutputKnowledge;
import com.zhiyixingnan.domain.ModelOutputScore;
import com.zhiyixingnan.service.IModelOutputKnowledgeService;
import com.zhiyixingnan.service.IModelOutputScoreService;
import org.springframework.stereotype.Service;

@Service
public class IModelOutputScoreServiceImpl extends ServiceImpl<ModelOutputScoreDao, ModelOutputScore>
    implements IModelOutputScoreService {}
