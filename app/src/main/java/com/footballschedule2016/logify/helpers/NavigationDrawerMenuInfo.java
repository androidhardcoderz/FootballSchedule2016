package com.footballschedule2016.logify.helpers;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Auman on 3/2/2016.
 */
public class NavigationDrawerMenuInfo {

    private List<Integer> menuItems = new ArrayList<>();

    /**
     * set up constructor
     * builds collections list that contains all ids of the menu
     * @param navigationView the navagation view from drawer layout
     */
    public NavigationDrawerMenuInfo(NavigationView navigationView){

        boolean noMore = false;
        int x = 0;
        while(!noMore){
            try {
                menuItems.add(navigationView.getMenu().getItem(x).getItemId());
                x += 1;
            }catch(IndexOutOfBoundsException iobe){
                iobe.printStackTrace();
                noMore = true;
            }
        }
    }

    /**
     * Returns the index of the menu item selected
     * @param itemId menu item id selected
     * @return index of menu item selected
     */
    public int findIndexOfMenuItem(int itemId){

        //loop through the menuItem collection
        for(Integer id: menuItems){
            if(id == itemId) //check for equal item id
                return menuItems.indexOf(id); //return index of menuItem found in collection
        }

        return 0; //default return
    }

    /**
     * Returns the index of the menu item selected
     * @param item menu item selected
     * @return index of menu item selected
     */
    public int findIndexOfMenuItem(MenuItem item){

        int itemId = item.getItemId();
        //loop through the menuItem collection
        for(Integer id: menuItems){
            if(id == itemId) //check for equal item id
                return menuItems.indexOf(id); //return index of menuItem found in collection
        }

        return 0; //default return
    }

    /**
     * @param index index of menuItem
     * @return int itemID of menu Item at Specified index
     */
    public int findIdOfMenuIndex(int index){
        return menuItems.get(index);
    }

}
