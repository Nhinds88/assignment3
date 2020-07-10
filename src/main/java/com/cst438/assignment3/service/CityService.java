package com.cst438.assignment3.service;

import com.cst438.assignment3.domain.*;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private WeatherService weatherService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private FanoutExchange fanout;
	
	public CityInfo getCityInfo(String cityName) {
		
		List<City> city = cityRepository.findByName(cityName);

		if (city.size() == 0) {
			return null;
		}

		City c = city.get(0);
		Country country = countryRepository.findByCode(c.getCountryCode());
		TempAndTime weather = weatherService.getTempAndTime(cityName);

		return new CityInfo(c, country.getName(), weather.getFahr(), weather.getLocalTime());
	}

	public void requestReservation(
			String cityName,
			String level,
			String email) {
		String msg = "{\"cityName\": \"" + cityName + "\" \"level\": \"" + level + "\" \"email\": \"" + email + "\"}";
		System.out.println("Sending message:" + msg);
		rabbitTemplate.convertSendAndReceive(fanout.getName(), "", msg); // routing key none.
	}
}