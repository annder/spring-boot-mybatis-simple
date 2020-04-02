# Spring 和 Mybatis 的整合

Spring Mybatis 的整合，这是它的`pom.xml`配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.6.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.mybatis</groupId>
    <artifactId>springboot-mybaits-dome2</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot-mybaits-dome2</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.2</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.16</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

- **在 MySQL 服务器的版本是 8 的话，那么`mysql-connector-java`的版本是`8.0+`。**

我的`mysql-connector-java`的版本号是：`8.0.16`。

接着在`application.properties`文件内写：

```
# 这是你的地址
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/spring_start?characterEncoding=utf-8&useSSL=true&serverTimezone=GMT
# 这是你的用户名
spring.datasource.username=root
# 这是你的密码
spring.datasource.password=root
# 这是你的数据库驱动
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

记住：

- MySQL 的最新驱动必须是`com.mysql.cj.jdbc.Driver`
- spring.datasource.url 后面必须接 characterEncoding=utf-8&useSSL=true&serverTimezone=GMT

---

首先写一个实体，一个类：

```java
package com.mybatis.domain;

public class User {
    private Integer id;
    private String name;
    private String password;
    private String phone;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
```

然后写一个`Mapper`，这个`Mapper`就是一个包：

```java
package com.mybatis.mapper;

import com.mybatis.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM T_USER WHERE PHONE = #{phone}")
    User findUserByPhone(@Param("phone") String phone);

    @Insert("INSERT INTO T_USER(NAME, PASSWORD, PHONE) VALUES(#{name}, #{password}, #{phone})")
    int insert(@Param("name") String name, @Param("password") String password, @Param("phone") String phone);
}
```

接着在 Controller 里面使用它：

```java
package com.mybatis.controller;

import com.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/")
    public Integer getUser(){
        userMapper.insert("annter","root","12367800");
        return 1;
    }
}
```

这样，就可以直接插入一个值了：