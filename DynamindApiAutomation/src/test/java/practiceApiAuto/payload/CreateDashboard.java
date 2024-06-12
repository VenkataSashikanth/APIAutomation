package practiceApiAuto.payload;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDashboard {
    String name;
    boolean isDefaultDashboard = false;
    List<DashboardCharts> charts=new ArrayList<>();
}
