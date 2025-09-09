package customer.batchinputproject.handlers;

import java.io.InputStream;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.alibaba.excel.EasyExcel;
import com.sap.cds.services.cds.CdsUpdateEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.adminservice.Upload;
import cds.gen.adminservice.Upload_;
import customer.batchinputproject.Upload.NoModelDataListener;

@Component
@ServiceName("AdminService")
public class AdminServiceHandler implements EventHandler {

    private final PersistenceService db;

    AdminServiceHandler(PersistenceService db) {
        this.db = db;
    }

    @On(entity = Upload_.CDS_NAME, event = CqnService.EVENT_READ)
    public Upload getUploadSingleton() {
        return Upload.create();
    }

    @On
    public void handle_excel(CdsUpdateEventContext context, Upload upload) {
        InputStream is = upload.getProducts();
        if (is != null) {
            // Process Excel file using EasyExcel
            EasyExcel.read(is, new NoModelDataListener(db)).sheet().doRead();
        }
        context.setResult(Arrays.asList(upload));
    }
}
