package com.meetify.server.controller

import com.meetify.server.model.entity.BaseEntity
import com.meetify.server.service.BaseService
import com.meetify.server.service.LoginService
import com.meetify.server.util.JsonUtils
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * This class represents some base controller. It has pre-implemented methods get, post, put, delete.
 * They are implemented in that way so they can be used without modifications on some usual tasks.
 * @version 0.0.1
 * @since   0.0.1
 * @property    service Some custom repo that represents connection with some database
 * @constructor         Autowired by Spring.
 */
@Suppress("unused")
abstract class AbstractController<T : BaseEntity>(
        open val service: BaseService<T, Long>,
        val login: LoginService) : BaseController<T> {
    /**
     * Returns a list with information about Ts.
     * Unknown ids in [ids] list are ignored.
     * @param   ids json representation of collection of the ids of requested Ts.
     * @return          collection with Ts info.
     */
    @ResponseBody @GetMapping
    override fun get(@RequestParam("ids") ids: String, @RequestParam("device") device: String
    ) = login(device).let {
        HashSet<T>().apply {
            service.get(JsonUtils.getList(ids)).forEach { item ->
                if (item.isAvailableFor(it.id)) add(item)
            }
        }
    }

    /**
     * Creates or updates T object. If object with the same id was found, it updates info about selected place.
     * Otherwise, it just saves new object. If param 'create' contains non-empty string,
     * server trying to save object with generated id,
     * but it isn't allowed when Controller doesn't override method [post]. For example,
     * it there is no way to generate id for users, but it is quite easy for places.
     * @param   t       json representation of T instance.
     * @return    saved object
     */
    @ResponseBody @PostMapping
    override fun post(@RequestBody t: T, @RequestParam("device") device: String) = login(device).let { service.edit(t) }

    /**
     * Creates new Object with some generated Id.
     * @param   t json representation of T instance.
     * @return    saved object
     */
    @ResponseBody @PutMapping
    override fun put(@RequestBody t: T, @RequestParam("device") device: String) = login(device).let { service.add(t) }

    /**
     * Deletes given object.
     * @param   t json representation of T instance.
     */
    @ResponseBody @DeleteMapping
    override fun delete(@RequestBody t: T, @RequestParam("device") device: String) = login(device).let { service.delete(t) }

    internal fun login(device: String) = login.get(device) ?: throw SecurityException("device exception")
}
