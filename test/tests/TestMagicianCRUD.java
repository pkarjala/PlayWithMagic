package tests;

import models.Magician;
import models.MagicianType;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import play.test.TestBrowser;
import tests.pages.EditMagicianPage;
import tests.pages.EditUserPage;
import tests.pages.IndexPage;
import tests.pages.ListMagiciansPage;
import tests.pages.LoginPage;
import tests.pages.ViewMagicianPage;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static org.fest.assertions.Assertions.assertThat;


/**
 * Test the live interaction of the Magician pages with Chrome.
 * <p>
 * Run a server with a fake application, in-memory database and browser (Chrome) to test the system.
 *
 * When you want to *go* to a page, do new IndexPage(browser);
 * When you are already *at* a page, do new IndexPage(browser.getDriver());
 *
 * @see https://www.playframework.com/documentation/2.3.0/api/java/play/test/WithBrowser.html
 */
public class TestMagicianCRUD extends play.test.WithBrowser {

  private static Magician magician1 = null;
  private static Magician magician2 = null;
  private static Magician magician3 = null;

  @Override
  protected play.test.FakeApplication provideFakeApplication() {
    return fakeApplication(inMemoryDatabase());
  }

  @Override
  protected int providePort() {
    return GlobalTest.TEST_PORT;
  }

  @Override
  protected TestBrowser provideBrowser(int port) {
    return new TestBrowser(new ChromeDriver(), "/");
  }

  /**
   * Populate static objects needed for testing.
   */
  public void initializeTest() {

    magician1 = new Magician(
        "Test First Name 01"
        , "Test Last Name 01"
        , "Test_eMail_01@playwithmagic.org"
        , MagicianType.getMagicianType("Professional")
        , "11P@ssw0rd111111"
    );

    magician1.setStageName("Test stage name 01");
    magician1.setLocation("Test location 01");
    // Not doing userPhoto right now
    magician1.setBiography("Test biography 01");
    magician1.setInterests("Test interests 01");
    magician1.setInfluences("Test influences 01");
    magician1.setYearStarted(2000);
    magician1.setOrganizations("Test organizations 01");
    magician1.setWebsite("http://test.website.test/01");
    magician1.setFacebook("Test Facebook 01");
    magician1.setTwitter("Test Twitter 01");
    magician1.setLinkedIn("Test LinkedIn 01");
    magician1.setGooglePlus("Test GooglePlus 01");
    magician1.setFlickr("Test Flickr 01");
    magician1.setInstagram("Test Instagram 01");

    // Note:  The eMail needs to stay the same.
    magician2 = new Magician(
        "02 Test First Name 02"
        , "02 Test Last Name 02"
        , magician1.getEmail()
        , MagicianType.getMagicianType("Historian")
        , "22P@ssw0rd222222"
    );
    magician2.setStageName("02 Test stage name 02");
    magician2.setLocation("02 Test location 02");
    // Not doing userPhoto right now
    magician2.setBiography("02 Test biography 02");
    magician2.setInterests("02 Test interests 02");
    magician2.setInfluences("02 Test influences 02");
    magician2.setYearStarted(1999);
    magician2.setOrganizations("02 Test organizations 02");
    magician2.setWebsite("http://test.website.test/02");
    magician2.setFacebook("02 Test Facebook 02");
    magician2.setTwitter("02 Test Twitter 02");
    magician2.setLinkedIn("02 Test LinkedIn 02");
    magician2.setGooglePlus("02 Test GooglePlus 02");
    magician2.setFlickr("02 Test Flickr 02");
    magician2.setInstagram("02 Test Instagram 02");

    magician3 = new Magician(
        "03 Test First Name 03"
        , "03 Test Last Name 03"
        , "Test_eMail_03@playwithmagic.org"
        , MagicianType.getMagicianType("Historian")
        , "P@ssw0rd"
    );

  }


  /**
   * Test Magician navigation from home page and navigation bars.
   */
//  @Test
  public void testMagicianNav() {
    // browser.maximizeWindow();

    // Start at the home page...
    IndexPage indexPage = new IndexPage(browser);

    // Click the Join the Community Today! button
    EditUserPage editUserPage = indexPage.clickJoinTheCommunityToday();

    // Click the Browse Magicians button
    ListMagiciansPage listMagiciansPage = editUserPage.clickBrowseMagiciansButton();
  }


