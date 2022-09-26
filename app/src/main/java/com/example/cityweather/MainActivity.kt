package com.example.cityweather

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.cityweather.databinding.ActivityMainBinding
import com.example.cityweather.model.WeatherInfoShowModel
import com.example.cityweather.model.WeatherInfoShowModelImpl
import com.example.cityweather.model.data_class.City
import com.example.cityweather.viewmodel.WeatherInfoViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var model: WeatherInfoShowModel
    private lateinit var viewModel: WeatherInfoViewModel
    private var cityList: MutableList<City> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

         navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
           // navController.navigate(R.id.action_FirstFragment_to_ThirdFragment,)
        }
        model = WeatherInfoShowModelImpl(applicationContext)

        viewModel = ViewModelProviders.of(this).get(WeatherInfoViewModel::class.java)

    }

    private fun setLiveDataListeners() {
        viewModel.getWeatherInfo(5128638, model)
        viewModel.cityListLiveData.observe(this, object : Observer<MutableList<City>> {
            override fun onChanged(cities: MutableList<City>) {
            Log.d("ad",cities.get(2).name)
            }
        })

        viewModel.cityListFailureLiveData.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })

        viewModel.weatherInfoLiveData.observe(this, Observer { weatherData ->
            Log.d("weatherData",weatherData.temperature);
            //setWeatherInfo(weatherData)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}