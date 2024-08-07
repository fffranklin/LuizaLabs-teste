package com.orderprocessor.service;

import com.orderprocessor.model.Order;
import com.orderprocessor.model.Product;
import com.orderprocessor.model.User;
import com.orderprocessor.utils.fileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderProcessorService {
	
	private static final String TXT_FILE_01 = "data_1.txt";
	private static final String TXT_FILE_02 = "data_2.txt";
	
	private int userId;
	private String userName;
	private int orderId;
	private int productId;
	private double productValue;
	private String date;
		
    public List<User> processFile(String filePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        
        if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = reader.lines().toList();
            Map<Integer, User> userMap = new HashMap<>();

            for (String line : lines) {            	
            	setOrderData(line);  
            	
                User user = setUser(userMap);                
                Order order = setOrder(user);
                
                setProductToOrder(user, order);                

                userMap.put(userId, user);
            }

            return new ArrayList<>(userMap.values());
        }
    }
   
    public String startService() {
        return "Service Started";
    }

    public static void main(String[] args) {
        OrderProcessorService service = new OrderProcessorService();
        
        List<String> filePaths = Arrays.asList(TXT_FILE_01, TXT_FILE_02);
        
        for (String filePath : filePaths) {
            try {
                List<User> users = service.processFile(filePath);
                
                fileUtils.saveJsonFile(filePath, users);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    
           
    private void setOrderData(String line) {
    	userId = Integer.parseInt(line.substring(0, 10).trim());
    	userName = line.substring(10, 55).trim();  
    	
    	orderId = Integer.parseInt(line.substring(55, 65).trim());
    	
    	productId = Integer.parseInt(line.substring(65, 75).trim());
        productValue = Double.parseDouble(line.substring(75, 87).trim());
        
        date = line.substring(87, 95).trim();   	
    }
    
    private User setUser(Map<Integer, User> userMap) {
    	
        User user = userMap.computeIfAbsent(userId, k -> new User());
        
        user.setUserId(userId);
        user.setName(userName);
        
        return user;
    }
    
    private Order setOrder(User user) {
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
        
        return order;
    }
    
    private void setProductToOrder(User user, Order order) {
    	Product product = new Product(productId, 
    								  productValue);
  	        
        order.getProducts().add(product);
        order.addToTotal(productValue);

        if (!user.getOrders().contains(order)) {
            user.getOrders().add(order);
        }
    }
    
}
