package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.Teacher;

public interface ITeacherService extends IService<Teacher> {

    /**
     * @param id:
     * @param password: * @return Boolean
     * @author ZJ
     * @description TODO 教师是否存在
     * @date 2023/3/12 21:36
     */
  public Boolean isExistTeacher(String id, String password);
}
