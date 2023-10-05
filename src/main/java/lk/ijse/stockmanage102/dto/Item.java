package lk.ijse.stockmanage102.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private String itemCode;
    private String description;
    private Double unitPrice;
    private Integer qtyOnHand;

}
