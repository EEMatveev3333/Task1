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
        account.setCurrencySaldoPair("RUB", 100);
        Account.AccountStateSavings snapshot= account.SaveState();
        System.out.println( snapshot.toString());
        account.setCurrencySaldoPair("USD", 200);
        System.out.println( account.toString());
        assertEquals("Ivanov Ivan Ivanovich", snapshot.getOwnerName());
        assertEquals(100, snapshot.getCurrencySaldo("RUB"));
        assertEquals(200, account.getCurrencySaldo("USD"));
    }

    @Test
    @DisplayName("Проверка неизменяемости сохранения")
    void testSaveImmutability()  {
        Account account = new Account("Petrov Petr Petrovich");
        account.setCurrencySaldoPair("EUR", 100);
        System.out.println( account.toString());
        Account.AccountStateSavings snapshot = account.SaveState();

//        Map<Currency, Integer> currencyMap = snapshot.getCurrencyMap();
//        // используем Assertions.assertThrows для проверки неизменяемости снимка
//        assertThrows(UnsupportedOperationException.class, () -> currencyMap.put("EUR", 200)  );

        // оригинал остался неизменным
        assertTrue(account.containsKeyCurr( "EUR"));
        assertEquals(100, account.getCurrencySaldo("EUR"));
    }

    @Test
    @DisplayName( "Востановление состояния счета из сохранения")
    void loadState() {
        Account account = new Account("Sidorov Sidor Sidorovich");
        account.setCurrencySaldoPair("EUR", 100);
        Account.AccountStateSavings snapshot = account.SaveState();  // снимок на 100 EUR
        System.out.println( snapshot.toString());
        account.setCurrencySaldoPair("EUR", 200);  //меняем на 200
        System.out.println( account.toString());
        //   account=account.restoreFromSnapshot(snapshot);
        account.LoadState(snapshot);
        System.out.println( "После востановления "+account.toString());
        // проверяем правильность востановления
        assertEquals("Sidorov Sidor Sidorovich", account.getOwnerName());
        assertTrue(account.containsKeyCurr("EUR")); // проверяем валюта EUR
        assertEquals(100, account.getCurrencySaldo("EUR"));  //Сумма 100

    }
}