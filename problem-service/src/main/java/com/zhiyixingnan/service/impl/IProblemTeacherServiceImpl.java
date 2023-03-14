package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.dao.ProblemDao;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.service.IProblemTeacherService;
import com.zhiyixingnan.utils.PageUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IProblemTeacherServiceImpl extends ServiceImpl<ProblemDao, Problem>
    implements IProblemTeacherService {

  private final ProblemDao problemDao;

  @Lazy
  public IProblemTeacherServiceImpl(ProblemDao problemDao) {
    this.problemDao = problemDao;
  }

  @Override
  public PageInfo<?> getProblemById(String problemId, int currentPage, int pageSize) {
    List<Problem> problems =
        problemDao.selectList(
            Wrappers.<Problem>lambdaQuery()
                .eq(Problem::getId, problemId)
                .eq(Problem::getDeleted, 0));
    return PageUtils.pageProblem(problems, currentPage, pageSize);
  }

  @Override
  public PageInfo<?> getProblemListByName(String problemName, int currentPage, int pageSize) {
    LambdaQueryWrapper<Problem> lambdaQueryWrapper = Wrappers.<Problem>lambdaQuery();
    lambdaQueryWrapper.eq(Problem::getDeleted, 0).like(Problem::getName, problemName);
    List<Problem> problems = problemDao.selectList(lambdaQueryWrapper);
    return PageUtils.pageProblem(problems, currentPage, pageSize);
  }

  @Override
  public PageInfo<Problem> getListByDifficulty(String difficulty, int currentPage, int pageSize) {
    if (!difficulty.equals("all")) {
      List<Problem> problems =
          problemDao.selectList(
              Wrappers.<Problem>lambdaQuery()
                  .eq(Problem::getDifficulty, difficulty)
                  .eq(Problem::getDeleted, 0));
      int total = problems.size();
      if (total > pageSize) {
        int toIndex = pageSize * currentPage;
        if (toIndex > total) toIndex = total;
        problems = problems.subList(pageSize * (currentPage - 1), toIndex);
      }
      com.github.pagehelper.Page<Problem> page = new Page<>(currentPage, pageSize);
      page.addAll(problems);
      page.setPages((total + pageSize - 1) / pageSize);
      page.setTotal(total);

      PageInfo<Problem> pageInfo = new PageInfo<>(page);
      return pageInfo;
    }
    List<Problem> problems =
        problemDao.selectList(Wrappers.<Problem>lambdaQuery().eq(Problem::getDeleted, 0));
    int total = problems.size();
    if (total > pageSize) {
      int toIndex = pageSize * currentPage;
      if (toIndex > total) toIndex = total;
      problems = problems.subList(pageSize * (currentPage - 1), toIndex);
    }
    com.github.pagehelper.Page<Problem> page = new Page<>(currentPage, pageSize);
    page.addAll(problems);
    page.setPages((total + pageSize - 1) / pageSize);
    page.setTotal(total);

    PageInfo<Problem> pageInfo = new PageInfo<>(page);
    return pageInfo;
  }
}
