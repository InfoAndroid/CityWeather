package com.example.cityweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cityweather.databinding.FragmentFirstBinding
import com.example.cityweather.model.WeatherInfoShowModel
import com.example.cityweather.model.WeatherInfoShowModelImpl
import com.example.cityweather.model.data_class.City
import com.example.cityweather.viewmodel.WeatherInfoViewModel

class CityListFragment : Fragment(), CityListAdapter.OnItemClickListener {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherInfoViewModel
    private lateinit var model: WeatherInfoShowModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(WeatherInfoViewModel::class.java)
        model = WeatherInfoShowModelImpl(requireContext().applicationContext)
        viewModel.getCityList(model)
        liveDataListeners()
    }

    private fun liveDataListeners() {
        // viewModel.getWeatherInfo(5128638, model)

        viewModel.cityListLiveData.observe(viewLifecycleOwner)
        { cities ->
            val cityAdapter = CityListAdapter(cities, this)
            binding.cityRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.cityRecyclerview.adapter = cityAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(city: City) {
        val bundle = Bundle()
        bundle.putSerializable("ID", city.id)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
    }
}