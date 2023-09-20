package com.bessisebzemeyve.controller;

import com.bessisebzemeyve.model.OrderItemResponseDTO;
import com.bessisebzemeyve.model.OrderRequestDTO;
import com.bessisebzemeyve.model.OrderResponseDTO;
import com.bessisebzemeyve.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<Boolean> addOrder(@Valid @RequestBody OrderRequestDTO dto) {
        LOGGER.info("A create order request has been sent.");
        boolean orderResponseDTO = orderService.addOrder(dto);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        LOGGER.info("A get orders request has been sent.");
        List<OrderResponseDTO> orderResponseDTOs = orderService.getOrders();
        return ResponseEntity.ok(orderResponseDTOs);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderItemResponseDTO> removeOrder(@PathVariable long id) {
        LOGGER.info("A remove order request has been sent.");
        OrderItemResponseDTO orderItemDTO = orderService.removeOrder(id);
        return ResponseEntity.ok(orderItemDTO);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> removeAllOrder() {
        LOGGER.info("A remove all order request has been sent.");
        boolean state = orderService.removeAllOrder();
        return ResponseEntity.ok(state);
    }
}
