package tests;

import com.typesafe.config.ConfigFactory;
import models.Magician;
import views.formdata.EditUserFormData;

/**
 * A support class for all application tests.
 */
public class GlobalTest {

  /**
   * The port number on which to run the tests.
   */
  public static final int TEST_PORT = 3333;


  /**
   * The application's name.
   */
  public static final String APPLICATION_NAME = ConfigFactory.load().getString("application.name");


  /**
   * A generic user that we can use to quickly login.
   */
  public static Magician testUser = null;

  /**
   * DANGEROUS:  Delete the entire PlayWithMagic database and create one test user.
   *
   * This is only used for testing.
   *
   * @param verify A simple verification to ensure programmers respect this very powerful method.
   */
  public static void resetDatabaseForTest(String verify) {
    if (!verify.equals("PlayWithMagic")) {
      // TODO: Throw an exception
      return;
    }

    for (Magician magician : Magician.find().all()) {
      magician.delete();
    }
  }


  /**
   * Add a single user to the database for the test framework.  Populate the testUser global object.
   */
  public static void addUserForTest() {
    EditUserFormData editUserFormData = new EditUserFormData();
    editUserFormData.id = 0;
    editUserFormData.firstName = "Test";
    editUserFormData.lastName = "User";
    editUserFormData.magicianType = "Neophyte";
    editUserFormData.email = "test@test.com";
    editUserFormData.password = "P@ssw0rd";

    // This stores the encrypted password in the database.
    testUser = Magician.createMagicianFromForm(editUserFormData);

    // Store the unencrypted password back in the object.
    testUser.setPassword(editUserFormData.password);
  }

}
