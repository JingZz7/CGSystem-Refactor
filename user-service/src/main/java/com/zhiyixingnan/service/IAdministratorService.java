package com.zhiyixingnan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zhiyixingnan.dao.AdministratorDao;
import com.zhiyixingnan.domain.Administrator;
import com.zhiyixingnan.domain.Student;

import java.util.HashMap;
import java.util.List;

public interface IAdministratorService extends IService<Administrator> {

  /**
   * @param id:
   * @param password: a * @return Bolean
   * @author ZJ
   * @description TODO 管理员是否存在
   * @date 2023/3/12 21:45
   */
  public Boolean isExistAdministrator(String id, String password);

  /**
   * @param currentPage:
   * @param pageSize: * @return Object
   * @author ZJ
   * @description TODO [管理员]获取用户列表(账户管理)
   * @date 2023/3/16 14:06
   */
  public Object administratorGetAccountList(int currentPage, int pageSize);

  /**
   * @param type:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<?>
   * @author ZJ
   * @description TODO [管理员]分类查找(账户管理)
   * @date 2023/3/16 14:18
   */
  public PageInfo<?> getAccountByType(String type, int currentPage, int pageSize);

  /**
   * @param id:
   * @param password:
   * @param email:
   * @param phone:
   * @param className: a * @return oolean
   * @author ZJ
   * @description TODO [管理员]编辑账户(账户管理)
   * @date 2023/3/16 14:21
   */
  public Boolean editAccount(
      String id, String password, String email, String phone, String className)
      throws InterruptedException;

  /**
   * @param type:
   * @param id:
   * @param name:
   * @param password:
   * @param email:
   * @param phone:
   * @param className: a * @return Boolan
   * @author ZJ
   * @description TODO [管理员]添加账户(账户管理)
   * @date 2023/3/16 14:26
   */
  public Boolean addAccount(
      String type,
      String id,
      String name,
      String password,
      String email,
      String phone,
      String className)
      throws InterruptedException;

  /**
   * @param id: * @return Boolean
   * @author ZJ
   * @description TODO [管理员]删除账号(账户管理)
   * @date 2023/3/16 14:30
   */
  public Boolean deleteAccount(String id) throws InterruptedException;

  /**
   * @param ids: * @return Boolean
   * @author ZJ
   * @description TODO
   * @date 2023/3/16 14:32
   */
  public Boolean bulkDeleteAccount(List<String> ids) throws InterruptedException;

  /**
   * @param id: * @return Boolean
   * @author ZJ
   * @description TODO [管理员]重置密码(账户管理)
   * @date 2023/3/16 14:36
   */
  public Boolean resetPassword(String id) throws InterruptedException;

  /**
   * @param ids: * @return Boolean
   * @author ZJ
   * @description TODO [管理员]批量重置密码(账户管理)
   * @date 2023/3/16 14:38
   */
  public Boolean bulkResetPasswords(List<String> ids) throws InterruptedException;

  /**
   * @param id:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<?>
   * @author ZJ
   * @description TODO [管理员]根据id查询账号(账户管理)
   * @date 2023/3/16 14:40
   */
  public PageInfo<?> getAccountById(String id, int currentPage, int pageSize);

  /**
   * @param name:
   * @param currentPage:
   * @param pageSize: * @return PageInfo<?>
   * @author ZJ
   * @description TODO [管理员]根据姓名查询账号(账户管理)
   * @date 2023/3/16 14:41
   */
  public PageInfo<?> getAccountByName(String name, int currentPage, int pageSize);

  /**
   * @param id: * @return List<HashMap<String,String>>
   * @author ZJ
   * @description TODO [管理员]展示个人信息(个人中心)
   * @date 2023/3/16 19:58
   */
  public List<HashMap<String, String>> displayPersonalInformation(String id);

  /**
   * @param id: * @return Boolean
   * @author ZJ
   * @description TODO [管理员]是否存在
   * @date 2023/3/16 20:02
   */
  public Boolean isAdministratorExist(String id);

  /**
   * @param id:
   * @param phone:
   * @param email: * @return Boolean
   * @author ZJ
   * @description TODO [管理员]修改电话和邮箱(个人中心)
   * @date 2023/3/16 20:06
   */
  public Boolean modifyPhoneAndEmailById(String id, String phone, String email) throws InterruptedException;

  /**
   * @param id:
   * @param password:  * @return Boolean
   * @author ZJ
   * @description TODO [管理员]修改密码(个人中心)
   * @date 2023/3/16 20:12
   */
  public Boolean modifyPasswordById(String id, String password) throws InterruptedException;
}
