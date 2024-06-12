package practiceApiAuto.payload;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChartNames {
    public int pageNumber;
    public int pageSize;
    public String searchKey;
    int dashboardId=1;
    List <String> chartNames=new ArrayList<>();

    public List<String> chartNamesList(){
        return chartNames;
    }
}
