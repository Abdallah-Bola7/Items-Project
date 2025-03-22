<%@page import="java.util.List"%>
<%@page import="model.Item"%>
<%@page import="service.ItemService"%>
<%@page import="service.impl.ItemServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    ItemService itemService = (ItemService) application.getAttribute("itemService");
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Show Items</title>
    <link rel="stylesheet" href="css/show-items.css">
</head>
<body>
<div class="layer">
    <h1>Items</h1>
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>PRICE</th>
                <th>TOTAL NUMBER</th>
                <th>DESCRIPTION</th>
                <th>ISSUE DATE</th>
                <th>EXPIRY DATE</th>
                <th>ACTIONS</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Item> items = (List<Item>) session.getAttribute("allItems");
                if (items != null && !items.isEmpty()) {
                    for (Item item : items) {
                        boolean hasDetails = itemService.hasDetails(item.getId());
            %>
            <tr>
                <td><%= item.getId() %></td>
                <td><%= item.getName() %></td>
                <td>$<%= item.getPrice() %></td>
                <td><%= item.getTotalNumber() %></td>
                <td><%= item.getDescription() != null ? item.getDescription() : "N/A" %></td>
                <td><%= item.getIssueDate() != null ? item.getIssueDate() : "N/A" %></td>
                <td><%= item.getExpiryDate() != null ? item.getExpiryDate() : "N/A" %></td>
                <td class="action-buttons">
                    <a href="ItemController?action=loadItem&itemId=<%= item.getId() %>" class="update-btn">Update</a>

                    <% if (!hasDetails) { %>
   						 <a href="add-item-details.jsp?itemId=<%= item.getId() %>" class="add-btn">Add Details</a>
					<% } %>

	                <% if (hasDetails) { %>
					    <a href="ItemController?action=deleteItemDetails&itemId=<%= item.getId() %>" class="delete-btn"
					       onclick="return confirm('Are you sure you want to delete item details?');">Delete Details</a>
					<% } %>	

                    <a href="ItemController?action=deleteItem&itemId=<%= item.getId() %>" class="delete-btn"
                       onclick="return confirm('Are you sure you want to delete this item?');">Delete</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="8">No items available.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <a href="add-item.html" class="add-item-btn"> Add Item</a>
</div>
</body>
</html>
