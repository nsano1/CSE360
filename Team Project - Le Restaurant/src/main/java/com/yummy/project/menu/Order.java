package com.yummy.project.menu;

import com.yummy.project.state.State;

import java.io.Serializable;
import java.util.*;

public class Order implements Serializable {
    private State state = State.getInstance();

    //variables for Order
    private List<Item> items;
    private UUID id; // order's ID
    private UUID owner; // Represents the account that owns this order
    private Date readyTime;

    public Order(UUID ownerID) {
        owner = ownerID;
        items = new ArrayList<Item>();
    }

    public UUID getID() {
        return id;
    }

    public UUID getOwnerID() {
        return owner;
    }

    public void addItem(Item item) {
        items.add(item);
        state.update();
    }

    public void removeItem(Item item) {
        items.remove(item);
        state.update();
    }

    public List<Item> getItemList() {
        return items;
    }

    public void setReadyTime(Date time) {
        readyTime = time;
    }

    public Date getReadyTime() {
        return readyTime;
    }

    // Returns true if the current time is before 30 minutes after ready time
    public Boolean isCurrent() {
        if (readyTime == null) return false;
        // Add 30 minutes to ready time.
        Date bufferTime = new Date(readyTime.getTime() + (30 * 10 * 1000));
        return Calendar.getInstance().getTime().before(bufferTime);
    }

    // Returns true if the current time is after the ready time
    public Boolean isReady() {
        if (readyTime == null) return false;
        return Calendar.getInstance().getTime().after(readyTime);
    }

    // Get price of entire order. todo: include coupons in the future
    public double getTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }
}
