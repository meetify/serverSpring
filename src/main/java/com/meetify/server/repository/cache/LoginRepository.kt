//package com.meetify.server.repository.cache
//
//import com.meetify.server.model.entity.Login
//import com.meetify.server.repository.LoginRepository
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.stereotype.Component
//
///**
// * Created on 10.12.2016.
// * @author  Dmitry Baynak
// */
//@Component
//open class LoginCacheRepository @Autowired constructor(private val repo: LoginRepository) : CacheRepository<Login>(repo) {
//    fun findByDevice(device: String) = repo.findByDevice(device)
//
//    @Bean open fun loginRepository() = LoginCacheRepository(repo)
//}