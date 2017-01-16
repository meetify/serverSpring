package com.meetify.server.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.meetify.server.service.PlaceService
import java.io.Serializable
import java.util.*

/**
 * Google place representation. It can be taken using [PlaceService.nearby].
 * All documentation about this class & inner classes was taken from
 * [Google documentation](https://developers.google.com/places/web-service/details)
 * and it's licensed under the [CC BY 3.0](https://creativecommons.org/licenses/by/3.0/).
 * No changes were made, except of some in-page redirects.
 * @property results      the detailed information about the place requested.
 * @property status      metadata on the request.
 * @property htmlAttributions a set of attributions about this listing which must be displayed to the user.
 * @property nextPageToken   a token which can be used to access some more places instead of default 20.
 * @since    0.1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class GooglePlace(
        var results: List<Result> = ArrayList(),
        val status: String = "",
        @JsonProperty("html_attributions") val htmlAttributions: List<String> = ArrayList(),
        @JsonProperty("next_page_token") val nextPageToken: String = "") : Serializable {
    /**
     * @property day  a number from 0–6, corresponding to the days of the week, starting on Sunday. For example, 2 means Tuesday.
     * @property time  may contain a time of day in 24-hour HHMM format. Values are in the range 0000–2359. The time will be reported in the place’s time zone.
     */
    data class DayTime(
            val day: Int = 0,
            val time: Int = 0) : Serializable

    /**
     * @property location the geocoded latitude,longitude value for this place.
     * @property viewport the preferred viewport when displaying this place on a map as a LatLngBounds if it is known.
     */
    data class Geometry(
            val location: GoogleLocation = GoogleLocation(),
            val viewport: Viewport = Viewport()) : Serializable

    /**
     * @property openNow is a boolean value indicating if the place is open at the current time.
     * @property periods is an array of opening periods covering seven days, starting from Sunday, in chronological order.
     * @property weekdayText is an array of seven strings representing the formatted opening hours for each day of the week.
     */
    data class OpeningHours(
            @JsonProperty("open_now") val openNow: Boolean = false,
            @JsonProperty("weekday_text") val weekdayText: List<String> = ArrayList(),
            val periods: List<Period> = ArrayList()) : Serializable

    /**
     * Contains the geocoded latitude, longitude value for this place.
     * @property lat latitude
     * @property lng longitude
     */
    data class GoogleLocation(
            val lat: Double = 0.0,
            val lng: Double = 0.0) : Serializable

    /**
     * @property open a pair of day and time objects describing when the place opens:
     * @property close may contain a pair of day and time objects describing when the place closes.
     */
    data class Period(
            val open: DayTime = DayTime(),
            val close: DayTime = DayTime()) : Serializable

    /**
     * @property photoReference — a string used to identify the photo when you perform a Photo request.
     * @property height — the maximum height of the image.
     * @property width — the maximum width of the image.
     * @property htmlAttributions — any required attributions. This field will always be present, but may be empty.
     */
    data class Photo(
            val width: Double = 0.0,
            val height: Double = 0.0,
            @JsonProperty("html_attributions") val htmlAttributions: List<String> = ArrayList(),
            @JsonProperty("photo_reference") var photoReference: StringBuilder = StringBuilder("")) : Serializable

    /**
     * @property formattedAddress is a string containing the human-readable address of this place.
     * @property formattedPhoneNumber the place's phone number in its local format.
     * @property geometry [Geometry]
     * @property icon the URL of a suggested icon which may be displayed to the user when indicating this result on a map.
     * @property id a unique stable identifier denoting this place.
     * @property internationalPhoneNumber the place's phone number in international format.
     * @property name the human-readable name for the returned result.
     * @property openingHours [OpeningHours]
     * @property permanentlyClosed is a boolean flag indicating whether the place has permanently shut down (value true).
     * @property photos an array of [Photo] objects, each containing a reference to an image.
     * @property placeId textual identifier that uniquely identifies a place.
     * @property scope can contain value APP, if it available only for application. Otherwise, it can be used everywhere.
     * @property priceLevel the price level of the place, on a scale of 0 to 4 based on local prices.
     * @property rating the place's rating, from 1.0 to 5.0, based on aggregated user reviews.
     * @property reference a token that can be used to query the Details service in future.
     * @property types an array of feature types describing the given result.
     * @property url the URL of the official Google page for this place.
     * @property utcOffset the number of minutes this place’s current timezone is offset from UTC.
     * @property vicinity lists a simplified address for the place, with the street name, street number, and locality.
     * @property website lists the authoritative website for this place, such as a business' homepage.
     */
    data class Result(
            val geometry: Geometry = Geometry(),
            @JsonProperty("opening_hours") val openingHours: OpeningHours = OpeningHours(),
            @JsonProperty("formatted_address") val formattedAddress: String = "",
            @JsonProperty("formatted_phone_number") val formattedPhoneNumber: String = "",
            @JsonProperty("international_phone_number") val internationalPhoneNumber: String = "",
            @JsonProperty("place_id") val placeId: String = "",
            val icon: String = "",
            val id: String = "",
            val name: String = "",
            val reference: String = "",
            val scope: String = "",
            val vicinity: String = "",
            val website: String = "",
            val url: String = "",
            val rating: Double = 0.0,
            @JsonProperty("permanently_closed") val permanentlyClosed: Boolean = false,
            @JsonProperty("utc_offset") val utcOffset: Int = 0,
            @JsonProperty("price_level") val priceLevel: Int = 0,
            val types: List<String> = ArrayList(),
            val photos: List<Photo> = ArrayList()) : Serializable

    /**
     * Contains the preferred viewport when displaying this place on a map as a LatLngBounds if it is known.
     * @property northeast North east bound.
     * @property southwest South west bound.
     */
    data class Viewport(
            val northeast: GoogleLocation = GoogleLocation(),
            val southwest: GoogleLocation = GoogleLocation()) : Serializable
}