  /**
   * A workflow that tests a basic add and delete Magician with only the required fields.
   */
//  @Test
  public void testMagicianMinimumAddDelete() {
    initializeTest();
    GlobalTest.resetDatabaseForTest("PlayWithMagic");

    // browser.maximizeWindow();

    // Start at the home page...
    IndexPage indexPage = new IndexPage(browser);

    // Click the Join the Community Today! button
    EditUserPage editUserPage = indexPage.clickJoinTheCommunityToday();

    // Click Add without entering any information... this should generate an error.
    editUserPage.doesNotHaveRequiredFieldErrors();
    editUserPage.clickSubmit();
    editUserPage.hasRequiredFieldErrors();

    // Populate only the required information and click Add
    editUserPage.populateMagician(magician3);
    editUserPage.clickSubmit();

    // This should be successful and the browser should go to IndexPage (unauthenticated).  Login as the new user.
    indexPage = new IndexPage(editUserPage.getDriver());
    assertThat(indexPage.pageSource()).contains("Successfully Signed Up!");
    assertThat(indexPage.isUnauthenticated()).isTrue();
    assertThat(indexPage.isAuthenticated()).isFalse();
    LoginPage loginPage = indexPage.clickLoginButton();
    loginPage.populateLogin(magician3);
    loginPage.clickSubmit();

    // This should be successful and the browser should go to IndexPage (authenticated).
    indexPage = new IndexPage(loginPage.getDriver());
    assertThat(indexPage.isUnauthenticated()).isFalse();
    assertThat(indexPage.isAuthenticated()).isTrue();

    // This should be successful and the browser should go to ViewMagician.  Verify the magician.
    ViewMagicianPage viewMagicianPage = indexPage.clickProfileButton();
    viewMagicianPage.hasMagician(magician3);

    // Check the magician in EditMagician.
    EditMagicianPage editMagicianPage = viewMagicianPage.clickEditProfileButton();
    editMagicianPage.checkMagician(magician3);
    editMagicianPage.clickSubmit();

    // This should be successful and the browser should go to ListMagicians.  Verify the magician.
    ListMagiciansPage listMagiciansPage = new ListMagiciansPage(editUserPage.getDriver());
    listMagiciansPage.hasMagician(magician3);

    // Delete the magician
    // TODO: This needs refactoring to 1) find the right magician  2) Do the right thing with the magician
    // listMagiciansPage.deleteFirstMagician();
    // listMagiciansPage.doesNotHaveMagician(magician3);

  }

  // TODO:  You can't change the eMail address right now or you get loads of errors.
  // TODO:  The password bound from the database (in EditUser) is the encrypted hash.  It needs to be... something else

