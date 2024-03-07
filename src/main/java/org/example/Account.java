package org.example;
//import java.util.ArrayDeque;
import java.util.*;

public class Account {
    //1. Account имеет поле для имени владельца: String
    private String ownerName;
    //-------------------------------------------------------------------

    //2. Account имеет поле, в котором хранятся пары валюта-количество.
    // Количество валюты хранится в виде целочисленного значения.
    // Валюта должна быть представлена таким образом, чтобы указать можно было только значение из некоторого фиксированного списка значений
    // (конкретный перечень допустимо указать произвольно в коде).

    //private String currencySaldoTextList = "";//"[RUB]{0};[USD]{0};[EUR]{0}";
    private HashMap <Currency,Integer> currencySaldoMap = new HashMap<>();//"";//"[RUB]{0};[USD]{0};[EUR]{0}";
    //-------------------------------------------------------------------

    //3. Создание объекта Account возможно только с указанием имени владельца счета.
    public Account(String ownerName) throws NullPointerException  {
        //Также необходимо реализовать следующие ограничения:
        //        — Имя не может быть null или пустым
        //В случае нарушения ограничений необходимо бросать подходящее исключение.
        if (ownerName != null && !ownerName.trim().isEmpty()) {
            // Обработка строки
            this.ownerName = ownerName;
        }
        else
            throw new NullPointerException("ownerName cannot be null or empty");


    }
    //-------------------------------------------------------------------

    //4. Для имени необходимо сделать геттеры и сеттеры
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        if (!ownerName.equals(this.ownerName)) {
//            this.setDeque(); // предварительно сохраняем историю
            this.ownerName = ownerName;
        }
    }
    //-------------------------------------------------------------------

    //5. Для пар валюта-количество необходимо сделать только геттер.
    public HashMap<Currency, Integer> getCurrencySaldoMap() {
        return currencySaldoMap;
    }

//    public String getCurrencySaldoTextList() {
//        return currencySaldoTextList;
//    }
//    //-------------------------------------------------------------------
//    public boolean containsKeyCurr(String Currency){
//        if (this.currencySaldoTextList.contains("["+ Currency + "]"))
//            return true;
//        else
//            return false;
//    }

    public Integer getCurrencySaldo(Currency cur){

        if (currencySaldoMap.containsKey(cur)) {
//            String tmp_currencySaldoTextList = this.currencySaldoTextList.substring
//                    (this.currencySaldoTextList.indexOf("[" + Currency + "]")  + Currency.length() + 2
//                            , this.currencySaldoTextList.indexOf("[/" + Currency + "]")
//                    );
            return currencySaldoMap.get(cur);
        }
        else
            return 0;
    }

    //6. Необходим метод, который принимает Валюту и её количество и заменяет текущее количество данной Валюты на указанное. Если такой валюты ранее не было – она добавляется в список.
    public void setCurrencySaldoPair(Currency cur, Integer Saldo) throws IllegalArgumentException   {
        //        — Количество валюты не может быть отрицательным
        if (Saldo < 0)
            throw new IllegalArgumentException("Saldo cannot be negative");

        currencySaldoMap.put(cur,Saldo);
//        if (this.currencySaldoTextList.contains("["+ Currency + "]")) {
//
//            String tmp_currencySaldoTextList = this.currencySaldoTextList.replace(
//                                                                    this.currencySaldoTextList.substring
//                                                                            (this.currencySaldoTextList.indexOf("[" + Currency + "]")  //Currency.length() + 1
//                                                                                    , this.currencySaldoTextList.indexOf("[/" + Currency + "]")
//                                                                            )
//                                                                    //"["+ Currency + "]" + Saldo + "[/"+ Currency + "]"
//                                                                    , "[" + Currency + "]" + Saldo// + "[/"+ Currency + "]"
//                                                            );
//            if (!tmp_currencySaldoTextList.equals(this.currencySaldoTextList))
//            {
//                this.setDeque(); // предварительно сохраняем историю
//                this.currencySaldoTextList = tmp_currencySaldoTextList;
//            }
//        }
//        else{
//            this.setDeque(); // предварительно сохраняем историю
//            this.currencySaldoTextList = this.currencySaldoTextList + "["+ Currency + "]" + Saldo + "[/"+ Currency + "]";
//        }

    }

    //Необходимо реализовать и приложить модульные тесты, проверяющие выполнение обозначенных требований.
    @Override
    public String toString() {
        return "Account{" +
                "ownerName='" + ownerName + '\'' +
                ", currencySaldoMap='" + currencySaldoMap + '\'' +
                '}';
    }
