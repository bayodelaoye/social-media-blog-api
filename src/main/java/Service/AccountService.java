package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account) {
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4 || accountDAO.getAccount(account.getUsername()) != null) return null;
        return accountDAO.createAccount(account); 
    }

    public Account login(Account account) {
        if (accountDAO.login(account) == null) return null;
        return accountDAO.login(account); 
    }
}
