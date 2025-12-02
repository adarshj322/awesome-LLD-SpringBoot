package com.awesomelld.atm.service;

import com.awesomelld.atm.dto.BalanceResponse;
import com.awesomelld.atm.dto.CashRequest;
import com.awesomelld.atm.dto.SessionResponse;
import com.awesomelld.atm.dto.TransactionResponse;
import com.awesomelld.atm.entity.AccountEntity;
import com.awesomelld.atm.entity.AtmEntity;
import com.awesomelld.atm.entity.AtmSessionEntity;
import com.awesomelld.atm.entity.CardEntity;
import com.awesomelld.atm.entity.TransactionEntity;
import com.awesomelld.atm.enums.AccountStatus;
import com.awesomelld.atm.enums.AtmStatus;
import com.awesomelld.atm.enums.SessionState;
import com.awesomelld.atm.enums.TransactionStatus;
import com.awesomelld.atm.enums.TransactionType;
import com.awesomelld.atm.exception.InsufficientFundsException;
import com.awesomelld.atm.exception.InvalidSessionException;
import com.awesomelld.atm.exception.ResourceNotFoundException;
import com.awesomelld.atm.mapper.AtmMapper;
import com.awesomelld.atm.repository.AccountRepository;
import com.awesomelld.atm.repository.AtmRepository;
import com.awesomelld.atm.repository.AtmSessionRepository;
import com.awesomelld.atm.repository.CardRepository;
import com.awesomelld.atm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AtmService {

    private final AtmRepository atmRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final AtmSessionRepository sessionRepository;
    private final TransactionRepository transactionRepository;
    private final AtmMapper mapper;

    @Transactional
    public SessionResponse startSession(String atmCode, String cardNumber, String pin) {
        AtmEntity atm = getActiveAtm(atmCode);
        CardEntity card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        if (!card.isActive()) {
            throw new InvalidSessionException("Card is inactive");
        }
        if (!card.getPinHash().equals(pin)) {
            throw new InvalidSessionException("Invalid PIN");
        }

        AccountEntity account = card.getAccount();
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidSessionException("Account is not active");
        }

        AtmSessionEntity session = new AtmSessionEntity();
        session.setAtm(atm);
        session.setCard(card);
        session.setState(SessionState.ACTIVE);

        AtmSessionEntity saved = sessionRepository.save(session);
        return mapper.toSessionResponse(saved);
    }

    public BalanceResponse balance(UUID sessionId) {
        AtmSessionEntity session = getActiveSession(sessionId);
        return new BalanceResponse(session.getCard().getAccount().getBalance());
    }

    @Transactional
    public TransactionResponse withdraw(UUID sessionId, BigDecimal amount) {
        AtmSessionEntity session = getActiveSession(sessionId);
        AccountEntity account = session.getCard().getAccount();
        AtmEntity atm = session.getAtm();
        ensureSufficient(account, amount, atm);

        account.setBalance(account.getBalance().subtract(amount));
        atm.setCashAvailable(atm.getCashAvailable().subtract(amount));

        TransactionEntity tx = new TransactionEntity(TransactionType.WITHDRAWAL, TransactionStatus.SUCCESS, amount,
                account, atm, session, "Dispensed cash");
        transactionRepository.save(tx);
        accountRepository.save(account);
        atmRepository.save(atm);
        return mapper.toTransactionResponse(tx, account);
    }

    @Transactional
    public TransactionResponse deposit(UUID sessionId, BigDecimal amount) {
        AtmSessionEntity session = getActiveSession(sessionId);
        AccountEntity account = session.getCard().getAccount();
        AtmEntity atm = session.getAtm();

        account.setBalance(account.getBalance().add(amount));
        atm.setCashAvailable(atm.getCashAvailable().add(amount));

        TransactionEntity tx = new TransactionEntity(TransactionType.DEPOSIT, TransactionStatus.SUCCESS, amount,
                account, atm, session, "Cash deposit");
        transactionRepository.save(tx);
        accountRepository.save(account);
        atmRepository.save(atm);
        return mapper.toTransactionResponse(tx, account);
    }

    @Transactional
    public void endSession(UUID sessionId) {
        AtmSessionEntity session = getActiveSession(sessionId);
        session.setState(SessionState.ENDED);
        session.setEndedAt(Instant.now());
        sessionRepository.save(session);
    }

    private void ensureSufficient(AccountEntity account, BigDecimal amount, AtmEntity atm) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidSessionException("Amount must be positive");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient account balance");
        }
        if (atm.getCashAvailable().compareTo(amount) < 0) {
            throw new InsufficientFundsException("ATM does not have enough cash");
        }
    }

    private AtmEntity getActiveAtm(String code) {
        AtmEntity atm = atmRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("ATM %s not found".formatted(code)));
        if (atm.getStatus() != AtmStatus.ACTIVE) {
            throw new InvalidSessionException("ATM %s is not active".formatted(code));
        }
        return atm;
    }

    private AtmSessionEntity getActiveSession(UUID sessionId) {
        AtmSessionEntity session = sessionRepository.findByToken(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        if (session.getState() != SessionState.ACTIVE) {
            throw new InvalidSessionException("Session is not active");
        }
        return session;
    }
}
