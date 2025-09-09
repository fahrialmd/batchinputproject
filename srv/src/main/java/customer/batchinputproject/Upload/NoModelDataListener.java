package customer.batchinputproject.Upload;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.adminservice.Products;

public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private final PersistenceService db;
    private static final int BATCH_COUNT = 5;
    private List<Map<Integer, String>> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // Constructor
    public NoModelDataListener(PersistenceService db) {
        this.db = db;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    private void saveData() {
        for (int i = 0; i < cachedDataList.size(); i++) {
            Map<Integer, String> map = cachedDataList.get(i);
            Products product = Products.create();

            for (Integer key : map.keySet()) {
                switch (key) {
                    case 0:
                        product.setProductID(map.get(key));
                        break;
                    case 1:
                        product.setName(map.get(key));
                        break;
                    case 2:
                        product.setDescr(map.get(key));
                        break;
                    case 3:
                        product.setStock(Integer.valueOf(map.get(key)));
                        break;
                    case 4:
                        product.setPrice(new BigDecimal(map.get(key)));
                        break;
                }
            }
            //     //  Products updatedProducts = adminService.run(Update.entity(PRODUCTS).data(product)).single(Products.class);
            //     db.run(Upsert.into(PRODUCTS).entry(product));
        }
    }
}
