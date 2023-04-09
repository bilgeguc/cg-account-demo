package com.bilge.cgaccountdemo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseObject {
	public Boolean result;
	public String message;

	public ResponseObject(Boolean result, String message) {
		this.result = result;
		this.message = message;
	}
}
