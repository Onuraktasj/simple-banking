package onuraktas.simplebanking.impl;

import onuraktas.simplebanking.constant.ErrorMessage;
import onuraktas.simplebanking.dto.model.BankAccount;
import onuraktas.simplebanking.dto.model.DepositTransaction;
import onuraktas.simplebanking.dto.model.Transaction;
import onuraktas.simplebanking.dto.model.WithdrawalTransaction;
import onuraktas.simplebanking.dto.request.CreateBankAccountRequest;
import onuraktas.simplebanking.dto.request.CreateTransactionRequest;
import onuraktas.simplebanking.dto.response.CreateBankAccountResponse;
import onuraktas.simplebanking.dto.response.CreateTransactionResponse;
import onuraktas.simplebanking.dto.response.GetBankAccountDetailResponse;
import onuraktas.simplebanking.entity.BankAccountEntity;
import onuraktas.simplebanking.entity.BankAccountTransactionEntity;
import onuraktas.simplebanking.entity.TransactionEntity;
import onuraktas.simplebanking.enums.Status;
import onuraktas.simplebanking.exception.BankAccountExistsException;
import onuraktas.simplebanking.exception.NoBankAccountFoundException;
import onuraktas.simplebanking.exception.NotEnoughMoneyException;
import onuraktas.simplebanking.mapper.BankAccountMapper;
import onuraktas.simplebanking.mapper.TransactionMapper;
import onuraktas.simplebanking.repository.BankAccountRepository;
import onuraktas.simplebanking.repository.BankAccountTransactionRepository;
import onuraktas.simplebanking.repository.TransactionRepository;
import onuraktas.simplebanking.service.BankAccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final BankAccountTransactionRepository bankAccountTransactionRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, BankAccountTransactionRepository bankAccountTransactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.bankAccountTransactionRepository = bankAccountTransactionRepository;
    }

    @Override
    public CreateBankAccountResponse createBankAccount(CreateBankAccountRequest createBankAccountRequest) {

        if (this.bankAccountRepository.existsByAccountNumber(createBankAccountRequest.getAccountNumber())) {
            throw new BankAccountExistsException(ErrorMessage.BANK_ACCOUNT_EXISTS);
        }

        BankAccountEntity bankAccountEntity = this.bankAccountRepository.save(BankAccountMapper.toEntity(createBankAccountRequest));
        CreateBankAccountResponse createBankAccountResponse = BankAccountMapper.toCreateBankAccountResponse(bankAccountEntity);
        createBankAccountResponse.setStatus(Status.OK.getStatus());
        return createBankAccountResponse;
    }


    @Override
    public CreateTransactionResponse depositMoney(String accountNumber, CreateTransactionRequest createTransactionRequest) {
        BankAccountEntity bankAccountEntity = this.bankAccountRepository.findByAccountNumber(accountNumber);

        bankAccountEntity.setBalance(bankAccountEntity.getBalance().add(createTransactionRequest.getAmount()));
        this.bankAccountRepository.save(bankAccountEntity);

        DepositTransaction depositTransaction = DepositTransaction.builder()
                .amount(createTransactionRequest.getAmount())
                .build();

        return this.executeTransaction(bankAccountEntity, TransactionMapper.toEntity(depositTransaction));
    }


    @Override
    public CreateTransactionResponse withdrawMoney(String accountNumber, CreateTransactionRequest createTransactionRequest) {
        BankAccountEntity bankAccountEntity = this.bankAccountRepository.findByAccountNumber(accountNumber);

        this.checkBankAccountBalance(createTransactionRequest, bankAccountEntity);

        bankAccountEntity.setBalance(bankAccountEntity.getBalance().subtract(createTransactionRequest.getAmount()));

        this.bankAccountRepository.save(bankAccountEntity);

        WithdrawalTransaction withdrawalTransaction = WithdrawalTransaction.builder()
                .amount(createTransactionRequest.getAmount())
                .build();

        return this.executeTransaction(bankAccountEntity, TransactionMapper.toEntity(withdrawalTransaction));
    }

    @Override
    public GetBankAccountDetailResponse getBankAccountDetails(String accountNumber) {
        BankAccount bankAccount = BankAccountMapper.toDto(this.bankAccountRepository.findByAccountNumber(accountNumber));

        if (Objects.isNull(bankAccount)) {
            throw new NoBankAccountFoundException(ErrorMessage.BANK_ACCOUNT_NOT_FOUND);
        }

        GetBankAccountDetailResponse response = GetBankAccountDetailResponse.builder()
                .accountNumber(bankAccount.getAccountNumber())
                .owner(bankAccount.getOwner())
                .balance(bankAccount.getBalance())
                .createdDate(bankAccount.getCreatedDate().toString())
                .build();

        List<UUID> transactionIdList = this.bankAccountTransactionRepository.findByBankAccountId(bankAccount.getId()).stream().map(BankAccountTransactionEntity::getTransactionId).toList();

        if (transactionIdList.isEmpty()) {
            return response;
        }

        List<Transaction> transactionList = TransactionMapper.toDtoList(this.transactionRepository.findAllById(transactionIdList));

        response.setTransactions(transactionList);

        return response;
    }

    private CreateTransactionResponse executeTransaction(BankAccountEntity bankAccountEntity, TransactionEntity entity) {

        this.transactionRepository.save(entity);

        BankAccountTransactionEntity bankAccountTransactionEntity = BankAccountTransactionEntity.builder()
                .bankAccountId(bankAccountEntity.getId())
                .transactionId(entity.getId())
                .build();

        this.bankAccountTransactionRepository.save(bankAccountTransactionEntity);

        return CreateTransactionResponse.builder()
                .approvalCode(entity.getId())
                .status(Status.OK.getStatus())
                .build();
    }

    private void checkBankAccountBalance(CreateTransactionRequest createTransactionRequest, BankAccountEntity bankAccountEntity) {
        if (bankAccountEntity.getBalance().compareTo(createTransactionRequest.getAmount()) < 0) {
            throw new NotEnoughMoneyException(ErrorMessage.NOT_ENOUGH_MONEY);
        }
    }
}
