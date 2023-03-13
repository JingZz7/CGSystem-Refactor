package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.dao.ProblemDao;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.service.IProblemService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class IProblemServiceImpl extends ServiceImpl<ProblemDao, Problem>
    implements IProblemService {

  private final ProblemDao problemDao;

  @Lazy
  public IProblemServiceImpl(ProblemDao problemDao) {
    this.problemDao = problemDao;
  }

  @Override
  public PageInfo<Problem> getProblemList(int currentPage, int pageSize) {
    PageHelper.startPage(currentPage, pageSize);
    List<Problem> problems =
        problemDao.selectList(new LambdaQueryWrapper<Problem>().eq(Problem::getDeleted, 0));
    PageInfo<Problem> page = new PageInfo<>(problems);
    int pageStart = currentPage == 1 ? 0 : (currentPage - 1) * pageSize;
    int pageEnd =
        problems.size() < pageSize * currentPage ? problems.size() : pageSize * currentPage;
    List<Problem> pageResult = new LinkedList<>();
    if (problems.size() > pageStart) pageResult = problems.subList(pageStart, pageEnd);
    else {
      int i = problems.size() / pageSize;
      pageResult = problems.subList(i * pageSize, pageEnd);
    }
    PageInfo pageInfo = new PageInfo(pageResult);
    BeanUtils.copyProperties(page, pageInfo);
    return pageInfo;
  }
}
