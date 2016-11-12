package com.meetify.server.controller

import com.meetify.server.model.Id
import com.meetify.server.model.entity.BaseEntity
import com.meetify.server.repository.BaseRepository
import com.meetify.server.utils.JsonUtils
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager

/**
 * Цей клас є абстрактним базовим для більшості контролерів.
 * Він надає певні методи для позбавлення необхідності у реалізації ідентичних для усіх контролерів методів.
 * Так, наприклад, методи в [PhotoController] повністю наслідувані від цього класу, у інших - реалізовані лише окремі методи.
 *
 * @version 0.0.1
 * @since   0.0.1
 * @property    repo    Репозиторій, який визначений за рахунок Generic-типу при оголошенні класу.
 * @property    manager Диспетчер сутностей, який є необхідним для запитів до бази даних.
 * @constructor         Приймає усі поля класу, які мають бути передані за рахунок @Autowired-аннотації при конструкторі класів-спадкоємців.
 */
@Suppress("unused")
abstract class BaseController<T : BaseEntity>(val repo: BaseRepository<T>,
                                              val manager: EntityManager) {

    /**
     * Повертає колекцію з інформацією про необхідні об'єкти.
     * Невідомі ідентифікатори у [idsJson] просто ігноруються.
     * @param   idsJson json представлення колекції ідентифікаторів.
     * @return          колекція із знайденою інформацією.
     */
    @ResponseBody @GetMapping
    open fun get(@RequestParam(name = "ids") idsJson: String): ArrayList<T> = getFromCollection(JsonUtils.getList(idsJson))

    /**
     * Створює чи оновлює інформацію про певний об'єкт.
     * Якщо заданий параметер [create], то у будь-якому випадку буде згенерований певний ідентифікатор,
     * таким чином у базу даних буде переданий новий унікальний для бази об'єкт.
     * Інакше, сервер просто робить спробу записати у базу передану інформацію (це і є способом оновлення).
     * @param   t       json представлення екземляру T.
     * @param   create  параметр, який впливає на необхідність генерації ідентифікатору.
     * @return    збережений об'єкт.
     */
    @ResponseBody @PostMapping
    open fun post(@RequestBody t: T,
                  @RequestParam(name = "create", defaultValue = "") create: String): T = repo.save(if (create.trim().isEmpty()) t else generate(t))

    /**
     * Є аналогом методу [post], за винятком того, що цей метод у будь-якому випадку генерує який-небудь ідентифікатор.
     * @param   t json представлення екземляру T.
     * @return    збережений об'єкт.
     */
    @ResponseBody @PutMapping
    open fun put(@RequestBody t: T): T = repo.save(generate(t))

    /**
     * Видаляє переданий об'єкт з бази даних.
     * @param   t json представлення екземляру T.
     */
    @ResponseBody @DeleteMapping
    open fun delete(@RequestBody t: T) = repo.delete(t)

    /**
     * Функція, що дозволяє відобразити певні індентифікатори до їх об'єктів у базі даних.
     * Якщо ідентифікатор не має відображення, він ігнорується.
     * @param   ids Колекція ідентифікаторів.
     * @return      Список знайдених об'єктів.
     */
    internal open fun getFromCollection(ids: Collection<Id>): ArrayList<T> = ArrayList<T>().apply {
        ids.forEach { repo.findById(it).ifPresent { add(it) } }
    }

//    /**
//     * Функція, яка
//     * Function that allows to find some maximum id in the table which the [t] belongs.
//     * @param   t   Instance of some class, in which should maximum id be found.
//     * @return      Id that has maximum id + 1.
//     */
//    internal open fun runMaxQuery(t: T): Id {
//        return Optional.ofNullable(manager
//                .createQuery("select max(t.id) from ${t.javaClass.name} as t")
//                .resultList[0]).orElse(Id(-1)) as Id
//    }

    /**
     * Функція, яка модифікує об'єкт шляхом генерації певного унікального ідентифікатору.
     * @param   t   об'єкт, ідентифікатор якого має бути згенерованим.
     * @return      модифікований переданий параметр.
     */
    internal open fun generate(t: T): T = t.apply {
        t.id = (Optional.ofNullable(manager
                .createQuery("select max(t.id) from ${t.javaClass.name} as t")
                .resultList[0]).orElse(Id(-1)) as Id).apply { id++ }
    }
}
