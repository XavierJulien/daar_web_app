package com.hxsarl.daar_web_app.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hxsarl.daar_web_app.java.Indexing;
import com.hxsarl.daar_web_app.java.Indexing.Pair;

public class Books {

	public String dir;
	public JSONObject books;
	public int size;
	public static Map<String, ArrayList<Pair>> index = new HashMap<String, ArrayList<Pair>>();
	
	@SuppressWarnings("unchecked")
	public Books(String dir) throws IOException {
		this.dir = dir;
		File[] directoryListing = new File(dir).listFiles();
		books = new JSONObject();
		JSONObject json_index = new JSONObject();
		index = Indexing.indexingFiles(directoryListing);
		Indexing.index.forEach((k,v) -> {
			JSONObject word_detail = new JSONObject();
			JSONArray array_files = new JSONArray();
			for(Pair p : v) {
				JSONObject file = new JSONObject();
				file.put(p.getKey(), p.getValue());
				array_files.add(file);
			}
			word_detail.put("files", array_files);
			json_index.put(k, word_detail);
		});
		System.out.println(index.size());
		books.put("index", json_index);
		size = books.size();
	}

	public String getdir() {
		return dir;
	}
	
	public JSONObject getBooks(){
		return books;
	}
}
