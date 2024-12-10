package com.nimisitech.wallet.service.controllers;

import com.nimisitech.wallet.lib.exceptions.ApiException;
import com.nimisitech.wallet.lib.exceptions.ApiResponseCode;
import com.nimisitech.wallet.lib.exceptions.TransferRequestNotFoundException;
import com.nimisitech.wallet.lib.models.WalletTransfer;
import com.nimisitech.wallet.lib.service.WalletTransferService;
import com.nimisitech.wallet.service.apimodels.WalletTransferRequest;
import com.nimisitech.wallet.service.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/transfer")
@RestController
public class WalletTransferController {

    @Autowired
    private WalletTransferService transferService;

    @Value("${client.id:xyz}")
    private String clientId;

    @PostMapping
    public WalletTransfer performTransfer(@Validated @RequestBody WalletTransferRequest request,
                                          @RequestHeader("client-id")String clientId) throws ApiException {
        validateClientId(clientId);
        return transferService.performFullTransfer(request.getServiceRequest(clientId));
    }

    @PostMapping("/reverse/{ref}")
    public WalletTransfer reverseTransfer(@PathVariable("ref")String transferRef,
             @RequestHeader("client-id")String clientId)throws ApiException{
        validateClientId(clientId);
        try{
            return transferService.performReversal(transferRef);
        }
        catch (TransferRequestNotFoundException e){
            return transferService.performReversal(transferRef, clientId);
        }
    }

    @GetMapping("/{ref}")
    public WalletTransfer getTransfer(@PathVariable("ref")String transferRef,
                                      @RequestHeader("client-id")String clientId)throws ApiException{
        validateClientId(clientId);
        try{
            return transferService.getTransfer(transferRef);
        }
        catch (TransferRequestNotFoundException e){
            return transferService.getTransfer(transferRef, clientId);
        }
    }

    private void validateClientId(String clientId)throws ClientException{
        if(!this.clientId.equals(clientId)){
            throw new ClientException("Invalid client id",ApiResponseCode.INVALID_CLIENT);
        }
    }
}
