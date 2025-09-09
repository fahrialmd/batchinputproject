using batchinputproject from '../db/schema';

service AdminService {

    entity Products as projection on batchinputproject.Products;

    entity Upload   as projection on batchinputproject.Upload;

}
