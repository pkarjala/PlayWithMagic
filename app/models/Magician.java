package models;

import org.mindrot.jbcrypt.BCrypt;
import views.formdata.EditMagicianFormData;
import views.formdata.EditUserFormData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.File;
import java.util.List;

/**
 * A Magician object that holds the information about a user of the Play With Magic site.
 * Essentially the user data.
 *
 * The synthetic unique constraint on this model is id.
 * The logical unique constraint on this model is email.
 *
 * @see https://github.com/PlayWithMagic/PlayWithMagic/issues/32
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"first_name", "last_name"}))
public class Magician extends play.db.ebean.Model {
  @Id
  private long id;
  // Magician Information
  @Column(nullable = false, length = GlobalDbInfo.MAX_SHORT_TEXT_LENGTH) private String firstName;
  @Column(nullable = false, length = GlobalDbInfo.MAX_SHORT_TEXT_LENGTH) private String lastName;
  @Column(unique = true, nullable = false, length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String email;
  @Column(nullable = false, length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String password;
  @Column(nullable = false)
  @ManyToOne
  private MagicianType magicianType;

  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String stageName;
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String location;  // City/State? Country? Perhaps a map.
  private File userPhoto;

  // Magic Info
  @Column(length = GlobalDbInfo.MAX_MULTILINE_TEXT_LENGTH) private String biography;
  @Column(length = GlobalDbInfo.MAX_MULTILINE_TEXT_LENGTH) private String interests;
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String influences;

  private Integer yearStarted;  // The year started - used to compute the number of years of experience.
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String organizations;
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String website;

  // Social Media
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String facebook;
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String twitter;
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String linkedIn;
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String googlePlus;
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String flickr;
  @Column(length = GlobalDbInfo.MAX_LONG_TEXT_LENGTH) private String instagram;


  /**
   * Create a magician with only the required fields.
   *
   * @param firstName       The magician's first name.
   * @param lastName        The magician's last name.
   * @param email           The magician's eMail address.
   * @param magicianType    The type of magician the user identifies with.
   * @param password        The magician's password.
   */
  public Magician(String firstName, String lastName, String email, MagicianType magicianType, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.magicianType = magicianType;
    this.password = password;
  }


  /**
   * The EBean ORM finder method for database queries.
   *
   * @return The finder method.
   */
  public static Finder<Long, Magician> find() {
    return new Finder<Long, Magician>(Long.class, Magician.class);
  }


  /**
   * Get all of the active Magicians.
   * <p>
   * TODO:  Add where status=active and create a getAllMagicians -- use only in Admin pages.
   *
   * @return The all active Magicians.
   */
  public static List<Magician> getActiveMagicians() {
    return Magician.find().all();
  }


  /**
   * Get all of the Magicians.
   * <p>
   * TODO:  Add where status=active and create a getAllMagicians -- use only in Admin pages.
   *
   * @return The all active Magicians.
   */
  public static List<Magician> getAllMagicians() {
    return Magician.find().all();
  }


  /**
   * Delete a Magician associated with a given id.
   * <p>
   * TO-DO:  Set the status=inactive instead of deleting the magician.  Rename method to deactivate Magician.
   * Create an activate Magician method.
   *
   * @param id The ID of the magician to delete.
   */
  public static void deleteMagician(long id) {
    Magician magician = getMagician(id);

    magician.delete();
  }


  /**
   * Get a Magician associated with a given email.
   *
   * @param email The ID of the magician to retrieve.
   * @return The retrieved magician object.
   */
  public static Magician getMagician(String email) {
    Magician magician = Magician.find().where().eq("email", email).findUnique();
    if (magician == null) {
      throw new RuntimeException("Unable to find Magician with the given email [" + email + "]");
    }
    return magician;
  }

  /**
   * Get a Magician associated with a given id.
   *
   * @param id The ID of the magician to retrieve.
   * @return The retrieved magician object.
   */
  public static Magician getMagician(long id) {
    Magician magician = Magician.find().byId(id);
    if (magician == null) {
      throw new RuntimeException("Unable to find Magician with the given ID [" + id + "]");
    }
    return magician;
  }

  /**
   * Check if an email address is associated with an existing Magician.
   *
   * @param email The email address to check against in the DB
   * @return True if found, otherwise false.
   */
  public static boolean isExistingMagician(String email) {
    int count = Magician.find().where().eq("email", email).findRowCount();
    return count >= 1;
  }


  /**
   * Check if a Magician's credentials are valid.
   *
   * @param email The email address of the Magician.
   * @param password The password of the Magician to check.
   * @return True if Magician exists and Password matches hashed password, otherwise false.
   */
  public static boolean isValidMagician(String email, String password) {
    return ((email != null)
        && (password != null)
        && isExistingMagician(email)
        && BCrypt.checkpw(password, getMagician(email).getPassword()));
  }


  /**
   * Update an existing Magician object from EditMagicianFormData.  Update the database and return the current
   * Magician.
   *
   * @param editMagicianFormData The source of data for the Magician.
   * @return A fully populated Magician object that's just been persisted in the database.
   */
  public static Magician createMagicianFromForm(EditMagicianFormData editMagicianFormData) {
    MagicianType magicianType = MagicianType.getMagicianType(editMagicianFormData.magicianType);

    Magician magician = Magician.find().byId(editMagicianFormData.id);
    if (magician == null) {
      magician = Magician.find().where().eq("email", editMagicianFormData.email).findUnique();
    }

    magician.setFirstName(editMagicianFormData.firstName);
    magician.setLastName(editMagicianFormData.lastName);
    magician.setEmail(editMagicianFormData.email);
    magician.setMagicianType(magicianType);

    magician.setStageName(editMagicianFormData.stageName);
    magician.setLocation(editMagicianFormData.location);
    // User photo
    magician.setBiography(editMagicianFormData.biography);
    magician.setInterests(editMagicianFormData.interests);
    magician.setInfluences(editMagicianFormData.influences);
    magician.setYearStarted(editMagicianFormData.yearStarted);
    magician.setOrganizations(editMagicianFormData.organizations);
    magician.setWebsite(editMagicianFormData.website);
    magician.setFacebook(editMagicianFormData.facebook);
    magician.setTwitter(editMagicianFormData.twitter);
    magician.setLinkedIn(editMagicianFormData.linkedIn);
    magician.setGooglePlus(editMagicianFormData.googlePlus);
    magician.setInstagram(editMagicianFormData.instagram);
    magician.setFlickr(editMagicianFormData.flickr);

    magician.save();
    return magician;
  }

