# 二手车评估管理系统

基于Spring Boot 3 + MyBatis-Plus + Swagger的二手车评估管理系统后端API。

## 技术栈

- Spring Boot 3.2.0
- MyBatis-Plus 3.5.3.1
- MySQL 8.0+
- SpringDoc OpenAPI (Swagger) 2.2.0
- JWT认证
- Lombok
- Hutool工具类

## 功能特性

- 用户管理 (普通用户/评估师/管理员)
- 汽车型号管理
- 二手车信息管理
- 车辆评估管理
- 损伤标记管理
- 3D模型管理
- VR视图会话管理
- 文件上传管理

## 如何运行

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+

### 配置数据库

1. 创建数据库：

```sql
CREATE DATABASE second_hand_car DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 执行初始化SQL脚本 (resources/db/init.sql)

### 修改配置

修改 `application.yml` 中的数据库连接配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/second_hand_car?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: your_username
    password: your_password
```

### 启动应用

```bash
mvn spring-boot:run
```

或打包后运行：

```bash
mvn clean package
java -jar target/second-hand-car-0.0.1-SNAPSHOT.jar
```

## API文档

启动应用后访问Swagger文档：

```
http://localhost:8080/api/swagger-ui.html
```

## 主要API模块

- /api/yonghu - 用户相关接口
- /api/qichexinghao - 汽车型号相关接口
- /api/ershouche - 二手车相关接口
- /api/assessment - 评估相关接口
- /api/vr - VR相关接口

## 其他说明

- 文件上传路径：默认为 ./uploads/
- JWT令牌有效期：24小时 