/**
 * 
 */
package com.dkb.bankaccounttoy.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Praneeth
 *
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7696882162300468739L;


@Temporal(TemporalType.TIMESTAMP)
@LastModifiedDate
private Date lastTransactionTime;


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



}

