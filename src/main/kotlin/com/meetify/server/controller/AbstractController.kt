package com.meetify.server.controller

import com.meetify.server.model.entity.BaseEntity
import com.meetify.server.model.entity.Login
import com.meetify.server.service.BaseService
import com.meetify.server.service.LoginService
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * This class represents some base controller. It has pre-implemented methods get, post, put, delete.
 * They are implemented in that way so they can be used without modifications on some usual tasks.
 * @since  0.1.0
 * @property service Layer between repository and controllers.
 * @property loginService Service, that allows perform security control.
 * @constructor     Autowired by Spring.
 */
abstract class AbstractController<T : BaseEntity>(
        open val service: BaseService<T, Long>,
        open val loginService: LoginService) : BaseController<T> {

    @GetMapping
    override fun get(@RequestParam("ids") ids: HashSet<Long>, @RequestParam("device") device: String): List<T>
            = login(device).let { login -> service.get(ids).filter { it.isAvailableFor(login.id) } }

    @PostMapping
    override fun post(@RequestBody item: T, @RequestParam("device") device: String): T
            = login(device).let { service.edit(item) }

    @PutMapping
    override fun put(@RequestBody item: T, @RequestParam("device") device: String): T
            = login(device).let { service.add(item) }

    @DeleteMapping
    override fun delete(@RequestBody item: T, @RequestParam("device") device: String): Unit
            = login(device).let { service.delete(item) }

    /**
     * Method, that allows to get login of user by it's device.
     * If there no such login, SecurityException is thrown.
     * @param  device UUID that allows to find information about user. More about this in [Login].
     * @return login found by [device]
     */
    internal fun login(device: String): Login
            = loginService.get(device) ?: throw SecurityException("unknown device")
}
