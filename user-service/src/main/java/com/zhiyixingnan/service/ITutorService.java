package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Tutor;

public interface ITutorService extends IService<Tutor> {

  /**
   * @param id:
   * @param password: a * @return Bolean
   * @author ZJ
   * @description TODO 助教是否存在
   * @date 2023/3/12 21:47
   */
  public Boolean isExistTutor(String id, String password);
}