  /**
   * Test Magician CRUD.
   */
  @Test
  public void testMagicianCrudWorkflow() {
    initializeTest();
    GlobalTest.resetDatabaseForTest("PlayWithMagic");

    // browser.maximizeWindow();

    // Start at the home page...
    IndexPage indexPage = new IndexPage(browser);

    // Click the Join the Community Today! button
    EditUserPage editUserPage = indexPage.clickJoinTheCommunityToday();

    // Populate all of the fields and click Add
    editUserPage.populateMagician(magician1);
    editUserPage.clickSubmit();

    // This should be successful and the browser should go to IndexPage (unauthenticated).  Login as the new user.
    indexPage = new IndexPage(editUserPage.getDriver());
    assertThat(indexPage.pageSource()).contains("Successfully Signed Up!");
    assertThat(indexPage.isUnauthenticated()).isTrue();
    assertThat(indexPage.isAuthenticated()).isFalse();
    LoginPage loginPage = indexPage.clickLoginButton();
    loginPage.populateLogin(magician1);
    loginPage.clickSubmit();

    // This should be successful and the browser should go to IndexPage (authenticated).
    indexPage = new IndexPage(loginPage.getDriver());
    assertThat(indexPage.isUnauthenticated()).isFalse();
    assertThat(indexPage.isAuthenticated()).isTrue();

    // Go to EditMagician page, populate all of the fields and click Add
    ViewMagicianPage viewMagicianPage = indexPage.clickProfileButton();
    EditMagicianPage editMagicianPage = viewMagicianPage.clickEditProfileButton();
    editMagicianPage.populateMagician(magician1);
    editMagicianPage.clickSubmit();

    // This should be successful and the browser should go to ListMagicians.  Verify the magician.
    ListMagiciansPage listMagiciansPage = new ListMagiciansPage(editMagicianPage.getDriver());
    listMagiciansPage.hasMagician(magician1);

    // Check ViewMagicians page.
    viewMagicianPage = listMagiciansPage.viewFirstMagician();
    viewMagicianPage.hasMagician(magician1);

    // Update the user
    editUserPage = listMagiciansPage.changeFirstMagicianPassword();
    editUserPage.checkMagician(magician1);
    editUserPage.populateMagician(magician2);
    editUserPage.clickSubmit();
/*
    // Update the magician
    editMagicianPage = new EditMagicianPage(editUserPage.getDriver());
    editMagicianPage.populateMagician(magician2);
    editMagicianPage.clickSubmit();

    // Verify the new information is in the list and the old information is not.
    listMagiciansPage = new ListMagiciansPage(editMagicianPage.getDriver());
    listMagiciansPage.doesNotHaveMagician(magician1);
    listMagiciansPage.hasMagician(magician2);

    // Verify the new information is in ViewMagician
    viewMagicianPage = listMagiciansPage.viewFirstMagician();
    viewMagicianPage.hasMagician(magician2);
    listMagiciansPage = viewMagicianPage.clickReturnToMagicianListButton();

    // Verify the new information is in the User
    editUserPage = listMagiciansPage.changeFirstMagicianPassword();
    editUserPage.checkMagician(magician2);
    listMagiciansPage = editUserPage.clickBrowseMagiciansButton();

    // Delete the magician
    listMagiciansPage.hasMagician(magician2);
    listMagiciansPage.deleteFirstMagician();
    listMagiciansPage.doesNotHaveMagician(magician2);
*/
  }


  /**
   * Test to ensure that the Magician and MagicianType entities are bi-directional.
   */
//  @Test
  public void testMagicianType() {
    initializeTest();

    // browser.maximizeWindow();

    // Quickly add our three magicians
    EditUserPage editUserPage = new EditUserPage(browser);
    editUserPage.populateMagician(magician1);
    editUserPage.clickSubmit();
    EditMagicianPage editMagicianPage = new EditMagicianPage(editUserPage.getDriver());
    editMagicianPage.populateMagician(magician1);

    editUserPage = new EditUserPage(browser);
    editUserPage.populateMagician(magician2);
    editUserPage.clickSubmit();
    editMagicianPage = new EditMagicianPage(editUserPage.getDriver());
    editMagicianPage.populateMagician(magician2);

    editUserPage = new EditUserPage(browser);
    editUserPage.populateMagician(magician3);
    editUserPage.clickSubmit();
    editMagicianPage = new EditMagicianPage(editUserPage.getDriver());
    editMagicianPage.populateMagician(magician3);

    //Magician.createMagicianFromForm(new EditMagicianFormData(magician1));
    //Magician.createMagicianFromForm(new EditMagicianFormData(magician2));
    //Magician.createMagicianFromForm(new EditMagicianFormData(magician3));

    // We should have 1 professional and 2 historians.
    assertThat(MagicianType.getMagicianType("Neophyte").getMagicians().size()).isEqualTo(0);
    assertThat(MagicianType.getMagicianType("Enthusiast").getMagicians().size()).isEqualTo(0);
    assertThat(MagicianType.getMagicianType("Hobbyist").getMagicians().size()).isEqualTo(0);
    assertThat(MagicianType.getMagicianType("Semi-Professional").getMagicians().size()).isEqualTo(0);
    assertThat(MagicianType.getMagicianType("Professional").getMagicians().size()).isEqualTo(1);
    assertThat(MagicianType.getMagicianType("Historian").getMagicians().size()).isEqualTo(2);
    assertThat(MagicianType.getMagicianType("Collector").getMagicians().size()).isEqualTo(0);

    // ...and test the other direction.
    Magician magicianOne = Magician.find().where().eq("email", magician1.getEmail()).findUnique();
    assertThat(magicianOne.getMagicianType()).isEqualTo(MagicianType.getMagicianType("Professional"));

  }

}
