package gateway.api.base.test;

import gateway.api.base.page.ApiBasePage;
import gateway.api.base.page.ApiRequestPage;

public class PageTests {

    public void test(){

        ApiRequestPage apiRequestPage = ApiRequestPage.builder().page(100).pageSize(100).orderBy("ss").filter("12").build();


    }
}
