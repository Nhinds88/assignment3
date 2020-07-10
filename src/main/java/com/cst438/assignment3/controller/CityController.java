package com.cst438.assignment3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cst438.assignment2.domain.*;
import com.cst438.assignment2.service.CityService;

@Controller
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@GetMapping("/cities/{city}")
	public String getWeather(@PathVariable("city") String cityName, Model model) {

		CityInfo cityInfo = cityService.getCityInfo(cityName);

		if(cityInfo == null) {
			return "no_city";
		} else {
			model.addAttribute("cityInfo", cityInfo);

			return "city_show";
		}
	}
}