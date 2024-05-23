package dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTm {
    private String orderID;
    private String deliverID;
    private String userID;
    private String userName;
    private String orderDate;
    private String TeaID;
    private String TeaType;
    private Double TeaPrice;
    private Integer qty;
    private Double total;
    private Button removeBtn;
}
