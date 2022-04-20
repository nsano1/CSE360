package com.yummy.project.account;

import com.yummy.project.menu.Order;
import com.yummy.project.state.State;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Account implements Serializable {
    private State state = State.getInstance();

    // Basic Info
    private UUID id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private boolean isOwner;
    private Order cart;

    public Account(String username,
                   String password,
                   String phone,
                   String email,
                   boolean isOwner) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.isOwner = isOwner;
        id = UUID.randomUUID();
        cart = new Order(id);
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public UUID getId() {
        return id;
    }

    public Order getCart() { return cart; }

    // Adds order to OrderManager and refreshes cart
    // May need to add more logic to this later on.
    public void submitOrder(int minutes) {
        long current = Calendar.getInstance().getTime().getTime();
        Order order = cart;
        cart = new Order(id);

        order.setReadyTime(new Date(current + (minutes * 60 * 1000)));
        state.getOrderManager().addOrder(order);
    }
}
