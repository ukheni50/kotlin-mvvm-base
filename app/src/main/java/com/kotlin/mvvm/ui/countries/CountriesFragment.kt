package com.kotlin.mvvm.ui.countries

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.mvvm.databinding.FragmentCountryListBinding
import com.kotlin.mvvm.repository.model.countries.Country
import com.kotlin.mvvm.ui.news.NewsActivity
import com.kotlin.mvvm.ui.posts.Posts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_country_list.view.*


/**
 * Created by Waheed on 08,November,2019
 * Updated to dagger 2.27, 29, September 2020
 */

/**
 * A fragment representing a list of Items.
 */

@AndroidEntryPoint
class CountriesFragment : Fragment() {
    /**
     * RegistrationViewModel is used to set the username and password information (attached to
     * Activity's lifecycle and shared between different fragments)
     * EnterDetailsViewModel is used to validate the user input (attached to this
     * Fragment's lifecycle)
     *
     * They could get combined but for the sake of the codelab, we're separating them so we have
     * different ViewModels with different lifecycles.
     *
     * @Inject annotated fields will be provided by Dagger
     */
    private val countriesViewModel: CountriesViewModel by activityViewModels()

    private var columnCount = 1
    private lateinit var countriesAdapter: CountriesAdapter
    private var listOfCountries = ArrayList<Country>()
    private lateinit var thisView: View
    private lateinit var binding: FragmentCountryListBinding

    /**
     *
     */
    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            CountriesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

    }

    /**
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryListBinding.inflate(inflater, container, false)

//        thisView = inflater.inflate(R.layout.fragment_country_list, container, false)


        load()
        observeCountries()
        return binding.root
//        return thisView
    }

    /**
     *
     */
    private fun load() {
        binding.recyclerviewCountries.layoutManager =
            if (columnCount <= 1) LinearLayoutManager(context) else GridLayoutManager(
                context,
                columnCount
            )
        countriesAdapter = CountriesAdapter(listOfCountries)
        binding.recyclerviewCountries.adapter = countriesAdapter

        countriesAdapter.onCountryClicked = { country ->
            val intent = Intent(context, NewsActivity::class.java)
//            val intent = Intent(context, Posts::class.java)
            intent.putExtra(NewsActivity.KEY_COUNTRY_SHORT_KEY, country.countryKey)
            startActivity(intent)
        }
    }

    /**
     *
     */
    private fun observeCountries() {
        countriesViewModel.getCountries().observe(viewLifecycleOwner, Observer {
            // You'l get list of countries here
            it?.let {
                listOfCountries.clear()
                listOfCountries.addAll(it)
                countriesAdapter.notifyDataSetChanged()
            }
        })
    }
}
