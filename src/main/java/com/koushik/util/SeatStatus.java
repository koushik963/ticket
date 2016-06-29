package com.koushik.util;

public enum SeatStatus {

	AVAILABLE, HOLD, RESERVED;

	public static int available() {
		return AVAILABLE.ordinal();
	}

	public static int hold() {
		return HOLD.ordinal();
	}

	public static int reserved() {
		return RESERVED.ordinal();
	}
}
