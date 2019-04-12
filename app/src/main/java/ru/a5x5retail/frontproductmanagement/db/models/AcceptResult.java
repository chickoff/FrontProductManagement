package ru.a5x5retail.frontproductmanagement.db.models;

import java.util.ArrayList;
import java.util.List;

public class AcceptResult {
    public int returnCode;
    public int isValidated;
    public int isAccepted;
    public String eventMessage;
    public List<AcceptValidateMessage> acceptValidateMessageList;

    public AcceptResult() {
        acceptValidateMessageList = new ArrayList<>();
    }
}
