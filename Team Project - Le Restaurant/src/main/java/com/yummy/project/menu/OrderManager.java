package com.yummy.project.menu;

import com.yummy.project.state.Manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderManager extends Manager implements Serializable {
    private List<Order> orders = new ArrayList<Order>();

    public void addOrder(Order order) {
        orders.add(order);
        stateListener.onChanged();
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        stateListener.onChanged();
    }

    // Returns a list of orders that are owner by a given account ID
    public List<Order> getOrdersByUser(UUID userID) {
        List<Order> userOrders = new ArrayList<Order>();

        // Add any orders that have matching ownerID
        for (Order order : orders) {
            if (order.getOwnerID().compareTo(userID) == 0) {
                userOrders.add(order);
            }
        }

        return userOrders;
    }

    // Find an order by its id. Returns null if not found
    public Order getOrderById(UUID id) {
        Order result = null;

        for (Order order : orders) {
            if (order.getID().compareTo(id) == 0) {
                result = order;
                break;
            }
        }

        return result;
    }
}
