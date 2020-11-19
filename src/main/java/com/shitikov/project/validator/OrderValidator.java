package com.shitikov.project.validator;

import com.shitikov.project.model.entity.type.OrderStatus;

/**
 * The type Order validator.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class OrderValidator implements Validator{
    private OrderValidator() {}

    /**
     * Check status boolean.
     *
     * @param status the status
     * @return the boolean
     */
    public static boolean checkStatus(String status) {
        boolean isStatusValid = false;

        if (status != null && !status.isEmpty()) {
            OrderStatus[] statuses = OrderStatus.values();
            for (OrderStatus orderStatus : statuses) {
                if (status.equalsIgnoreCase(orderStatus.name())) {
                    isStatusValid = true;
                    break;
                }
            }
        }
        return isStatusValid;
    }
}
