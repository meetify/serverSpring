package com.meetify.server.model.entity

import java.io.Serializable
import javax.persistence.Embeddable

/**
 * Цей клас є вбудовуваним, який відображає географічні координати певного об'єкту.
 * @author      Дмитро Байнак
 * @version     0.0.1
 * @since       0.0.1
 * @property    lat  широта.
 * @property    lon  довгота.
 * @constructor приймає 2 два числа з плаваючою комою.
 */
@Embeddable
data class Location(var lat: Double = 0.0, var lon: Double = 0.0) : Serializable