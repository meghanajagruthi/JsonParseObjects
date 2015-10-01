package com.plash.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Hello world!
 *
 */

public class App {
	/*
	 * public static Map<String, Object> globalBucket = new HashMap<String,
	 * Object>();
	 */
	static Gson gson = new Gson();

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getElementsFromObject(JsonElement element) {
		JsonObject temp = element.getAsJsonObject();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (!element.isJsonNull()) {

			for (Entry<String, JsonElement> entry : temp.entrySet()) {
				if (entry.getValue().isJsonArray()) {
					JsonArray tempList = entry.getValue().getAsJsonArray();

					List<String> resultList = gson.fromJson(
							tempList.toString(), List.class);

					map.put(entry.getKey(), resultList);
				} else {
					map.put(entry.getKey(), entry.getValue().getAsString());
				}

			}

		}
		return map;
	}

	public static void printElements(Map<String, Object> temp) {

		for (Entry<String, Object> entry : temp.entrySet()) {

			System.out.print(entry.getKey()+ "   :   ");
			if (entry.getValue() instanceof List) {
				List<String> tempValues = (List<String>) entry.getValue();
				System.out.print(tempValues.toString());
			} else {
				System.out.print(entry.getValue().toString());
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) throws IOException {
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String temp = "";
			temp = br.readLine();
			
			JsonElement read = new JsonParser().parse(temp);

			Map<String, Object> result = new LinkedHashMap<String, Object>();
			JsonObject jsonObject = read.getAsJsonObject();
			int i = 0;
			for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {

				if (entry.getValue().isJsonPrimitive()) {

					result.put(entry.getKey(), entry.getValue());

				} else if (entry.getValue().isJsonArray()) {
					JsonArray tempList = entry.getValue().getAsJsonArray();

					List<String> resultList = gson.fromJson(
							tempList.toString(), List.class);

					result.put(entry.getKey(), resultList);

				} else if (entry.getValue().isJsonObject()) {

					result.put(entry.getKey(),
							getElementsFromObject(entry.getValue()));
				}

			}

			System.out.println("The output is as follows");
			for (Entry<String, Object> p : result.entrySet()) {

				System.out.print(p.getKey() + "   :  ");
				if (p.getValue() instanceof Map) {

					Map<String, Object> tempValues = (Map<String, Object>) p
							.getValue();
					printElements(tempValues);
				} else if (p.getValue() instanceof List) {
					List<String> tempValues = (List<String>) p.getValue();
					System.out.print(tempValues.toString());
				} else {
					System.out.print(p.getValue().toString());
				}
				System.out.println("");
			}
			
			

		

	}
}
