package com.orderprocessor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.orderprocessor.model.User;

public class fileUtils {
	
	private static final String JSONS_FILEPATH_WITH_JSON_PREFIX_NAME = "src/main/resources/generatedJson_";
	
	public static void saveJsonFile(String fileName, List<User> users) throws IOException {
		String jsonfile = JSONS_FILEPATH_WITH_JSON_PREFIX_NAME + fileName + ".json";

        Path path = Paths.get(jsonfile);

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            Gson gson2 = new GsonBuilder().setPrettyPrinting().create();

            JsonElement tree = gson2.toJsonTree(users);
            gson2.toJson(tree, writer);
        }
	}	

}
