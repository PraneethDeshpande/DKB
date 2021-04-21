/**
 * 
 */
package com.dkb.bankaccounttoy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Praneeth
 *
 */
@JsonIgnoreProperties
@Getter
@Setter
@ToString
public class TransferModel {

	
	private String senderIban;
	
	private String receiverIban;
	
	private String amount;
	
	private String senderAccountType;
	
	private String receiverAccountType;

	public String getSenderIban() {
		return senderIban;
	}

	public void setSenderIban(String senderIban) {
		this.senderIban = senderIban;
	}

	public String getReceiverIban() {
		return receiverIban;
	}

	public void setReceiverIban(String receiverIban) {
		this.receiverIban = receiverIban;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSenderAccountType() {
		return senderAccountType;
	}

	public void setSenderAccountType(String senderAccountType) {
		this.senderAccountType = senderAccountType;
	}

	public String getReceiverAccountType() {
		return receiverAccountType;
	}

	public void setReceiverAccountType(String receiverAccountType) {
		this.receiverAccountType = receiverAccountType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransferModel [senderIban=");
		builder.append(senderIban);
		builder.append(", receiverIban=");
		builder.append(receiverIban);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", senderAccountType=");
		builder.append(senderAccountType);
		builder.append(", receiverAccountType=");
		builder.append(receiverAccountType);
		builder.append("]");
		return builder.toString();
	}
	
	

	
	
	
	
}
