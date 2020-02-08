package com.hxsarl.daar_web_app.controllers;


import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hxsarl.daar_web_app.items.Books;
import com.hxsarl.daar_web_app.items.Search;

@RestController
public class SearchController {

	@GetMapping("/search")
	@CrossOrigin(origins = {"http://localhost:3000"})
	public Search searchBook(@RequestParam(value = "word", defaultValue = "") String word) {
		return new Search(word);
	}
	
	@GetMapping("/books")
	public Books books(@RequestParam(value = "directory", defaultValue = "src/books1") String dir) throws IOException {
		return new Books(dir);
	}
	
}