package com.meetify.server.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

/**
 * Клас, який використовується самим фреймворком Spring для конфігурації Hibernate.
 * @author  Дмитро Байнак
 * @version 0.0.1
 * @since   0.0.1
 * @property    env                     поле, що дозволяє завантажувати конфігурацію, задану в application.properties.
 * @property    dataSource              поле, що надає доступу до бази даних.
 * @property    entityManagerFactory    фабрика, що дозволяє створити екземляри entityManager.
 */
@Configuration @EnableTransactionManagement @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
open class DatabaseConfig {

    @Autowired
    private val env: Environment? = null

    @Autowired
    private val dataSource: DataSource? = null

    @Suppress("SpringKotlinAutowiring") @Autowired
    private val entityManagerFactory: LocalContainerEntityManagerFactoryBean? = null

    /**
     * Метод, що дозволяє створити DataSource з параметрами, заданими в application.properties
     * @return dataSource
     */
    @Bean
    open fun dataSource(): DataSource = DriverManagerDataSource().apply {
        setDriverClassName(env!!.getProperty("db.driver"))
        url = env.getProperty("db.url")
        username = env.getProperty("db.username")
        password = env.getProperty("db.password")
    }

    /**
     * Метод, що дозволяє створити фабрику диспетчерів сутностей.
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean
    open fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = this@DatabaseConfig.dataSource!!
            setPackagesToScan(env!!.getProperty("entitymanager.packagesToScan"))
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            setJpaProperties(Properties().apply {
                put("hibernate.dialect", env.getProperty("hibernate.dialect"))
                put("hibernate.show_sql", env.getProperty("hibernate.show_sql"))
                put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"))
            })
        }
    }

    /**
     * Метод, що дозволяє створити менеджер транзакцій.
     * @return JpaTransactionManager
     */
    @Bean
    open fun transactionManager(): JpaTransactionManager {
        return JpaTransactionManager().apply {
            entityManagerFactory = this@DatabaseConfig.entityManagerFactory!!.`object`
        }
    }

    /**
     * PersistenceExceptionTranslationPostProcessor це компонентний пост-процесор,
     * який дає змоги будь-якій компоненті із аннотацією Repository
     * "кидати" специфічні відловлювані винятки і передавати їх фреймворкові.
     * @return PersistenceExceptionTranslationPostProcessor
     */
    @Bean
    open fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor {
        return PersistenceExceptionTranslationPostProcessor()
    }
}
