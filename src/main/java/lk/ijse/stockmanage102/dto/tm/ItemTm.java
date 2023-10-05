package lk.ijse.stockmanage102.dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*@Getter
@Setter
@ToString*/

public class ItemTm {
    private String itemCode;
    private String description;
    private Double unitPrice;
    private Integer qtyOnHand;
}
