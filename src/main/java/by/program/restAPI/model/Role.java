package by.program.restAPI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return super.toString() + " {" +
                "name='" + name + '\'' +
                '}';
    }
}
