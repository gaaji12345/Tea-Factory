package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paymentdto {
    String paymentId;
    String userId;
    String userName;
    String dId;
    String supId;
    String cDate;
    String dDate;
    Double orderAm;
   // Double total;
}
