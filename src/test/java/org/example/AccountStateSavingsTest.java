package org.example;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.example.Account;
import static org.junit.jupiter.api.Assertions.*;

class AccountStateSavingsTest {

    @Test
    @DisplayName("Сохранение состояния счета")
    void saveState() {
        Account account = new Account("Ivanov Ivan Ivanovich");
        account.setCurrencySaldoPair(Currency.RUB, 100);
        Account.AccountStateSavings snapshot= account.SaveState();
        System.out.println( snapshot.toString());
        account.setCurrencySaldoPair(Currency.USD, 200);
        System.out.println( account.toString());
        assertEquals("Ivanov Ivan Ivanovich", snapshot.getOwnerName());
        assertEquals(100, snapshot.getCurrencySaldo(Currency.RUB));
        assertEquals(200, account.getCurrencySaldo(Currency.USD));

        assertEquals("Petrov Petr Petrovich", snapshot.getOwnerName());
        assertEquals(1000, snapshot.getCurrencySaldo(Currency.RUB));
        assertEquals(2000, account.getCurrencySaldo(Currency.USD));
    }

    @Test
    @DisplayName("Проверка неизменяемости оригинала после сохранения")
    void testSaveImmutability()  {
        Account account = new Account("Petrov Petr Petrovich");
        account.setCurrencySaldoPair(Currency.EUR, 100);
        System.out.println( account.toString());
        Account.AccountStateSavings snapshot = account.SaveState();

//        Map<Currency, Integer> currencyMap = snapshot.getCurrencyMap();
//        // используем Assertions.assertThrows для проверки неизменяемости снимка
//        assertThrows(UnsupportedOperationException.class, () -> currencyMap.put("EUR", 200)  );

        // оригинал остался неизменным
        assertTrue(account.containsKeyCurr(Currency.EUR));
        assertEquals(100, account.getCurrencySaldo(Currency.EUR));
        assertEquals("Petrov Petr Petrovich", account.getOwnerName());
        assertEquals("Ivanov Ivan Ivanovich", account.getOwnerName());
        assertEquals(1000, account.getCurrencySaldo(Currency.EUR));
    }

    @Test
    @DisplayName( "Востановление состояния счета из сохранения")
    void loadState() {
        Account account = new Account("Sidorov Sidor Sidorovich");
        account.setCurrencySaldoPair(Currency.EUR, 100);
        Account.AccountStateSavings snapshot = account.SaveState();  // снимок на 100 EUR
        System.out.println( snapshot.toString());
        account.setCurrencySaldoPair(Currency.EUR, 200);  //меняем на 200
        System.out.println( account.toString());
        account.setCurrencySaldoPair(Currency.USD, 300);  //меняем на 200
        System.out.println( account.toString());
        System.out.println( snapshot.toString());
        //   account=account.restoreFromSnapshot(snapshot);
        account.LoadState(snapshot);
        System.out.println( "После востановления "+account.toString());
        // проверяем правильность востановления
        assertEquals("Sidorov Sidor Sidorovich", account.getOwnerName());
        assertTrue(account.containsKeyCurr(Currency.EUR)); // проверяем валюта EUR
        assertEquals(100, account.getCurrencySaldo(Currency.EUR));  //Сумма 100

    }
}