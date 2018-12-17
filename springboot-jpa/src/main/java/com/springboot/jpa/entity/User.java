package com.springboot.jpa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: springboot-all
 * @description:
 * @author: wangtengke
 * @create: 2018-12-08
 **/
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;


}
