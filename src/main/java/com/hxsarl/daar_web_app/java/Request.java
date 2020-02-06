package com.hxsarl.daar_web_app.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Request {

	static String jsonFileName = "src/index.json";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		System.out.println("Enter the word searched :");
		BufferedReader br =  new BufferedReader(new InputStreamReader(System.in)); 
		String word = br.readLine();
		System.out.println(word);

		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(jsonFileName))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONObject json = (JSONObject) obj;

			JSONObject index = (JSONObject) json.get("index");

			JSONObject word_obj = (JSONObject)index.get(word);

			JSONArray files = (JSONArray)word_obj.get("files");
			//Iterate over employee array
			files.forEach( file -> 
			{
				JSONObject file_json = (JSONObject) file;
				System.out.println(file_json.toJSONString());
			});

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
