package com.paf.synthlib.navigation

enum class Destination {
    Home,
    PresetDetails;
    companion object {
        fun fromRoute(route: String?): Destination =
            when (route?.substringBefore("/")) {
                Home.name -> Home
                PresetDetails.name -> PresetDetails
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}