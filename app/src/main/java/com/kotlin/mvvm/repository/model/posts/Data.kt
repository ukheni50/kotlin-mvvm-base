package com.kotlin.mvvm.repository.model.posts

import com.google.gson.annotations.SerializedName


data class Data (

	@SerializedName("id") val id : String,
	@SerializedName("image") val image : String,
	@SerializedName("publishDate") val publishDate : String,
	@SerializedName("text") val text : String,
	@SerializedName("tags") val tags : List<String>,
	@SerializedName("link") val link : String,
	@SerializedName("likes") val likes : Int
)