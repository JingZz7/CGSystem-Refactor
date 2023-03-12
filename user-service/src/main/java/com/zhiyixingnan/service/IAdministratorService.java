package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.domain.Administrator;
import com.zhiyixingnan.domain.Student;

public interface IAdministratorService extends IService<Administrator> {

  /**
   * @param id:
   * @param password: a * @return Bolean
   * @author ZJ
   * @description TODO 管理员是否存在
   * @date 2023/3/12 21:45
   */
  public Boolean isExistAdministrator(String id, String password);
}
