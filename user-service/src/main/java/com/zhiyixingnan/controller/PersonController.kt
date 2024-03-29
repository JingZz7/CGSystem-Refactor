package com.zhiyixingnan.controller

import com.alibaba.fastjson.JSONObject
import com.zhiyixingnan.service.IAdministratorService
import com.zhiyixingnan.service.IStudentService
import com.zhiyixingnan.service.ITeacherService
import com.zhiyixingnan.service.ITutorService
import com.zhiyixingnan.service.impl.UserService
import com.zhiyixingnan.utils.JsonResult
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/person")
class PersonController {

    @Autowired
    private val iStudentService: IStudentService? = null

    @Autowired
    private val iTeacherService: ITeacherService? = null

    @Autowired
    private val iTutorService: ITutorService? = null

    @Autowired
    private val iAdministratorService: IAdministratorService? = null

    @Autowired
    private val userService: UserService? = null

    /**
     * @param null:
     * @return null
     * @author ZJ
     * @description TODO 展示个人信息(个人中心)
     * @date 2023/3/16 20:03
     */
    @RequestMapping(value = ["/displayPersonalInformation"], method = [RequestMethod.POST])
    open fun displayPersonalInformation(@RequestBody jsonObject: JSONObject): JsonResult<List<HashMap<String, String>>>? {
        val flagStudent = iStudentService?.isStudentExist(jsonObject.getString("id"))
        val flagTeacher = iTeacherService?.isTeacherExist(jsonObject.getString("id"))
        val flagTutor = iTutorService?.isTutorExist(jsonObject.getString("id"))
        val flagAdministrator = iAdministratorService?.isAdministratorExist(jsonObject.getString("id"))
        if (flagStudent == true) {
            return JsonResult.success(iStudentService?.displayPersonalInformation(jsonObject.getString("id")), "获取成功")
        } else if (flagTeacher == true) {
            return JsonResult.success(iTeacherService?.displayPersonalInformation(jsonObject.getString("id")), "获取成功")
        } else if (flagTutor == true) {
            return JsonResult.success(iTutorService?.displayPersonalInformation(jsonObject.getString("id")), "获取成功")
        } else if (flagAdministrator == true) {
            return JsonResult.success(
                iAdministratorService?.displayPersonalInformation(jsonObject.getString("id")),
                "获取成功"
            )
        }
        return JsonResult.failed("获取失败")
    }

    /**
     * @param null:
     * @return null
     * @author ZJ
     * @description TODO 修改电话和邮箱(个人中心)
     * @date 2023/3/16 20:03
     */
    @RequestMapping(value = ["/modifyPhoneAndEmailById"], method = [RequestMethod.PUT])
    open fun modifyPhoneAndEmailById(@RequestBody jsonObject: JSONObject): JsonResult<*>? {
        val flagStudent = iStudentService?.modifyPhoneAndEmailById(
            jsonObject.getString("id"), jsonObject.getString("phone"), jsonObject
                .getString("email")
        )
        val flagTeacher = iTeacherService?.modifyPhoneAndEmailById(
            jsonObject.getString("id"), jsonObject.getString("phone"), jsonObject
                .getString("email")
        )
        val flagTutor = iTutorService?.modifyPhoneAndEmailById(
            jsonObject.getString("id"), jsonObject.getString("phone"), jsonObject
                .getString("email")
        )
        val flagAdministrator = iAdministratorService?.modifyPhoneAndEmailById(
            jsonObject.getString("id"), jsonObject.getString("phone"), jsonObject
                .getString("email")
        )
        if (flagStudent == true || flagTeacher == true || flagTutor == true || flagAdministrator == true) {
            return JsonResult.success("success", "修改成功");
        }
        return JsonResult.failed("error", "修改失败")
    }

    /**
     * @param null:
     * @return null
     * @author ZJ
     * @description TODO 获取密码(个人中心)
     * @date 2023/3/16 20:08
     */
    @RequestMapping(value = ["/getPasswordById"], method = [RequestMethod.POST])
    open fun getPasswordById(@RequestBody jsonObject: JSONObject): JsonResult<String>? {
        return JsonResult.success(userService?.getPasswordById(jsonObject.getString("id")), "获取成功");
    }

    /**
     * @param null:
     * @return null
     * @author ZJ
     * @description TODO 修改密码(个人中心)
     * @date 2023/3/16 20:10
     */
    @RequestMapping(value = ["/modifyPasswordById"], method = [RequestMethod.PUT])
    open fun modifyPasswordById(@RequestBody jsonObject: JSONObject): JsonResult<*>? {
        val flagStudent =
            iStudentService?.modifyPasswordById(jsonObject.getString("id"), jsonObject.getString("password"))
        val flagTeacher =
            iTeacherService?.modifyPasswordById(jsonObject.getString("id"), jsonObject.getString("password"))
        val flagTutor =
            iTutorService?.modifyPasswordById(jsonObject.getString("id"), jsonObject.getString("password"))
        val flagAdministrator =
            iAdministratorService?.modifyPasswordById(jsonObject.getString("id"), jsonObject.getString("password"))
        if (flagStudent == true || flagTeacher == true || flagTutor == true || flagAdministrator == true) {
            return JsonResult.success("success", "修改成功")
        }
        return JsonResult.failed("error", "修改失败")
    }
}