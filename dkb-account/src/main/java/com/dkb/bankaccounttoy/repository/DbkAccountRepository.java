/**
 * 
 */
package com.dkb.bankaccounttoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dkb.bankaccounttoy.model.DkbAccount;

/**
 * @author praneeth
 *
 */
public interface DbkAccountRepository extends JpaRepository<DkbAccount, String>{

	@Query(value = "SELECT * FROM dbk_Account ch WHERE ch.accounttype = ?1",
			  nativeQuery = true)
	public DkbAccount getRecordbyAccount(String accounttype) ;
	
	@Query(value = "SELECT balance FROM dbk_Account ch WHERE ch.iban = ?1", nativeQuery=true)
	public long getBalancebyAccount(String iban) ;
}
