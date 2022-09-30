package com.example.project3

import com.google.gson.annotations.SerializedName


class BestMovies {
//    @SerializedName("rank")
//    var rank = 0
    @JvmField
    @SerializedName("title")
    var title: String? = null

//    @JvmField
//    @SerializedName("author")
//    var author: String? = null

    //TODO bookImageUrl
    @SerializedName("poster_path")
    var movieImageUrl: String? = null


    //TODO description
    @SerializedName("overview")
    var overview: String? = null


}