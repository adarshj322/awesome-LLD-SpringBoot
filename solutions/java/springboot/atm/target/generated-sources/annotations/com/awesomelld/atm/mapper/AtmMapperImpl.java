package com.awesomelld.atm.mapper;

import com.awesomelld.atm.dto.SessionResponse;
import com.awesomelld.atm.dto.TransactionResponse;
import com.awesomelld.atm.entity.AccountEntity;
import com.awesomelld.atm.entity.AtmEntity;
import com.awesomelld.atm.entity.AtmSessionEntity;
import com.awesomelld.atm.entity.CardEntity;
import com.awesomelld.atm.entity.TransactionEntity;
import com.awesomelld.atm.enums.TransactionStatus;
import com.awesomelld.atm.enums.TransactionType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-02T18:35:41+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.7 (Azul Systems, Inc.)"
)
@Component
public class AtmMapperImpl implements AtmMapper {

    @Override
    public SessionResponse toSessionResponse(AtmSessionEntity session) {
        if ( session == null ) {
            return null;
        }

        UUID sessionId = null;
        String atmCode = null;
        String accountNumber = null;
        String accountHolder = null;
        BigDecimal balance = null;

        sessionId = session.getToken();
        atmCode = sessionAtmCode( session );
        accountNumber = sessionCardAccountNumber( session );
        accountHolder = sessionCardAccountHolderName( session );
        balance = sessionCardAccountBalance( session );

        SessionResponse sessionResponse = new SessionResponse( sessionId, atmCode, accountNumber, accountHolder, balance );

        return sessionResponse;
    }

    @Override
    public TransactionResponse toTransactionResponse(TransactionEntity entity, AccountEntity account) {
        if ( entity == null && account == null ) {
            return null;
        }

        Long transactionId = null;
        TransactionType type = null;
        TransactionStatus status = null;
        BigDecimal amount = null;
        Instant createdAt = null;
        if ( entity != null ) {
            transactionId = entity.getId();
            type = entity.getType();
            status = entity.getStatus();
            amount = entity.getAmount();
            createdAt = entity.getCreatedAt();
        }
        BigDecimal balanceAfter = null;
        if ( account != null ) {
            balanceAfter = account.getBalance();
        }

        TransactionResponse transactionResponse = new TransactionResponse( transactionId, type, status, amount, balanceAfter, createdAt );

        return transactionResponse;
    }

    private String sessionAtmCode(AtmSessionEntity atmSessionEntity) {
        if ( atmSessionEntity == null ) {
            return null;
        }
        AtmEntity atm = atmSessionEntity.getAtm();
        if ( atm == null ) {
            return null;
        }
        String code = atm.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String sessionCardAccountNumber(AtmSessionEntity atmSessionEntity) {
        if ( atmSessionEntity == null ) {
            return null;
        }
        CardEntity card = atmSessionEntity.getCard();
        if ( card == null ) {
            return null;
        }
        AccountEntity account = card.getAccount();
        if ( account == null ) {
            return null;
        }
        String number = account.getNumber();
        if ( number == null ) {
            return null;
        }
        return number;
    }

    private String sessionCardAccountHolderName(AtmSessionEntity atmSessionEntity) {
        if ( atmSessionEntity == null ) {
            return null;
        }
        CardEntity card = atmSessionEntity.getCard();
        if ( card == null ) {
            return null;
        }
        AccountEntity account = card.getAccount();
        if ( account == null ) {
            return null;
        }
        String holderName = account.getHolderName();
        if ( holderName == null ) {
            return null;
        }
        return holderName;
    }

    private BigDecimal sessionCardAccountBalance(AtmSessionEntity atmSessionEntity) {
        if ( atmSessionEntity == null ) {
            return null;
        }
        CardEntity card = atmSessionEntity.getCard();
        if ( card == null ) {
            return null;
        }
        AccountEntity account = card.getAccount();
        if ( account == null ) {
            return null;
        }
        BigDecimal balance = account.getBalance();
        if ( balance == null ) {
            return null;
        }
        return balance;
    }
}
