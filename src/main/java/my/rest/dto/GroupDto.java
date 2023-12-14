package my.rest.dto;

import java.util.List;
import java.util.Objects;

public class GroupDto {

    private long id;
    private String name;
    private CoachDto coach;
    private List<ClientDto> clients;

    public GroupDto() {
    }

    public GroupDto(String name, CoachDto coach) {
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

    public CoachDto getCoach() {
        return coach;
    }

    public void setCoach(CoachDto coach) {
        this.coach = coach;
    }

    public List<ClientDto> getClients() {
        return clients;
    }

    public void setClients(List<ClientDto> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupDto groupDto)) return false;
        return Objects.equals(name, groupDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
