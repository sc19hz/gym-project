package gym.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import gym.util.R;
import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class ExceptionHandlers
{
	@ExceptionHandler
	public @ResponseBody Object handleTokenExpire(ExpiredJwtException e) {
		return R.raw(HttpStatus.UNAUTHORIZED, "Your token has expired, please re-login");
	}
	
	public @ResponseBody Object handleIOException(IOException e)
	{
		return R.raw(
			HttpStatus.INTERNAL_SERVER_ERROR,
			"An I/O exception has occurred while attampting to perform the operation..."
		);
	}
	
	public @ResponseBody Object handleException(Exception e)
	{
		return R.raw(
			HttpStatus.INTERNAL_SERVER_ERROR,
			"An unexpected error has occurred while attampting to perform the operation..."
		);
	}
}
