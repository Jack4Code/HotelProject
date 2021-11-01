import main.java.Managers.UserManager;
import main.java.Utilities.InputValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class InputValidationTest {

    @Test
    public void validEmailTest() {
        Assertions.assertTrue(InputValidator.isValidEmail("john.doe@gmail.com"));
        Assertions.assertTrue(InputValidator.isValidEmail("emmanuel@hibernate.org"));
        Assertions.assertTrue(InputValidator.isValidEmail("emma+nuel@hibernate.org"));
        Assertions.assertTrue(InputValidator.isValidEmail("emmanuel@hibernate.net"));
        Assertions.assertTrue(InputValidator.isValidEmail("emma+nuel@example.net"));
        Assertions.assertTrue(InputValidator.isValidEmail("fredAndbarny@example.gov"));
        Assertions.assertTrue(InputValidator.isValidEmail("---@example.com"));
        Assertions.assertTrue(InputValidator.isValidEmail("foo-bar@example.net"));
        Assertions.assertTrue(InputValidator.isValidEmail("prettyandsimple@example.com"));
        Assertions.assertTrue(InputValidator.isValidEmail("very.common@example.com"));
        Assertions.assertTrue(InputValidator.isValidEmail("disposable.style.email.with+symbol@example.com"));
        Assertions.assertTrue(InputValidator.isValidEmail("other.email-with-dash@example.com"));
    }

    @Test
    public void invalidEmailTest() {
        Assertions.assertFalse(InputValidator.isValidEmail("abcdefg.com"));
        Assertions.assertFalse(InputValidator.isValidEmail("emma nuel@hibernate.org"));
        Assertions.assertFalse(InputValidator.isValidEmail("emma(nuel@hibernate.org"));
        Assertions.assertFalse(InputValidator.isValidEmail("emmanuel@"));
        Assertions.assertFalse(InputValidator.isValidEmail("emma\nnuel@hibernate.org"));
        Assertions.assertFalse(InputValidator.isValidEmail("emma@nuel@hibernate.org"));
        Assertions.assertFalse(InputValidator.isValidEmail("emma@nuel@.hibernate.org"));
        Assertions.assertFalse(InputValidator.isValidEmail("Just a string"));
        Assertions.assertFalse(InputValidator.isValidEmail("string"));
        Assertions.assertFalse(InputValidator.isValidEmail("me@"));
        Assertions.assertFalse(InputValidator.isValidEmail("@example.com"));
        Assertions.assertFalse(InputValidator.isValidEmail("me\\@example.com"));
    }

    @Test
    public void registerInvalidEmailTest() {
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "abcdefg.com", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "emma nuel@hibernate.org", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "emma(nuel@hibernate.org", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "emmanuel@", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "emma\nnuel@hibernate.org", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "emma@nuel@hibernate.org", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "emma@nuel@.hibernate.org", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "Just a string", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "string", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "me@", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "@example.com", "password"));
        Assertions.assertFalse(UserManager.registerUser("John", "Doe", "me\\@example.com", "password"));
    }


}
