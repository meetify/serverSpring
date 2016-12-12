package com.meetify.server.service.impl

import com.meetify.server.model.entity.MeetifyLocation
import com.meetify.server.model.entity.User
import com.meetify.server.repository.UserRepository
import com.meetify.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by dmitry on 12/12/16.
 */
@Service
class UserServiceImpl @Autowired constructor(repo: UserRepository)
    : AbstractLongService<User>(repo), UserService {
    override fun friends(id: Long) = HashSet<User>().apply { get(id)?.friends?.map { get(it)?.let { add(it) } } }

    override fun update(user: User, location: MeetifyLocation) = edit(user.apply {
        user.location = location
        user.time = System.currentTimeMillis()
        return edit(user)
    })

    override fun edit(item: User): User = super.edit(item.apply {
        get(item.id)?.let {
            allowed = it.allowed
            created = it.created
            time = System.currentTimeMillis()
        }
    })

    override fun add(item: User): User = edit(item)

    override fun add(items: Collection<User>) = edit(items)
}