//package com.meetify.server.repository.cache
//
//import com.meetify.server.model.Id
//import com.meetify.server.model.entity.BaseEntity
//import com.meetify.server.repository.BaseRepository
//import org.springframework.stereotype.Repository
//import java.io.Serializable
//import java.util.*
//
///**
// * Created on 10.12.2016.
// * @author  Dmitry Baynak
// */
//
////@Repository
//abstract class CacheRepository<T : BaseEntity> : Serializable {
//
//    private val repo: BaseRepository<T>
//
//    private val items:HashMap<Id, T>
//
//    private val queue:LinkedList<T>
//
//    constructor(repo: BaseRepository<T>,
//                items: HashMap<Id, T> = HashMap<Id, T>(),
//                queue: LinkedList<T> = LinkedList<T>()) {
//        println("Init of CacheRepository")
//        this.repo = repo
//        this.items = items
//        this.queue = queue
//        Thread(Runnable {
//            while (queue.isNotEmpty()) {
//                repo.save(queue.pollFirst())
//            }
//            Thread.sleep(1000)
//        }).start()
//        repo.findAll().forEach {
//            items.put(it, it)
//        }
//    }
//
//    fun exists(p0: Id): Boolean = items[p0] != null
//
//    fun findById(id: Id): T? = items[id]
//
//    fun save(p0: T): T = p0.apply {
//        items.put(id, this)
//        queue.addLast(p0)
//    }
//
//    fun delete(p0: T) = items.remove(p0.id)
//
//    fun delete(p0: Id) = items.remove(p0)
//}