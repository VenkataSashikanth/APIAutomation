package practiceApiAuto.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;


public class DataProviders {
    @DataProvider(name="countryData")
    public Object[][] getAllData() throws IOException {
//        String path="/home/thrymr/IdeaProjects/DynamindApiAutomation/testdata/CountryData.xlsx";
        String path = System.getProperty("user.dir")+"/testdata/CountryData.xlsx";
        XLUtility xlUtility = new XLUtility(path);
        int rownum = xlUtility.getRowCount("Sheet1");
        int colcount = xlUtility.getCellCount("Sheet1", 1);
        String apidata[][] = new String[rownum][colcount];

        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colcount; j++) {
                apidata[i - 1][j] = xlUtility.getCellData("Sheet1",i, j);
            }
        }
        return apidata;
    }
}
