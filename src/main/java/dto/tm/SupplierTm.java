package dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierTm {
    private String id;
    private String name;
    private String contact;
    private String address;
}
