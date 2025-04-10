package commandfactory;

import command.Command;
import command.LogOutCommand;
import exception.SyncException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import participant.Participant;
import participant.ParticipantManager;
import storage.UserStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class LogOutCommandFactoryTest {

    private LogOutCommandFactory factory;
    private ParticipantManager participantManager;

    @BeforeEach
    void setUp() throws SyncException {
        UserStorage userStorage = new UserStorage("./data/test-users.txt");
        participantManager = new ParticipantManager(new ArrayList<>(), null, userStorage);

        Participant testUser = new Participant("john_doe", "password123",
                Participant.AccessLevel.ADMIN, new ArrayList<>());
        participantManager.addNewUser(testUser);
        participantManager.setCurrentUser(testUser);

        factory = new LogOutCommandFactory(participantManager);
    }

    @Test
    public void testCreateCommandNotLoggedInThrowsException() {
        participantManager.setCurrentUser(null);

        SyncException exception = assertThrows(SyncException.class, factory::createCommand);
        assertEquals("You are not logged in. Please enter 'login' to log in first.", exception.getMessage());
    }

    @Test
    public void testCreateCommandLoggedInReturnsLogOutCommand() throws SyncException {
        Command command = factory.createCommand();
        assertTrue(command instanceof LogOutCommand);
    }
}
