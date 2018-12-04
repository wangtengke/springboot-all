package org.spring.springboot.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.spring.springboot.domain.City;
import org.spring.springboot.dubbo.CityDubboService;

/** 
* @Description: 城市实现类 
* @Param:  
* @return:  
* @Author: Wtk 
* @Date: 2018/9/29 
*/ 
// 注册为 Dubbo 服务
@Service(version = "1.0.0")
public class CityDubboServiceImpl implements CityDubboService {

    @Override
    public City findCityByName(String cityName) {
        return new City(1L,2L,"温岭","是我的故乡");
    }
}
