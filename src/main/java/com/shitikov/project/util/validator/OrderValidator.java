package com.shitikov.project.util.validator;

import com.shitikov.project.model.entity.type.OrderStatus;

public class OrderValidator extends Validator{
    private OrderValidator() {}

    public static boolean checkStatus(String status) {
        boolean isStatusValid = false;

        if (status != null && !status.isEmpty()) {
            OrderStatus[] statuses = OrderStatus.values();
            for (OrderStatus orderStatus : statuses) {
                if (status.equalsIgnoreCase(orderStatus.name())) {
                    isStatusValid = true;
                }
            }
        }
        return isStatusValid;
    }
}
