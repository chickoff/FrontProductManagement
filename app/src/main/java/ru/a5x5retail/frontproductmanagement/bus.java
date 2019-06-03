package ru.a5x5retail.frontproductmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;

public class bus {

    static Map<String,List<BasePresenter>> presentersGroupMap;
    public static Map<String,List<BasePresenter>> getPresentersGroupMap() {
        if (presentersGroupMap == null) {
            presentersGroupMap = new HashMap<>();
        }
        return presentersGroupMap;
    }

    public static void addListener(BasePresenter presenter){

        List<BasePresenter> list = getPresentersGroupMap().get(presenter.getGroupTag());
        if (list == null) {
            list = new ArrayList<>();
            getPresentersGroupMap().put(presenter.getGroupTag(),list);
        }

        if (!list.contains(presenter)) {
            list.add(presenter);
        }
    }

    public static void removeListener(BasePresenter presenter){

        List<BasePresenter> list = getPresentersGroupMap().get(presenter.getGroupTag());
        if (list == null) return;

        if (!list.contains(presenter)) {
            list.remove(presenter);
        }
    }

    public static void sendBusMessage(BasePresenter presenter, BusMessage msg) {

        List<BasePresenter> list = getPresentersGroupMap().get(presenter.getGroupTag());
        if (list == null ) return;
        for (BasePresenter o : list) {
            if (o instanceof IPresenterReceiveMessage)
            ((IPresenterReceiveMessage)o).receiveBusMessage(msg);

        }
    }
    public static class BusMessage {
        public String from;
        public String To;
        public Object data;
    }

    public interface IPresenterReceiveMessage{
        void receiveBusMessage(bus.BusMessage msg);
    }
}
