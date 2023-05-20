package com.deepcode.jiaming;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

/**
 * @author winmanboo
 * @date 2023/5/20 21:47
 */
public class CodeGen {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/jiaming?serverTimezone=GMT%2B8&useSSL=false", "root", "mingge123")
                .globalConfig(builder -> {
                    builder.author("winmanboo") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/wangzhiming/StudyProjects/jiaming-cloud/jiaming-admin/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.deepcode.jiaming") // 设置父包名
                            .moduleName("admin") // 设置父包模块名
                            .controller("controller")
                            .service("service")
                            .mapper("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/wangzhiming/StudyProjects/jiaming-cloud/jiaming-admin/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
                            .addTablePrefix("sys_") // 过滤掉表的前缀
                            .addInclude("sys_dept", "sys_group_role", "sys_group_user", "sys_menu", "sys_post", "sys_role", "sys_role_menu", "sys_tenant", "sys_tenant_package", "sys_user", "sys_user_group", "sys_user_role") // 设置需要生成的表名
                            .entityBuilder() // 实体类配置
                            .naming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                            .columnNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                            .enableLombok() // lombok 模型 @Accessor(chain = true) setter 链式操作
                            .controllerBuilder() // controller 配置
                            .enableRestStyle() // restful api 风格
                            .enableHyphenStyle() // url 中驼峰赚连字符
                            .serviceBuilder()
                            .formatServiceFileName("%sService");
                })
                .templateEngine(new VelocityTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
