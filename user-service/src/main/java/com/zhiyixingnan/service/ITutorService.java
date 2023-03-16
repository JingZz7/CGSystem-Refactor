package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyixingnan.domain.Student;
import com.zhiyixingnan.domain.Tutor;

import java.util.HashMap;
import java.util.List;

public interface ITutorService extends IService<Tutor> {

  /**
   * @param id:
   * @param password: a * @return Bolean
   * @author ZJ
   * @description TODO 助教是否存在
   * @date 2023/3/12 21:47
   */
  public Boolean isExistTutor(String id, String password);

  /**
   * @param id: * @return List<HashMap<String,String>>
   * @author ZJ
   * @description TODO [助教]展示个人信息(个人中心)
   * @date 2023/3/16 19:57
   */
  public List<HashMap<String, String>> displayPersonalInformation(String id);

  /**
   * @param id: * @return Boolean
   * @author ZJ
   * @description TODO [助教]是否存在
   * @date 2023/3/16 20:01
   */
  public Boolean isTutorExist(String id);

  /**
   * @param id:
   * @param phone:
   * @param email: * @return Boolean
   * @author ZJ
   * @description TODO [助教]修改电话和邮箱(个人中心)
   * @date 2023/3/16 20:05
   */
  public Boolean modifyPhoneAndEmailById(String id, String phone, String email);

  /**
   * @param id:
   * @param password: * @return Boolean
   * @author ZJ
   * @description TODO [助教]修改密码(个人中心)
   * @date 2023/3/16 20:12
   */
  public Boolean modifyPasswordById(String id, String password) throws InterruptedException;
}
