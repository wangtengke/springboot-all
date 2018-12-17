package com.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: springboot-all
 * @description:
 * @author: wangtengke
 * @create: 2018-12-17
 **/
@Data
@AllArgsConstructor
public class User  implements Serializable {
    private int id;
    private int age;
    private String sex;
    private String likes;

}
