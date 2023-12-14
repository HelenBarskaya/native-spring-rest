package my.rest.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "group_seq")
    @SequenceGenerator(name = "group_seq", sequenceName = "groups_id_seq", allocationSize = 1)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id_coach")
    private Coach coach;

    @ManyToMany(mappedBy = "groups")
    private List<Client> clients = new ArrayList<>();

    public Group() {
    }

    public Group(String name, Coach coach) {
        this.name = name;
        this.coach = coach;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
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

        Group group = (Group) o;
        if (id != group.getId()) {
            return false;
        }
        if (!getName().equals(group.getName())) {
            return false;
        }

        return (getCoach().getId() == (group.getCoach().getId()));
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + getName().hashCode();
        result = 31 * result + (int) getCoach().getId();
        return result;
    }
}
