package ru.a5x5retail.frontproductmanagement.db.unitsofwork;

public class BaseUnitOfWork {

    private IViewWork iViewWork;

    private boolean isVisualisationProcess;
    private boolean inProcess;

    public IViewWork getiViewWork() {
        return iViewWork;
    }

    public void setiViewWork(IViewWork iViewWork) {
        this.iViewWork = iViewWork;
    }

    private void showView() {
        if (iViewWork != null) {
            iViewWork.showView();
        }
    }

    private void disableView() {
        if (iViewWork != null) {
            iViewWork.disableView();
        }
    }

    public interface IViewWork{
        void showView();
        void disableView();
    }
}
