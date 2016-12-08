package com.meetify.server.controller

import com.meetify.server.model.Id
import com.meetify.server.model.entity.BaseEntity
import com.meetify.server.model.entity.Login
import com.meetify.server.repository.BaseRepository
import com.meetify.server.utils.JsonUtils
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager

/**
 * This class represents some base controller. It has pre-implemented methods get, post, put, delete.
 * They are implemented in that way so they can be used without modifications on some usual tasks.
 * @version 0.0.1
 * @since   0.0.1
 * @property    repo    Some custom repository that represents connection with some database
 * @property    manager Entity manager used for queries.
 * @constructor         Autowired by Spring.
 */
@Suppress("unused")
abstract class BaseController<T : BaseEntity>(val repo: BaseRepository<T>,
                                              val manager: EntityManager) {

    /**
     * Returns a list with information about Ts.
     * Unknown ids in [idsJson] list are ignored.
     * @param   idsJson json representation of collection of the ids of requested Ts.
     * @return          collection with Ts info.
     */
    @ResponseBody @GetMapping
    open fun get(@RequestParam(name = "ids") idsJson: String,
                 @RequestParam(name = "device") device: String): ArrayList<T> {
        return getFromCollection(JsonUtils.getList(idsJson), device)
    }

    /**
     * Creates or updates T object. If object with the same id was found, it updates info about selected place.
     * Otherwise, it just saves new object. If param 'create' contains non-empty string,
     * server trying to save object with generated id,
     * but it isn't allowed when Controller doesn't override method [post]. For example,
     * it there is no way to generate id for users, but it is quite easy for places.
     * @param   t       json representation of T instance.
     * @param   create  if this value isn't empty,
     * @return    saved object
     */
    @ResponseBody @PostMapping
    open fun post(@RequestBody t: T,
                  @RequestParam(name = "create", defaultValue = "") create: String,
                  @RequestParam(name = "device") device: String): T {
        check(t, device)
        return repo.save(if (create == "" || create.trim().isEmpty()) t else generate(t))
    }

    /**
     * Creates new Object with some generated Id.
     * @param   t json representation of T instance.
     * @return    saved object
     */
    @ResponseBody @PutMapping
    open fun put(@RequestBody t: T,
                 @RequestParam(name = "device") device: String): T {
        check(t, device)
        return repo.save(generate(t))
    }

    /**
     * Deletes given object.
     * @param   t json representation of T instance.
     */
    @ResponseBody @DeleteMapping
    open fun delete(@RequestBody t: T,
                    @RequestParam(name = "device") device: String) {
        check(t, device)
        repo.delete(t)
    }

    /**
     * This function allows to map some [ids] to objects.
     * If some id doesn't have mapping, it is simply ignored.
     * @param   ids Collection that contains some ids.
     * @return      Collection that contains the instances of defined objects.
     */
    internal fun getFromCollection(ids: Collection<Id>, device: String): ArrayList<T> = ArrayList<T>().apply {
        val login = getLogin(device)
        println("ids in are $ids")
        ids.map { repo.findById(it) }
                .filter { it.isPresent }
                .map { it.get() }
                .filter { it != null && it.isAvailable(login.id) }
                .forEach { add(it) }
    }

    internal fun getLogin(device: String): Login {
        println("$device with ${device.trim()}")
        return LoginController.findByDevice(device.trim()).orElseThrow { SecurityException() }
    }

    internal fun check(t: T, device: String) {
        if (t.isAvailable(getLogin(device).id)) return
        throw SecurityException("check exception")
    }

    /**
     * Function which puts on t.id maximum id in the table which the object belongs.
     * @return      modified T.
     */
    internal fun generate(t: T): T = t.apply {
        id = repo.findMaxId().orElse(Id(0)).apply { id++ }
    }
}
