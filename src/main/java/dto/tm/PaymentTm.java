package dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTm {
    String paymentId;
    String userId;
    String userName;
    String dId;
    String supId;
    String cDate;
    String dDate;
    Double orderAm;
    //Double total;
}
