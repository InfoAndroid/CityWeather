package com.example.cityweather.model

import com.example.cityweather.common.RequestCompleteListener
import com.example.cityweather.model.data_class.City
import com.example.cityweather.model.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}