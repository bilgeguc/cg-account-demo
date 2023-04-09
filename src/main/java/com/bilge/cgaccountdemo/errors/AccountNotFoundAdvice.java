package com.bilge.cgaccountdemo.errors;

import com.bilge.cgaccountdemo.model.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AccountNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(AccountNotFoundException.class)
	ResponseEntity<Object> accountNotFoundHandler(AccountNotFoundException anfe){
		ResponseObject responseObject = new ResponseObject(false, anfe.getMessage());
		return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
	}
}