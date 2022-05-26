package template.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.springframework.data.annotation.Id;


@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode
public class CarmaModel {

    public static final String TIMESTAMP="timestamp";

//    @Id
//    @SerializedName(value = "id")
//    private long id;

    @SerializedName(value = "numberOfTreesUK")
    private double plantedTreesUK;
    @SerializedName(value = "numberOfTreesOffShore")
    private double plantedTreesOffShore;

    @SerializedName(value = "goldStandardCo2Purchased")
    private double goldStandardCo2Purchased;

    @SerializedName(value = "carbonOffsetCo2")
    private double carbonOffsetCo2;
    @SerializedName(value = "token")
    private String token;

    @SerializedName(value = "workdaysCreated")
    private double workdaysCreated;


    @EqualsAndHashCode.Exclude
    @SerializedName(value = TIMESTAMP)
    private long timeUpdated = System.currentTimeMillis();
}
