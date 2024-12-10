package com.nimisitech.wallet.integration.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * ,
 *     {
 *       "createdOn": "2023-04-30T21:03:23",
 *       "lastModifiedOn": "2023-04-30T21:03:23",
 *       "walletKey": "51a670a008774db9bff0db3936bb26c2",
 *       "amount": 50000,
 *       "reference": "23043021032327131003",
 *       "externalReference": "2304302103230158",
 *       "transactionType": "DEBIT",
 *       "valueTime": "2023-04-30T21:03:23",
 *       "narration": "Fund Wallet",
 *       "data": {
 *         "source": "Fincra Virtual Account",
 *         "accountNumber": "0123456789",
 *         "bank": "Wema Bank"
 *       },
 *       "status": "COMPLETED",
 *       "balanceBefore": 0,
 *       "balanceAfter": -50000
 *     }
 *   ],
 *   "responseCode": "00"
 * }
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionApiResponse {
    private String createdOn;
    private String lastModifiedOn;
    private String walletKey;
    private Long amount;
    private String reference;
    private String externalReference;
    private String transactionType;
    private String valueTime;
    private String narration;
    private Map<String,String> data;
    private String status;
    private Long balanceBefore;
    private Long balanceAfter;
}
