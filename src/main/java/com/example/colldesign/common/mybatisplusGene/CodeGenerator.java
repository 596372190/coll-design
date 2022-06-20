package com.example.colldesign.common.mybatisplusGene;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class CodeGenerator {
    private static String outputDir = "src/main/java";
    private static String author = "dragon";

    private static String entitySrc = "model";
    private static String mapperSrc = "dao";
    private static String xmlSrc = "mapper";
    private static String serviceSrc = "service";
    private static String serviceImplSrc = "service.impl";
    private static String controllerSrc = "controller";

    private static String dbDriverName = "com.mysql.cj.jdbc.Driver";
    private static String dbUrl = "jdbc:mysql://localhost:3306/comment?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static String dbName = "dragon";
    private static String dbPassword = "dragon";

    //包路径
    private static String packageSrc = "com.example.colldesign";
    //表名
    private static String tableName = "tb_comment_attachment";
    //模块名称
    private static String modelName = "comment";

    public static void main(String[] args) {
        //代码生成器对象
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        //final String projectPath = "D:\\ideaProject\\base";
        final String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/" + outputDir);
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setEnableCache(true);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);//开启Record模式 让model拥有基本crud方法；
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setIdType(IdType.UUID);
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(dbDriverName);
        dsc.setUrl(dbUrl);
        dsc.setUsername(dbName);
        dsc.setPassword(dbPassword);
        mpg.setDataSource(dsc);

        //包配置
        final PackageConfig pc = new PackageConfig();
        pc.setParent(packageSrc);
        pc.setModuleName(modelName);
        pc.setEntity(entitySrc);
        pc.setMapper(mapperSrc);
        pc.setXml(xmlSrc);
        pc.setService(serviceSrc);
        pc.setServiceImpl(serviceImplSrc);
        pc.setController(controllerSrc);
        mpg.setPackageInfo(pc);

        //自定义配置


        //配置模版
        TemplateConfig templateConfig = new TemplateConfig();
        mpg.setTemplate(templateConfig);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
        strategyConfig.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        strategyConfig.setInclude(tableName);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setEntityLombokModel(true);

        //设置策略  搜索引擎 执行生成操作
        mpg.setStrategy(strategyConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        TemplateConfig tc = new TemplateConfig();
        tc.setEntity("/templates/entity.java");
        tc.setMapper("/templates/mapper.java");
        tc.setController("/templates/controller.java");
        tc.setService("/templates/service.java");
        tc.setServiceImpl("/templates/serviceImpl.java");
        mpg.setTemplate(tc);

        mpg.execute();

    }

    public static void main1(String[] args) {
        final String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
    }
}