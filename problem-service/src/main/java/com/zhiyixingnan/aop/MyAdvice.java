package com.zhiyixingnan.aop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.domain.Problem;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class MyAdvice {

  @Pointcut("execution(* com.zhiyixingnan.service.IProblemService.getProblemsList(*,*,*))")
  private void getProblemsListPt() {}

  @Around("MyAdvice.getProblemsListPt()")
  public Object getProblemsListAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Object[] args = proceedingJoinPoint.getArgs();
    Signature signature = proceedingJoinPoint.getSignature();
    Object target = proceedingJoinPoint.getTarget();

    // 方法[{}]开始执行
    Object objects = proceedingJoinPoint.proceed();
    // 方法[{}]执行结束

    if (objects instanceof List) {
      List objList = (List) objects;
      int total = objList.size();
      if (total > Integer.parseInt(args[2].toString())) {
        int toIndex = Integer.parseInt(args[2].toString()) * Integer.parseInt(args[1].toString());
        if (toIndex > total) toIndex = total;
        objList =
            objList.subList(
                Integer.parseInt(args[2].toString()) * (Integer.parseInt(args[1].toString()) - 1),
                toIndex);
      }
      com.github.pagehelper.Page<Problem> page =
          new Page<>(Integer.parseInt(args[1].toString()), Integer.parseInt(args[2].toString()));
      page.addAll(objList);
      page.setPages(
          (total + Integer.parseInt(args[2].toString()) - 1)
              / Integer.parseInt(args[2].toString()));
      page.setTotal(total);

      PageInfo<Problem> pageInfo = new PageInfo<>(page);
      return pageInfo;
    }
    return objects;
  }
}
