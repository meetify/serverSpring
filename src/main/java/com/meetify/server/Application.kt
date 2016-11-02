package com.meetify.server

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.meetify.server.model.GooglePlace
import org.springframework.boot.autoconfigure.SpringBootApplication


/**
 * Main class, that is marked as SpringBootApplication, so it's used to launch server.
 * @author  Dmitry Baynak
 * @version 0.0.1
 * @since   0.0.1
 * @constructor empty.
 */
@SpringBootApplication
open class Application {
    companion object {
        /**
         * Main method, that is used to run server.
         * @param   args    command-line options
         */
        @JvmStatic fun main(args: Array<String>) {
//            SpringApplication.run(Application::class.java, *args)
            val o = jacksonObjectMapper()
//            val a = "${o.writeValueAsString(User())}\n${o.writeValueAsString(Place())}\n${o.writeValueAsString(Id())}\n${o.writeValueAsString(Location())}"
//            println(a)
//            val s = """[{"id":0},{"id":81},{"id":12}]"""
//            val f = o.readValue(s, List::class.java)
//            val ids = ArrayList<Id>()
//            f.forEach {
//                ids.add(Id((it as LinkedHashMap<*, *>)["id"].toString().toLong()))
//            }
//            ids.forEach(::println)
            val s = {
                """{
            "html_attributions": [],
            "results": [
            {
                "geometry": {
                "location": {
                "lat": 48.4685415,
                "lng": 35.0493444
            }
            },
                "icon": "https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png",
                "id": "9f25012935d9e4b51332b178800554aea1876a62",
                "name": "Kniazia Volodymyra Velykoho St, 2",
                "place_id": "EpEB0LLRg9C70LjRhtGPINCa0L3Rj9C30Y8g0JLQvtC70L7QtNC40LzQuNGA0LAg0JLQtdC70LjQutC-0LPQviwgMiwg0JTQvdGW0L_RgNC-zIEsINCU0L3RltC_0YDQvtC_0LXRgtGA0L7QstGB0YzQutCwINC-0LHQu9Cw0YHRgtGMLCDQo9C60YDQsNGX0L3QsA",
                "reference": "CrQBsAAAAMrcaGvYn8pIRflTMWjKBEZ1W8g1ena48eGdPP8Hhf31RDlzwi03BziXA1r-ni4SsZEcYHJWvDD0V5IWNaHumV5DhDQMbrgug1eSbfOuhYp_CjodbSjTuatYt2xsIQE4G1HTcz_50NPdli4NYxASkUZaeOZqeyv6CjQiTHGg7WeX16dGUnIQ5W7uTfqTe63MSzRxjXgzTk0Q_dp7vuN7c_F6yZ_sUkOmvwRxuus-n97jEhDFNDzqSAJ97-XL_EcZzcmZGhTG5Nvp4bZ6s4G61wHiFwSBdx8nvQ",
                "scope": "GOOGLE",
                "types": [
                "route",
                "street_address"
                ],
                "vicinity": "Shevchenkivskyi district"
            },
            {
                "geometry": {
                "location": {
                "lat": 48.4683582,
                "lng": 35.0496272
            },
                "viewport": {
                "northeast": {
                "lat": 48.46857314999999,
                "lng": 35.0496587
            },
                "southwest": {
                "lat": 48.46828655,
                "lng": 35.04953269999999
            }
            }
            },
                "icon": "https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png",
                "id": "5574291cab6b95143c3b06f86fc4884e706487a1",
                "name": "Sushiya",
                "opening_hours": {
                "open_now": true,
                "weekday_text": []
            },
                "photos": [
                {
                    "height": 2368,
                    "html_attributions": [
                    "\u003ca href=\"https://maps.google.com/maps/contrib/101918665286332115736/photos\"\u003eIhor Vusyk\u003c/a\u003e"
                    ],
                    "photo_reference": "CoQBcwAAAFHI7kS4krekOok9mvXmepRVsxwKeEXzrmVPeN7t5zvSHbVIIrU4l4tsnC4KS6U9UpZkp85AcLGBkPfS0jyzkszIw98cLdvFZP2ylGqonwCz1GbMxrWlXeNBh21bXGpHJfOJFJGqk_RmFhecH8wX5FIYoOelKnvs56pDaUjpShoHEhBOi8wxLLRf-jy3-DGV-61oGhQLHNms1scqyhc7v-kyDdCdVEFoJA",
                    "width": 3200
                }
                ],
                "place_id": "ChIJU0LDSeji20ARDy2wIWPv3zY",
                "rating": 4.1,
                "reference": "CmRRAAAA9TEaBNVEh7sUr8NMExeB0QBSNfWPRgp42XSrzsf7cd9kNCm_Zd2SBKNctojbThCp7WzsTWL8ZZnABlCHodw91BjBrZOV9MQPqRfmaq0J-7yR27WuDZXHfFNZJ0w3FyiaEhAFvZbR-GTo1b3yw0yxPOTbGhSloJBR28fi5iHZEb7kJcEd4NoXQg",
                "scope": "GOOGLE",
                "types": [
                "restaurant",
                "food",
                "point_of_interest",
                "establishment"
                ],
                "vicinity": "Kniazia Volodymyra Velykoho Street, 1�, Dnipro"
            },
            {
                "geometry": {
                "location": {
                "lat": 48.40465889999999,
                "lng": 35.013305
            },
                "viewport": {
                "northeast": {
                "lat": 48.482421,
                "lng": 35.058669
            },
                "southwest": {
                "lat": 48.355729,
                "lng": 34.965108
            }
            }
            },
                "icon": "https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png",
                "id": "44e857f8e32a600f633b657e38fa28092aec8643",
                "name": "Shevchenkivskyi district",
                "photos": [
                {
                    "height": 2988,
                    "html_attributions": [
                    "\u003ca href=\"https://maps.google.com/maps/contrib/117541544404995855141/photos\"\u003e���� ������\u003c/a\u003e"
                    ],
                    "photo_reference": "CoQBdwAAAE24gjA-ZBOgwh6EL8-O__FrhD9t2D3bJ-17Mm2wjtZ54iLLBURaFqwnJms8z3C9AHs-BXNoPNPGcrc1rloCjG4ZtJFwcF82UChIf2uVXjMKS_nK3hWXV8N0RErZnEKH7ynstJGDVkJ5hlfodbF7L9Z5MCDinkoPqtYzkiZpOTq7EhDmwYB3QUXJCf_vmx1osksnGhQWm_xQ_aoVhS91mdmrR1j7bpQl-w",
                    "width": 5312
                }
                ],
                "place_id": "ChIJPQlFZank20ARRlE2VT3wg20",
                "reference": "CmRbAAAAFFuaJQys_P8d0jwGQJlgluwN1lI3xNKcFeOss_wuf19N_AxZhzFFJdchRIAhnq0s7aQH0bxN_eYE3ydWt5juwd8Q_IkQMJuPHDSOd3GvfrrIro3UfMyggy2O14FrsANHEhDLMQmemCcSSz1BnuGsWBymGhRUtZkfgHguCOYsBTV1UTCNuVFUwg",
                "scope": "GOOGLE",
                "types": [
                "sublocality_level_1",
                "sublocality",
                "political"
                ],
                "vicinity": "Shevchenkivskyi district"
            }
            ],
            "status": "OK"
        }"""
            }.invoke()
            val place = o.readValue(s, GooglePlace::class.java)
            println(o.writeValueAsString(place))
        }
    }
}