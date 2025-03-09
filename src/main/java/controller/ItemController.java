package controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Item;
import service.ItemService;
import service.impl.ItemServiceImpl;

@WebServlet("/ItemController")
public class ItemController extends HttpServlet {
    
    @Resource(name = "jdbc/item")
    private DataSource dataSource;

    private ItemService itemService;

    @Override
    public void init() throws ServletException {
        super.init();
        itemService = new ItemServiceImpl(dataSource); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "loadItems";
        }

        switch(action) {
            case "loadItems":
                loadItems(request, response);
                break;
            case "loadItem":
                loadItem(request, response);
                break;
            case "addItem":
                addItem(request, response);
                break;
            case "deleteItem":
                deleteItem(request, response);
                break;
            case "updateItem":
                updateItem(request, response);
                break;
            default:
                loadItems(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        doGet(request, response);
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) {
        try {
            ItemService itemService = new ItemServiceImpl(dataSource);
            Item item = new Item(
                request.getParameter("itemName"),
                Double.parseDouble(request.getParameter("itemPrice")),
                Integer.parseInt(request.getParameter("itemTotalNumber")),
                request.getParameter("description"),
                java.sql.Date.valueOf(request.getParameter("issueDate")),
                java.sql.Date.valueOf(request.getParameter("expiryDate"))
            );

            boolean added = itemService.addItem(item);

            if (added) {
                response.sendRedirect("ItemController?action=loadItems"); 
            } else {
                response.getWriter().println("Error: Item could not be added.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void updateItem(HttpServletRequest request, HttpServletResponse response) {
        try {
            ItemService itemService = new ItemServiceImpl(dataSource);
            Item item = new Item(
                Integer.parseInt(request.getParameter("itemId")),
                request.getParameter("itemName"),
                Double.parseDouble(request.getParameter("itemPrice")),
                Integer.parseInt(request.getParameter("itemTotalNumber")),
                request.getParameter("description"),
                java.sql.Date.valueOf(request.getParameter("issueDate")),
                java.sql.Date.valueOf(request.getParameter("expiryDate"))
            );

            boolean updated = itemService.updateItemById(item);

            if (updated) {
                response.sendRedirect("ItemController?action=loadItems"); 
            } else {
                response.getWriter().println("Update failed. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) {
        try {
            ItemService itemService = new ItemServiceImpl(dataSource);
            int itemId = Integer.parseInt(request.getParameter("itemId"));

            boolean deleted = itemService.removeItemById(itemId);

            if (deleted) {
                response.sendRedirect("ItemController?action=loadItems"); 
            } else {
                response.getWriter().println("Error: Item could not be deleted.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadItem(HttpServletRequest request, HttpServletResponse response) {
        try {
            ItemService itemService = new ItemServiceImpl(dataSource);
            int itemId = Integer.parseInt(request.getParameter("itemId"));

            Item item = itemService.getItemById(itemId);

            if (item == null) {
                response.getWriter().println("Error: Item not found.");
                return;
            }

            request.setAttribute("itemSelected", item);
            request.getRequestDispatcher("/update-item.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> items = itemService.getAllItem();
        request.setAttribute("allItems", items);
        request.getRequestDispatcher("/load-items.jsp").forward(request, response);
    }
}
