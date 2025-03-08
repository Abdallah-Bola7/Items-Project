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

    private void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Item item = new Item(
                request.getParameter("itemName"),
                Double.parseDouble(request.getParameter("itemPrice")),
                Integer.parseInt(request.getParameter("itemTotalNumber"))
        );

        itemService.addItem(item);
        response.sendRedirect("ItemController?action=loadItems"); 
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Item item = new Item(
                Integer.parseInt(request.getParameter("itemId")),
                request.getParameter("itemName"),
                Double.parseDouble(request.getParameter("itemPrice")),
                Integer.parseInt(request.getParameter("itemTotalNumber"))
        );

        itemService.updateItemById(item);
        response.sendRedirect("ItemController?action=loadItems");
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        itemService.removeItemById(Integer.parseInt(request.getParameter("itemId")));
        response.sendRedirect("ItemController?action=loadItems");
    }

    private void loadItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Item item = itemService.getItemById(Integer.parseInt(request.getParameter("itemId")));
        request.setAttribute("itemSelected", item);
        request.getRequestDispatcher("/update-item.jsp").forward(request, response);
    }

    private void loadItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> items = itemService.getAllItem();
        request.setAttribute("allItems", items);
        request.getRequestDispatcher("/load-items.jsp").forward(request, response);
    }
}
