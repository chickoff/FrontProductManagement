package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;

import com.arellomobile.mvp.InjectViewState;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.BInfo;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncPresenterBridge;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.models.EnterInUoW;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.models.IEnterInUoWEventsListener;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.models.In;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListManufacturerDate;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsyncGroup;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncMarkListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncPositionListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetSKUContextQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.AddCheckingListPositionQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.EditCheckingListPositionDateQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.EditCheckingListPositionQtyQuery;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;


@InjectViewState
public class CheckingListIncPresenter extends BasePresenter<ICheckingListIncView> {

    public static final String TAG = "CheckingListIncPresenter";

    public List<CheckingListManufacturerDate> checkingListManufacturerDateList;




    /*************************************************************************************************************************************************/

    public List<CheckingListPosition> checkingListPositionList;
    public List<CheckingListPosition> getCheckingListPositionList() {
        return checkingListPositionList;
    }

    public void setCheckingListPositionList(List<CheckingListPosition> checkingListPositionList) {
        this.checkingListPositionList = checkingListPositionList;
        getViewState().updateUi();
    }

    /*************************************************************************************************************************************************/


public List<CheckingListMark> checkingListMarkList;
public List<CheckingListMark> getCheckingListMarkList() {
    return checkingListMarkList;
}

    public void setCheckingListMarkList(List<CheckingListMark> checkingListMarkList) {
        this.checkingListMarkList = checkingListMarkList;
    }

    /*************************************************************************************************************************************************/

    public CheckingListHead selectedCheckingListHead;
    public CheckingListHead getSelectedCheckingListHead() {
        return selectedCheckingListHead;
    }
    public void setSelectedCheckingListHead(CheckingListHead selectedCheckingListHead) {
        this.selectedCheckingListHead = selectedCheckingListHead;
        getViewState().updateUi();
    }

    /*************************************************************************************************************************************************/

    private GetCheckingListIncPositionListQuery getCheckingListIncPositionListQuery(){
        return new GetCheckingListIncPositionListQuery(selectedCheckingListHead.Guid);
    }

    /*************************************************************************************************************************************************/

    private GetCheckingListIncMarkListQuery getCheckingListIncMarkListQuery(){
        return new GetCheckingListIncMarkListQuery(selectedCheckingListHead.Guid);
    }

