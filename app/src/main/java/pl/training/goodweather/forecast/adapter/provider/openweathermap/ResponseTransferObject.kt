package pl.training.goodweather.forecast.adapter.provider.openweathermap

import com.google.gson.annotations.SerializedName

internal data class ResponseTransferObject(
    @SerializedName("list") val forecast: List<DayForecastTransferObject>
)