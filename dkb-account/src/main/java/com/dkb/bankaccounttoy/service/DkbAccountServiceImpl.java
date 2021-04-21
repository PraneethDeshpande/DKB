/**
 * 
 */
package com.dkb.bankaccounttoy.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dkb.bankaccounttoy.controller.DbkAccountController;
import com.dkb.bankaccounttoy.model.DkbAccount;
import com.dkb.bankaccounttoy.model.TransactionHistory;
import com.dkb.bankaccounttoy.model.TransferModel;
import com.dkb.bankaccounttoy.repository.DbkAccountRepository;
import com.dkb.bankaccounttoy.repository.TransactionHistoryRepository;

/**
 * @author praneeth
 *
 */
@Service
public class DkbAccountServiceImpl implements DkbAccountService {

	@Autowired
	DbkAccountRepository repo;

	@Autowired
	TransactionHistoryRepository transactionRepo;

	Logger logger = LoggerFactory.getLogger(DbkAccountController.class);

	@Override
	public boolean save(DkbAccount emp) {

		try {
			if (null != emp) {

				TransactionHistory transactionHistoryDTO = new TransactionHistory();

				transactionHistoryDTO.setIban(emp.getIban());
				transactionHistoryDTO.setLastTransactionTime(emp.getLastTransactionTime());
				transactionHistoryDTO.setTransactiondetails(emp.getTransactiondetails());
				transactionRepo.save(transactionHistoryDTO);
			}
			/* transactionRepo.save() */
			repo.save(emp);
		} catch (Exception e) {

			logger.error("Error occured in transfer:", e);
			return false;
		}
		return true;

	}

	@Override
	public DkbAccount getAccount(String iban) {
		DkbAccount dbkAccount = null;
		try {
			dbkAccount = repo.getOne(iban);
		} catch (Exception e) {
			logger.error("Error occured in getting account by iban:", e);
		}
		return dbkAccount;
	}

	@Override
	public DkbAccount getRecordByAccountType(String accounttype) {
		DkbAccount dbkAccount = null;
		try {
			dbkAccount = repo.getRecordbyAccount(accounttype);
		} catch (Exception e) {
			logger.error("Error occured in getting Account by Accounttype:", e);
		}
		return dbkAccount;
	}

	@Override
	public long getBalance(String iban) {
		long balance = 0;
		try {
			balance = repo.getBalancebyAccount(iban);
		} catch (Exception e) {
			logger.error("Error occured in getting balance from accounts:", e);
		}
		return balance;
	}

	@Override
	public List<DkbAccount> findall() {
		
		List<DkbAccount> accountList = null;
		try {
			accountList = repo.findAll();
			if(accountList==null) {
				logger.error("No account found");
			}
		} catch (Exception e) {
			logger.error("Error occured in getting all accounts:", e);
		}
		return accountList;

	}

	@Override
	public List<TransactionHistory> getallTransactions(String iban) {
		List<TransactionHistory> transactions = null;
		try {
			transactions = transactionRepo.getTransactionsbyIban(iban);
			if(transactions==null) {
				logger.error("No Transactions found.");
				
			}
		} catch (Exception e) {
			logger.error("Error occured in getting all accounts:", e.getMessage());
		}

		return transactions;
	}

	@Override
	public String transferFunds(TransferModel transferModel) {
		String returnVal = null;
		/**
		 * if the sender accounttype = PrivateLoanAccount then we stop the process
		 */

		boolean transferAmountFlag = false;
		try {
			if (transferModel.getSenderAccountType().equalsIgnoreCase("privateLoanAccount")) {
				transferAmountFlag = false;
				/**
				 * if the sender accounttype = SavingsLoanAccount then we check for reciever
				 * Accounttype if it is privateLoanAccount we stop the process with flag
				 */

			} else if (transferModel.getSenderAccountType().equalsIgnoreCase("SavingsAccount")) {
				if (transferModel.getReceiverAccountType().equalsIgnoreCase("CheckingAccount")) {
					transferAmountFlag = true;
				} else {
					transferAmountFlag = false;
				}

				/**
				 * if the sender accounttype = CheckingLoanAccount then we change the flag to
				 * true
				 */
			} else if (transferModel.getSenderAccountType().equalsIgnoreCase("CheckingAccount")) {
				transferAmountFlag = true;
			}

			else {
				transferAmountFlag = false;
			}

			/**
			 * We end the Process with respective to flag
			 */
			if (transferAmountFlag) {

				DkbAccount sendingAccount = getAccount(transferModel.getSenderIban());

				if (sendingAccount.getBalance() >= Long.valueOf(transferModel.getAmount())) {
					withdraw(transferModel.getAmount(), sendingAccount);
					DkbAccount receiveingAccount = getAccount(transferModel.getReceiverIban());
					deposit(transferModel.getAmount(), receiveingAccount);
					returnVal = "Transfer successful";
				} else {
					returnVal = "Not enough funds in the From Account";
				}
				// ResponseEntity.ok().body(transferModel.getAmount() + "is transfer from" +
				// transferModel.getSenderIban() + " to" + transferModel.getReceiverIban());
			} else {
				returnVal = "Transfer cannot be made ";
				// ResponseEntity.unprocessableEntity() .body(transferModel.getAmount() +
				// "cannot transfer transaction ended");

			}
		} catch (Exception e) {
			logger.error("Error occured in transfer:", e);
		}
		return returnVal;
	}

	@Override
	public boolean withdraw(String amount, DkbAccount dto) {
		boolean returnFlag = false;
		try {
			dto.setBalance(dto.getBalance() - Long.valueOf(amount));
			dto.setLastTransactionTime(new Date());
			dto.setTransactiondetails(amount + " is transferred from " + dto.getIban());
			save(dto);
			returnFlag = true;
		} catch (Exception e) {
			logger.error("Error occured in withdraw:", e);
		}
		return returnFlag;

	}

	/**
	 * 
	 * This is a common method for Deposit
	 * 
	 */

	@Override
	public boolean deposit(String amount, DkbAccount dto) {
		boolean returnFlag = false;
		try {
			dto.setBalance(dto.getBalance() + Long.valueOf(amount));
			dto.setLastTransactionTime(new Date());
			dto.setTransactiondetails(amount + " is deposited in " + dto.getIban());
			save(dto);
			returnFlag = true;
		} catch (Exception e) {
			logger.error("Error occured in Deposit:", e);
		}
		return returnFlag;

	}

}
