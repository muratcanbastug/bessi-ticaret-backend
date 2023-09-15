package com.bessisebzemeyve.service;

import com.bessisebzemeyve.entity.Order;
import com.bessisebzemeyve.entity.Product;
import com.bessisebzemeyve.entity.User;
import com.bessisebzemeyve.model.OrderItemDTO;
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

    public OrderResponseDTO addOrder(OrderRequestDTO dto) {
        User user = getUserByName();
        List<OrderItemDTO> orderItems = dto.getOrderItemDTOList();
        for (OrderItemDTO orderItemDTO : orderItems) {
            Product product = productRepository.findById(orderItemDTO.getProductId()).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
            Order order = new Order();
            order.setAmount(orderItemDTO.getAmount());
            order.setNote(orderItemDTO.getNote());
            order.setProduct(product);
            order.setUser(user);
            orderRepository.save(order);
        }

        return generateResponse(dto.getOrderItemDTOList(), user);
    }

    private OrderResponseDTO generateResponse(List<OrderItemDTO> orderItems, User user) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderItemDTOList(orderItems);
        orderResponseDTO.setUser(user);
        return orderResponseDTO;
    }

    public List<OrderResponseDTO> getOrders() {
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setUser(user);
            List<OrderItemDTO> orderItemDTOs = orderRepository.findAllByUserIdOrderByAmount(user.getId()).stream().map(order -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setAmount(order.getAmount());
                orderItemDTO.setNote(order.getNote());
                orderItemDTO.setProductId(order.getProduct().getId());
                return orderItemDTO;
            }).toList();
            orderResponseDTO.setOrderItemDTOList(orderItemDTOs);
            orderResponseDTOS.add(orderResponseDTO);
        }
        return orderResponseDTOS;
    }

    public OrderItemDTO removeOrder(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
        orderRepository.deleteById(id);
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(order.getProduct().getId());
        orderItemDTO.setNote(order.getNote());
        orderItemDTO.setAmount(order.getAmount());
        return orderItemDTO;
    }

    public boolean removeAllOrder() {
        orderRepository.deleteAll();
        return true;
    }
}
