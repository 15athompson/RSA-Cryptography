import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RSAKeyGeneratorTest {

    @Test
    void testGenerateKeyPair() {
        assertDoesNotThrow(() -> {
            RSAKeyGenerator.generateKeyPair();
        });
    }

    @Test
    void testEncryptAndDecryptMessage() {
        try {
            KeyPair keyPair = RSAKeyGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            String originalMessage = "Hello, World!";
            String encryptedMessage = RSAKeyGenerator.encryptMessage(originalMessage, publicKey);
            String decryptedMessage = RSAKeyGenerator.decryptMessage(encryptedMessage, privateKey);

            assertEquals(originalMessage, decryptedMessage);
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    // Add more test cases for RSAPublicKeySpec, RSAPrivateKeySpec, etc.
}

class UserManagerTest {

    private SecureKeyStorage keyStorage;
    private UserManager userManager;

    @BeforeEach
    void setup() {
        keyStorage = new SecureKeyStorage();
        userManager = new UserManager(keyStorage);
    }

    @Test
    void testCreateUser() {
        assertDoesNotThrow(() -> {
            userManager.createUser("testUser");
        });
    }

    @Test
    void testCreateExistingUser() {
        userManager.createUser("testUser");
        assertThrows(UserAlreadyExistsException.class, () -> {
            userManager.createUser("testUser");
        });
    }

    @Test
    void testLoginUser() {
        userManager.createUser("testUser");
        User user = userManager.loginUser("testUser");
        assertNotNull(user);
    }

    @Test
    void testLoginNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> {
            userManager.loginUser("nonExistingUser");
        });
    }

    @Test
    void testRetrieveKeyPair() {
        userManager.createUser("testUser");
        KeyPair keyPair = userManager.retrieveKeyPair("testUser");
        assertNotNull(keyPair);
    }

    // Add more test cases for edge cases, error handling, etc.
}

class SecureKeyStorageTest {

    private SecureKeyStorage keyStorage;

    @BeforeEach
    void setup() {
        keyStorage = new SecureKeyStorage();
    }

    @Test
    void testStoreAndRetrieveKeyPair() {
        KeyPair keyPair = RSAKeyGenerator.generateKeyPair();
        assertDoesNotThrow(() -> {
            keyStorage.storeKeyPair("testUser", keyPair);
        });

        KeyPair retrievedKeyPair = keyStorage.retrieveKeyPair("testUser");
        assertNotNull(retrievedKeyPair);
        assertEquals(keyPair, retrievedKeyPair);
    }

    @Test
    void testRetrieveNonExistingKeyPair() {
        assertNull(keyStorage.retrieveKeyPair("nonExistingUser"));
    }

    // Add more test cases for edge cases, error handling, etc.
}

class RSACryptographySystemIntegrationTest {

    private SecureKeyStorage keyStorage;
    private UserManager userManager;

    @BeforeEach
    void setup() {
        keyStorage = new SecureKeyStorage();
        userManager = new UserManager(keyStorage);
    }

    @Test
    void testCreateUserLoginEncryptDecrypt() {
        userManager.createUser("userA");
        userManager.createUser("userB");

        User userA = userManager.loginUser("userA");
        User userB = userManager.loginUser("userB");

        assertNotNull(userA);
        assertNotNull(userB);

        String originalMessage = "Hello, UserB!";
        String encryptedMessage = userA.encryptMessage(originalMessage, userB.getPublicKey());
        String decryptedMessage = userB.decryptMessage(encryptedMessage);

        assertEquals(originalMessage, decryptedMessage);
    }

    // Add more integration test cases for various scenarios.
}
