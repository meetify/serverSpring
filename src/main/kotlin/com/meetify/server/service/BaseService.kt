package com.meetify.server.service

import java.io.Serializable
import java.util.*

/**
 * BaseService is an interface, that allows to create a layer of abstraction between Repository and Controller.
 * It's contains a logic that works with some information from database, gives to Controller in appropriate form.
 * T is a item, which we are working with.
 * V is item's ID.
 * @since 0.3.0
 */
interface BaseService<T, in V : Serializable> {

    /**
     * Saves [item] to database with generating id, (except LoginService, where it's forbidden).
     * @param item given item.
     * @return saved item with generated id.
     */
    fun add(item: T): T

    /**
     * Saves [items] to database with generating id, (except LoginService, where it's forbidden).
     * @param items given items.
     */
    fun add(items: Collection<T>): Unit

    /**
     * This method allows finding object by [id].
     * @param  id id of object that should be found in database.
     * @return optional that has information about found object.
     */
    fun get(id: V): T?

    /**
     * This method allows finding objects by [ids]. If some object isn't present, it's ignored.
     * @param  ids ids of objects which should be found in database.
     * @return collection with items
     */
    fun get(ids: Collection<V>): HashSet<T>

    /**
     * This method allows deleting [item] from database.
     * @param item object that should be deleted from database.
     */
    fun delete(item: T): Unit

    /**
     * This method allows deleting object from database by it's [id].
     * @param id id of object that should be deleted from database.
     */
    fun delete(id: V): Unit

    /**
     * Updates some object's information in database with given [item].
     * @param item given item.
     * @return edited item.
     */
    fun edit(item: T): T

    /**
     * Updates objects' information in database with given [items].
     * @param items given items.
     */
    fun edit(items: Collection<T>): Unit
}