/*

    //------------------------------------------------------------------------------------------------------------------------------------------------------
    //    Часть 2. Отмена
//    Необходимо реализовать в классе Account метод undo, который будет отменять одно последнее изменение объекта класса Account. Метод должен поддерживать следующие требования:
//
//            — Вызывать метод можно несколько раз подряд. Каждый вызов откатывает еще одно изменение. Изменениями считаются смена имени владельца и смена значений для валют. Например:
//
//    после создания объекта, мы сначала добавили сто рублей,
//    потом сменили имя на “Василий Иванов”
//    потом установили количество рублей на 300.
//    Первый вызов отмены установит число рублей на 100, второй вызов вернет начальное имя, третий вызов уберет рубли из списка вообще.
//— Откатывать изменения можно до тех пор, пока объект не вернется к состоянию, в котором он был на момент создания. Необходимо предоставить метод проверки возможности отмены.
//
//            — Попытка отменить изменения, если их не было — это ошибка.
//
//            — Реализация отмены должна быть выполнена таким образом, чтобы, когда в класс будут добавлены новые поля, их можно было учитывать в отмене, однако ранее реализованный код не требовал бы изменения. Например: к уже реализованному коду Account необходимо добавить тип: обычный или премиальный счет. Реализация отмены смены типа счета не должна требовать изменений в код метода undo или методов работы с именем и валютами.
//
//            — Метод undo не имеет параметров
//
//    Реализуйте модульные тесты для проверки работоспособности кода.
//
    private Deque<String> deque = new ArrayDeque<>();

    public boolean canUndo() {
        return !deque.isEmpty();
    }

    //public Deque<String> getDeque() {
    public void undo() throws NoSuchElementException{
        if (this.deque.isEmpty())
            throw new NoSuchElementException("Элемент не содержит элементов истории для отката");
        else
        {
            String propHistInst = this.deque.removeLast();
            //Account{ownerName='Petrov Petr Petrovich', currencySaldoTextList='[RUB]8888[/RUB]'}
            String propHistInst_ownerName =
                            propHistInst.substring
                                    (propHistInst.indexOf("Account{ownerName='") + 19 //Currency.length() + 1
                                            , propHistInst.indexOf("', currencySaldoTextList='")
                                    );
            String propHistInst_currencySaldoTextList =
                    propHistInst.substring
                            (propHistInst.indexOf("currencySaldoTextList='") + 23 //Currency.length() + 1
                                    , propHistInst.indexOf("'}")
                            );
//            System.out.println(propHistInst);
//            System.out.println(propHistInst_ownerName);
//            System.out.println(propHistInst_currencySaldoTextList);

            if (!this.ownerName.equals(propHistInst_ownerName))
                this.ownerName = propHistInst_ownerName;

            if (!this.currencySaldoTextList.equals(propHistInst_currencySaldoTextList))
                this.currencySaldoTextList = propHistInst_currencySaldoTextList;

        }
        //return deque;
    }

    public void setDeque() {
        this.deque.add(this.toString());
    }


    //private PriorityQueue<String> myPriorityQueue = new PriorityQueue<String>();

    //    Часть 3. Сохранение
//    Необходимо реализовать возможность получать Сохранения у объектов класса Account. Требования включают:
    public static final class AccountStateSavings {
        //private final @Getter String ownerName;
        //private final Map<Currency, Integer> currencyMap;
        private final String ownerName;
        private final String currencySaldoTextList;

        public AccountStateSavings(String ownerName, String currencySaldoTextList) {
            this.ownerName = ownerName;
            this.currencySaldoTextList = currencySaldoTextList;
        }

        public Integer getCurrencySaldo(String Currency){

            if (this.currencySaldoTextList.contains("["+ Currency + "]")) {
                String tmp_currencySaldoTextList = this.currencySaldoTextList.substring
                        (this.currencySaldoTextList.indexOf("[" + Currency + "]")  + Currency.length() + 2
                                , this.currencySaldoTextList.indexOf("[/" + Currency + "]")
                        );
                return Integer.parseInt(tmp_currencySaldoTextList);
            }
            else
                return 0;
        }

        public String getOwnerName() {
            return ownerName;
        }
    }

    public AccountStateSavings SaveState(){
        AccountStateSavings ret = new AccountStateSavings(this.ownerName,this.currencySaldoTextList);
        return ret;
    }
    public void LoadState(AccountStateSavings ASS){
        this.ownerName = ASS.ownerName;
        this.currencySaldoTextList = ASS.currencySaldoTextList;
        this.deque.clear();
    };


//
//            — Метод сохранения возвращает объект, который хранит состояние Account на момент запроса сохранения.
//
//— Объект сохранения не должен изменяться после его создания (другими словами – иммутабелен) и не нарушает ранее реализованные требования инкапсуляции.
//
//            — Изменения оригинального объекта Account также не оказывают влияния на Сохранение.
//
//            — Объектов Сохранений может быть сколько угодно для каждого из Account.
//
//            — Любое Сохранение может быть использовано для приведения соответствующего ему объекта Account в состояние соответствующее моменту создания сохранения.
//
//— Всю информацию для отмены действий можно не сохранять.
//
//    Реализуйте модульные тесты для проверки работоспособности кода.
//
//            Внимание! Задание проверяется преподавателем. Выполните его и укажите ваш ответ в форме ниже.
*/

}
