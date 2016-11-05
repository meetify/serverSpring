package com.meetify.server.controller

import com.meetify.server.model.BaseEntity
import com.meetify.server.model.Id
import com.meetify.server.repository.BaseRepository
import com.meetify.server.utils.JsonUtils
import com.meetify.server.utils.JsonUtils.mapper
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager

/**
 * This class represents some base controller. It has pre-implemented methods get, post, put, delete.
 * They are implemented in that way so they can be used without modifications on some usual tasks.
 * @author  Dmitry Baynak
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
    open fun get(@RequestParam(name = "ids") idsJson: String): ArrayList<T> = getFromCollection(JsonUtils.getList(idsJson))

    /**
     * Creates or updates T object. If object with the same id was found, it updates info about selected place.
     * Otherwise, it just saves new object. If param 'create' contains non-empty string,
     * server trying to save object with generated id,
     * but it isn't allowed when Controller doesn't override method [post]. For example,
     * it there is no way to generate id for users, but it is quite easy for places.
     * @param   t json representation of T instance.
     * @return    saved object
     */
    @ResponseBody @PostMapping
    open fun post(@RequestBody t: T, @RequestParam(name = "create", defaultValue = "") create: String): T {
        println(">$create<")
        println(mapper.writeValueAsString(t))
        return repo.save(if (create.trim().isEmpty()) {
            t
        } else {
            generate(t)
        })
    }

    /**
     * Creates new Object with some generated Id.
     * @param   t json representation of T instance.
     * @return    saved object
     */
    @ResponseBody @PutMapping
    open fun put(@RequestBody t: T): T = repo.save(generate(t))

    /**
     * Deletes given object.
     * @param   t json representation of T instance.
     */
    @ResponseBody @DeleteMapping
    open fun delete(@RequestBody t: T) = repo.delete(t)

    /**
     * This function allows to map some [ids] to objects.
     * If some id doesn't have mapping, it is simply ignored.
     * @param   ids Collection that contains some ids.
     * @return      Collection that contains the instances of defined objects.
     */
    open fun getFromCollection(ids: Collection<Id>): ArrayList<T> = ArrayList<T>().apply { ids.forEach { repo.findById(it).ifPresent { add(it) } } }

    /**
     * Function that allows to find some maximum id in the table which the [t] belongs.
     * String.
     * String.
     * @param   t   Instance of some class, in which should maximum id be found.
     * @return      Id that has maximum id + 1.
     */
    open fun runMaxQuery(t: T): Id {
        return Optional.ofNullable(manager
                .createQuery("select max(t.id) from ${t.javaClass.name} as t")
                .resultList[0]).orElse(Id(-1)) as Id
    }

    /**
     * Function which puts on t.id maximum id in the table which the object belongs.
     * @return      modified T.
     */
    open fun generate(t: T): T = t.apply { t.id = runMaxQuery(this).apply { id++ } }
}
