/****************************************
 * 2018 - 2021 版权所有 CopyRight(c) 快程乐码信息科技有限公司所有, 未经授权，不得复制、转发
 */

package com.kclm.xsap.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.MySqlDialect;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/******************
 * @Author yejf
 * @Version v1.0
 * @Create 2020-09-07 14:20
 * @Description 自定义mybatis-plug 的配置[可选]
 */
@Configuration
@MapperScan("com.kclm.xsap.mapper")
@Slf4j
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInnerInterceptor() {
        log.debug("创建mybatis-plus的分页插件....");
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialect(new MySqlDialect());
        page.setDbType(DbType.MYSQL);
        //返回
        return page;
    }

    /******
     * SQL执行效率监视器插件，用于输出每条sql语句及其执行时间
     * @return
     */
    @Bean
    @Profile({"dev","pro"})  //设置 dev和pro环境开启
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        log.debug("创建SQL执行效率监视插件...");
        PerformanceMonitorInterceptor pmi = new PerformanceMonitorInterceptor();
        return pmi;
    }
    
    /**
     *	乐观锁配置
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    	log.debug("create OptimisticLockerInterceptor....");
    	OptimisticLockerInterceptor oli = new OptimisticLockerInterceptor();
    	return oli;
    }
}
