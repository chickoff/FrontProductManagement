package ru.a5x5retail.frontproductmanagement.inventories.presenters;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.BInfo;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckListInventory;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListGoodsQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetSKUContextQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.CheckingListGoodsAddQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.EditCheckingListGoodsQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.InventoryItemsDeleteQuery;
import ru.a5x5retail.frontproductmanagement.db_local.loSkuContext;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.ActualAssortmentFilterFragment;
import ru.a5x5retail.frontproductmanagement.inventories.model.IInventoryUoWListener;
import ru.a5x5retail.frontproductmanagement.inventories.model.InputTatem;
import ru.a5x5retail.frontproductmanagement.inventories.model.InventoriesModel;
import ru.a5x5retail.frontproductmanagement.inventories.model.InventoryUoW;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventoryScanGoodsView;



@InjectViewState
public class InventoryScanGoodsPresenter extends BasePresenter<IInventoryScanGoodsView> {

    InventoriesModel model = InventoriesModel.getModel();
    public InventoryUoW inventoryUoW;


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        load();
        inventoryUoW = new InventoryUoW();
        inventoryUoW.setListener(uoWListener);
    }

    public void refresh() {
        load();
        getViewState().updateUi();
    }

    private void load() {
        final GetCheckingListGoodsQuery q = new GetCheckingListGoodsQuery(model.getSelectedInventorySheet().checkingListHeadGuid);
        registerAwaitEvents(q);
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                model.setGoodsList(q.getItemList());
                getViewState().updateUi();
            }
        });
        q.ExecuteAsync();
    }

    public List<CheckingListGoods> getCheckingListGoods() {
        return model.getGoodsList();
    }

    public CheckListInventory getCheckListInventory() {
        return model.getSelectedInventorySheet();
    }



    private List<SKUContext> bInfo;
    public List<SKUContext> getSkuContextByBarcode(String barcode) {
        final GetSKUContextQuery q = new GetSKUContextQuery(barcode);
        registerAwaitEvents(q);
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                if (q.getList() != null && q.getList().size() > 0) {
                    bInfo = q.getList();
                } else {
                    bInfo = null;
                }
            }
        });

        return bInfo;

        /*query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
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
        query.ExecuteAsync();*/

    }

    public void setBarcode(String barcode) {
        inventoryUoW.setBarcode(barcode);
    }

    IInventoryUoWListener uoWListener = new IInventoryUoWListener() {
        @Override
        public void getBarcodeInfo(final String barcode) {
            final GetSKUContextQuery q = new GetSKUContextQuery(barcode);
            registerAwaitEvents(q);
            q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
                @Override
                public void onSuccessful() {
                    if (q.getList() != null && q.getList().size() > 0) {
                        BInfo b = new BInfo(barcode);
                        b.setSkuContext(q.getList().get(0));
                        inventoryUoW.setBarcodeInfo(b);
                    } else {
                        inventoryUoW.setBarcodeInfo(null);
                    }
                }
            });
            q.ExecuteAsync();
        }

        @Override
        public void findInList(InputTatem tatem) {
            CheckingListGoods find = null;
            for (CheckingListGoods checkingListGoods : model.getGoodsList()) {
                if (checkingListGoods.Code.equals(tatem.getSku())) {
                    find = checkingListGoods;
                }
            }

            inventoryUoW.setLocalGoods(find);
        }

        @Override
        public void getQtyDialog(InputTatem tatem) {
            getViewState().openEditableDialog(tatem);
        }

        @Override
        public void saveQtyValue(final InputTatem tatem) {
            String headGuid = model.getSelectedInventorySheet().checkingListHeadGuid;
            EditCheckingListGoodsQuery q = new EditCheckingListGoodsQuery(headGuid,tatem.getSku(),tatem.getNewQty(),tatem.getOperationType() );
            registerAwaitEvents(q);
            q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
                @Override
                public void onSuccessful() {
                    inventoryUoW.clear();
                    load();
                    getViewState().updateUi();
                }
            });
            q.ExecuteAsync();
        }

        @Override
        public void raiseError(String message) {
            getViewState().closeEditableDialog();
            ProdManApp.Alerts.MakeDoubleVibrate();
            ProdManApp.Alerts.MakeToast(message, 0);
        }

        @Override
        public void raiseClear() {
            inventoryUoW.clear();
        }
    };

    public void selectAllGoods() {
        for (CheckingListGoods checkingListGoods : model.getGoodsList()) {
            checkingListGoods.Check = true;
        }
        getViewState().updateUi();
    }

    public void addCodes(ArrayList<CodeInfo> codeInfoList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<root>");
        for (CodeInfo codeInfo : codeInfoList) {
            sb.append("<code>").append(codeInfo.code).append("</code>");
        }
        sb.append("</root>");
        CheckingListGoodsAddQuery q = new CheckingListGoodsAddQuery(model.getSelectedInventorySheet().checkingListHeadGuid,sb.toString());
        registerAwaitEvents(q);
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                load();
                getViewState().updateUi();
            }
        });
        q.ExecuteAsync();

    }

    public void deleteSelectedCodes() {


        ArrayList<Integer> codes4del = new ArrayList<>();
        for (CheckingListGoods checkingListGoods : model.getGoodsList()) {
            if (checkingListGoods.Check) {
                codes4del.add(checkingListGoods.Code);
            }
        }

        InventoryItemsDeleteQuery q = new InventoryItemsDeleteQuery(model.getSelectedInventorySheet().checkingListHeadGuid,getXmlByCodeArray(codes4del));
        registerAwaitEvents(q);
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                load();
                getViewState().updateUi();
            }
        });
        q.ExecuteAsync();
    }

    private String getXmlByCodeArray(ArrayList<Integer> codeInfoList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<root>");
        for (Integer code : codeInfoList) {
            sb.append("<code>").append(code).append("</code>");
        }
        sb.append("</root>");
        return sb.toString();
    }

    public void callActualAssortmentFilter() {
        getViewState().callActualAssortmentFilter(new ActualAssortmentFilterFragment.IActualAssortmentFilterResultListener() {
            @Override
            public void assortmentFilterResult(loSkuContext sku) {
                inventoryUoW.setSkuContext(sku);
            }
        });
    }
}
