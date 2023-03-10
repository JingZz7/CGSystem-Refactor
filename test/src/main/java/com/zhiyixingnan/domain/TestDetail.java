package com.zhiyixingnan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TestDetail {

  Student student;
  List<Problem> problemList;
}
