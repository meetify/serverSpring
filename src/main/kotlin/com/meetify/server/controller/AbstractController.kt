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

    @ResponseBody @GetMapping
    override fun get(@RequestParam("ids") ids: HashSet<Long>, @RequestParam("device") device: String
    ) = login(device).let { service.get(ids).filter { item -> item.isAvailableFor(it.id) } }

    @ResponseBody @PostMapping
    override fun post(@RequestBody item: T, @RequestParam("device") device: String) = login(device).let { service.edit(item) }

    @ResponseBody @PutMapping
    override fun put(@RequestBody item: T, @RequestParam("device") device: String) = login(device).let { service.add(item) }

    @ResponseBody @DeleteMapping
    override fun delete(@RequestBody item: T, @RequestParam("device") device: String) = login(device).let { service.delete(item) }

    /**
     * Method, that allows to get login of user by it's device.
     * If there no such login, SecurityException is thrown.
     * @param  device UUID that allows to find information about user. More about this in [Login].
     * @return login found by [device]
     */
    internal fun login(device: String) = loginService.get(device) ?: throw SecurityException("device exception")
}
