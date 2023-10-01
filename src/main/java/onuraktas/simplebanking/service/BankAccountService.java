package onuraktas.simplebanking.service;

import onuraktas.simplebanking.dto.request.CreateBankAccountRequest;
import onuraktas.simplebanking.dto.request.CreateTransactionRequest;
import onuraktas.simplebanking.dto.response.CreateBankAccountResponse;
import onuraktas.simplebanking.dto.response.CreateTransactionResponse;
import onuraktas.simplebanking.dto.response.GetBankAccountDetailResponse;

public interface BankAccountService {

    CreateBankAccountResponse createBankAccount(CreateBankAccountRequest createBankAccountRequest);

    CreateTransactionResponse depositMoney(String accountNumber, CreateTransactionRequest createTransactionRequest);

    CreateTransactionResponse withdrawMoney(String accountNumber, CreateTransactionRequest createTransactionRequest);

    GetBankAccountDetailResponse getBankAccountDetails(String accountNumber);
}
