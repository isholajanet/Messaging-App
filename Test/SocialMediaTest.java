import Models.FriendRequest;
import Models.Platform;
import Models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Exception.UserNotFoundException;
import Exception.UnregisteredUserException;

import static org.junit.jupiter.api.Assertions.*;

public class SocialMediaTest {
    Platform platform;
    User user;
    FriendRequest friendRequest;
    @BeforeEach
    void setUp() throws Exception{
        platform = new Platform();
        user = new User("jane", "jane@gmail.com", "1234");
    }

    @AfterEach()
    void tearDown(){
        Platform.reset();
    }
    @Test
    void platformCanRegisterUserWithDetails(){
        platform.register("janet", "jane@gmail.com", "1234");
        assertEquals(1, platform.getTotalNumberOfUser());
    }

    @Test
    void platformCanLogUserOnToThePlatformWithUsername(){
        platform.register("jane", "jane@gmail.com", "1234");
        assertTrue(platform.isLoggedOn("jane", "1234"));
    }
    @Test
    void platformCannotLogUserOnToThePlatformWithEitherTheWrongUsernameOrPassword(){
        platform.register("jane", "jane@gmail.com", "1234");
        assertFalse(platform.isLoggedOn("janet", "1234"));
    }

    @Test
    void userCanSearchForUsers() {
        User jane = platform.register("jane", "jane@gmail.com", "1234");
        User timi = platform.register("timi", "timi@gmail.com", "3456");

        assertThrows(UserNotFoundException.class, () -> jane.searchForFriends("timi"));
    }
    @Test
    void platformCanCheckIfAUserExistTest(){
        User jane = platform.register("jane", "jane@gmail.com", "1234");
        User timi = platform.register("timi", "timi@gmail.com", "3456");

        assertTrue(Platform.contains(jane));
    }
    @Test
    void userCanSendFriendRequestToAnotherRegisteredUserTest() throws UnregisteredUserException {
        User jane = platform.register("jane", "jane@gmail.com", "1234");
        User timi = platform.register("timi", "timi@gmail.com", "3456");

        assertFalse(jane.isFriendsWith(timi));
        assertFalse(timi.isFriendsWith(jane));
        assertEquals(0,jane.getPendingSentRequests());
        assertEquals(0, timi.getPendingReceivedRequests());

        jane.sendFriendRequestTo(timi);

        assertEquals(1,jane.getPendingSentRequests());
        assertEquals(1, timi.getPendingReceivedRequests());
        assertFalse(jane.isFriendsWith(timi));
        assertFalse(timi.isFriendsWith(jane));

    }
    @Test
    void testThatUserCanAcceptFriendRequest() throws UnregisteredUserException {
        User jane = platform.register("jane", "jane@gmail.com", "1234");
        User timi = platform.register("timi", "timi@gmail.com", "3456");

        assertEquals(0, jane.getPendingSentRequests());
        assertEquals(0, timi.getPendingReceivedRequests());

        assertEquals(0, timi.getTotalNumberOfFriends());
        assertEquals(0, jane.getTotalNumberOfFriends());

        jane.sendFriendRequestTo(timi);

        assertFalse(jane.isFriendsWith(timi));
        assertFalse(timi.isFriendsWith(jane));

        assertEquals(1, jane.getPendingSentRequests());
        assertEquals(1, timi.getPendingReceivedRequests());

        assertEquals(0, timi.getTotalNumberOfFriends());
        assertEquals(0, jane.getTotalNumberOfFriends());

        timi.acceptFriendRequest(jane);

        assertEquals(0, jane.getPendingSentRequests());
        assertEquals(0, timi.getPendingReceivedRequests());

        assertEquals(1, timi.getTotalNumberOfFriends());
        assertEquals(1, jane.getTotalNumberOfFriends());

        assertTrue(jane.isFriendsWith(timi));
        assertTrue(timi.isFriendsWith(jane));
    }

    @Test
    void testThatUnregisteredUserCannotSendFriendRequest(){
        User timi = platform.register("timi", "timi@gmail.com", "3456");
        User tomi = new User("tomi", "tomi@gmail.com", "1234");

        assertEquals(0, tomi.getPendingSentRequests());
        assertEquals(0, timi.getPendingReceivedRequests());

        assertEquals(0, timi.getTotalNumberOfFriends());
        assertEquals(0, tomi.getTotalNumberOfFriends());


        assertThrows(UnregisteredUserException.class, () ->  tomi.sendFriendRequestTo(timi));

    }
    @Test
    void testThatRegisteredUserCannotSendFriendRequestToAnUnregisteredUser(){
        User tomi = platform.register("timi", "timi@gmail.com", "3456");
        User timi = new User("tomi", "tomi@gmail.com", "1234");

        assertEquals(0, tomi.getPendingSentRequests());
        assertEquals(0, timi.getPendingReceivedRequests());

        assertEquals(0, timi.getTotalNumberOfFriends());
        assertEquals(0, tomi.getTotalNumberOfFriends());


        assertThrows(UnregisteredUserException.class, () ->  tomi.sendFriendRequestTo(timi));

    }





}
