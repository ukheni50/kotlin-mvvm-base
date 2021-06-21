package com.kotlin.mvvm.ui.countries

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kotlin.mvvm.R
import com.kotlin.mvvm.databinding.RowCountryListBinding
import com.kotlin.mvvm.repository.model.countries.Country

class CountriesAdapter(private val countries: List<Country>) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {


    var onCountryClicked: ((Country) -> Unit)? = null

    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowCountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     *
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.bindView(country)
    }

    /**
     *
     */
    override fun getItemCount(): Int = countries.size

    /**
     *
     */
    inner class ViewHolder(private val binding: RowCountryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onCountryClicked?.invoke(countries[adapterPosition])
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + binding.tvCountryName.text + "'"
        }

        fun bindView(country: Country) {
            binding.tvCountryName.text = country.displayName

            Glide.with(binding.root.context)
                .load(Uri.parse(country.countryFagUrl))
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_banner_image)
                        .error(R.drawable.loading_banner_image)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(binding.ivCountryImage)
        }
    }
}
