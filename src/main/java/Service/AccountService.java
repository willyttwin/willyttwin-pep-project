package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    private AccountDAO accountDAO;
    //no args constructor
    public AccountService() {
        accountDAO = new AccountDAO();
    }
    //constructor for mocking
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // register account username not blank, pass += 4 chars, username doesnt exist
    /*
     * Username must not be blank
     * password must be at least 4 characters
     * username shouldn't exist already
     */
    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return null;
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }

        return accountDAO.insertAccount(account);
    }
    // verify login

    public Account login(Account account) {
        //find account
        Account foundAccount = accountDAO.getAccountByUsername(account.getUsername());

        //verify password
        if (foundAccount != null && foundAccount.getPassword().equals(account.getPassword())) {
            return foundAccount;
        }
        //login fails
        return null;
    }
}
