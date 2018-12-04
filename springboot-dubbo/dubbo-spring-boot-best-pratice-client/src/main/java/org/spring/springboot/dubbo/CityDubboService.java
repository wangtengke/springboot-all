package org.spring.springboot.dubbo;

import org.spring.springboot.domain.City;

/** 
* @Description: 服务层 
* @Param:  
* @return:  
* @Author: Wtk 
* @Date: 2018/9/29 
*/ 
public interface CityDubboService {

    /**
     * 根据城市名称，查询城市信息
     * @param cityName
     */
    City findCityByName(String cityName);
}
