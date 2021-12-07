package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import pl.training.goodweather.commons.getProperty
import pl.training.goodweather.commons.hideKeyboard
import pl.training.goodweather.commons.setDrawable
import pl.training.goodweather.commons.setProperty
import pl.training.goodweather.databinding.ActivityForecastBinding

class ForecastActivity : AppCompatActivity() {

    companion object {

        const val CITY_KEY = "cityName"
        const val DEFAULT_CITY_NAME = "warsaw"

    }

    private val viewModel: ForecastViewModel by viewModels()
    private val forecastListAdapter = ForecastListAdapter()
    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        bindViews()
        loadLatestForecast()
    }

    private fun initViews() {
        binding.forecastRecyclerView.layoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        binding.forecastRecyclerView.adapter = forecastListAdapter
    }

    private fun bindViews() {
        viewModel.forecast.observe(this, ::updateView)
        binding.checkButton.setOnClickListener {
            it.hideKeyboard()
            val cityName = binding.cityNameEditText.text.toString()
            setProperty(CITY_KEY, cityName)
            viewModel.refreshForecast(cityName)
        }
    }

    private fun loadLatestForecast() {
        getProperty(CITY_KEY, DEFAULT_CITY_NAME)?.let {
            viewModel.refreshForecast(it)
            binding.cityNameEditText.setText(it)
        }
    }

    private fun updateView(forecast: List<DayForecastViewModel>) {
        with(forecast.first()) {
            binding.iconImage.setDrawable(icon)
            binding.descriptionTextView.text = description
            binding.temperatureTextView.text = temperature
            binding.pressureTextView.text = pressure
        }
        forecastListAdapter.update(forecast.drop(1))
    }

}