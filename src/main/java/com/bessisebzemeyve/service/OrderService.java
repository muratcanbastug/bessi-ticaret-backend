package com.bessisebzemeyve.service;

import com.bessisebzemeyve.entity.Order;
import com.bessisebzemeyve.entity.Product;
import com.bessisebzemeyve.entity.User;
import com.bessisebzemeyve.model.OrderItemRequestDTO;
import com.bessisebzemeyve.model.OrderItemResponseDTO;
import com.bessisebzemeyve.model.OrderRequestDTO;
import com.bessisebzemeyve.model.OrderResponseDTO;
import com.bessisebzemeyve.repository.OrderRepository;
import com.bessisebzemeyve.repository.ProductRepository;
import com.bessisebzemeyve.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    private User getUserByName() {
        String name = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
        return userRepository.findByUsername(name);
    }

    public boolean addOrder(OrderRequestDTO dto) {
        User user = getUserByName();
        List<OrderItemRequestDTO> orderItems = dto.getOrderItemDTOList();
        for (OrderItemRequestDTO orderItemDTO : orderItems) {
            Product product = productRepository.findById(orderItemDTO.getProductId()).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
            Order order = new Order();
            order.setAmount(orderItemDTO.getAmount());
            order.setNote(orderItemDTO.getNote());
            order.setProduct(product);
            order.setUser(user);
            order.setUnit(orderItemDTO.getUnit());
            orderRepository.save(order);
        }

        return true;
    }

    public List<OrderResponseDTO> getOrders() {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setUser(user);
            List<OrderItemResponseDTO> orderItemDTOs = orderRepository.findAllByUserIdOrderByAmount(user.getId()).stream().map(order -> {
                OrderItemResponseDTO orderItemDTO = new OrderItemResponseDTO();
                orderItemDTO.setAmount(order.getAmount());
                orderItemDTO.setNote(order.getNote());
                orderItemDTO.setProductId(order.getProduct().getId());
                orderItemDTO.setUnit(order.getUnit());
                orderItemDTO.setProductName(order.getProduct().getName());
                orderItemDTO.setOrderId(order.getId());
                return orderItemDTO;
            }).toList();
            orderResponseDTO.setOrderItemDTOList(orderItemDTOs);
            orderResponseDTOS.add(orderResponseDTO);
        }
        return orderResponseDTOS;
    }

    public OrderItemResponseDTO removeOrder(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
        orderRepository.deleteById(id);
        OrderItemResponseDTO orderItemDTO = new OrderItemResponseDTO();
        orderItemDTO.setAmount(order.getAmount());
        orderItemDTO.setNote(order.getNote());
        orderItemDTO.setProductId(order.getProduct().getId());
        orderItemDTO.setUnit(order.getUnit());
        orderItemDTO.setProductName(order.getProduct().getName());
        orderItemDTO.setOrderId(order.getId());
        return orderItemDTO;
    }

    public boolean removeAllOrder() {
        orderRepository.deleteAll();
        return true;
    }
}
