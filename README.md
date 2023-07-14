# trans4j

## 介绍

trans4j是一个翻译框架，旨在为Java应用程序提供多种翻译功能。它支持字典翻译、枚举翻译和数据库翻译，帮助开发人员简化开发，并使开发人员能够轻松地处理不同类型的翻译需求

## 特性

- 字典翻译：我有一个sex字段，有一个sex_enum字典码，我想展示字典描述给用户
- 枚举翻译：我有一个枚举字段，我想展示枚举里面的某个属性给用户
- 数据库翻译：我有一个其他表的主键，我想通过主键查询出该表的某些字段给用户

## 适用场景

- 场景1：我有一个字段，有一个对应的字典，我想展示字典的描述给用户
- 场景2：我有一个枚举字段，我想展示枚举里面的某个属性给用户
- 场景3：我有一个其他表的主键，我想通过主键查询出该表的某些字段给用户

待完成

- 我有一组userIdList 比如 1,2,3 我希望能展示成 张三,李四,王五 给客户

## 快速开始

> 所有的使用案例都在example-trans4j-spring-boot里面，直接下载源码看即可

添加依赖:

```xml

<dependency>
    <groupId>io.github.neondeep</groupId>
    <artifactId>trans4j-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```

在yaml配置：

```yml
trans:
    enable-global: true
    enable-proxy: true
```

vo配置，所有的vo是要实现了TransVO接口即可实现翻译，下面用ExampleVO举例：

```java

@Data
@Accessors(chain = true)
public class ExampleVO implements TransVO {
    // 字典翻译代理
    @Trans(key = "sex_enum")
    private Integer sex = 1;

    // 字典翻译ref
    @Trans(key = "sex_enum", refs = "genderName")
    private Integer gender = 2;
    private String genderName;

    // 枚举翻译ref
    @Trans(type = TransType.ENUM, key = "desc", refs = "sexEnumNameRef")
    private SexEnum sex = SexEnum.MALE;
    private String sexEnumNameRef;

    // db翻译代理
    @Trans(type = TransType.DB, target = Student.class, fields = {"name", "createTime"})
    private Long studentId = 1L;

    // db翻译ref
    @Trans(type = TransType.DB, target = Student.class, refs = {"studentName", "studentCreateTime"}, fields = {"name", "createTime"})
    private Long studentId = 1L;
    private String studentName;
    private LocalDateTime studentCreateTime;
}
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





