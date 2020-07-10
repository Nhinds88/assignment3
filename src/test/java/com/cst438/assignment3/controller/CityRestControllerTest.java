package com.cst438.assignment3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cst438.assignment3.domain.*;
import com.cst438.assignment3.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

	@MockBean
	private CityService cityService;
	
	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<CityInfo> json;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void getCityInfo() throws Exception {

		CityInfo cityInfo = new CityInfo(1, "Test_City", "CTS", "County_Test", "District_Test", 9000, 80.62, "18:56");
		// given for city info
		given(cityService.getCityInfo("Test_City")).willReturn(cityInfo);
		// response from mvc
		MockHttpServletResponse response = mvc.perform(get("/api/cities/Test_City")).andReturn().getResponse();
		// check the status
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		// convert json to an object
		CityInfo returnedCityInfo = json.parseObject(response.getContentAsString());
		// final assert test
		assertThat(cityInfo).isEqualTo(returnedCityInfo);
	}
}