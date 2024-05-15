package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Devliverydto {
    private String userId;
    private String d_Id;
    private String orderDate;
    private String supId;
    private String c_date;
    private String d_date;
}
