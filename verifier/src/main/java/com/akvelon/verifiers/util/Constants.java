package com.akvelon.verifiers.util;

public class Constants {

	// Request parameters
	public static final String ORDER_BY = "orderby";
	public static final String ORDER = "order";
	public static final String CONTROL = "control";
	public static final String ACCOUNT = "account";
	public static final String PROJECT = "project";
	public static final String STATUS = "status";
	public static final String TYPE = "type";
	public static final String DATE_FROM = "datefrom";
	public static final String DATE_TO = "dateto";
	public static final String CHANGE_PERIOD = "changeperiod";
	
	// Default options for request parameters
	public static final String CONTROL_DEFAULT_OPTION = "filterform.status";
	public static final String PROJECT_DEFAULT_OPTION = "all";
	public static final String STATUS_DEFAULT_OPTION = "prepared";
	public static final String TYPE_DEFAULT_OPTION = "all";
	public static final String CHANGE_PERIOD_DEFAULT_OPTION = "month"; // could be day, week
	
	public static final String STATUS_ACCEPTED_OPTION = "closed";
	
	public static final String REPORTED_HOURS_URL = "https://ua.ets.akvelon.com/accountreport.ets";
	
	public static final String V1_URL = "v1.url";
	
	// request types
	public static final String POST_REQUEST = "POST";
	public static final String GET_REQUEST = "GET";
	
	// v1 request parameters properties
	public static final String V1_TEAM_PROPS = "Team.Name";
	public static final String V1_MEMBERS_PROPS = "Member.Nickname";
	public static final String V1_SPRINT_PROPS = "Timebox.Name";
	
	public static final String EMPTY_PARAM = "";
	
	public static final int RESPONSE_CODE_OK = 200;
	
	public static final String RECIPIENT_TO = "to";
	public static final String RECIPIENT_CC = "cc";
	
	public static final String ETS_LOGIN = "ets.login";
	public static final String ETS_PASSWORD = "ets.password";
	
	public static final String V1_LOGIN = "v1.login";
	public static final String V1_PASSWORD = "v1.password";
	
	public static final String PROJECT_VERSION = "project.version";
	public static final String BUILD_NUMBER = "build.number";
	
	public static final int ETS_HOURS_PER_DAY = 8;
	public static final int V1_HOURS_PER_DAY = 7;
	
	public static final Integer V1_REQUIRED_HOURS = 1;
	public static final Integer ETS_REQUIRED_HOURS = 2;
	public static final Integer V1_HOUR_REPORTS = 3;
	public static final Integer ETS_HOUR_REPORTS = 4;
	public static final Integer V1_REPORTED_HOURS = 5;
	public static final Integer ETS_REPORTED_HOURS = 6;
	
	
}