// TODO:  Need a mechanism to prevent a user from deleting their own account (or if they do, then log them out first).

  /**
   * Create a new Magician object from EditUserFormData.  Add it to the database and return the newly created
   * Magician.
   *
   * @param editUserFormData The source of data for the Magician.
   * @return A fully populated Magician object that's just been persisted in the database.
   */
  public static Magician createMagicianFromForm(EditUserFormData editUserFormData) {
    Magician magician = null;
    MagicianType magicianType = MagicianType.getMagicianType(editUserFormData.magicianType);

    if (editUserFormData.id == 0) {
      magician = new Magician(
          editUserFormData.firstName
          , editUserFormData.lastName
          , editUserFormData.email
          , magicianType
          , BCrypt.hashpw(editUserFormData.password, BCrypt.gensalt(12)));
    }
    else {
      magician = Magician.find().byId(editUserFormData.id);
      magician.setFirstName(editUserFormData.firstName);
      magician.setLastName(editUserFormData.lastName);
      magician.setEmail(editUserFormData.email);
      magician.setMagicianType(magicianType);
      magician.setPassword(BCrypt.hashpw(editUserFormData.password, BCrypt.gensalt(12)));
    }

    magician.save();
    return magician;
  }

  /**
   * Get the ID of the magician.
   *
   * @return The ID.
   */
  public long getId() {
    return id;
  }

  /**
   * Set the ID of the magician.
   *
   * @param id The ID of the magician.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Get the first name of the magician.
   *
   * @return The first name.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Set the first name of the magician.
   *
   * @param firstName The magician's first name.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get the last name of the magician.
   *
   * @return The last name.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Set the magician's last name.
   *
   * @param lastName The magician's last name.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get the magician's encrypted password.
   *
   * @return The magician's password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set the magician's encrypted password.
   *
   * @param password The magician's password.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get the stage name of the magician.
   *
   * @return The stage name.
   */
  public String getStageName() {
    return stageName;
  }

  /**
   * Set the magician's stage name.
   *
   * @param stageName The magician's stage name.
   */
  public void setStageName(String stageName) {
    this.stageName = stageName;
  }

  /**
   * Get the location of the magician.
   *
   * @return The location.
   */
  public String getLocation() {
    return location;
  }

  /**
   * Set the magician's location.
   *
   * @param location The magician's location.
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Get the magician photo file object.
   *
   * @return The magician photo file object.
   */
  public File getUserPhoto() {
    return userPhoto;
  }

  /**
   * Set the magician's photo.
   *
   * @param userPhoto A file containing the image of the magician.
   */
  public void setUserPhoto(File userPhoto) {
    this.userPhoto = userPhoto;
  }

  /**
   * Get the biography of the magician.
   *
   * @return The biography.
   */
  public String getBiography() {
    return biography;
  }

  /**
   * St the magician's biography.
   *
   * @param biography The magician's biography.
   */
  public void setBiography(String biography) {
    this.biography = biography;
  }

  /**
   * Get the interests of the magician.
   *
   * @return The interests.
   */
  public String getInterests() {
    return interests;
  }

  /**
   * Set the magician's interests.
   *
   * @param interests The magician's interests.
   */
  public void setInterests(String interests) {
    this.interests = interests;
  }

  /**
   * Get the influences of the magician.
   *
   * @return The influences.
   */
  public String getInfluences() {
    return influences;
  }

  /**
   * Set the magician's influences.
   *
   * @param influences The magician's influences.
   */
  public void setInfluences(String influences) {
    this.influences = influences;
  }

  /**
   * Get the number of years the magician has been practicing.
   *
   * @return The number of years of practice.
   */
  public Integer getYearStarted() {
    return yearStarted;
  }

  /**
   * Set the year the magician started practicing magic.
   *
   * @param yearStarted The year the magician started practcing magic.
   */
  public void setYearStarted(Integer yearStarted) {
    this.yearStarted = yearStarted;
  }

  /**
   * Get the magician's affiliated organizations.
   *
   * @return The organizations.
   */
  public String getOrganizations() {
    return organizations;
  }

  /**
   * Set the organizations the magician is affiliated with.
   *
   * @param organizations The magician's organizations.
   */
  public void setOrganizations(String organizations) {
    this.organizations = organizations;
  }

  /**
   * Get the magician's website.
   *
   * @return The website.
   */
  public String getWebsite() {
    return website;
  }

  /**
   * Set the magician's website.
   *
   * @param website The magician's website.
   */
  public void setWebsite(String website) {
    this.website = website;
  }

  /**
   * Get the magician's email address.
   *
   * @return The email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set the magician's eMail address.
   *
   * @param email The magician's eMail address.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Get the magician's Facebook page link.
   *
   * @return The Facebook page link.
   */
  public String getFacebook() {
    return facebook;
  }

  /**
   * Set the magician's Facebook page link.
   *
   * @param facebook The magician's Facebook page link.
   */
  public void setFacebook(String facebook) {
    this.facebook = facebook;
  }

  /**
   * Get the magician's Twitter page link.
   *
   * @return The Twitter page link.
   */
  public String getTwitter() {
    return twitter;
  }

  /**
   * Set the magician's Twitter page link.
   *
   * @param twitter The magician's Twitter page link.
   */
  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  /**
   * Get the magician's LinkedIn link.
   *
   * @return The LinkedIn link.
   */
  public String getLinkedIn() {
    return linkedIn;
  }

  /**
   * Set the magician's LinkedIn profile link.
   *
   * @param linkedIn The magician's LinkedIn profile link.
   */
  public void setLinkedIn(String linkedIn) {
    this.linkedIn = linkedIn;
  }

  /**
   * Get the magician's Google Plus link.
   *
   * @return The Google Plus link.
   */
  public String getGooglePlus() {
    return googlePlus;
  }

  /**
   * Set the magician's Google Plus link.
   *
   * @param googlePlus The magician's Google Plus link.
   */
  public void setGooglePlus(String googlePlus) {
    this.googlePlus = googlePlus;
  }

  /**
   * Get the magician's flickr account link.
   *
   * @return The flickr account link.
   */
  public String getFlickr() {
    return flickr;
  }

  /**
   * Set the magician's Flickr account link.
   *
   * @param flickr The Flickr account link.
   */
  public void setFlickr(String flickr) {
    this.flickr = flickr;
  }

  /**
   * Get the magician's Instagram account link.
   *
   * @return The Instagram account.
   */
  public String getInstagram() {
    return instagram;
  }

  /**
   * Set the magician's Instagram account link.
   *
   * @param instagram The Instagram account.
   */
  public void setInstagram(String instagram) {
    this.instagram = instagram;
  }

  /**
   * Get the MagicianType object.
   *
   * @return The MagicianType object.
   */
  public MagicianType getMagicianType() {
    return magicianType;
  }

  /**
   * Set the MagicianType object.
   *
   * @param magicianType The MagicianType object.
   */
  public void setMagicianType(MagicianType magicianType) {
    this.magicianType = magicianType;
  }


  /**
   * Add a magician to the database, but check to see if the magician is already in there first.
   *
   * @param magician The magician to add.
   * @return The magician that was just added.
   */
  public static Magician init(Magician magician) {
    Magician theMagician = Magician.find().where().eq("email", magician.email).findUnique();

    if (theMagician != null) {
      return theMagician;
    }

    magician.save();

    return magician;
  }


  /**
   * Initialize the Magician dataset.
   *
   * TODO:  Get this to go through the backing classes.
   */
  public static void init() {
    MagicianType magicianTypeSemiProfessional = MagicianType.getMagicianType("Semi-Professional");
    MagicianType magicianTypeProfessional = MagicianType.getMagicianType("Professional");
    MagicianType magicianTypeEnthusiast = MagicianType.getMagicianType("Enthusiast");

    // --------------------------------------

    Magician magician = null;

    magician = new Magician("Mark", "Nelson", "mr_nelson@icloud.com", magicianTypeSemiProfessional, "P@ssw0rd");
    magician.setStageName("Mark Nelson");
    magician.setLocation("Honolulu, HI");
    // No photo
    magician.setBiography("I got started in magic in 2004.  A retired magician, JC Dunn, showed me a 2-card monte and "
        + "I was hooked.  Since then, I've learned the craft, performed hundreds of shows in Honolulu and most "
        + "recently I nailed a parlor act in Beijing.");
    magician.setInterests("I'm most comfortable with close-up magic, but I'd like to develop a stage show.  I strive "
        + "to be fluent in all mediums of the art (cards, coins, rope, etc.).");
    magician.setInfluences("Tony Slydini, David Regal, Lee Asher, Aaron Fisher, my brother Steve Johnson and many, "
        + "many others.");
    magician.setYearStarted(2004);
    //magician.setOrganizations();
    magician.setWebsite("http://mark.nelson.engineer/wordpress/index.php/magic-home-page/");
    magician.setFacebook("mark.nelson.engineer");
    magician.setTwitter("@mr_marknelson");
    magician.setLinkedIn("http://www.linkedin.com/in/marknelsonengineer/en");
    magician.setGooglePlus("mr_nelson@icloud.com");
    magician.setInstagram("mr_mark_nelson");

    Magician.init(magician);

    magician = new Magician("Patrick", "Karjala", "pkarjala@gmail.com", magicianTypeEnthusiast, "P@ssw0rd");
    Magician.init(magician);

    magician = new Magician("David", "Neely", "dneely@hawaii.edu", magicianTypeEnthusiast, "P@ssw0rd");
    Magician.init(magician);

    magician = new Magician("Lee", "Asher", "lee@leeasher.com", magicianTypeProfessional, "P@ssw0rd");
    Magician.init(magician);

    magician = new Magician("Steve", "Johnson", "steve@grandillusions.com", magicianTypeProfessional, "P@ssw0rd");
    Magician.init(magician);

    magician = new Magician("Wayne", "Houchin", "wayne@waynehouchin.com", magicianTypeProfessional, "P@ssw0rd");
    Magician.init(magician);
  }

}
