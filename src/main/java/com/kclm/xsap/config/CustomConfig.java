package com.kclm.xsap.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:xsap-config.properties")
public class CustomConfig {

	@Value("${reservation.gap_minute}")
	private Long gap_minute;

	@Value("${custom.cache_time}")
	private Long cache_time;

	public CustomConfig() {
		super();
	}

	public Long getGap_minute() {
		return gap_minute;
	}
	public Long getCache_time() {
		return cache_time;
	}

	public void setGap_minute(Long gap_minute) {
		this.gap_minute = gap_minute;
	}

	public void setCache_time(Long cache_time) {
		this.cache_time = cache_time;
	}
}
