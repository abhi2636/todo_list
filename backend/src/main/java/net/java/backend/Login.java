package net.java.backend;
import jakarta.persistence.*;

@Entity
@Table(name = "login") 
public class Login {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    @Column(name = "username", nullable = false, unique = true) 
    private String username;

    @Column(name = "password", nullable = false) 
    private String password;

    public Login() {}

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
