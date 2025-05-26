package com.example.movies_app.realtime_database

data class RealTimeUser(
    val items: RealTimeItems,
    val key: String? = ""
) {
    data class RealTimeItems(
        var userName: String = "",
        var email: String = "",
        var password: String = "",

    ) {
        constructor() : this("","","")
    }
}