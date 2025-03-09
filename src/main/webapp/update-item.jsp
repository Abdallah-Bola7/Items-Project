<%@page import="model.Item"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Update Item</title>
  <link rel="stylesheet" href="css/form-style.css">
</head>
<body>

<%
    Item item = (Item) request.getAttribute("itemSelected");
%>

<div class="container">
  <div class="text">Update Item</div>
  
  <form action="ItemController" method="post" autocomplete="off">
    <input type="hidden" name="action" value="updateItem">
    <input type="hidden" name="itemId" value="<%= item.getId() %>">

    <div class="form-row">
      <div class="input-data">
        <input type="text" required name="itemName" value="<%= item.getName() %>">
        <label>Name</label>
      </div>
      <div class="input-data">
        <input type="number" required name="itemPrice" step="0.01" value="<%= item.getPrice() %>">
        <label>Price</label>
      </div>
    </div>
    
    <div class="form-row">
      <div class="input-data">
        <input type="number" required name="itemTotalNumber" value="<%= item.getTotalNumber() %>">
        <label>Total Number</label>
      </div>
    </div>

    <div class="form-row">
      <div class="input-data">
        <input type="text" required name="description" value="<%= item.getDescription() %>">
        <label>Description</label>
      </div>
    </div>

    <div class="form-row">
      <div class="input-data">
        <input type="date" required name="issueDate" value="<%= item.getIssueDate() %>" placeholder=" ">
        <label>Issue Date</label>
      </div>
      <div class="input-data">
        <input type="date" required name="expiryDate" value="<%= item.getExpiryDate() %>" placeholder=" ">
        <label>Expiry Date</label>
      </div>
    </div>
    
    <button type="submit" class="btn submit-btn">Save Changes</button>
  </form>

  <a href="load-items.jsp" class="btn back-btn">Back to Items</a>
</div>

</body>
</html>
