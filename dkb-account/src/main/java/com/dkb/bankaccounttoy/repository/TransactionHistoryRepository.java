/**
 * 
 */
package com.dkb.bankaccounttoy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dkb.bankaccounttoy.model.TransactionHistory;

/**
 * @author praneeth
 *
 */
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, String>{

	@Query(value = "SELECT * FROM transaction_history ch WHERE ch.iban = ?1",
			  nativeQuery = true)
	public List<TransactionHistory> getTransactionsbyIban(String iban) ;
	
}
