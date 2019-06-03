package ru.a5x5retail.frontproductmanagement.assortmentcard;

public class AssortmentCardModel {

    private static AssortmentCardModel model;

    public static AssortmentCardModel getModel() {
        if (model == null) {
            model = new AssortmentCardModel();
        }
        return model;
    }

    public static void clearModel(){
        model = null;
    }
}
