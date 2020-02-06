package com.hxsarl.daar_web_app.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	private static Map<String, Map<String,Integer>> database = new HashMap<String, Map<String,Integer>>();
	private static Map<String, File> allFiles = new HashMap<>();
	private static Map<Integer, String> fileNumber = new HashMap<>();
	static int cpt = 0;
	final static DecimalFormat df = new DecimalFormat("#0.000");
	
	//should be a parameter of exec
	final static String commonestWordFilename = "src/main/java/com/hxsarl/daar_web_app/java/200_commonest_word.txt";
	static Set<String> commonestWords = new HashSet<>();
	
	public static Map<String, Integer> buildIndexing(Path p) throws IOException {
		return Indexing.indexing(p.toString());
	}

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
	
	public static boolean buildDataBase(File dir) throws IOException {
		File[] directoryListing = dir.listFiles();
		Map<String, ArrayList<Pair>> index = indexingFiles(directoryListing);
		index.forEach((k,v) -> {
			v.forEach((p) -> {
				System.out.println("Word : "+k+", File : "+p.getKey()+", Occurence : "+p.getValue());
			});
		});
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
	
	public static Map<String, Integer> indexing (String fileName) throws IOException {
		InputStream flux=new FileInputStream(fileName); 
		InputStreamReader lecture=new InputStreamReader(flux);
		BufferedReader buff=new BufferedReader(lecture);
		try {
			Map<String, Integer> index = new HashMap<>();
			String ligne;
			int c=0;
			int i = 1, j = 1;
			while ((ligne=buff.readLine())!=null){
				if(ligne.length() == 0) continue;
				j=1;
				String[] words = ligne.split("\\s*[^a-zA-Z'-]+\\s*");
				int cpt = 0;
				for (cpt=0;cpt<words.length;cpt++) {
					String word = words[cpt];
					word = word.toLowerCase();
					if(index.containsKey(word)) {
						index.put(word, index.get(word)+1);
					}
					else {
						index.put(word, 1);
					}
					int space_count = 0;
					j += word.length();
					if(cpt < words.length-1) 
						while(ligne.charAt(j-1) != words[cpt+1].charAt(0)) {
							j++;
						}
				}
				i++;
			}
			
			for(String s:Main.commonestWords) {
				index.remove(s);
			}
			
			return index;
		}finally {
			buff.close();
		}

	}

	@SuppressWarnings("unchecked")
	public static Map<String, ArrayList<Pair>> indexingFiles (File[] fileNames) throws IOException {
		Map<String, ArrayList<Pair>> index = new HashMap<>();
		
		for(int i = 0;i < fileNames.length;i++) {
			System.out.println("Computing for "+fileNames[i]);
			Map<String, Integer> index_temp = new HashMap<>();
			String fileName = fileNames[i].getAbsolutePath();
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
					l.add(new Pair(fileName,v));
					index.replace(k, l);
				}
				else {
					ArrayList<Pair> list  = new ArrayList<>();
					list.add(new Pair(fileName,v));
					index.put(k, list);
				}
			});
			
			br.close();
		}
		
		for(String s:Main.commonestWords) {
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
