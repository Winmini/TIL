package com.webflux.practice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponseResult {
	private final String state = "SUCCESS";
	private Object data;
}