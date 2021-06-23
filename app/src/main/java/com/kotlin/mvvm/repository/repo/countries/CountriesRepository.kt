package com.kotlin.mvvm.repository.repo.countries

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.kotlin.mvvm.repository.api.Postservice
import com.kotlin.mvvm.repository.db.countries.CountriesDao
import com.kotlin.mvvm.repository.model.countries.Country
import com.kotlin.mvvm.utils.CountryNameMapping
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Waheed on 08,November,2019
 */

/**
 * Repository abstracts the logic of fetching the data and persisting it for
 * offline. They are the data source as the single source of truth.
 *
 */

@Singleton
class CountriesRepository @Inject constructor(
    private val countriesDao: CountriesDao,
    @ApplicationContext val context: Context, private val service: Postservice
) {

    /**
     * Fetch the news articles from database if exist else fetch from web
     * and persist them in the database
     */
    suspend fun getCountries(): List<Country> {
        withContext(Dispatchers.IO) {
            val list: List<String>? = getListFromAssets()
            val listOfCountries = ArrayList<Country>()
            list?.forEach { item ->
                val country = Country().apply {
                    countryName = item
                    displayName = getDisplayName(item)
                    countryFagUrl = getFlagUrl(item)
                    countryKey = CountryNameMapping.getCountryKey(item)
                }
                listOfCountries.add(country)
            }
            countriesDao.deleteAllCountries()
            countriesDao.insertCountries(listOfCountries)
            val response = service.getPostsSource(1)
            Log.d("post", response.toString())
            // Storing data into SharedPreferences
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()
            myEdit.putString("response", response.toString())
            myEdit.apply()

        }

        return countriesDao.getCountries()
    }


    private suspend fun getListFromAssets(): List<String>? = withContext(Dispatchers.IO) {
        val asList = context.assets.list("countries")?.asList<String>()
        asList
    }


    private fun getDisplayName(name: String): String =
        name.replace("_", " ").replace(".png", "")


    private fun getFlagUrl(name: String): String = "file:///android_asset/countries/$name"

}