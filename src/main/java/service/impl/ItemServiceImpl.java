package service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import model.Item;
import service.ItemService;

public class ItemServiceImpl implements  ItemService {

	private DataSource dataSource;
	
	public ItemServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Item> getAllItem() {
	    List<Item> items = new ArrayList<>();
	    String sql = "SELECT i.id, i.name, i.price, i.total_number, d.description, d.issue_date, d.expiry_date " +
	             "FROM item i LEFT JOIN item_details d ON i.id = d.id";


	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	    		
	        while (rs.next()) {
	            Item item = new Item(
	                rs.getInt("id"),
	                rs.getString("name"),
	                rs.getDouble("price"),
	                rs.getInt("total_number"),
	                rs.getString("description"),
	                rs.getDate("issue_date"),
	                rs.getDate("expiry_date")
	            );
	            items.add(item);
	        }
	        
	        System.out.println("Fetched items: " + items.size());

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return items;
	}

	@Override
	public Item getItemById(int id) {
	    String query = "SELECT i.ID, i.NAME, i.PRICE, i.TOTAL_NUMBER, d.DESCRIPTION, d.ISSUE_DATE, d.EXPIRY_DATE " +
	                   "FROM Item i " +
	                   "LEFT JOIN Item_details d ON i.ID = d.ID " +
	                   "WHERE i.ID = ?";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(query)) {

	        stmt.setInt(1, id);
	        ResultSet resultSet = stmt.executeQuery();

	        if (resultSet.next()) {
	            return new Item(
	                resultSet.getInt("ID"),
	                resultSet.getString("NAME"),
	                resultSet.getDouble("PRICE"),
	                resultSet.getInt("TOTAL_NUMBER"),
	                resultSet.getString("DESCRIPTION"),
	                resultSet.getDate("ISSUE_DATE"),
	                resultSet.getDate("EXPIRY_DATE")
	            );
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null; 
	}

	@Override
	public boolean addItem(Item item) {
	    String insertItemQuery = "INSERT INTO Item (ID, NAME, PRICE, TOTAL_NUMBER) VALUES (item_seq.NEXTVAL, ?, ?, ?)";
	    String insertDetailsQuery = "INSERT INTO Item_details (ID, DESCRIPTION, ISSUE_DATE, EXPIRY_DATE) VALUES (?, ?, ?, ?)";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement itemStmt = connection.prepareStatement(insertItemQuery, new String[] {"ID"});
	         PreparedStatement detailsStmt = connection.prepareStatement(insertDetailsQuery)) {

	        connection.setAutoCommit(false); 

	        	        itemStmt.setString(1, item.getName());
	        itemStmt.setDouble(2, item.getPrice());
	        itemStmt.setInt(3, item.getTotalNumber());
	        int rowsInserted = itemStmt.executeUpdate();

	        
	        int generatedId = -1;
	        try (ResultSet generatedKeys = itemStmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                generatedId = generatedKeys.getInt(1); 
	                item.setId(generatedId);
	            }
	        }

	        	        detailsStmt.setInt(1, generatedId);
	        detailsStmt.setString(2, item.getDescription());
	        detailsStmt.setDate(3, new java.sql.Date(item.getIssueDate().getTime()));
	        detailsStmt.setDate(4, new java.sql.Date(item.getExpiryDate().getTime()));
	        detailsStmt.executeUpdate();

	        connection.commit(); 
	        return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean addItemDetails(int itemId, String description, Date issueDate, Date expiryDate) {
	    String sql = "INSERT INTO item_details (id, description, issue_date, expiry_date) VALUES (?, ?, ?, ?)";

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, itemId);
	        stmt.setString(2, description);
	        stmt.setDate(3, new java.sql.Date(issueDate.getTime()));
	        stmt.setDate(4, new java.sql.Date(expiryDate.getTime()));

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;  //
	}



	@Override
	public boolean deleteItemDetails(int itemId) {
	    String sql = "DELETE FROM Item_details WHERE id = ?";
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, itemId);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	@Override
	public boolean hasDetails(int itemId) {
	    boolean exists = false;
	    String sql = "SELECT COUNT(*) FROM Item_details WHERE id = ?";
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, itemId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                exists = rs.getInt(1) > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return exists;
	}



	
	@Override
	public boolean updateItemById(Item item) {
	    String updateItemQuery = "UPDATE Item SET NAME = ?, PRICE = ?, TOTAL_NUMBER = ? WHERE ID = ?";
	    String updateDetailsQuery = "UPDATE Item_details SET description = ?, issue_date = ?, expiry_date = ? WHERE ID = ?";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement itemStmt = connection.prepareStatement(updateItemQuery);
	         PreparedStatement detailsStmt = connection.prepareStatement(updateDetailsQuery)) {

	        itemStmt.setString(1, item.getName());
	        itemStmt.setDouble(2, item.getPrice());
	        itemStmt.setInt(3, item.getTotalNumber());
	        itemStmt.setInt(4, item.getId());
	        int itemUpdated = itemStmt.executeUpdate();

	        detailsStmt.setString(1, item.getDescription());
	        detailsStmt.setDate(2, new java.sql.Date(item.getIssueDate().getTime()));
	        detailsStmt.setDate(3, new java.sql.Date(item.getExpiryDate().getTime()));
	        detailsStmt.setInt(4, item.getId());
	        int detailsUpdated = detailsStmt.executeUpdate();

	        return itemUpdated > 0 && detailsUpdated > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}


	@Override
	public boolean removeItemById(int id) {
	    String query = "DELETE FROM Item WHERE ID = ?";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(query)) {

	        stmt.setInt(1, id);
	        int rowsDeleted = stmt.executeUpdate();

	        return rowsDeleted > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}








}
