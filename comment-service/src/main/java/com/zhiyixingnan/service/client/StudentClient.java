package com.zhiyixingnan.service.client;

import com.zhiyixingnan.domain.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user-service")
public interface StudentClient {

    @RequestMapping("/students/selectOneStudentByIdAndDeleted/{id}")
    public Student selectOneStudentByIdAndDeleted(@PathVariable("id") String id);
}
