package com.yummy.project.account;

import com.yummy.project.state.Listener;
import com.yummy.project.state.Manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class AccountManager extends Manager implements Serializable {
    private ArrayList<Account> accounts = new ArrayList<Account>();
    private transient boolean loggedIn = false;
    private transient Account currentAccount = null; // Account that is logged in

    // Adds an account and calls State update method
    public void add(Account account) {
        accounts.add(account);
        stateListener.onChanged();
    }

    // Removes an account and calls State update method
    public void remove(Account account) {
        accounts.remove(account);
        stateListener.onChanged();
    }

    // Checks if username and password match an account,
    // and updates state if the account is logged into.
    public void login(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)
                    && account.getPassword().equals(password)) {
                forceLogin(account);
                return;
            }
        }
    }

    // Login to account if it exists in accounts array
    public void login(Account account) {
        if (accounts.contains(account)) {
            forceLogin(account);
        }
    }

    // Logs into account no matter what
    private void forceLogin(Account account) {
        currentAccount = account;
        loggedIn = true;
        stateListener.onChanged();
    }

    // Getter for currentAccount
    public Account getCurrentAccount() {
        return currentAccount;
    }

    // Search for an account by UUID, returns null if not found.
    public Account getAccountById(UUID id) {
        Account account = null;

        for (Account acc : accounts) {
            if (acc.getId().compareTo(id) == 0) {
                account = acc;
                break;
            }
        }

        return account;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }
}
