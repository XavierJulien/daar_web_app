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
		return new Search(word,"mot-clef");
	}
	
	@GetMapping("/contains")
	@CrossOrigin(origins = {"http://localhost:3000"})
	public Search searchBookRegex(@RequestParam(value = "contains", defaultValue = "") String contains) {
		return new Search(contains,"contains");
	}
	
	@GetMapping("/title")
	@CrossOrigin(origins = {"http://localhost:3000"})
	public Search searchBookTitle(@RequestParam(value = "title", defaultValue = "") String title) {
		return new Search(title,"title");
	}
	
}