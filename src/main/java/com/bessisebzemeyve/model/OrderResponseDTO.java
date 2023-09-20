package com.bessisebzemeyve.model;

import com.bessisebzemeyve.entity.User;

import java.util.List;

public class OrderResponseDTO {
    private List<OrderItemResponseDTO> orderItemDTOList;
    private User user;

    public List<OrderItemResponseDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public void setOrderItemDTOList(List<OrderItemResponseDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
