package com.example.cityweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cityweather.databinding.FragmentSecondBinding
import com.example.cityweather.model.WeatherInfoShowModel
import com.example.cityweather.model.WeatherInfoShowModelImpl
import com.example.cityweather.model.data_class.WeatherData
import com.example.cityweather.viewmodel.WeatherInfoViewModel

class WeatherDetailsFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherInfoViewModel
    private lateinit var model: WeatherInfoShowModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(WeatherInfoViewModel::class.java)
        model = WeatherInfoShowModelImpl(requireContext().applicationContext)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getInt("ID")
        viewModel.getWeatherInfo(id, model)
        liveDataListeners()
    }

    private fun liveDataListeners() {
        viewModel.progressBarLiveData.observe(
            viewLifecycleOwner, Observer { isShowLoader ->
                if (isShowLoader)
                    binding.progressBar.visibility = View.VISIBLE
                else
                    binding.progressBar.visibility = View.GONE
            })
        viewModel.weatherInfoLiveData.observe(viewLifecycleOwner, Observer { weatherData ->
            setWeatherInfo(weatherData)
        })
    }

    private fun setWeatherInfo(weatherData: WeatherData) {
        binding.layoutWeatherBasic.tvDateTime.text = weatherData.dateTime
        binding.layoutWeatherBasic.tvTemperature.text = weatherData.temperature
        binding.layoutWeatherBasic.tvCityCountry.text = weatherData.cityAndCountry
        binding.layoutWeatherBasic.tvWeatherCondition.text =
            weatherData.weatherConditionIconDescription
        binding.layoutWeatherAdditional.tvHumidityValue.text = weatherData.humidity
        binding.layoutWeatherAdditional.tvPressureValue.text = weatherData.pressure
        binding.layoutWeatherAdditional.tvVisibilityValue.text = weatherData.visibility
        binding.layoutSunsetSunrise.tvSunriseTime.text = weatherData.sunrise
        binding.layoutSunsetSunrise.tvSunriseTime.text = weatherData.sunset
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}