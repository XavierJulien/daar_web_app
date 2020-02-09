package com.hxsarl.daar_web_app.items;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Search {

	//INNER CLASS PAIR
		public static class Pair {

			String key;
			Integer value;
			
			public Pair(String key, Integer value) {
				this.key = key;
				this.value = value;
			}
			public String getKey() {
				return key;
			}
			public Integer getValue() {
				return value;
			}
			
			public void setKey(String key) {
				this.key = key;
			}
			public void setValue(Integer value) {
				this.value = value;
			}
		}
	
	private static String jsonFileName = "src/index.json";

	private final String word;
	
	private HashMap<String,Long> files = new HashMap<String,Long>();

	private HashMap<String,String> file_title = new HashMap<String,String>();
	private HashMap<String,String> title_file = new HashMap<String,String>();
	
	@SuppressWarnings("unchecked")
	public Search(String word, String method) {
		this.word = word;
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/titres.txt")));
			String line;
			while((line = br.readLine()) != null) {
				String[] line_split = line.split("#");
				String[] filename_split = line.split("books/");
				file_title.put(filename_split[1], line_split[0]);
				title_file.put(line_split[0], filename_split[1]);
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try (FileReader reader = new FileReader(jsonFileName))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONObject json = (JSONObject) obj;

			JSONObject index = (JSONObject) json.get("index");

			if(method.equals("mot-clef")) {
				JSONObject word_obj = (JSONObject)index.get(word);
				JSONArray files_json = (JSONArray)word_obj.get("files");
				//Iterate over files array
				Iterator<Object> it = files_json.iterator();
				while(it.hasNext()) {
					JSONObject jsonObject = (JSONObject) it.next();
					for(Object key : jsonObject.keySet()) {
						Long value = (Long) jsonObject.get(key.toString());
						String title = key.toString();
						if(file_title.containsKey(key.toString())) {
							title = file_title.get(key.toString());
						}
						files.put(title, value);
					}
					
				}
			}
			if(method.equals("contains")) {
				Iterator<String> iterator = index.keySet().iterator();
				while(iterator.hasNext()) {
					String key = iterator.next();
					if(key.contains(word)) {
						JSONObject word_obj = (JSONObject)index.get(key);
						JSONArray files_json = (JSONArray)word_obj.get("files");
						//Iterate over files array
						Iterator<Object> it = files_json.iterator();
						while(it.hasNext()) {
							JSONObject jsonObject = (JSONObject) it.next();
							for(Object key2 : jsonObject.keySet()) {
								Long value = (Long) jsonObject.get(key2.toString());
								String title = key2.toString();
								if(file_title.containsKey(key2.toString())) {
									title = file_title.get(key2.toString());
								}
								files.put(title, value);
							}
						}
					}
				}
			}
			if(method.equals("title")) {
				title_file.forEach((title,file) -> {
					if(title.toLowerCase().contains(word)) {
						files.put(title, (long)0);
					}
				});
			}
			

		} catch (FileNotFoundException e) {
			System.out.println("file not found !");
		} catch (IOException e) {
			System.out.println("io Exception !");
		} catch (ParseException e) {
			System.out.println("parse execption !");
		}
	}

	public String getWord() {return word;}
	public HashMap<String,Long> getFiles(){return files;}
}