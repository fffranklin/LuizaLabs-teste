package com.pedidoprocessor.util;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.io.IOException;

public class FileUtil {
	public static List<String> readFile(String filePath) throws IOException {
		return Files.readAllLines(Paths.get(filePath));
	}
	
	public static void writeFile(String filePath, String content) throws IOException {
		Files.write(Paths.get(filePath), content.getBytes());
	}
}
