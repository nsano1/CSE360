package com.yummy.project.menu;

import com.yummy.project.state.Manager;
import com.yummy.project.ui.MenuPage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MenuManager extends Manager implements Serializable {
    // Sorting filters
    public static final Comparator<? super Item> priceSort = Comparator.comparing(Item::getPrice,
            Comparator.nullsLast(Comparator.naturalOrder()));
    public static final Comparator<? super Item> nameSort = Comparator.comparing(Item::getName,
            Comparator.nullsLast(Comparator.naturalOrder()));

    private List<Item> items = new ArrayList<Item>();

    // Keep track of last filter used for sorting
    private transient Comparator<? super Item> filter;
    private transient boolean sorted = false;

    // Add item to list, set sorted to false
    public void add(Item item) {
        items.add(item);
        stateListener.onChanged();
        sorted = false;
    }

    public void delete(String name) {

        for(int i = 0; i < items.size();i++){
            if(items.get(i).getName().equals(name)) {
                items.remove(items.get(i));
                break;
            }
        }
        stateListener.onChanged();
    }

    // Return a new list of items of a certain type.
    // Takes in a
    public List<Item> getItemsByType(Item.Type itemType, Comparator<? super Item> c) {
        // Sort list if filter is different, or if it is not sorted.
        if (!c.equals(filter) || !sorted) {
            items.sort(c);
            sorted = true;
        }
        filter = c; // Update filter field

        // Create new item list based on given Item Type
        List<Item> list = new ArrayList<Item>();
        items.forEach(item -> {
            if (item.getType() == itemType)
                list.add(item);
        });

        // if itemType is null then add all items to list.
        if (itemType == null)
            items.forEach(item -> list.add(item));

        return list;
    }

    //create a new list of items by a certain name

    public List<Item> getItemsByName(String itemName, Comparator<?super Item> c){
       if(!c.equals(filter) || !sorted){
           items.sort(c);
           sorted = true;
       }
       filter = c;

        List<Item> list = new ArrayList<Item>();
        items.forEach(item -> {
            if (item.getName().contains(itemName))
                list.add(item);
        });
        return list;
    }
}
