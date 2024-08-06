package com.pedidoprocessor.service;

import com.pedidoprocessor.model.User;
import com.google.gson.Gson;
import com.pedidoprocessor.model.Order;
import com.pedidoprocessor.model.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PedidoProcessorService {
    public List<User> processFile(String filePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("Arquivo não encontrado: " + filePath);
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = reader.lines().toList();
            Map<Integer, User> userMap = new HashMap<>();

            for (String line : lines) {
                int userId = Integer.parseInt(line.substring(0, 10).trim());
                String userName = line.substring(10, 55).trim();
                int orderId = Integer.parseInt(line.substring(55, 65).trim());
                int productId = Integer.parseInt(line.substring(65, 75).trim());
                double productValue = Double.parseDouble(line.substring(75, 87).trim());
                String date = line.substring(87, 95).trim();

                User user = userMap.getOrDefault(userId, new User());
                user.setUserId(userId);
                user.setName(userName);

                Order order = user.getOrders().stream()
                        .filter(o -> o.getOrderId() == orderId)
                        .findFirst()
                        .orElse(new Order());
                order.setOrderId(orderId);
                order.setDate(date);

                if (user.getOrders() == null) {
                    user.setOrders(new ArrayList<>());
                }

                if (order.getProducts() == null) {
                    order.setProducts(new ArrayList<>());
                }

                Product product = new Product();
                product.setProductId(productId);
                product.setValue(productValue);
                order.getProducts().add(product);
                order.addToTotal(productValue);

                if (!user.getOrders().contains(order)) {
                    user.getOrders().add(order);
                }

                userMap.put(userId, user);
            }

            return new ArrayList<>(userMap.values());
        }
    }

    public String convertToJson(List<User> users) {
        Gson gson = new Gson();
        return gson.toJson(users);
    }
    
    public String start() {
        return "serviço iniciado";
    }

    public static void main(String[] args) {
        PedidoProcessorService service = new PedidoProcessorService();
        List<String> filePaths = Arrays.asList("data_1.txt", "data_2.txt");
        for (String filePath : filePaths) {
            try {
                List<User> users = service.processFile(filePath);
                String jsonOutput = service.convertToJson(users);
                System.out.println("Output for file: " + filePath);
                System.out.println(jsonOutput);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
