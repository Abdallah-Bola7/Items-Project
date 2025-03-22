<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Item Details</title>
    <link rel="stylesheet" href="css/dark-style.css"> 
</head>
<body>
<div class="container">
    <h2>Add Item Details</h2>

    <form action="ItemController" method="POST">
        <input type="hidden" name="action" value="addItemDetails">
        <input type="hidden" name="itemId" value="<%= request.getParameter("itemId") %>">

        <div class="form-group">
            <label>Description:</label>
            <input type="text" name="description" placeholder="Enter item description" required>
        </div>

        <div class="form-group">
            <label>Issue Date:</label>
            <input type="date" name="issueDate" required>
        </div>

        <div class="form-group">
            <label>Expiry Date:</label>
            <input type="date" name="expiryDate" required>
        </div>

        <div class="form-buttons">
            <button type="submit" class="btn btn-primary">Save Details</button>
            <a href="ItemController?action=loadItems" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
