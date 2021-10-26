package com.xml_parser_second_server.repository;

import com.xml_parser_second_server.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByBankRcpBicPayAndBankPayBicPay(String bankRCPBIC_pay, String bankPayBIC_pay);

}
