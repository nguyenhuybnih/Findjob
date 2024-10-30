package doan.timkiemvieclam.service;

import doan.timkiemvieclam.entity.Accounts;

import doan.timkiemvieclam.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AccountService {

    @Autowired
    private AccountRepository AccountRepository;

    public List<Accounts> getAllAccounts() {
        return AccountRepository.findAll();
    }

    public Optional<Accounts> getAccountsById(Integer id) {
        return AccountRepository.findById(id);
    }

    public Accounts saveAccounts(Accounts Account) {
        return AccountRepository.save(Account);
    }

    public Accounts updateAccounts(Integer id, Accounts AccountsDetails) {
        Accounts Account = AccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found with id " + id));

        Account.setAccountName(AccountsDetails.getAccountName());
        Account.setPassword(AccountsDetails.getPassword());
        Account.setIsActive(AccountsDetails.getIsActive());
        Account.setRole(AccountsDetails.getRole());


        return AccountRepository.save(Account);
    }


    public void deleteAccountsById(Integer id) {
        AccountRepository.deleteById(id);
    }


}
