package my.rest.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    Client client;

    @BeforeEach
    void initMethod() {
        client = new Client();
    }

    @Test
    void getAndSetIdTest() {
        assertEquals(0, client.getId());
        client.setId(1L);
        assertEquals(1L, client.getId());
    }

    @Test
    void getAndSetFirstNameTest() {
        assertNull(client.getFirstName());
        client.setFirstName("Анна");
        assertEquals("Анна", client.getFirstName());
    }

    @Test
    void getAndSetLastNameTest() {
        assertNull(client.getLastName());
        client.setLastName("Морозова");
        assertEquals("Морозова", client.getLastName());
    }

    @Test
    void getAndSetNumberPhoneTest() {
        assertNull(client.getPhoneNumber());
        client.setPhoneNumber("89379127450");
        assertEquals("89379127450", client.getPhoneNumber());
    }

    @Test
    void getAndSetGroupsTest() {
        List<Group> groups = new ArrayList<>();
        Group group = new Group();
        assertTrue(client.getGroups().isEmpty());

        groups.add(group);
        client.setGroups(groups);
        assertArrayEquals(groups.toArray(), client.getGroups().toArray());
    }
}
