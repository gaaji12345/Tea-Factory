package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class OrderDetails {
    private String orderID;
    private String  TeaID;
    private Integer qty;
    private Double total;
    private String date;
}
