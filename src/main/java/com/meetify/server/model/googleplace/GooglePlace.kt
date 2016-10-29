package com.meetify.server.model.googleplace

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*


@JsonInclude(JsonInclude.Include.NON_NULL)
data class GooglePlace constructor(var results: List<Result> = ArrayList(),
                                   var status: String = "",
                                   @JsonProperty("html_attributions") var htmlAttributions: List<String> = ArrayList(),
                                   @JsonProperty("next_page_token") var nextPageToken: String = "") : Serializable



