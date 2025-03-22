package model;

import java.sql.Date;

public class Item {
    private int id;
    private String name;
    private double price;
    private int totalNumber;
    private String description;
    private Date issueDate;
    private Date expiryDate;
    private boolean hasDetails;

    public Item() {}

    public Item(String name, double price, int totalNumber, String description, Date issueDate, Date expiryDate) {
        this.name = name;
        this.price = price;
        this.totalNumber = totalNumber;
        this.description = description;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public Item(int id, String name, double price, int totalNumber, String description, Date issueDate, Date expiryDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.totalNumber = totalNumber;
        this.description = description;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getTotalNumber() { return totalNumber; }
    public void setTotalNumber(int totalNumber) { this.totalNumber = totalNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public boolean isHasDetails() { return hasDetails; }
    public void setHasDetails(boolean hasDetails) { this.hasDetails = hasDetails; }
}
