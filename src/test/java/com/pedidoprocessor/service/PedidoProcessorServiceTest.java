package com.pedidoprocessor.service;

import org.junit.jupiter.api.Test;

import com.pedidoprocessor.model.User;
import com.pedidoprocessor.service.PedidoProcessorService;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PedidoProcessorServiceTest {

    @Test
    public void testStart() {
        PedidoProcessorService service = new PedidoProcessorService();
        String result = service.start();
        assertEquals("serviço iniciado", result);
    }
}
