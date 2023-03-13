package melonproject.melon.entity.info;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ticket_info")
public class TicketInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ti_seq") private Long tiSeq;
    @Column(name="ti_price") private Integer tiPrice;
    @Column(name="ti_down") private Integer tiDown;
    @Column(name="ti_streaming") private Integer tiStreaming;
    @Column(name="ti_term") private Integer tiTerm;
    @Column(name="ti_name") private String name;
    @Column(name="ti_ex") private String tiEx;

}