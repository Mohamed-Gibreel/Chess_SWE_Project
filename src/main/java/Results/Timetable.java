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
public class Timetable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long member_id;

        @Column(nullable = false)
        private String Day;

        private Integer Time;

        private String Availability;

    }
