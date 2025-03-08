package service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	    String query = "SELECT * FROM Item";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(query);
	         ResultSet resultSet = stmt.executeQuery()) {

	        while (resultSet.next()) {
	            items.add(new Item(
	                    resultSet.getInt("ID"),
	                    resultSet.getString("NAME"),
	                    resultSet.getDouble("PRICE"),
	                    resultSet.getInt("TOTAL_NUMBER")
	            ));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return items;
	}


	@Override
	public Item getItemById(int id) {
	    String query = "SELECT * FROM Item WHERE ID = ?";
	    
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(query)) {
	        
	        stmt.setInt(1, id);
	        try (ResultSet resultSet = stmt.executeQuery()) {
	            if (resultSet.next()) {
	                return new Item(
	                        resultSet.getInt("ID"),
	                        resultSet.getString("NAME"),
	                        resultSet.getDouble("PRICE"),
	                        resultSet.getInt("TOTAL_NUMBER")
	                );
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}


	@Override
	public boolean addItem(Item item) {
	    String sql = "INSERT INTO Item (NAME, PRICE, TOTAL_NUMBER) VALUES (?, ?, ?)";

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        stmt.setString(1, item.getName());
	        stmt.setDouble(2, item.getPrice());
	        stmt.setInt(3, item.getTotalNumber());

	        int rowsInserted = stmt.executeUpdate();

	        if (rowsInserted > 0) {
	            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    item.setId(generatedKeys.getInt(1)); 
	                }
	            }
	            return true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	@Override
	public boolean updateItemById(Item item) {
	    String query = "UPDATE Item SET NAME = ?, PRICE = ?, TOTAL_NUMBER = ? WHERE ID = ?";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(query)) {

	        stmt.setString(1, item.getName());
	        stmt.setDouble(2, item.getPrice());
	        stmt.setInt(3, item.getTotalNumber());
	        stmt.setInt(4, item.getId());

	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}


	@Override
	public boolean removeItemById(int id) {
	    String deleteQuery = "DELETE FROM Item WHERE ID = ?";
	    String resetSequenceQuery = "DECLARE v_max_id NUMBER; " +
	                                "BEGIN " +
	                                "SELECT COALESCE(MAX(ID), 0) INTO v_max_id FROM Item; " +
	                                "EXECUTE IMMEDIATE 'ALTER SEQUENCE item_seq RESTART START WITH ' || (v_max_id + 1); " +
	                                "END;";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
	         Statement resetStmt = connection.createStatement()) {

	        deleteStmt.setInt(1, id);
	        int rowsDeleted = deleteStmt.executeUpdate();

	        if (rowsDeleted > 0) {
	            resetStmt.execute(resetSequenceQuery);
	            return true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}







}
