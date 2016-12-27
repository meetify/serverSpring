package com.meetify.server.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.meetify.server.util.jackson.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource


/**
 * Class that used by Spring to configure Hibernate and database using application.properties.
 * @since  0.1.0
 * @property  env           property that allows to load some config lines.
 * @property  dataSource       property that allows to connect to database.
 * @property  entityManagerFactory  factory that used to create entityManager instances.
 */
@Configuration
@EnableTransactionManagement
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
open class DatabaseConfig {

    @Autowired
    private val env: Environment? = null

    @Autowired
    private val dataSource: DataSource? = null

    @Suppress("SpringKotlinAutowiring") @Autowired
    private val entityManagerFactory: LocalContainerEntityManagerFactoryBean? = null

    /**
     * Declare the DataSource with parameters of application.properties.
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
     * Declare the JPA entity manager factory.
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
     * Declare the transaction manager.
     * @return JpaTransactionManager
     */
    @Bean
    open fun transactionManager(): JpaTransactionManager {
        return JpaTransactionManager().apply {
            entityManagerFactory = this@DatabaseConfig.entityManagerFactory!!.`object`
        }
    }

    /**
     * PersistenceExceptionTranslationPostProcessor is a bean post processor
     * which adds an advisor to any bean annotated with Repository so that any
     * platform-specific exceptions are caught and then rethrown as one
     * Spring's unchecked data access exceptions (i.e. a subclass of DataAccessException).
     * @return PersistenceExceptionTranslationPostProcessor
     */
    @Bean
    open fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor {
        return PersistenceExceptionTranslationPostProcessor()
    }

    @Bean
    open fun objectMapperBuilder(): Jackson2ObjectMapperBuilder {
        println("Bean objectMapperBuilder #||# going to give own builder")
        return Jackson2ObjectMapperBuilder().apply {
            serializationInclusion(JsonInclude.Include.NON_NULL)
            configure(jacksonObjectMapper())
        }
    }
}
