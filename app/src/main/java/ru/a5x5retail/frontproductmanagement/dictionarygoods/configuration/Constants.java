package ru.a5x5retail.frontproductmanagement.dictionarygoods.configuration;

public final class Constants {


    /*  Types of module enum  */
    public enum TypeOfDocument {
        INVENTORY (1),
        INCOME (2);


        private int index;
        TypeOfDocument(int index){
            this.index = index;
        }
        public int getIndex() {
            return index;
        }
    }







    public enum ViewModelStateEnum {NEW, LOADED}
}
