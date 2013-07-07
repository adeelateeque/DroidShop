package com.droidshop.api;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.droidshop.api.error.WebServiceError;
import com.droidshop.api.error.WebServiceException;

public abstract class AbstractController
{
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Error handleException(RuntimeException e)
	{
		return new Error(e.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Error handleException(ResourceNotFoundException e)
	{
		return new Error(e.getMessage());
	}

	public static class Error
	{
		private final String error;

		public Error(String error)
		{
			this.error = error;
		}

		public String getError()
		{
			return error;
		}
	}

	// Exception handler for WebServiceException cases
	@ExceptionHandler(WebServiceException.class)
	public ResponseEntity<WebServiceError> handleWebServiceException(WebServiceException webServiceException)
	{
		System.out.println("Controller.handleWebServiceException");
		WebServiceError webServiceError = new WebServiceError(webServiceException.getExceptionCode(),
				webServiceException.getExceptionMessage());

		// This should happen in case of input constraint validations
		if (webServiceException.getExceptionMessageList() != null)
		{
			webServiceError.setErrorMessageList(webServiceException.getExceptionMessageList());
		}
		return new ResponseEntity<WebServiceError>(webServiceError, HttpStatus.BAD_REQUEST);
	}

	// Exception handler for generic Exception cases
	@ExceptionHandler(Exception.class)
	public ResponseEntity<WebServiceError> handleException(Exception exception)
	{
		System.out.println("Controller.handleException");
		WebServiceError webServiceError = new WebServiceError(400, exception.getMessage());
		return new ResponseEntity<WebServiceError>(webServiceError, HttpStatus.BAD_REQUEST);
	}
}