    /*************************************************************************************************************************************************/

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        enterInUoW = new EnterInUoW();
        enterInUoW.setmListener(listener);
        setSelectedCheckingListHead(ProjectMap.getCurrentCheckingListHead());
        CheckingListIncPresenterBridge.checkingListIncPresenterWeakReference = new WeakReference<>(this);
        loadAll();
    }

    /*************************************************************************************************************************************************/

    private void loadAll(){

        final CallableQAsyncGroup queryGroup = new CallableQAsyncGroup();
        final GetCheckingListIncPositionListQuery q1 = getCheckingListIncPositionListQuery();
        q1.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                setCheckingListPositionList(q1.getList());
            }
        });

        final GetCheckingListIncMarkListQuery q2  = getCheckingListIncMarkListQuery();
        q2.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                checkingListMarkList = q2.getList();
            }
        });

        /*final GetCheckingListIncMarkListQuery q3 = getCheckingListIncMarkListQuery();
        q3.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                setCheckingListMarkList(q3.getList());
            }
        });*/

        queryGroup.addQuery(q1);

        queryGroup.addQuery(q2);

        //queryGroup.addQuery(q3);

        queryGroup.addOnPreExecuteListener(
                new DataBaseQuery.OnPreExecuteListener() {
                    @Override
                    public void onPreExecute() {
                        showAwaitDialog(true);
                    }
                });

        queryGroup.addOnPostExecuteListener(
                new DataBaseQuery.OnPostExecuteListener() {
                    @Override
                    public void onPostExecute() {
                        showAwaitDialog(false);
                    }
                });

        queryGroup.executeAsyncAll();
    }

    /*************************************************************************************************************************************************/

    private void loadPositionLists ()  {

        if (selectedCheckingListHead == null) return;

        final GetCheckingListIncPositionListQuery query = getCheckingListIncPositionListQuery();

        query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                showAwaitDialog(true);
            }
        });

        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                setCheckingListPositionList(query.getList());
                showAwaitDialog(false);
            }
        });

        query.ExecuteAsync();
    }

    /*************************************************************************************************************************************************/

    private void loadMarks()  {
        if (selectedCheckingListHead == null) return;
        final GetCheckingListIncMarkListQuery query = getCheckingListIncMarkListQuery();
        query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                showAwaitDialog(true);
            }
        });


        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                checkingListMarkList = query.getList();
                showAwaitDialog(false);
            }
        });

        query.ExecuteAsync();
    }

    public List<CheckingListPosition> getPositionListForDateOfManufacturer(){
        List<CheckingListPosition> tmp = new ArrayList<>();
        if (checkingListPositionList == null) return null;
        for (CheckingListPosition checkingListPosition : checkingListPositionList) {
            if (checkingListPosition.manufacturingDateFl == 1) {
                tmp.add(checkingListPosition);
            }
        }
        return tmp;
    }

    /*************************************************************************************************************************************************/

    public void addQty(final CheckingListPosition position, final BigDecimal qty, final int operationType) {
        final EditCheckingListPositionQtyQuery query =
                new EditCheckingListPositionQtyQuery(position.checkingListIncHeadGuid, position.guid, qty,operationType);

        query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                showAwaitDialog(true);
            }
        });

        query.addOnPostExecuteListener(

                new CallableQAsync.OnPostExecuteListener() {
                    @Override
                    public void onPostExecute() {
                        if (operationType == 1) {
                            position.qtyUser = position.qtyUser.add(qty).setScale(3);
                        } else {
                            position.qtyUser = qty;
                        }

                        if (!position.qtyUser.setScale(3).equals(query.getNewQty().setScale(3)) ){
                            loadPositionLists();
                        } else {
                            getViewState().updateUi();
                        }

                        showAwaitDialog(false);
                    }
                } );

        query.ExecuteAsync();
    }

    /*************************************************************************************************************************************************/

    public void addDate(CheckingListPosition position, Date date) {
        EditCheckingListPositionDateQuery query =
                new EditCheckingListPositionDateQuery(position.checkingListIncHeadGuid, position.guid,date);

        query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                showAwaitDialog(true);
            }
        });

        query.addOnPostExecuteListener(
                new CallableQAsync.OnPostExecuteListener() {
                    @Override
                    public void onPostExecute() {
                        showAwaitDialog(false);
                        loadPositionLists();
                    }
                }
       );

        query.ExecuteAsync();

    }

    /*************************************************************************************************************************************************/

    public void addNewSku(int sku) {
        if (selectedCheckingListHead == null) {
            return;
        }
        AddCheckingListPositionQuery query = new AddCheckingListPositionQuery(selectedCheckingListHead.Guid, sku);

        query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                showAwaitDialog(true);
            }
        });

        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                showAwaitDialog(false);
                loadPositionLists();
            }
        });
        query.ExecuteAsync();
    }

    /*************************************************************************************************************************************************/

    public void getSkuContextByBarcode(String barcode) {
        final GetSKUContextQuery query = new GetSKUContextQuery(barcode);
        query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                showAwaitDialog(true);
            }
        });
        query.addOnPostExecuteListener(
                new CallableQAsync.OnPostExecuteListener() {
                    @Override
                    public void onPostExecute() {
                        showAwaitDialog(false);
                        if (query.getList() != null && query.getList().size() > 0) {
                            enterInUoW.setSkuContext(query.getList().get(0));
                        } else {
                            enterInUoW.setSkuContext(null);
                        }
                    }
                } );
        query.ExecuteAsync();

    }

   /*************************************************************************************************************************************************/

    EnterInUoW enterInUoW;

    IEnterInUoWEventsListener listener = new IEnterInUoWEventsListener() {
        @Override
        public void getSkuContext(BInfo barcodeInfo) {
            getSkuContextByBarcode(barcodeInfo.barcode);
        }

        @Override
        public void findLocalPosition(BInfo barcodeInfo) {
            List<CheckingListPosition> pl = new ArrayList<>();
            for (CheckingListPosition checkingListPosition : checkingListPositionList) {
                if (checkingListPosition.code == barcodeInfo.getSkuContext().Code) {
                    pl.add(checkingListPosition);
                }
            }
            enterInUoW.setPositionListForChange(pl);
        }

        @Override
        public void getNewQty(CheckingListPosition position) {
           getViewState().openEditableDialog(position);
        }

        @Override
        public void selectOnePosition(List<CheckingListPosition> checkingListPositionList) {
            getViewState().openSelectiblePositionDialog(checkingListPositionList);
        }

        @Override
        public void saveNewQty(In input) {
            if (input.getInType() == In.InputType.ByClick) {
                addQty(input.getSelectedPosition(), input.getNewQty(), 0);
            } else {
                addQty(input.getSelectedPosition(), input.getNewQty(), 1);
            }
            enterInUoW.clear();

            //recyclerView.scrollToPosition(adapter.getPositionBySource(input.getSelectedPosition()));
        }

        @Override
        public void raiseError(String message) {
            ProdManApp.Alerts.MakeDoubleVibrate();
            getViewState().showEventToast(message,0);
        }

        @Override
        public void raiseClear() {

        }
    };


}
