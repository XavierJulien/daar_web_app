package com.hxsarl.daar_web_app.items;

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

	
	@SuppressWarnings("unchecked")
	public Search(String word, String method) {
		this.word = word;
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(jsonFileName))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONObject json = (JSONObject) obj;

			JSONObject index = (JSONObject) json.get("index");

			if(method.equals("normal")) {
				JSONObject word_obj = (JSONObject)index.get(word);
				JSONArray files_json = (JSONArray)word_obj.get("files");
				//Iterate over files array
				Iterator<Object> it = files_json.iterator();
				while(it.hasNext()) {
					JSONObject jsonObject = (JSONObject) it.next();
					for(Object key : jsonObject.keySet()) {
						Long value = (Long) jsonObject.get(key.toString());
						files.put(key.toString(), value);
					}
					
				}
			}
			if(method.equals("regex")) {
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
								files.put(key2.toString(), value);
							}
						}
					}
				}
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