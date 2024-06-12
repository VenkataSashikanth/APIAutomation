package practiceApiAuto.payload;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DashboardCharts {
    int id;
    int sequence;
    String chartName;
    String qhDashboardId;
    boolean isChartActive;
    String chartLabel;
    String chartType;

    public DashboardCharts(int id, int sequence, String chartName, String chartLabel, String chartType, boolean isChartActive){
        this.id=id;
        this.sequence=sequence;
        this.chartName=chartName;
        this.chartLabel=chartLabel;
        this.chartType=chartType;
        this.isChartActive=isChartActive;
    }

    public DashboardCharts(int sequence, boolean isChartActive){
        this.sequence=sequence;
        this.isChartActive=isChartActive;
    }
}
