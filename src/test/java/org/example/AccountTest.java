package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.example.Account;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    @DisplayName("Откат имени счета ")
    void testUndoNameChange() {
        Account account = new Account("Ivanov Ivan Ivanovich");
        System.out.println(account.toString());
        account.setOwnerName("Petrov Petr Petrovich");
        System.out.println(account.toString());
        account.setOwnerName("Sidorov Sidor Sidorovich");
        System.out.println(account.toString());

        System.out.println(account.canUndo());
        assertTrue(account.canUndo());     //признак счет есть
        account.undo();
        System.out.println("после первого отката " +account.toString());  // Должно быть Petrov Petr Petrovich
        account.undo();
        System.out.println("после второго отката  ="+ account.toString());  // должно быть Ivanov Ivan Ivanovich
        assertEquals("Ivanov Ivan Ivanovich", account.getOwnerName());
        assertTrue(account.canUndo());
    }

    @Test
    @DisplayName("Откат валюты счета ")
    void testUndoCurrencyChange() {
        Account account = new Account("Ivanov Ivan Ivanovich");
        System.out.println("После создания счета " +account.toString());
        account.setCurrencySaldoPair( "RUB", 200);
        System.out.println("счет до отката  " +account.toString());  // должны быть рубли
        account.setCurrencySaldoPair( "USD", 100);
        System.out.println("счет до отката  " +account.toString());  //должны быть доллары и рубли
        account.setCurrencySaldoPair( "USD", 1000);  // меняем 100 доларов на 1000
        System.out.println("счет после того как 100 доларов заменили на 1000 " +account.toString());  //  должны остаться только рубли
        assertTrue(account.canUndo());
        account.undo();   // откатываем 1000 доллары
        System.out.println("счет после отката 1000 долларов  должно остатся 100 доларов " +account.toString());  //  должны остаться только рубли
        account.undo();   // откатываем 100 доллары
        System.out.println("счет после отката долларов должны остатся только рубли  " +account.toString());  //  должны остаться только рубли
        assertFalse(account.containsKeyCurr("USD"));    //проверяем что долларов нет
        account.undo();     //  откат рублей
        System.out.println("счет после отката рублей " +account.toString());  // должен быть голый счет т.е без валюты
        assertFalse(account.containsKeyCurr("RUB"));    //откат  долларов выполнен
        assertTrue(account.canUndo());  //остался голый счет

    }

    @Test
    @DisplayName("Откат валюты счета  и имени счета  ")
    void testMultipleUndo() {
        Account account = new Account("Ivanov Ivan Ivanovich");
        account.setOwnerName("Petrov Petr Petrovich");
        account.setCurrencySaldoPair("RUB", 100);
        assertTrue(account.canUndo());
        account.undo(); // откатываем валюту
        System.out.println("счет после отката валюты  " +account.toString());  // должен быть голый счет
        assertTrue(account.canUndo());
        account.undo(); // Undo name change
        System.out.println("счет после отката имени должно быть начальное имя  " +account.toString());  // должен быть голый счет
        assertTrue(account.canUndo());
    }

    @Test
    @DisplayName("Полный откат до сотояния  exception нет действий для отката ")
    void testCannotUndoInitially() {
        Account account = new Account("Ivanov Ivan Ivanovich");
        System.out.println("счет  " +account.toString());
        assertTrue(account.canUndo());
        account.undo();
        System.out.println("счет  после отката " +account.toString());
        assertThrows(IllegalStateException.class, account::undo);      // откатили полностью счет
    }
}