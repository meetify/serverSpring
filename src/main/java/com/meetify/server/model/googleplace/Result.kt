package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

import java.io.Serializable
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Result(var geometry: Geometry = Geometry(),
                  @JsonProperty("opening_hours") var openingHours: OpeningHours = OpeningHours(),
                  @JsonProperty("formatted_address") var formattedAddress: String = "",
                  @JsonProperty("formatted_phone_number") var formattedPhoneNumber: String = "",
                  @JsonProperty("international_phone_number") var internationalPhoneNumber: String = "",
                  @JsonProperty("place_id") var placeId: String = "",
                  var icon: String = "",
                  var id: String = "",
                  var name: String = "",
                  var reference: String = "",
                  var scope: String = "",
                  var vicinity: String = "",
                  var website: String = "",
                  var url: String = "",
                  var rating: Double = 0.0,
                  @JsonProperty("permanently_closed") var permanentlyClosed: Boolean = false,
                  @JsonProperty("utc_offset") var utcOffset: Int = 0,
                  @JsonProperty("price_level") var priceLevel: Int = 0,
                  var types: List<String> = ArrayList(),
                  var photos: List<Photo> = ArrayList(),
                  @JsonProperty("alt_ids") var alternativeIds: List<AlternativeId> = ArrayList(),
                  @JsonProperty("address_components") var addressComponents: List<AddressComponent> = ArrayList(),
                  var reviews: List<Review> = ArrayList()) : Serializable