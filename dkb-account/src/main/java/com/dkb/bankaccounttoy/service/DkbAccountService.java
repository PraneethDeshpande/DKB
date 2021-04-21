package com.dkb.bankaccounttoy.service;

import java.util.List;

import com.dkb.bankaccounttoy.model.DkbAccount;
import com.dkb.bankaccounttoy.model.TransactionHistory;
import com.dkb.bankaccounttoy.model.TransferModel;

public interface DkbAccountService {

	//DbkAccount save(DbkAccount emp);
	public boolean save(DkbAccount emp);
	public boolean withdraw(String amount, DkbAccount emp);
	public boolean deposit(String amount, DkbAccount emp);

	DkbAccount getAccount(String iban);

	DkbAccount getRecordByAccountType(String accounttype);

	long getBalance(String iban);

	List<TransactionHistory> getallTransactions(String iban);

	List<DkbAccount> findall();
	
	public String transferFunds(TransferModel transferModel);

}
