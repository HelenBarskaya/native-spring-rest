package my.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientDto {

    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<GroupDto> groups = new ArrayList<>();

    public ClientDto() {
    }

    public ClientDto(String firstName, String lastName, String phoneNumber) {
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

    public List<GroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDto> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientDto clientDto)) return false;
        return id == clientDto.id && Objects.equals(firstName, clientDto.firstName) && Objects.equals(lastName, clientDto.lastName) && Objects.equals(phoneNumber, clientDto.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phoneNumber);
    }
}
