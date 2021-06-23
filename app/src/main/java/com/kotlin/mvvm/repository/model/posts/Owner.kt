package com.kotlin.mvvm.repository.model.posts

import com.google.gson.annotations.SerializedName

data class Owner (

	@SerializedName("id") val id : String,
	@SerializedName("lastName") val lastName : String,
	@SerializedName("title") val title : String,
	@SerializedName("email") val email : String,
	@SerializedName("picture") val picture : String,
	@SerializedName("firstName") val firstName : String
)