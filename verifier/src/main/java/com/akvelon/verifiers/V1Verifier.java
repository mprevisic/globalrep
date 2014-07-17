package com.akvelon.verifiers;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.akvelon.verifiers.parser.DomParser;
import com.akvelon.verifiers.parser.V1Parser;
import com.akvelon.verifiers.reports.V1HourReport;
import com.akvelon.verifiers.senders.IV1RequestSender;
import com.akvelon.verifiers.senders.V1RequestSender;
import com.akvelon.verifiers.util.Constants;
import com.akvelon.verifiers.util.Util;
import com.akvelon.verifiers.util.V1UrlBuilder;

public class V1Verifier {
	
	private static final Logger log = Logger.getLogger(V1Verifier.class);

	private static final String V1_EXTERNAL_PROPERTIES = "v1.properties";
	
	private V1Parser parser;
	private IV1RequestSender requestSender;
	private V1UrlBuilder urlBuilder;
	
	private int requiredWorkingHours = 0;
	
	public V1Verifier() throws Exception {
		super();
		parser = new DomParser();
		requestSender = new V1RequestSender();
		urlBuilder = new V1UrlBuilder();
	}
	
	public int getRequiredWorkingHours() {
		return requiredWorkingHours;
	}

	public List<V1HourReport> verify(String login, String password) throws Exception {
		
		log.info("Load v1 properties...");
		Properties v1Properties = Util.loadProperties(V1_EXTERNAL_PROPERTIES);

		if (v1Properties.isEmpty()) {
			log.error("Actual properties can not be loaded");
			log.error("Exit...");
			return null;
		}
		
		Calendar sprintBeginDate = null;
		
		InputStream response = null;
		try {
			response = requestSender.sendRequest(login, password, urlBuilder.buildUrlTimebox(v1Properties));
			sprintBeginDate = parser.parseSprintStartDate(response);
		} finally {
			response.close();
		}
		
		List<V1HourReport> reports = null;
		
		try {
			response = requestSender.sendRequest(login, password, urlBuilder.buildUrlActual(v1Properties));
			reports = parser.parseReportedHours(response);
		} finally {
			response.close();
		}
		
		requiredWorkingHours = Util.calculateWorkingHoursBetweenDates(sprintBeginDate, Util.getToday(), Constants.V1_HOURS_PER_DAY);
		
		for (V1HourReport v1Report : reports) {
			log.info(v1Report.getName() + " - " + v1Report.getReportedHours());
		}
		
		return reports;
	}
	
}