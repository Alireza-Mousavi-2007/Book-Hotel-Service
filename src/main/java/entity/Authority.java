package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "authority_name", unique = true, nullable = false)
    @NotBlank(message = "Authority_name can't be blank")
    private String Authority;

    public Authority() {
    }

    public Authority(String authority) {
        Authority = authority;
    }

    public Authority(Integer id, String authority) {
        this.id = id;
        Authority = authority;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public @Nullable String getAuthority() {
        return null;
    }

    public void setAuthority(String authority) {
        Authority = authority;
    }
}
