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

    public Item(int id, String name, double price, int totalNumber, String description, Date issueDate, Date expiryDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.totalNumber = totalNumber;
        this.description = description;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }
    public Item(String name, double price, int totalNumber) {
        this.name = name;
        this.price = price;
        this.totalNumber = totalNumber;
    }


    public Item(int id, String name, double price, int totalNumber) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.totalNumber = totalNumber;
    }
    public Item(String name, double price, int totalNumber, String description, Date issueDate, Date expiryDate) {
        this.name = name;
        this.price = price;
        this.totalNumber = totalNumber;
        this.description = description;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

	public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getTotalNumber() { return totalNumber; }
    public String getDescription() { return description; }
    public Date getIssueDate() { return issueDate; }
    public Date getExpiryDate() { return expiryDate; }
}
