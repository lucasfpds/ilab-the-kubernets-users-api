package com.project.userapi.model;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "cpf", length = 11, nullable = false, unique = true)
	private String cpf;
	
	@Column(name = "telephone", nullable = false)
	private String telephone;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

	@Column(name="email", length = 50, nullable = false, unique = true)
	private String email;

    public User(Integer id, String name, String cpf, String telephone, String birthDate, String email) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.email = email;
    }
    public User(){

    }
    public Integer getId() {
        return id;
    }

    public void setNumero(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "User [birthDate=" + birthDate + ", cpf=" + cpf + ", email=" + email + ", id=" + id + ", name=" + name
                + ", telephone=" + telephone + "]";
    }

}
