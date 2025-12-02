package com.awesomelld.atm.mapper;

import com.awesomelld.atm.dto.SessionResponse;
import com.awesomelld.atm.dto.TransactionResponse;
import com.awesomelld.atm.entity.AccountEntity;
import com.awesomelld.atm.entity.AtmEntity;
import com.awesomelld.atm.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AtmMapper {

    @Mapping(target = "sessionId", source = "token")
    @Mapping(target = "atmCode", source = "atm.code")
    @Mapping(target = "accountNumber", source = "card.account.number")
    @Mapping(target = "accountHolder", source = "card.account.holderName")
    @Mapping(target = "balance", source = "card.account.balance")
    SessionResponse toSessionResponse(com.awesomelld.atm.entity.AtmSessionEntity session);

    @Mapping(target = "transactionId", source = "entity.id")
    @Mapping(target = "type", source = "entity.type")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "amount", source = "entity.amount")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "balanceAfter", source = "account.balance")
    TransactionResponse toTransactionResponse(TransactionEntity entity, AccountEntity account);

    default TransactionResponse toTransactionResponse(TransactionEntity entity) {
        return toTransactionResponse(entity, entity.getAccount());
    }
}
