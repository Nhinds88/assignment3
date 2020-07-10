package com.cst438.assignment3.domain;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TempAndTime {
	public double temp;
	public long time;
	public int timezone;

	public TempAndTime(double temp, long time, int timezone){
		this.temp = temp;
		this.time = time;
		this.timezone = timezone;
	}

	public double getTemp() { return temp; }

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public long getTime() { return time; }

	public void setTime(long time) {
		this.time = time;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	// converts the Kelvin temp provided by the api to fahrenheit
	public double getFahr() {

		Double f = (double) Math.round((getTemp() - 273.15) * 9.0 / 5.0 + 32.0);

		return f;
	}

	// converts the UTC time provided by the api to local time by using the timezone offset, then formats in an am/pm time format
	public String getLocalTime() {

		Instant utcTime = Instant.ofEpochSecond(getTime());
		ZoneOffset timeZone = ZoneOffset.ofTotalSeconds(getTimezone());
		OffsetDateTime offsetDateTime = utcTime.atOffset(timeZone);
		String format = "hh:mm a";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		String localTime = offsetDateTime.format(formatter);

		return localTime;
	}
 }