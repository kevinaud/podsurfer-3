package com.credera;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.nio.file.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.LineNumberReader;
import java.io.FileReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

@SpringBootApplication
public class SpringTemplateApplication {

	public static void main(String[] args) {
		
		configureUnirest();
		configureApiEndpoint();

		SpringApplication.run(SpringTemplateApplication.class, args);
	}
	
	/**
	 * Configure object mapper for Unirest so it can translate Java objects
	 * to json strings.
	 */
	private static void configureUnirest(){
		Unirest.setObjectMapper(new ObjectMapper() {
		    private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
		                = new com.fasterxml.jackson.databind.ObjectMapper();

		    public <T> T readValue(String value, Class<T> valueType) {
		        try {
		            return jacksonObjectMapper.readValue(value, valueType);
		        } catch (IOException e) {
		            throw new RuntimeException(e);
		        }
		    }

		    public String writeValue(Object value) {
		        try {
		            return jacksonObjectMapper.writeValueAsString(value);
		        } catch (JsonProcessingException e) {
		            throw new RuntimeException(e);
		        }
		    }
		});
	}

	private static void configureApiEndpoint() {


		Map<String, String> env = System.getenv();

		if(env.get("API_URL") != null){

			String filepath = "./src/main/resources/public/api/api.service.js";

			try {

				LineNumberReader rdr = new LineNumberReader(new FileReader(filepath));

				try {

					String newFile = "";
					String line;
					boolean found = false;

					while(!found) {
						line = rdr.readLine();

						if(line == null) {
							found = true;
						} else {

							int position = line.indexOf("let apiUrl = ");
							if (position > -1) {

								String replacementLine = line.substring(0, position + 13);
								replacementLine += "\"" + env.get("API_URL") + "\";\n";
								newFile += replacementLine;

							} else {
								newFile += line + "\n";
							}

						}
					}

					Charset charset = StandardCharsets.UTF_8;
					Path path = Paths.get(filepath);
					Files.write(path, newFile.getBytes(charset));

				} finally {
					rdr.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}


		}


	}
}
