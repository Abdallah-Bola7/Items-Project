package service;

import java.util.Date;
import java.util.List;

import model.Item;

public interface ItemService {
	List<Item> getAllItem();
	Item getItemById(int id);
	boolean addItem(Item item);
	boolean updateItemById(Item item);
	boolean removeItemById(int id);
	boolean addItemDetails(int itemId, String description, Date issueDate, Date expiryDate);
	boolean deleteItemDetails(int itemId);
	boolean hasDetails(int itemId);

}
