package com.bessisebzemeyve.model;

import java.util.List;

public class OrderRequestDTO {
    private List<OrderItemDTO> orderItemDTOList;

    public List<OrderItemDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public void setOrderItemDTOList(List<OrderItemDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
    }
}
