package ru.a5x5retail.frontproductmanagement.configuration;

public final class Constants {


    /*  Types of module enum  */
    public enum TypeOfDocument {
        PARTIAL_INVENTORY (1),
        FULL_INVENTORY (2),
        OUTER_INCOME (3),
        INNER_INCOME (4),
        DISCARD (5),
        PRINT_PRICE (6),
        SETTINGS (0);


        private int index;

        TypeOfDocument(int index){
            this.index = index;
        }
        public int getIndex() {
            return index;
        }

        public static TypeOfDocument getByOrd(int index){
            for (TypeOfDocument value : TypeOfDocument.values()) {
                if (value.getIndex() == index)
                    return value;
            }
            return null;
        }
    }







    public enum ViewModelStateEnum {NEW, LOADED}

    public enum DIALOGRESULTENUM {TRUE, FALSE ,ERROR}

    public static final String PWD_SI = "3860";
    public static final String TYPEOFDOCUMENT_CONST = "TYPEOFDOCUMENT_CONST";
    public static final String PACKINGLISTHEAD_CONST = "PACKINGLISTHEAD_CONST";


    public final class Messages {
        public static final String SQL_EXEPTION_MSG = "Ошибка запроса в базу данных! Программа не работает!";
    }
}
