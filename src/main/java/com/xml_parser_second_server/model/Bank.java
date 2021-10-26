package com.xml_parser_second_server.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY)
    private List<DataFromTransfer.InnerDataList.payDoc> payDocs;

    private String bankPayBicPay;
    private String bankRcpBicPay;

    public Bank(String bankRcpBicPay, String bankPayBicPay) {
        this.bankRcpBicPay = bankRcpBicPay;
        this.bankPayBicPay = bankPayBicPay;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankPayBicPay='" + this.getBankPayBicPay() + '\'' +
                ", bankRcpBicPay='" + this.getBankRcpBicPay() + '\'' +
                '}';
    }
}
