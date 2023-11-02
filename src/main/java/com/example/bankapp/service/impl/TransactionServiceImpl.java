package com.example.bankapp.service.impl;


import com.example.bankapp.dto.AccountActivityDTO;
import com.example.bankapp.dto.TransactionCreationRequestDto;
import com.example.bankapp.dto.TransactionDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Client;
import com.example.bankapp.entity.Transaction;
import com.example.bankapp.entity.enums.AccountStatus;
import com.example.bankapp.entity.enums.Status;
import com.example.bankapp.entity.enums.TransactionStatus;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.exception.transaction_processing_exception.*;
import com.example.bankapp.mapper.TransactionMapper;
import com.example.bankapp.repository.AccountRepository;
import com.example.bankapp.repository.ClientRepository;
import com.example.bankapp.repository.TransactionRepository;
import com.example.bankapp.service.util.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    private static final String EXCEPTION_MESSAGE_ACCOUNT= "Account not found.";

    @Override
    public TransactionDto getTransactionById(UUID id){
        return transactionMapper.transactionToTransactionDto(transactionRepository.findById(id)
                .orElseThrow(() -> new DatabaseAccessException("Transaction not found with ID: " + id)));
    }

    @Transactional
    @Override
    public TransactionDto createTransaction(TransactionCreationRequestDto transactionCreationRequestDto){

        Optional<Account> debitAccount = accountRepository.findById(UUID.fromString(transactionCreationRequestDto.getDebitAccountId()));
        Optional<Account> creditAccount = accountRepository.findById(UUID.fromString(transactionCreationRequestDto.getCreditAccountId()));

        if (debitAccount.isEmpty() || creditAccount.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_ACCOUNT);
        }

        boolean isDataForTransactionValid = checkIsDataForTransactionValid(debitAccount, creditAccount, transactionCreationRequestDto);

        if (isDataForTransactionValid) {
            Transaction transaction = transactionMapper.transactionRequestToTransaction(transactionCreationRequestDto);

            transaction.setTransactionId(UUID.randomUUID());
            transaction.setTransactionStatus(TransactionStatus.NEW);
            transaction.setDebitAccountId(debitAccount.get());
            transaction.setCreditAccountId(creditAccount.get());

            /* When bank account is debited, money is withdrawn from the account to make a payment,
            credited to bank account means money is added to the account:
             */
            BigDecimal amount = new BigDecimal(transactionCreationRequestDto.getAmount());
            withdrawFundsFromAccount(debitAccount.get(), amount);
            depositFundsToAccount(creditAccount.get(), amount);

            transaction.setAmount(amount);
            transaction.setDescription(transactionCreationRequestDto.getDescription());
            Timestamp currentTimestamp = new Timestamp(new Date().getTime());
            transaction.setCreatedAt(currentTimestamp);

            accountRepository.save(debitAccount.get());
            accountRepository.save(creditAccount.get());

            transaction.setTransactionStatus(TransactionStatus.APPROVED);
            transactionRepository.save(transaction);

            return transactionMapper.transactionToTransactionDto(transaction);
        } else {
            throw new DataForTransactionNotValidException("Data for transaction is not valid.");
        }
    }


    private boolean checkIsDataForTransactionValid(Optional<Account> debitAccount, Optional<Account> creditAccount, TransactionCreationRequestDto newTransaction) throws ClientNotActiveException, AccountNotActiveException, CurrencyMismatchException, InsufficientFundsException, DatabaseAccessException, SameAccountTransactionException {
        if (debitAccount.isPresent() && creditAccount.isPresent()) {

            checkIfUuidSame(debitAccount.get(), creditAccount.get());

            Optional<Client> clientDebitAccOwner = clientRepository.findById(debitAccount.get().getClientId().getClientId());
            Optional<Client> clientCreditAccOwner = clientRepository.findById(creditAccount.get().getClientId().getClientId());

            checkIfClientIsPresent(clientDebitAccOwner, clientCreditAccOwner);

            checkIfClientStatusActive(clientDebitAccOwner, clientCreditAccOwner);

            checkIfAccountIsActive(debitAccount, creditAccount);

            checkIfCurrencyMatch(debitAccount, creditAccount);

            BigDecimal amount = new BigDecimal(newTransaction.getAmount());

            checkIfEnoughMoneyOnAccount(debitAccount, amount);

        } else {
            return false;
        }
        return true;
    }

    private void checkIfEnoughMoneyOnAccount(Optional<Account> debitAccount, BigDecimal amount) {
        boolean isBalanceSufficient = checkBalanceForTransaction(debitAccount, amount);

        if (debitAccount.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_ACCOUNT);
        }

        if (!isBalanceSufficient) {
            throw new InsufficientFundsException("Insufficient funds on account with id: " + debitAccount.get().getAccountUUID());
        }
    }

    private void checkIfCurrencyMatch(Optional<Account> debitAccount, Optional<Account> creditAccount) {

        if (debitAccount.isEmpty() || creditAccount.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_ACCOUNT);
        }

        boolean isCurrencyMatch = checkCurrencyMatching(debitAccount.get(), creditAccount.get());
        if (!isCurrencyMatch) {
            throw new CurrencyMismatchException("Currency mismatch.");
        }
    }

    private void checkIfAccountIsActive(Optional<Account> debitAccount, Optional<Account> creditAccount) {
        if (debitAccount.isEmpty() || creditAccount.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_ACCOUNT);
        }

        boolean isDebitAccountActive = isAccountActive(debitAccount.get());
        boolean isCreditAccountActive = isAccountActive(creditAccount.get());
        if (!isDebitAccountActive || !isCreditAccountActive) {
            throw new AccountNotActiveException("Account not active.");
        }
    }

    private void checkIfClientStatusActive(Optional<Client> clientDebitAccOwner, Optional<Client> clientCreditAccOwner) {
        if (clientDebitAccOwner.isEmpty() || clientCreditAccOwner.isEmpty()) {
            throw new DatabaseAccessException("Client not found.");
        }

        boolean isClientDebitAccOwnerActive = isClientStatusActive(clientDebitAccOwner.get());
        boolean isClientCreditAccOwnerActive = isClientStatusActive(clientCreditAccOwner.get());
        if (!isClientDebitAccOwnerActive || !isClientCreditAccOwnerActive) {
            throw new ClientNotActiveException("Client status is not active.");
        }
    }

    private static void checkIfClientIsPresent(Optional<Client> clientDebitAccOwner, Optional<Client> clientCreditAccOwner) {
        if (clientDebitAccOwner.isEmpty() || clientCreditAccOwner.isEmpty()) {
            throw new DatabaseAccessException("Client not found.");
        }
    }

    private static void checkIfUuidSame(Account debitAccount, Account creditAccount) {
        if (debitAccount.getAccountUUID().equals(creditAccount.getAccountUUID())) {
            throw new SameAccountTransactionException("Same account transaction is not allowed.");
        }
    }

    private boolean isClientStatusActive(Client client) {
        return client.getClientStatus().equals(Status.ACTIVE);
    }

    private boolean isAccountActive(Account account) {
        return account.getAccountStatus().equals(AccountStatus.ACTIVE);
    }

    @Override
    public TransactionDto updateTransactionStatusToRejected(UUID uuid){
        Transaction transaction = transactionRepository.findById(uuid).orElseThrow(() -> new DatabaseAccessException("Transaction not found with ID: " + uuid));
        transaction.setTransactionStatus(TransactionStatus.REJECTED);
        transactionRepository.save(transaction);
        return transactionMapper.transactionToTransactionDto(transaction);
    }

    @Transactional
    @Override
    public Set<TransactionDto> getTransactionsForPeriodByClient(UUID uuid, AccountActivityDTO activityDTO){
        Client client = clientRepository.findById(uuid).orElseThrow(() -> new DatabaseAccessException("Client not found with ID: " + uuid));
        Set<Account> accountSet = client.getAccountSet();
        Set<Transaction> transactions = new HashSet<>();
        for (Account account : accountSet) {
            transactions.addAll(account.getCreditTransactionSet());
            transactions.addAll(account.getDebitTransactionSet());
        }
        Set<Transaction> transactionsFilteredByDate = filterSetByDate(transactions, activityDTO);
        if (transactionsFilteredByDate.isEmpty()) {
            throw new DatabaseAccessException("No transactions in period " + activityDTO.getYear() + "/" + activityDTO.getMonth() + " found for client with ID: " + uuid);
        }
        return transactionMapper.transactionsToTransactionDto(transactionsFilteredByDate);
    }

    private Set<Transaction> filterSetByDate(Set<Transaction> transactions, AccountActivityDTO activityDTO) {
        Set<Transaction> filtered = new HashSet<>();
        for (Transaction transaction : transactions) {
            Date date = new Date(transaction.getCreatedAt().getTime());
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String year = yearFormat.format(date);
            String month = monthFormat.format(date);
            if (year.equals(activityDTO.getYear()) && month.equals(activityDTO.getMonth())) {
                filtered.add(transaction);
            }
        }
        return filtered;
    }

    private boolean checkBalanceForTransaction(Optional<Account> debitAccount, BigDecimal amount) {
        return debitAccount.filter(account -> account.getAccountBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0).isPresent();
    }

    private void withdrawFundsFromAccount(Account account, BigDecimal amount) {
        account.setAccountBalance(account.getAccountBalance().subtract(amount));
    }

    private void depositFundsToAccount(Account account, BigDecimal amount) {
        account.setAccountBalance(account.getAccountBalance().add(amount));
    }

    private boolean checkCurrencyMatching(Account debitAccount, Account creditAccount) {
        return debitAccount.getCurrencyCode().equals(creditAccount.getCurrencyCode());
    }
}
