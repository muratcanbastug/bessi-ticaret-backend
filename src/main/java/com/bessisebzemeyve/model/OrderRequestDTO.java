package com.bessisebzemeyve.model;

import java.util.List;

public class OrderRequestDTO {
    private List<OrderItemRequestDTO> orderItemDTOList;

    public List<OrderItemRequestDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public void setOrderItemDTOList(List<OrderItemRequestDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
    }
}
