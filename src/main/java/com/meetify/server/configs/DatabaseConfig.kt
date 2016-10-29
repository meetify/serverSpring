//package com.meetify.server.configs
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.env.Environment
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
//import org.springframework.jdbc.datasource.DriverManagerDataSource
//import org.springframework.orm.jpa.JpaTransactionManager
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
//import org.springframework.transaction.annotation.EnableTransactionManagement
//import java.util.*
//import javax.sql.DataSource
//
//
//@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
//@Configuration
//@EnableTransactionManagement
//open class DatabaseConfig {
//
//
//    @Autowired
//    private val env: Environment? = null
//    @Autowired
//    private val dataSource: DataSource? = null
//
//    @SuppressWarnings("SpringJavaAutowiringInspection")
//    @Autowired
//    private val entityManagerFactory: LocalContainerEntityManagerFactoryBean? = null
//
//    @Bean
//    open fun dataSource(): DataSource {
//        val dataSource = DriverManagerDataSource()
//        dataSource.setDriverClassName(env!!.getProperty("db.driver"))
//        dataSource.url = env.getProperty("db.url")
//        dataSource.username = env.getProperty("db.username")
//        dataSource.password = env.getProperty("db.password")
//        return dataSource
//    }
//
//
//    @Bean
//    open fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
//        val entityManagerFactory = LocalContainerEntityManagerFactoryBean()
//
//        entityManagerFactory.dataSource = dataSource
//
//        // Classpath scanning of @Component, @Service, etc annotated class
//        entityManagerFactory.setPackagesToScan(
//                env!!.getProperty("entitymanager.packagesToScan"))
//
//        // Vendor adapter
//        val vendorAdapter = HibernateJpaVendorAdapter()
//        entityManagerFactory.jpaVendorAdapter = vendorAdapter
//
//        // Hibernate properties
//        val additionalProperties = Properties()
//        additionalProperties.put(
//                "hibernate.dialect",
//                env.getProperty("hibernate.dialect"))
//        additionalProperties.put(
//                "hibernate.show_sql",
//                env.getProperty("hibernate.show_sql"))
//        additionalProperties.put(
//                "hibernate.hbm2ddl.auto",
//                env.getProperty("hibernate.hbm2ddl.auto"))
//        entityManagerFactory.setJpaProperties(additionalProperties)
//
//        return entityManagerFactory
//    }
//
//
//    @Bean
//    open fun transactionManager(): JpaTransactionManager {
//        val transactionManager = JpaTransactionManager()
//        transactionManager.entityManagerFactory = entityManagerFactory!!.`object`
//        return transactionManager
//    }
//
//
//    @Bean
//    open fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor {
//        return PersistenceExceptionTranslationPostProcessor()
//    }
//}
