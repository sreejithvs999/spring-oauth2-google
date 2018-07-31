package com.svs.learn.guser.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class L7Utils {

	private static final DateTimeFormatter SDF_LAST_LOGIN = DateTimeFormatter.ofPattern("dd MMM yyy hh:mm a");

	public static String formatLastLogin(LocalDateTime last) {

		return SDF_LAST_LOGIN.format(last);
	}

	public static final Timestamp currentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
}
