package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyixingnan.dao.ClassDao;
import com.zhiyixingnan.domain.Administrator;
import com.zhiyixingnan.domain.Classes;
import com.zhiyixingnan.service.IClassService;
import org.springframework.stereotype.Service;

@Service
public class IClassServiceImpl extends ServiceImpl<ClassDao, Classes> implements IClassService {}
