package com.comp344.ecommerce.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Byambatsog on 9/27/16.
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.comp344.ecommerce", excludeFilters = { @ComponentScan.Filter(Configuration.class) })
@PropertySource("application.properties")
public class MainConfig implements ApplicationContextAware {

    static ApplicationContext cxt;

    @Autowired
    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        cxt = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return cxt;
    }

    public static Object getFirstSpringBeanOfType(Class clazz){
        Map beans = cxt.getBeansOfType(clazz);
        if (beans.size()==0) return null;
        return beans.values().toArray()[0];
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(environment.getProperty("db.driverClassName"));
        ds.setUrl(environment.getProperty("db.url"));
        ds.setUsername(environment.getProperty("db.user"));
        ds.setPassword(environment.getProperty("db.password"));
        ds.setMaxActive(environment.getProperty("db.maxActive", Integer.class, 200));
        ds.setMaxIdle(environment.getProperty("db.maxIdle", Integer.class, 30));
        ds.setMaxWait(environment.getProperty("db.maxWait", Long.class, 10000l));
        ds.setRemoveAbandoned(environment.getProperty("db.removeAbandoned", Boolean.class, true));
        ds.setRemoveAbandonedTimeout(environment.getProperty("db.removeAbandonedTimeout", Integer.class, 15));
        ds.setLogAbandoned(environment.getProperty("db.logAbandoned", Boolean.class, false));
        return ds;
    }

    @Bean(name="sessionFactory")
    public AnnotationSessionFactoryBean sessionFactoryBean() throws IOException {
        AnnotationSessionFactoryBean sessionFactoryBean=new AnnotationSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setConfigurationClass(org.hibernate.cfg.AnnotationConfiguration.class);
        sessionFactoryBean.setPackagesToScan(new String[]{"com.comp344.ecommerce.domain"});
        sessionFactoryBean.setHibernateProperties(PropertiesLoaderUtils.loadAllProperties("hibernate.properties"));
        return sessionFactoryBean;
    }

    public SessionFactory sessionFactory(){
        try{
            return sessionFactoryBean().getObject();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
