package io.springworks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ApplicationController {

	@GetMapping()
	public String welcome() {
		return "Hello, World!";
	}
}
