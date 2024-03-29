package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EnableJpaRepositories
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}

		@Bean
		public CommandLineRunner initData(ClientRepository clientRepository , AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository, PasswordEncoder passwordEncoder) {
			return (args) -> {

				System.out.println("<==================================>");
				System.out.println("MindHub Bank has just started!!!");
				System.out.println("<==================================>");

				Client cliente1 = new Client("Melba","Morel", "melba@mindhub.com", passwordEncoder.encode("123456"), Role.CLIENT);
				clientRepository.save(cliente1);
				Client cliente2 = new Client( "Federico","Rouyere", "federicorouyere@gmail.com", passwordEncoder.encode("123456"), Role.CLIENT); clientRepository.save(cliente2);
				Client clientAdmin = new Client("admin", "admin", "admin@mindhub.com", passwordEncoder.encode("123456"), Role.ADMIN); clientRepository.save(clientAdmin);
				Client cliente4 = new Client("Federica", "Risso Patron", "federicarp@gmail.com", passwordEncoder.encode("123456"), Role.CLIENT); clientRepository.save(cliente4);

				Account account1 = new Account("VIN001", LocalDateTime.now() , 270725.2 , cliente1); accountRepository.save(account1);
				Account account2 = new Account("VIN002" , LocalDateTime.now().plusDays(1) , 7500.0 , cliente1); accountRepository.save(account2);
				Account account3 = new Account("VIN003" , LocalDateTime.now().plusMonths(1) , 15950.0 , cliente1); accountRepository.save(account3);
				Account account4 = new Account("VIN004" , LocalDateTime.now().plusMonths(5) , 9243.75 , cliente2); accountRepository.save(account4);
				Account accountTest = new Account("TEST-VIN024" , LocalDateTime.now().plusMonths(5) , 1000 , clientAdmin); accountRepository.save(accountTest);
				Account accountTest1 = new Account("TEST-VIN034" , LocalDateTime.now().plusMonths(5) , 1000 , clientAdmin); accountRepository.save(accountTest1);
				Account accountTest2 = new Account("TEST-VIN044" , LocalDateTime.now().plusMonths(5) , 1000 , clientAdmin); accountRepository.save(accountTest2);

				Transaction transaction1 = new Transaction(-456.65, "tornillos", TransactionType.DEBITO, LocalDateTime.now(), account1); transactionRepository.save(transaction1);
				Transaction transaction2 = new Transaction(253451.65, "maderas", TransactionType.CREDITO, LocalDateTime.now().plusMonths(1), account1); transactionRepository.save(transaction2);
				Transaction transaction3 = new Transaction(-20.50, "chicles", TransactionType.DEBITO, LocalDateTime.now(), account1); transactionRepository.save(transaction3);
				Transaction transaction4 = new Transaction(17850.70, "zapatillas", TransactionType.CREDITO, LocalDateTime.now().plusMonths(5), account1); transactionRepository.save(transaction4);
				Transaction transaction5 = new Transaction(-674.00, "cuadernos", TransactionType.DEBITO, LocalDateTime.now().plusMonths(2), account2); transactionRepository.save(transaction5);
				Transaction transaction6 = new Transaction(-135.20, "pan", TransactionType.DEBITO, LocalDateTime.now().plusMonths(3), account2); transactionRepository.save(transaction6);
				Transaction transaction7 = new Transaction(950.00, "impresiones", TransactionType.CREDITO, LocalDateTime.now().plusMonths(1), account3); transactionRepository.save(transaction7);
				Transaction transaction8 = new Transaction(15000, "remera", TransactionType.CREDITO, LocalDateTime.now().plusMonths(2), account3); transactionRepository.save(transaction8);

				List<Integer> payments = new ArrayList<>();
				payments.add(12);

				Loan loan1 = new Loan("Mortgage", 500000, payments); loanRepository.save(loan1);
				Loan loan2 = new Loan("Personal", 100000, List.of(6,12,24)); loanRepository.save(loan2);
				Loan loan3 = new Loan("Automotive", 300000, List.of(6,12,24,36)); loanRepository.save(loan3);

				ClientLoan clientLoan1 = new ClientLoan(400000 , 60, cliente1, loan1); clientLoanRepository.save(clientLoan1);
				ClientLoan clientLoan2 = new ClientLoan(50000,12,cliente1,loan2); clientLoanRepository.save(clientLoan2);
				ClientLoan clientLoan3 = new ClientLoan(100000, 24, cliente2, loan2); clientLoanRepository.save(clientLoan3);
				ClientLoan clientLoan4 = new ClientLoan(20000, 36, cliente2, loan3); clientLoanRepository.save(clientLoan4);

				Card card1 = new Card(CardType.DEBIT, CardColor.GOLD, "1234-4678-9876-5432", LocalDate.now(), LocalDate.now().plusYears(5),117, cliente1);
				cardRepository.save(card1);
				Card card2 = new Card(CardType.CREDIT, CardColor.TITANIUM, "3445-4454-7588-9234", LocalDate.now(), LocalDate.now().plusYears(5),753, cliente1);
				cardRepository.save(card2);
				Card expCard = new Card(CardType.CREDIT, CardColor.SILVER,  "4767-6324-1491-2718", LocalDate.now(), LocalDate.of(2020, 05, 22),394, cliente1);
				cardRepository.save(expCard);
				Card card3 = new Card(CardType.CREDIT, CardColor.SILVER,  "4547-6534-4391-2795", LocalDate.now(), LocalDate.now().plusYears(5),394, cliente2);
				cardRepository.save(card3);


				Card card4 = new Card(CardType.DEBIT, CardColor.GOLD, "5631-4678-9876-6571", LocalDate.now(), LocalDate.now().plusYears(5),132, cliente4); cardRepository.save(card4);
				Card card5 = new Card(CardType.DEBIT, CardColor.TITANIUM, "5109-4454-7588-9234", LocalDate.now(), LocalDate.now().plusYears(5),564, cliente4); cardRepository.save(card5);
				Card card6 = new Card(CardType.DEBIT, CardColor.SILVER,  "2190-3513-4391-2795", LocalDate.now(), LocalDate.now().plusYears(5),902, cliente4); cardRepository.save(card6);
				Card card7 = new Card(CardType.CREDIT, CardColor.GOLD, "6059-4678-9876-5432", LocalDate.now(), LocalDate.now().plusYears(5),230, cliente4); cardRepository.save(card7);
				Card card8 = new Card(CardType.CREDIT, CardColor.TITANIUM, "7358-4454-7588-9234", LocalDate.now(), LocalDate.now().plusYears(5),465, cliente4); cardRepository.save(card8);
				Card card9 = new Card(CardType.CREDIT, CardColor.SILVER,  "3620-9250-6510-2795", LocalDate.now(), LocalDate.now().plusYears(5),354, cliente4); cardRepository.save(card9);



			};
		}

	}





