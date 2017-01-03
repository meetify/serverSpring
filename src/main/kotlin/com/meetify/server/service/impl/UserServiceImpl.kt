package com.meetify.server.service.impl

import com.meetify.server.model.Location
import com.meetify.server.model.UserExtended
import com.meetify.server.model.entity.Place
import com.meetify.server.model.entity.User
import com.meetify.server.repository.PlaceRepository
import com.meetify.server.repository.UserRepository
import com.meetify.server.service.PlaceService
import com.meetify.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * @suppress
 */
@Service
class UserServiceImpl @Autowired constructor(repo: UserRepository,
                                             val placeRepository: PlaceRepository)
    : AbstractLongService<User>(repo), UserService {

    private val placeService: PlaceService by lazy { PlaceServiceImpl(placeRepository, this) }

    override fun unvisited(user: User): HashSet<Place>
            = user.allowed.filterValues { !it }.keys.map { placeService.get(it)!! }.toHashSet().apply {
        edit(user.apply {
            allowed.replaceAll { _, _ -> true }
        })
    }

    override fun friends(id: Long): HashSet<User>
            = HashSet<User>().apply { get(id)?.friends?.map { get(it)?.let { add(it) } } }

    override fun update(user: User, location: Location) = edit(user.apply {
        user.location = location
        user.time = System.currentTimeMillis()
    })

    override fun edit(item: User): User = super.edit(item.apply {
        get(item.id)?.let {
            allowed = it.allowed
            created = it.created
        }
    })

    override fun update(user: User): UserExtended = UserExtended(
            friends(user.id),
            placeService.get(user.created),
            placeService.get(user.allowed.keys))

    override fun add(item: User): User = edit(item)

    override fun add(items: Collection<User>) = edit(items)
}