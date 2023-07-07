# trans4j

## 介绍

- Trans4j是一个翻译框架，旨在为Java应用程序提供多种翻译功能。它支持字典翻译、枚举翻译和数据库翻译，帮助开发人员简化开发，并使开发人员能够轻松地处理不同类型的翻译需求
- 遵循Apache-2.0许可，因此您可以对它进行复制、修改、传播并用于任何个人或商业行为。

## 适用场景

- 字典翻译：我有一个sex字段，有一个sex_enum字典码，我想展示字典描述给用户
- 枚举翻译：我有一个枚举字段，我想展示枚举里面的某个属性给用户

## 待完成

- 数据库翻译

## 快速开始

添加依赖:

```xml

<dependency>
    <groupId>io.github.neondeep</groupId>
    <artifactId>trans4j-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 分支介绍

- master： 正式分支，最终发布到maven中央仓库的版本
- develop： 开发分支，定期同步到master，默认为下个版本的SNAPSHOT版本
- feature-xxx：开发的新功能分支，最终开发完毕合并到develop

## 参与贡献

- 在Gitee或者Github上fork项目到自己的repo
- 把fork过去的项目clone到你的本地
- 修改代码
- commit后push到自己的库
- 登录Gitee或Github在你首页可以看到一个 pull request 按钮，点击它，填写一些说明信息，然后提交即可。
- 等待合并





