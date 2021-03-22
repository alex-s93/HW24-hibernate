package com.alevel.homework24.model;

import java.sql.Timestamp;

public class Order {
    private long id;
    private Product product;
    private User user;
    private int count;
    private Status status;
    private Timestamp orderDate;

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order info: " +
                " | order_id=" + id +
                " | count=" + count +
                " | status='" + status + "'" +
                " | orderDate='" + orderDate + "' | " +
                product + " | " +
                user + " |";

    }
}
