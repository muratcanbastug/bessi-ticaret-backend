package com.bessisebzemeyve.model;

import com.bessisebzemeyve.entity.User;

import java.util.List;

public class OrderResponseDTO {
    private List<OrderItemDTO> orderItemDTOList;
    private User user;

    public List<OrderItemDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public void setOrderItemDTOList(List<OrderItemDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
