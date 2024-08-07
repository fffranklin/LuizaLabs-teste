package com.pedidoprocessor.service;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonIOException;
import com.orderprocessor.model.Order;
import com.orderprocessor.model.Product;
import com.orderprocessor.model.User;
import com.orderprocessor.service.OrderProcessorService;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PedidoProcessorServiceTest {

	private static final String SERVICE_STARTED = "Service Started";
	
    @Test
    public void testStartService() {
        OrderProcessorService service = new OrderProcessorService();
        
        String result = service.startService();
        assertEquals(SERVICE_STARTED, result);
    }
      
    @Test
    public void testConvertJsonWithComplexData() throws JsonIOException, IOException {
        OrderProcessorService service = new OrderProcessorService();

        User user1 = new User();
        	user1.setUserId(1);
        	user1.setName("User1");
        
        Order order1 = new Order();
        	order1.setOrderId(1);
        	order1.setDate("2024-08-06");
        
        Product product1 = new Product(1, 10.0);
        
        order1.getProducts().add(product1);
        user1.getOrders().add(order1);
        
        User user2 = new User();
        	user2.setUserId(2);
        	user2.setName("User2");

        Order order2 = new Order();
        	order2.setOrderId(2);
        	order2.setDate("2024-08-06");

        Product product2 = new Product(2, 20.0);

        order2.getProducts().add(product2);
        	user2.getOrders().add(order2);

        String json = service.convertToJson(List.of(user1, user2));
        assertNotNull(json);
        assertTrue(json.contains("User1"));
        assertTrue(json.contains("User2"));
        assertTrue(json.contains("10.0"));
        assertTrue(json.contains("20.0"));
    }

    
    @Test
    public void testConvertJson() throws JsonIOException, IOException {
    	OrderProcessorService service = new OrderProcessorService();
    	User user = new User();
    		user.setUserId(1);
    		user.setName("LuMagalu");
    		
    	String json = service.convertToJson(List.of(user));
    		assertNotNull(json);
    		assertTrue(json.contains("LuMagalu"));
    	
    }
}
