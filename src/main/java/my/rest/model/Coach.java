package my.rest.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coaches")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "coach_seq")
    @SequenceGenerator(name = "coach_seq", sequenceName = "coaches_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "coach", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Group> groups = new ArrayList<>();

    public Coach() {
    }

    public Coach(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }

        Coach coach = (Coach) o;
        if (id != coach.getId()) {
            return false;
        }

        if (!getFirstName().equals(coach.getFirstName())) {
            return false;
        }
        if (!getLastName().equals(coach.getLastName())) {
            return false;
        }

        return getPhoneNumber().equals(coach.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getPhoneNumber().hashCode();
        return result;
    }
}
