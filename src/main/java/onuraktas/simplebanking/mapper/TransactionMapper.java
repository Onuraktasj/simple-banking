package onuraktas.simplebanking.mapper;

import onuraktas.simplebanking.dto.model.DepositTransaction;
import onuraktas.simplebanking.dto.model.Transaction;
import onuraktas.simplebanking.dto.model.WithdrawalTransaction;
import onuraktas.simplebanking.entity.TransactionEntity;
import onuraktas.simplebanking.enums.TransactionType;

import java.util.List;

public class TransactionMapper {


    public static Transaction toDto(TransactionEntity transactionEntity){
        return Transaction.builder()
                .approvalCode(transactionEntity.getId())
                .date(transactionEntity.getDate())
                .amount(transactionEntity.getAmount())
                .type(transactionEntity.getType())
                .build();
    }
    public static TransactionEntity toEntity(DepositTransaction depositTransaction){
        return TransactionEntity.builder()
                .id(depositTransaction.getApprovalCode())
                .date(depositTransaction.getDate())
                .amount(depositTransaction.getAmount())
                .type(TransactionType.DEPOSIT.getType())
                .build();
    }

    public static TransactionEntity toEntity(WithdrawalTransaction withdrawalTransaction){
        return TransactionEntity.builder()
                .id(withdrawalTransaction.getApprovalCode())
                .date(withdrawalTransaction.getDate())
                .amount(withdrawalTransaction.getAmount())
                .type(withdrawalTransaction.getType())
                .build();
    }

    public static List<Transaction> toDtoList(List<TransactionEntity> transactionEntityList){
        return transactionEntityList.stream()
                .map(TransactionMapper::toDto)
                .toList();
    }
}
