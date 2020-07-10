package com.cst438.assignment3.service;
 
import com.cst438.assignment3.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;


	@Test
	public void contextLoads() {
	}


	@Test
	public void testCityFound() throws Exception {
		// test city setup and added to arraylist
		Country country = new Country("CTS", "CountryTest");
		City city = new City(1, "CityTest", "CTS", "DistrictTest", 1000);

		List<City> cityList = new ArrayList<City>();
		cityList.add(city);

		// three givens for the mock services
		given(weatherService.getTempAndTime("CityTest")).willReturn(new TempAndTime(290.41, 1594156817, 	-25200));
		given(cityRepository.findByName("CityTest")).willReturn(cityList);
		given(countryRepository.findByCode("CTS")).willReturn(country);

		// get the city created setup the expected return
		CityInfo resultCityInfo = cityService.getCityInfo("CityTest");
		CityInfo expectedCityInfo = new CityInfo(1, "CityTest", "CTS", "CountryTest", "DistrictTest", 1000, 63.0, "02:20 PM");

		// final assert test
		assertThat(resultCityInfo).isEqualTo(expectedCityInfo);
	}

	@Test
	public void  testCityNotFound() {
		// test city setup and added to arraylist
		Country country = new Country("CTS","CountryTest");
		City city = new City(1, "CityTest","CTS","DistrictTest",1000);
		List<City> cityList = new ArrayList<>();
		cityList.add(city);

		// three givens for the mock services
		given(weatherService.getTempAndTime("TestCity")).willReturn(new TempAndTime(300, 100000,1000));
		given(cityRepository.findByName("CityTest")).willReturn(cityList);
		given(countryRepository.findByCode("CTS")).willReturn(country);

		// get the city created setup the expected return
		CityInfo resultCityInfo = cityService.getCityInfo("Random Name of City");
		CityInfo expectedCityInfo = null;

		// final assert test
		assertThat(resultCityInfo).isEqualTo(expectedCityInfo);
	}

	@Test 
	public void  testCityMultiple() {
		// test cities setup and added to arraylist
		Country country = new Country("CTS", "CountryTest");

		List<City> cityList = new ArrayList<City>();
		City city1 = new City(1, "CityTest", "CTS", "DistrictTest", 9000);
		cityList.add(city1);
		City city2 = new City(1, "CityTest", "CTS", "DistrictTest2", 9000);
		cityList.add(city2);
		City city3 = new City(1, "CityTest", "CTS", "DistrictTest3", 9000);
		cityList.add(city3);
		City city4 = new City(1, "CityTest", "CTS", "DistrictTest4", 9000);
		cityList.add(city4);

		// three givens for the mock services
		given(weatherService.getTempAndTime("CityTest")).willReturn(new TempAndTime(290.41, 1594156817, 	-25200));
		given(cityRepository.findByName("CityTest")).willReturn(cityList);
		given(countryRepository.findByCode("CTS")).willReturn(country);

		// get the city created setup the expected return
		CityInfo resultCityInfo = cityService.getCityInfo("CityTest");
		CityInfo expectedCityInfo = new CityInfo(1, "CityTest", "CTS", "CountryTest", "DistrictTest", 9000, 63.0, "02:20 PM");

		// final assert test
		assertThat(resultCityInfo).isEqualTo(expectedCityInfo);
	}
}
