package com.n26.transactions.errors;

import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

	private String status;

	private long timestamp;

	private String message;

	private String debugMessage;

	private List<ApiSubError> subErrors;

	public ApiError() {
		timestamp = Calendar.getInstance().getTimeInMillis();
	}

	ApiError(String status) {
		this();
		this.status = status;
	}

	ApiError(String status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ApiError(String status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
//		this.debugMessage = ex.getLocalizedMessage();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public List<ApiSubError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<ApiSubError> subErrors) {
		this.subErrors = subErrors;
	}
}
