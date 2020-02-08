package com.hxsarl.daar_web_app.controllers;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hxsarl.daar_web_app.items.Search;

@RestController
public class SearchController {

	@GetMapping("/search")
	@CrossOrigin(origins = {"http://localhost:3000"})
	public Search searchBookWord(@RequestParam(value = "word", defaultValue = "") String word) {
		return new Search(word,"normal");
	}
	
	@GetMapping("/regex")
	public Search searchBookRegex(@RequestParam(value = "regex", defaultValue = "") String regex) {
		return new Search(regex,"regex");
	}
	
}