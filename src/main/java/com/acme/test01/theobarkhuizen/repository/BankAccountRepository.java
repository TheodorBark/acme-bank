package com.acme.test01.theobarkhuizen.repository;

import com.acme.test01.theobarkhuizen.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountId(Long accountId);
}