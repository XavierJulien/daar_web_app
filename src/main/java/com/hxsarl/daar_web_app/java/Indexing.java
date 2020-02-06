package com.hxsarl.daar_web_app.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Indexing {

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
	//CODE
	final static String commonestWordFilename = "src/main/java/com/hxsarl/daar_web_app/java/200_commonest_word.txt";
	final static String jsonFilename = "src/index.json";
	static Set<String> commonestWords = new HashSet<>();
	public static Map<String, ArrayList<Pair>> index = new HashMap<String, ArrayList<Pair>>();

	public static void parseCommonestWords(String filename) throws IOException {
		InputStream flux=new FileInputStream(filename); 
		InputStreamReader lecture=new InputStreamReader(flux);
		BufferedReader buff=new BufferedReader(lecture);
		try {
			String ligne;
			while ((ligne=buff.readLine())!=null){
				commonestWords.add(ligne);
			}
		}finally {
			buff.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean buildDataBase(File dir) throws IOException {
		File[] directoryListing = dir.listFiles();
		JSONObject json_root = new JSONObject();
		JSONObject json_index = new JSONObject();
		index = indexingFiles(directoryListing);
		long start = System.currentTimeMillis();
		index.forEach((k,v) -> {
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
		json_root.put("index", json_index);
		try(FileWriter file = new FileWriter(jsonFilename)) {
			System.out.println(json_root.toJSONString());
			file.write(json_root.toJSONString());
			file.flush();
			file.close();
		}catch (IOException e) {
			e.printStackTrace();
		}		
		long end = System.currentTimeMillis();
		double duration = (end - start)/1000000.0;
		System.out.println("End : "+duration+" ms");
		return true;
	}

	public static void menu() throws IOException {
		parseCommonestWords(commonestWordFilename);
		System.out.println("Enter the repository containing the data");
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in)); 
		String name = reader.readLine(); 
		File dir = new File(name);
		if (!buildDataBase(dir)) throw new IOException("Not a repository");
	}
	
	public static Map<String, ArrayList<Pair>> indexingFiles (File[] fileNames) throws IOException {
		Map<String, ArrayList<Pair>> index = new HashMap<>();
		
		for(int i = 0;i < fileNames.length;i++) {
			System.out.println("Computing for "+fileNames[i]);
			Map<String, Integer> index_temp = new HashMap<>();
			String fileName = fileNames[i].getAbsolutePath();
			String fileNameSmall = fileNames[i].getName();
			InputStream flux=new FileInputStream(fileName); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader br=new BufferedReader(lecture);
			
			String line;
			while((line = br.readLine()) != null){
				String[] words = line.split("\\s*[^a-zA-Z'-]+\\s*");
				for(String word : words) {
					word = word.toLowerCase();
					if(index_temp.containsKey(word)){
						index_temp.replace(word,index_temp.get(word)+1);
					}
					else {
						index_temp.put(word, 1);
					}
				}
			}
			index_temp.forEach((k,v) -> {
				if(index.containsKey(k)) {
					ArrayList<Pair> l = index.get(k);
					l.add(new Pair(fileNameSmall,v));
					index.replace(k, l);
				}
				else {
					ArrayList<Pair> list  = new ArrayList<>();
					list.add(new Pair(fileNameSmall,v));
					index.put(k, list);
				}
			});
			
			br.close();
		}
		
		for(String s:commonestWords) {
			index.remove(s);
		}
		return index;
	}

	public static void main(String[] args) {
		try {
			menu();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
