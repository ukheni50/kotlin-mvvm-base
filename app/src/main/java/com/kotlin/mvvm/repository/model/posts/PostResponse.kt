package com.kotlin.mvvm.repository.model.posts

import com.google.gson.annotations.SerializedName


data class PostResponse (

	@SerializedName("data") val data : List<Data>,
	@SerializedName("total") val total : Int,
	@SerializedName("page") val page : Int,
	@SerializedName("limit") val limit : Int,
	@SerializedName("offset") val offset : Int
)