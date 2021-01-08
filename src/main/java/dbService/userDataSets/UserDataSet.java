package dbService.userDataSets;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserDataSet implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true, updatable = false)
    private String name;

    @Column(name = "password")
    private String password;

    public UserDataSet() {
    }

    public UserDataSet(String name, String password) {
        this.setId(-1);
        this.setName(name);
        this.setPassword(password);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
