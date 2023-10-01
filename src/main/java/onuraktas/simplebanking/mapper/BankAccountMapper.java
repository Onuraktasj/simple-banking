package onuraktas.simplebanking.mapper;

import onuraktas.simplebanking.dto.model.BankAccount;
import onuraktas.simplebanking.dto.request.CreateBankAccountRequest;
import onuraktas.simplebanking.dto.response.CreateBankAccountResponse;
import onuraktas.simplebanking.entity.BankAccountEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BankAccountMapper {

    public static BankAccount toDto(BankAccountEntity bankAccountEntity){
        if (Objects.isNull(bankAccountEntity))
            return null;
        return BankAccount.builder()
                .accountNumber(bankAccountEntity.getAccountNumber())
                .owner(bankAccountEntity.getOwner())
                .balance(bankAccountEntity.getBalance())
                .build();
    }

    public static BankAccountEntity toEntity(BankAccount bankAccount){
        if (Objects.isNull(bankAccount))
            return null;

        return BankAccountEntity.builder()
                .accountNumber(bankAccount.getAccountNumber())
                .owner(bankAccount.getOwner())
                .balance(bankAccount.getBalance())
                .build();
    }

    public static BankAccountEntity toEntity(CreateBankAccountRequest createBankAccountRequest){
        if (Objects.isNull(createBankAccountRequest))
            return null;

        return BankAccountEntity.builder()
                .accountNumber(createBankAccountRequest.getAccountNumber())
                .owner(createBankAccountRequest.getOwner())
                .balance(createBankAccountRequest.getBalance())
                .build();
    }

    public static List<BankAccountEntity> toEntityList(List<BankAccount> bankAccountList){
        if (bankAccountList.isEmpty())
            return Collections.emptyList();

        return bankAccountList.stream()
                .map(BankAccountMapper::toEntity)
                .toList();

    }

    public static CreateBankAccountResponse toCreateBankAccountResponse(BankAccountEntity bankAccountEntity){
        if (Objects.isNull(bankAccountEntity))
            return null;

        return CreateBankAccountResponse.builder()
                .approvalCode(bankAccountEntity.getId())
                .build();
    }
}
