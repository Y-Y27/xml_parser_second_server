package com.xml_parser_second_server.service;

import com.xml_parser_second_server.model.Bank;
import com.xml_parser_second_server.model.DataFromTransfer;
import com.xml_parser_second_server.model.Organization;
import com.xml_parser_second_server.repository.BankRepository;
import com.xml_parser_second_server.repository.OrganizationRepository;
import com.xml_parser_second_server.repository.PayDocRepository;
import com.xml_parser_second_server.repository.ReportDocRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InputDataHandler {

    private final BankRepository bankRepository;
    private final OrganizationRepository organizationRepository;
    private final PayDocRepository payDocRepository;
    private final ReportDocRepository reportDocRepository;

    public InputDataHandler(BankRepository bankRepository, OrganizationRepository organizationRepository, PayDocRepository payDocRepository, ReportDocRepository reportDocRepository) {
        this.bankRepository = bankRepository;
        this.organizationRepository = organizationRepository;
        this.payDocRepository = payDocRepository;
        this.reportDocRepository = reportDocRepository;
    }


    public void parseInputData(DataFromTransfer dataFromTransfer) {

        List<DataFromTransfer.InnerDataList> innerDataList = dataFromTransfer.getInnerDataList();

        for (DataFromTransfer.InnerDataList innerData : innerDataList) {

            DataFromTransfer.InnerDataList.payDoc payDoc = innerData.getPayDoc();
            DataFromTransfer.InnerDataList.ReportDoc reportDoc = innerData.getReportDoc();

            DataFromTransfer.InnerDataList.payDoc.bankRCP bankRCP = payDoc.getBankRCP();
            DataFromTransfer.InnerDataList.payDoc.bankPay bankPay = payDoc.getBankPay();

            String bankRCPBIC_pay = bankRCP.getBIC_PAY();
            String bankPayBIC_pay = bankPay.getBIC_PAY();

            Optional<Bank> bankByBIC = checkBICInRepo(bankRCPBIC_pay, bankPayBIC_pay);
            Bank bank = bankByBIC.orElseGet(() -> new Bank(bankRCPBIC_pay, bankPayBIC_pay));
            payDoc.setBank(bank);

            DataFromTransfer.InnerDataList.payDoc.infPay infPay = innerData.getPayDoc().getInfPay();
            DataFromTransfer.InnerDataList.payDoc.infRcp infRcp = innerData.getPayDoc().getInfRcp();

            DataFromTransfer.InnerDataList.payDoc doc = checkOrganization(infPay, infRcp, payDoc);

            reportDoc.setPayDoc(doc);
            reportDocRepository.save(reportDoc);
        }
    }

    public DataFromTransfer.InnerDataList.payDoc checkOrganization(DataFromTransfer.InnerDataList.payDoc.infPay infPay,
                                                                   DataFromTransfer.InnerDataList.payDoc.infRcp infRcp,
                                                                   DataFromTransfer.InnerDataList.payDoc payDoc) {

        String INN_PAY_Inf_PAY = infPay.getINN_PAY();
        String KPP_PAY_Inf_PAY = infPay.getKPP_PAY();
        String CName_PAY_Inf_PAY = infPay.getCName_PAY();

        payDoc = checkINNandKPPorCName_Inf_PAY(INN_PAY_Inf_PAY, KPP_PAY_Inf_PAY, CName_PAY_Inf_PAY, payDoc);

        String INN_PAY_Inf_RCP = infRcp.getINN_PAY();
        String KPP_PAY_Inf_RCP = infRcp.getKPP_PAY();
        String CName_PAY_Inf_RCP = infRcp.getCName_PAY();

        payDoc = checkINNandKPP_Inf_RCP(INN_PAY_Inf_RCP, KPP_PAY_Inf_RCP, CName_PAY_Inf_RCP, payDoc);

        if (payDoc.getOrganization() == null) {

            Organization newOrganization = new Organization();

            newOrganization.setInfPayInnPay(INN_PAY_Inf_PAY);
            newOrganization.setInfPayKppPay(KPP_PAY_Inf_PAY);
            newOrganization.setInfPayCnamePay(CName_PAY_Inf_PAY);
            newOrganization.setInfRcpInnPay(INN_PAY_Inf_RCP);
            newOrganization.setInfRcpKppPay(KPP_PAY_Inf_RCP);
            newOrganization.setInfRcpCnamePay(CName_PAY_Inf_RCP);

            payDoc.setOrganization(newOrganization);
        }
        return payDoc;
    }

    public DataFromTransfer.InnerDataList.payDoc checkINNandKPPorCName_Inf_PAY(String Inf_PAY_INN_PAY,
                                                                               String Inf_Pay_KPP_PAY,
                                                                               String Inf_Pay_CName_PAY,
                                                                               DataFromTransfer.InnerDataList.payDoc payDoc) {

        List<Organization> listByINNandKPP = checkINNandKPPInRepo(Inf_PAY_INN_PAY, Inf_Pay_KPP_PAY);

        if (!listByINNandKPP.isEmpty() && listByINNandKPP.size() == 1) {
            payDoc.setOrganization(listByINNandKPP.get(0));
            return payDoc;
        } else {
            List<Organization> listByCName = checkCName_PAYInRepo(Inf_Pay_CName_PAY);
            if (!listByCName.isEmpty() && listByCName.size() == 1) {
                payDoc.setOrganization(listByCName.get(0));
                return payDoc;
            }
        }
        return payDoc;
    }


    public DataFromTransfer.InnerDataList.payDoc checkINNandKPP_Inf_RCP(String Inf_RCP_INN_PAY,
                                                                        String Inf_RCP_KPP_PAY,
                                                                        String Inf_RCP_CName_PAY,
                                                                        DataFromTransfer.InnerDataList.payDoc payDoc) {

        List<Organization> listByINNandKPP = checkINNandKPP_Inf_RCP(Inf_RCP_INN_PAY, Inf_RCP_KPP_PAY);
        if (!listByINNandKPP.isEmpty() && listByINNandKPP.size() == 1) {
            payDoc.setOrganization(listByINNandKPP.get(0));
            return payDoc;
        } else {
            List<Organization> listByCName = checkCName_RCPInRepo(Inf_RCP_CName_PAY);
            if (!listByCName.isEmpty() && listByCName.size() == 1) {
                payDoc.setOrganization(listByCName.get(0));
                return payDoc;
            }
        }
        return payDoc;
    }

    public LinkedHashMap<String, List<String>> findAllOrganizations() {

        List<Organization> organizationList = organizationRepository.findAll();

        LinkedHashMap<String, List<String>> mapWithNameAndStats = new LinkedHashMap<>();
        Set<String> organizationsNameList = new HashSet<>();
        for (Organization organization : organizationList) {

            organizationsNameList.add(organization.getInfPayCnamePay());
            organizationsNameList.add(organization.getInfRcpCnamePay());
        }

        for (String orgName : organizationsNameList) {
            List<String> innerList = new LinkedList<>();

            Long checkStatPay = payDocRepository.checkStatPay(orgName);
            Long checkStatRcp = payDocRepository.checkStatRcp(orgName);

            innerList.add("Pay: " + checkStatPay);
            innerList.add("Rec: " + checkStatRcp);

            mapWithNameAndStats.put(orgName, innerList);
        }
        return mapWithNameAndStats;
    }

    public Optional<Bank> checkBICInRepo(String bankRCPBIC_pay, String bankPayBIC_pay) {
        return bankRepository.findByBankRcpBicPayAndBankPayBicPay(bankRCPBIC_pay, bankPayBIC_pay);
    }

    public List<Organization> checkINNandKPPInRepo(String Inf_PAY_INN_PAY, String Inf_Pay_KPP_PAY) {
        return organizationRepository.findDistinctByInfPayInnPayAndInfPayKppPay(Inf_PAY_INN_PAY, Inf_Pay_KPP_PAY);
    }

    public List<Organization> checkCName_PAYInRepo(String Inf_Pay_CName_Pay) {
        return organizationRepository.findDistinctByInfPayCnamePay(Inf_Pay_CName_Pay);
    }

    public List<Organization> checkINNandKPP_Inf_RCP(String Inf_RCP_INN_PAY, String Inf_RCP_KPP_PAY) {
        return organizationRepository.findDistinctByInfRcpInnPayAndInfRcpKppPay(Inf_RCP_INN_PAY, Inf_RCP_KPP_PAY);
    }

    public List<Organization> checkCName_RCPInRepo(String Inf_RCP_CName_PAY) {
        return organizationRepository.findDistinctByInfRcpCnamePay(Inf_RCP_CName_PAY);
    }

}
