/**
 * 
 */
package com.dkb.bankaccounttoy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dkb.bankaccounttoy.model.DkbAccount;
import com.dkb.bankaccounttoy.model.TransactionBalance;
import com.dkb.bankaccounttoy.model.TransactionHistory;
import com.dkb.bankaccounttoy.model.TransferModel;
import com.dkb.bankaccounttoy.service.DkbAccountService;

import lombok.NonNull;

/**
 * 
 * This is acontroller method which is called by Overview Microservice of DKB.
 * 
 * @author Praneeth
 *
 */
@RestController
@RequestMapping("/dkbaccount")
public class DbkAccountController {

	@Autowired
	private DkbAccountService dkbAccountService;

	Logger logger = LoggerFactory.getLogger(DbkAccountController.class);

	/**
	 * Usecase: Bonus - account creation ‣ To open an account and assign it with an
	 * IBAN an endpoint should be provided.
	 * 
	 * Description: This method will return all the checking accounts of the
	 * customer.
	 * 
	 * @param account
	 * @return DbkAccount
	 */

	@PostMapping("/save")
	public Object saveAccounts(@RequestBody DkbAccount dbkAccount) {
		Object returnVal = null;

		if (dkbAccountService.save(dbkAccount)) {
			returnVal = new ResponseEntity<DkbAccount>(HttpStatus.CREATED).getBody();
		} else {

			returnVal = new ResponseEntity<DkbAccount>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error("Records Couldn't save");
		}
		return returnVal;
	}

	/**
	 * This Method is Used to get the account with respect to IBAN
	 * 
	 * @param iban
	 * @return DbkAccount
	 */

	@GetMapping("iban/{iban}")
	public ResponseEntity<DkbAccount> getAccountByIban(@PathVariable("iban") String iban) {

		DkbAccount dbkAccount = dkbAccountService.getAccount(iban);

		if (dbkAccount == null) {
			ResponseEntity.unprocessableEntity().body(" No Accounts found with this " + iban);
		}

		return new ResponseEntity<DkbAccount>(HttpStatus.ACCEPTED);

	}

	/**
	 * 
	 * Usecase: Filter accounts by account type This Method is Used to get the
	 * account with respect to IBAN
	 * 
	 * @param iban
	 * @return DbkAccount
	 */
	@GetMapping("/accounttype/{accounttype}")
	public ResponseEntity<DkbAccount> getAccountbyAccountType(@PathVariable(value = "accounttype") String accounttype) {

		DkbAccount dbkAccount = dkbAccountService.getRecordByAccountType(accounttype);

		if (null != dbkAccount) {
			ResponseEntity.unprocessableEntity().body(" No Accounts found with this " + accounttype);
		}

		return ResponseEntity.ok().body(dbkAccount);

	}

	/**
	 * This Method is Used to get the accounts
	 * 
	 * @param iban
	 * @return DbkAccount
	 */

	@GetMapping("/getAccounts")
	public ResponseEntity<List<DkbAccount>> getAccounts() {

		List<DkbAccount> dbkAccount = dkbAccountService.findall();

		if (dbkAccount == null) {
			ResponseEntity.unprocessableEntity().body(dbkAccount + " No Accounts found for this Person");
		}

		return ResponseEntity.ok().body(dbkAccount);

	}

	/***
	 * Usecase: Show current balance of the specific bank account
	 * 
	 * @param accounttype
	 * @return Long
	 */
	@GetMapping("/balance/{accounttype}")
	public ResponseEntity<Long> getBalance(@PathVariable(value = "accounttype") String accounttype) {

		long dbkAccount = dkbAccountService.getBalance(accounttype);

		return ResponseEntity.ok().body(dbkAccount);

	}

	/***
	 * Deposit money into a specified bank account ‣ Enable adding some amount to a
	 * specified bank account
	 * 
	 * 
	 * @param accounttype
	 * @return String
	 */

	@PostMapping("/deposit")
	public Object depositAMount(@RequestBody TransactionBalance addBalanceDto) {
		Object returnVal = null;
		String iban = addBalanceDto.getIban();
		String amount = addBalanceDto.getAmount();
		DkbAccount dbkAccount = dkbAccountService.getAccount(iban);

		if (dkbAccountService.deposit(amount, dbkAccount)) {
			returnVal = ResponseEntity.ok().body(amount + "is added");
		} else {
			returnVal = new ResponseEntity<DkbAccount>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return returnVal;
	}

	/***
	 * Withdraw money from a specified bank account ‣ Enable deducting some amount
	 * from a specified bank account
	 * 
	 * 
	 * @param accounttype
	 * @return String
	 */

	@PostMapping("/withdraw")
	public Object WithdrawAmount(@RequestBody TransactionBalance addBalanceDto) {
		Object returnVal = null;
		String iban = addBalanceDto.getIban();
		String amount = addBalanceDto.getAmount();
		DkbAccount dbkAccount = dkbAccountService.getAccount(iban);

		if (dbkAccount.getBalance() >= Long.valueOf(amount)) {
			if (dkbAccountService.withdraw(amount, dbkAccount)) {
				returnVal = ResponseEntity.ok().body(amount + "is added");
			} else {
				returnVal = new ResponseEntity<DkbAccount>(HttpStatus.BAD_REQUEST);
			}
		} else {
			returnVal = ResponseEntity.unprocessableEntity().body(amount + "cannot be withdrawn");
			logger.error("Amount cannot be Withdrawn");
		}

		return returnVal;
	}

	/**
	 * Usecase: Show a transaction history ‣ For an account, specified by IBAN, show
	 * the transaction history.
	 * 
	 * This Method is responsible for getting all the transactions
	 * 
	 */

	@GetMapping("/transaction/{iban}")
	public ResponseEntity<List<TransactionHistory>> getAllTransaction(@PathVariable(value = "iban") String iban) {

		List<TransactionHistory> trasactions = dkbAccountService.getallTransactions(iban);
		if (trasactions == null) {
			ResponseEntity.unprocessableEntity().body(trasactions + "No Transactions Found");
		}
		return ResponseEntity.ok().body(trasactions);

	}

	/**
	 * 
	 * ‣Transfer some money across two bank accounts ‣ Imagine this like a regular
	 * bank transfer. You should be able to withdraw money from one account and
	 * deposit it to another account
	 * 
	 * This Call is used to transfer the Money. It is a trasactional Method as there
	 * are two different calls hit to the database
	 * 
	 */
	@PostMapping("/transfer")
	@Transactional
	public Object TransferAmount(@NonNull @RequestBody TransferModel transferModel) {

		Object resp = dkbAccountService.transferFunds(transferModel);

		if (resp == null) {
			ResponseEntity.unprocessableEntity().body(transferModel.getAmount() + "cannot be transferred");
		}
		return ResponseEntity.ok().body(resp);

	}

}