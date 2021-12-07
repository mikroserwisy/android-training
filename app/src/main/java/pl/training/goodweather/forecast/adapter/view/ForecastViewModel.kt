package pl.training.goodweather.forecast.adapter.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.goodweather.GoodWeatherApplication.Companion.componentsGraph
import pl.training.goodweather.forecast.model.ForecastService
import javax.inject.Inject

class ForecastViewModel : ViewModel() {

    @Inject
    lateinit var forecastService: ForecastService

    init {
        componentsGraph.inject(this)
    }

    private val forecastData = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = forecastData

    fun refreshForecast(city: String) {
        viewModelScope.launch {
            forecastData.value = forecastService.getCachedForecast(city).map(::toViewModel)
            forecastData.value = forecastService.getForecast(city).map(::toViewModel)
        }
    }

}