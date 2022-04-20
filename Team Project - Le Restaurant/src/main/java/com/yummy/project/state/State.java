package com.yummy.project.state;

import com.yummy.project.account.AccountManager;
import com.yummy.project.menu.MenuManager;
import com.yummy.project.menu.OrderManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Object that stores all information that may be changed
// Follows the singleton design pattern
public class State implements Serializable {
    private static final String filePath = System.getProperty("user.dir") + "\\storage\\";
    private static final String fileName = filePath + "state.ser";
    private static State instance = null;

    private transient List<Listener> listeners; // transient = avoid serialization
    private transient Listener listener;
    private final AccountManager accountManager;
    private final MenuManager menuManager;
    private final OrderManager orderManager;

    // Private constructor
    private State() {
        accountManager = new AccountManager();
        menuManager = new MenuManager();
        orderManager = new OrderManager();
        setupListeners();
    }

    private void setupListeners() {
        listeners = new ArrayList<Listener>();
        listener = new ChangeListener();
        accountManager.setStateListener(listener);
        menuManager.setStateListener(listener);
        orderManager.setStateListener(listener);
    }

    public static State getInstance() {
        if (instance == null)
            instance = loadFromDisk();
        return instance;
    }

    // Loads and returns State from storage. If the file is not found,
    // a new State object is returned
    private static State loadFromDisk() {
        State state = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            state = (State)in.readObject();
            in.close();
            fileIn.close();
            state.setupListeners(); // Listeners must be setup after serialization
        } catch (FileNotFoundException e) {
            // File was not found, so create new State
            System.out.println("No state file found, a new one will be created.");
            state = new State();
        } catch (InvalidClassException e) {
            System.out.println("Old state file was invalid, a new one will be created.");
            state = new State();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return state;
    }

    // Saves state instance to disk
    private void saveInstance() {
        // Make necessary directories for file
        File file = new File(filePath);
        file.mkdirs();

        // Write state object to storage
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // used by other classes to trigger a State update
    public void update() {
        listener.onChanged();
    }

    // Manager getters
    public AccountManager getAccountManager() {
        return accountManager;
    }
    public MenuManager getMenuManager() { return menuManager; }
    public OrderManager getOrderManager() { return orderManager; }

    public void addListener(Listener changeListener) {
        listeners.add(changeListener);
    }

    // Change Listener that is called by any Manager class when data is changed.
    private class ChangeListener implements Listener {
        // Notify all listeners when state is changed
        public void onChanged() {
            for (Listener listener : listeners) {
                saveInstance(); // Save state
                listener.onChanged();
            }
        }
    }
}