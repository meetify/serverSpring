package com.meetify.server.service.impl

import com.meetify.server.model.entity.Login
import com.meetify.server.repository.LoginRepository
import com.meetify.server.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by dmitry on 12/12/16.
 */

@Service
class LoginServiceImpl @Autowired constructor(private val repo: LoginRepository)
    : AbstractService<Login, String>(repo), LoginService {
    override fun add(item: Login): Login = repo.save(item)

    override fun get(id: String): Login? = repo.findByDevice(id)
}
