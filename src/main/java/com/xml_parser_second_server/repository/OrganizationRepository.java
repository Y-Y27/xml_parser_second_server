package com.xml_parser_second_server.repository;

import com.xml_parser_second_server.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    List<Organization> findDistinctByInfPayInnPayAndInfPayKppPay(String InfPayInnPay, String InfPayKppPay);

    List<Organization> findDistinctByInfPayCnamePay(String InfPayCnamePay);

    List<Organization> findDistinctByInfRcpInnPayAndInfRcpKppPay(String InfRcpInnPay, String InfRcpKppPay);

    List<Organization> findDistinctByInfRcpCnamePay(String InfRcpCnamePay);

    List<Organization> findByInfPayCnamePayOrInfRcpCnamePay(String organizationForInfPay, String organizationForInfRcp);

}
