package melonproject.melon.entity.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="member_info")
@Builder
@DynamicInsert
public class MemberInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mi_seq") private Long miSeq;
    @Column(name="mi_id") private String miId;
    @Column(name="mi_pwd") private String miPwd;
    @Column(name="mi_name") private String miName;
    @Column(name="mi_age") private Integer miAge;
    @Column(name="mi_phone") private String miPhone;
    @Column(name="mi_email") private String miEmail;
    @Column(name="mi_birth") private LocalDate miBirth;
    @Column(name="mi_reg_dt") private LocalDateTime miRegDt;
    @Column(name="mi_role") private String miRole;

    public boolean isEnabled() {
        return false;
    }
}