package ru.a5x5retail.frontproductmanagement.configuration;

import ru.a5x5retail.frontproductmanagement.db.models.DivisionInfo;

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
    public static final String CONTRACTOR_INFO_CONST = "CONTRACTOR_INFO_CONST";
    public static final String PLAN_INCOME_CONST = "PLAN_INCOME_CONST";


    public final class Messages {
        public static final String SQL_EXEPTION_MSG = "Ошибка запроса в базу данных! Программа не работает!";
    }

    /** typeOfDocument */
    private static TypeOfDocument typeOfDocument;
    public static final TypeOfDocument getCurrentTypeOfDocument() {
        return typeOfDocument;
    }

    public static final void setCurrentTypeOfDocument(TypeOfDocument typeOfDocument) {
        Constants.typeOfDocument = typeOfDocument;
    }

     /** divisionInfo*/
    private static DivisionInfo divisionInfo;
    public static DivisionInfo getDivisionInfo() {
        return divisionInfo;
    }

    public static void setDivisionInfo(DivisionInfo divisionInfo) {
        Constants.divisionInfo = divisionInfo;
    }
}
