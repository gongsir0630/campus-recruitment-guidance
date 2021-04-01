<h1 align="center">柚子帮-校招指导服务平台</h1>
<p align="center">后端 API</p>

## 前言
这里是我的本科毕业设计,建设一个方便、快捷、能有效联系在校毕业生和已就业师兄师姐的在线校招指导服务平台——“柚子帮”，并完成系统的设计与开发.平台致力于为西柚学子提供校招指导服务，提高毕业生在校招季的求职效率。

整个平台包含`微信小程序端`和 `web 管理端`两部分,项目开发采用前后端分离,这里是后端项目工程,基于 SpringBoot 搭建,对接 [wx-java-miniapp](https://github.com/gongsir0630/wx-java-miniapp) 为小程序提供数据接口服务.

涉及的技术栈:
* SpringBoot -> 后端基础环境
* Shiro -> 安全框架
* JWT -> 加密token
* MySQL -> 主库，存储业务数据
* MyBatis-Plus -> 操作数据库
* Redis -> 缓存token和其他热点数据
* Lombok -> 简化开发
* FastJson -> json消息处理
* RestTemplate -> 优雅的处理web请求
* [wx-java-miniapp](https://github.com/gongsir0630/wx-java-miniapp) -> 微信服务端API

## 特性
* 基于WxJava对接微信小程序，实现用户登录、消息处理
* 支持Shiro注解编程，保持高度的灵活性
* 使用JWT进行校验，完全实现无状态鉴权
* 使用Redis存储自定义登陆态token，支持过期时间
* 支持跨域请求

## 使用
0. 使用该项目之前请先部署 [wx-java-miniapp](https://github.com/gongsir0630/wx-java-miniapp)


1. clone项目到本地
```shell
git clone https://github.com/gongsir0630/campus-recruitment-guidance.git
```

2. 安装redis和mysql,执行`resources/sql/campus-graduation-design.sql`脚本,修改`application-*.yml`配置文件


3. mvn编译运行
```shell
mvn clean package
```
