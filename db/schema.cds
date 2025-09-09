namespace batchinputproject;

using {
    Currency,
    managed,
    cuid
} from '@sap/cds/common';

@cds.persistence.skip
entity Upload @odata.singleton {
    Products : LargeBinary @Core.MediaType: 'application/vnd.ms-excel';
}

entity Products : cuid, managed {
    ProductID : String        @title: '{i18n>OrderNumber}'  @mandatory; //> readable key
    name      : String;
    descr     : String;
    stock     : Integer;
    price     : Decimal(9, 2) @readonly;
    currency  : Currency;
}
