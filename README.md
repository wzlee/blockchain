# blockchain

#### 项目介绍
java版区块链项目
![注册页面](https://gitee.com/uploads/images/2018/0703/103414_e7e5f15c_3007.png "屏幕截图.png")
![注册成功](https://gitee.com/uploads/images/2018/0703/103227_109877d2_3007.png "屏幕截图.png")
![登录页面](https://gitee.com/uploads/images/2018/0703/103338_3b640c1a_3007.png "屏幕截图.png")
![登录成功](https://gitee.com/uploads/images/2018/0703/103449_c34dc1ec_3007.png "屏幕截图.png")

#### 软件架构
采用SpringBoot 2.0.3-REALEASE + MongoDB + Redis + Spring Security + Thymeleaf

#### 安装教程

1. 检出代码：git clone https://gitee.com/wzlee/blockchain.git
2. 修改src/main/resource/application-dev.yml相关配置(mongodb连接信息)
3. 运行：mvn spring-boot:run
4. 访问：http://127.0.0.1:8080
5. 注册账号后登录
6. 创建交易和区块入链的逻辑见单元测试代码src/test/java/.../BlockChainApplicationTests.java

#### 开发计划
1. 基础框架搭建<已完成>
2. 核心业务单元测试<已通过>
3. 登录&注册<已完成>
4. 创建交易<未完成>
5. 区块入链<未完成>
6. 节点通讯<未完成>

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request