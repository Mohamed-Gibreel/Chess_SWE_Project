package results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Main {

    @Id
    private Long member_id;

    private String Name;

    private String profession;

    @Column(nullable = false)
    private String Day;

    private Integer Time;

    private String Availability;
}
