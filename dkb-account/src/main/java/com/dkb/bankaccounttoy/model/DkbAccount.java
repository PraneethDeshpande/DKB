/**
 * 
 */
package com.dkb.bankaccounttoy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author praneeth This Class contains the fields of Checking Account
 *
 */
@Entity
@Table(name = "Dbk_Account")
public class DkbAccount extends AuditModel {

	/**
	 * private static final long serialVersionUID = -8051941834348633714L;
	 * 
	 */
	private static final long serialVersionUID = -8051941834348633714L;

	@Id
	private String iban;

	private String accounttype;

	private long balance;

	private Date lastTransactionTime;

	private String accountStatus;

	private String transactiondetails;

	public DkbAccount() {
		super();
	}

	public DkbAccount(String iban, String accounttype, long balance, Date lastTransactionTime,
			String accountStatus, String transactiondetails) {
		this.iban = iban;
		this.accounttype = accounttype;
		this.balance = balance;
		this.lastTransactionTime = lastTransactionTime;
		this.accountStatus = accountStatus;
		this.transactiondetails = transactiondetails;

	}

	/**
	 * @return the balance
	 */
	public long getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(long balance) {
		this.balance = balance;
	}

	/**
	 * @return the lastTransactionTime
	 */
	public Date getLastTransactionTime() {
		return lastTransactionTime;
	}

	/**
	 * @param lastTransactionTime the lastTransactionTime to set
	 */
	public void setLastTransactionTime(Date lastTransactionTime) {
		this.lastTransactionTime = lastTransactionTime;
	}

	/**
	 * @return the accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	
	/**
	 * @return the transactiondetails
	 */
	public String getTransactiondetails() {
		return transactiondetails;
	}

	/**
	 * @param transactiondetails the transactiondetails to set
	 */
	public void setTransactiondetails(String transactiondetails) {
		this.transactiondetails = transactiondetails;
	}

	/**
	 * @return the iban
	 */
	public String getIban() {
		System.out.println("12345" + iban);
		return iban;
	}

	/**
	 * @param iban the iban to set
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * @return the accounttype
	 */
	public String getaccounttype() {
		System.out.println("12345" + accounttype);
		return accounttype;
	}

	/**
	 * @param accounttype the accounttype to set
	 */
	public void setaccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

}
