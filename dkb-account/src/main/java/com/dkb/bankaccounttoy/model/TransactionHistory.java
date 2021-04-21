/**
 * 
 */
package com.dkb.bankaccounttoy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

/**
 * @author Praneeth
 *
 */
@Entity
@Table(name = "Transaction_History")
@JsonIgnoreProperties(value = { "lastTransactionTime" }, allowGetters = true)
public class TransactionHistory extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7804077540290681538L;

	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) long id;
	private long tno;
	
	@NotNull
	private String iban;

	private String transactiondetails;

	/**
	 * @return the checkingAccountDTO
	 */

	/**
	 * @return the iban
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * @param iban the iban to set
	 */
	public void setIban(String iban) {
		this.iban = iban;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransactionHistoryDTO [iban=");
		builder.append(iban);
		builder.append(", lastTransactionTime=");
		builder.append(", transactiondetails=");
		builder.append(transactiondetails);
		builder.append("]");
		return builder.toString();
	}

}
