package com.springboot.redis;

import com.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class RedisApplication implements CommandLineRunner {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public void run(String... args) throws Exception {
//        ValueOperations<String, String> ops = this.template.opsForValue();
        this.redisTemplate.opsForList().rightPush("user",new User(1,10,"f","apple"));
        this.redisTemplate.opsForList().rightPush("user",new User(2,18,"m","banana"));
        this.redisTemplate.opsForValue().set("key","test");
        this.redisTemplate.opsForSet().add("set", new HashSet<String>().add("obj"));
        Map<String,User> map = new HashMap<>();
        map.put("map1",new User(1,10,"f","apple"));
        map.put("map2",new User(5,11,"f","apple1"));
        this.redisTemplate.opsForHash().putAll("hash",map);
        String key = "spring.boot.redis.test";
        List<Object> list = this.redisTemplate.opsForList().range("user",0,1);
//        if (!this.template.hasKey(key)) {
//            ops.set(key, "foo");
//        }
//        System.out.println("Found key " + key + ", value=" + ops.get(key));
        System.out.println(redisTemplate.opsForSet().members("set"));
    }
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}

