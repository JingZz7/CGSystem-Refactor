package com.zhiyixingnan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.dao.ProblemDao;
import com.zhiyixingnan.domain.Favorite;
import com.zhiyixingnan.domain.ModelOutputKnowledge;
import com.zhiyixingnan.domain.Problem;
import com.zhiyixingnan.service.IProblemStudentService;
import com.zhiyixingnan.service.client.FavoriteClient;
import com.zhiyixingnan.service.client.ModelOutputKnowledgeClient;
import com.zhiyixingnan.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class IProblemStudentServiceImpl extends ServiceImpl<ProblemDao, Problem>
    implements IProblemStudentService {

  private final ProblemDao problemDao;
  private final ModelOutputKnowledgeClient modelOutputKnowledgeClient;
  private final FavoriteClient favoriteClient;

  @Lazy
  public IProblemStudentServiceImpl(
      ProblemDao problemDao,
      ModelOutputKnowledgeClient modelOutputKnowledgeClient,
      FavoriteClient favoriteClient) {
    this.problemDao = problemDao;
    this.modelOutputKnowledgeClient = modelOutputKnowledgeClient;
    this.favoriteClient = favoriteClient;
  }

  @Override
  public Problem getProblemByIdAndDeleted0(String id) {
    return problemDao.selectOne(
        Wrappers.<Problem>lambdaQuery().eq(Problem::getId, id).eq(Problem::getDeleted, 0));
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

  @Override
  public Object getProblemsList(String id, int currentPage, int pageSize) {
    List<ModelOutputKnowledge> list = modelOutputKnowledgeClient.getModelOutputKnowledgeById(id);
    HashMap<String, BigDecimal> map = new HashMap<>();
    for (ModelOutputKnowledge modelOutputKnowledge : list)
      map.put(modelOutputKnowledge.getKnowledgePointId(), modelOutputKnowledge.getForecast());

    List<String> arrayList = new ArrayList<>(); // 存放知识点id

    for (Map.Entry<String, BigDecimal> vo : map.entrySet()) {
      BigDecimal b = new BigDecimal("0.5");
      // forecast大于0.5
      if (vo.getValue().compareTo(b) == 1) continue;
      arrayList.add(vo.getKey());
    }

    List<Problem> problems = new ArrayList<>(); // 存放推荐题目
    int len = arrayList.size();

    for (int i = 0; i < len; ++i) {
      List<Problem> problemList =
          problemDao.selectList(
              Wrappers.<Problem>lambdaQuery()
                  .eq(Problem::getKnowledgePointId, arrayList.get(i))
                  .eq(Problem::getDeleted, 0)); // 第i个知识点的所有题目集合
      for (Problem problem : problemList) problems.add(problem);
    }

    return problems;
  }

  @Override
  public PageInfo<Problem> getFavoriteProblemList(String studentId, int currentPage, int pageSize) {
    List<Favorite> favorites = favoriteClient.getProblemsByStudentId(studentId);

    List<Problem> problems = new ArrayList<>();
    for (Favorite favorite : favorites) {
      if (problemDao.selectOne(
              Wrappers.<Problem>lambdaQuery()
                  .eq(Problem::getId, favorite.getProblemId())
                  .eq(Problem::getDeleted, 0))
          == null) continue;
      Problem problem =
          problemDao.selectOne(
              Wrappers.<Problem>lambdaQuery()
                  .eq(Problem::getId, favorite.getProblemId())
                  .eq(Problem::getDeleted, 0));
      //      BeanUtils.copyProperties(favorite, problem);
      problems.add(problem);
    }

    int total = problems.size();
    if (total > pageSize) {
      int toIndex = pageSize * currentPage;
      if (toIndex > total) toIndex = total;
      problems = problems.subList(pageSize * (currentPage - 1), toIndex);
    }
    Page<Problem> page = new Page<>(currentPage, pageSize);
    page.addAll(problems);
    page.setPages((total + pageSize - 1) / pageSize);
    page.setTotal(total);

    PageInfo<Problem> pageInfo = new PageInfo<>(page);
    return pageInfo;
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
  public Object getProblemsByDifficulty(
      String id, String difficulty, int currentPage, int pageSize) {
    if (!difficulty.equals("all")) {
      List<ModelOutputKnowledge> list = modelOutputKnowledgeClient.getModelOutputKnowledgeById(id);
      HashMap<String, BigDecimal> map = new HashMap<>();
      for (ModelOutputKnowledge modelOutputKnowledge : list)
        map.put(modelOutputKnowledge.getKnowledgePointId(), modelOutputKnowledge.getForecast());
      List<String> arrayList = new ArrayList<>(); // 存放知识点id
      for (Map.Entry<String, BigDecimal> vo : map.entrySet()) {
        BigDecimal b = new BigDecimal("0.5");
        // forecast大于0.5
        if (vo.getValue().compareTo(b) == 1) continue;
        arrayList.add(vo.getKey());
      }
      List<Problem> problems = new ArrayList<>(); // 存放推荐题目
      int len = arrayList.size();

      for (int i = 0; i < len; ++i) {
        List<Problem> problemList =
            problemDao.selectList(
                Wrappers.<Problem>lambdaQuery()
                    .eq(Problem::getKnowledgePointId, arrayList.get(i))
                    .eq(Problem::getDifficulty, difficulty.charAt(0))
                    .eq(Problem::getDeleted, 0)); // 第i个知识点的所有题目集合
        for (Problem problem : problemList) problems.add(problem);
      }
      return problems;
    }
    List<ModelOutputKnowledge> list = modelOutputKnowledgeClient.getModelOutputKnowledgeById(id);
    HashMap<String, BigDecimal> map = new HashMap<>();
    for (ModelOutputKnowledge modelOutputKnowledge : list)
      map.put(modelOutputKnowledge.getKnowledgePointId(), modelOutputKnowledge.getForecast());
    List<String> arrayList = new ArrayList<>(); // 存放知识点id
    for (Map.Entry<String, BigDecimal> vo : map.entrySet()) {
      BigDecimal b = new BigDecimal("0.5");
      // forecast大于0.5
      if (vo.getValue().compareTo(b) == 1) continue;
      arrayList.add(vo.getKey());
    }
    List<Problem> problems = new ArrayList<>(); // 存放推荐题目
    int len = arrayList.size();
    for (int i = 0; i < len; ++i) {
      List<Problem> problemList =
          problemDao.selectList(
              Wrappers.<Problem>lambdaQuery()
                  .eq(Problem::getKnowledgePointId, arrayList.get(i))
                  .eq(Problem::getDeleted, 0)); // 第i个知识点的所有题目集合
      for (Problem problem : problemList) problems.add(problem);
    }
    return problems;
  }

  @Override
  public PageInfo<Problem> getProblemInFavoriteById(
      String studentId, String problemId, int currentPage, int pageSize) {
    Favorite favorite = favoriteClient.getProblemInFavoriteById(studentId, problemId);
    List<Problem> problems =
        problemDao.selectList(
            Wrappers.<Problem>lambdaQuery().eq(Problem::getId, favorite.getProblemId()));
    int total = problems.size();
    if (total > pageSize) {
      int toIndex = pageSize * currentPage;
      if (toIndex > total) toIndex = total;
      problems = problems.subList(pageSize * (currentPage - 1), toIndex);
    }
    Page<Problem> page = new Page<>(currentPage, pageSize);
    page.addAll(problems);
    page.setPages((total + pageSize - 1) / pageSize);
    page.setTotal(total);

    PageInfo<Problem> pageInfo = new PageInfo<>(page);
    return pageInfo;
  }

  @Override
  public PageInfo<Problem> getProblemByName(
      String studentId, String problemName, int currentPage, int pageSize) {
    List<Favorite> favorites = favoriteClient.getProblemsByStudentId(studentId);
    List<Problem> problems = new ArrayList<>();
    for (Favorite favorite : favorites)
      if (problemDao.selectOne(
              Wrappers.<Problem>lambdaQuery()
                  .eq(Problem::getId, favorite.getProblemId())
                  .eq(Problem::getDeleted, 0)
                  .like(Problem::getName, problemName))
          != null)
        problems.add(
            problemDao.selectOne(
                Wrappers.<Problem>lambdaQuery()
                    .eq(Problem::getId, favorite.getProblemId())
                    .eq(Problem::getDeleted, 0)
                    .like(Problem::getName, problemName)));
    int total = problems.size();
    if (total > pageSize) {
      int toIndex = pageSize * currentPage;
      if (toIndex > total) toIndex = total;
      problems = problems.subList(pageSize * (currentPage - 1), toIndex);
    }
    Page<Problem> page = new Page<>(currentPage, pageSize);
    page.addAll(problems);
    page.setPages((total + pageSize - 1) / pageSize);
    page.setTotal(total);

    PageInfo<Problem> pageInfo = new PageInfo<>(page);
    return pageInfo;
  }
}
