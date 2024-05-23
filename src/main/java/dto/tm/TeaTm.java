package dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaTm {
    private String id;
    private String name;
    private String details;
    private Double price;
}
