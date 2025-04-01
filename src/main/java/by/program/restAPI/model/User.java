package by.program.restAPI.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    @NotNull
    private String email;

    @Column(name = "firstname", nullable = false)
    @NotNull
    @Size(min = 1, max = 128)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    @NotNull
    @Size(min = 1, max = 128)
    private String lastName;

    @Column(name = "password", nullable = false)
    @NotNull
    @Size(min = 5)
    private String password;

    @Column(name = "about_me")
    @Size(max = 2000)
    private String aboutMe;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "is_active", nullable = false, columnDefinition = "bool default true")
    private boolean isActive;

    @CreatedDate
    @Column(name = "created", updatable = false)
    private LocalDate created;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Task> tasks;

    @Override
    public String toString() {
        return super.toString() + " {" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
