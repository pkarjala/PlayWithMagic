package models;

import java.io.File;

/**
 * A Magician object that holds the information about a user of the Play With Magic site.
 * Essentially the user data.
 *
 * @see https://github.com/PlayWithMagic/PlayWithMagic/issues/32
 */
public class Magician {

  private long id;
  // User Info
  private String firstName;
  private String lastName;
  private String stageName;
  private String location; // City/State?  Country?  Perhaps a map of values instead?
  private File userPhoto;
  // Magic Info
  private String biography;
  private String interests;
  private String influences;
  private String experienceLevel;
  private Integer yearStarted;  // The year started - used to compute the number of years of experience.
  private String organizations;
  // Contact Info
  private String website;
  private String email;
  private String facebook;
  private String twitter;
  private String linkedIn;
  private String googlePlus;
  private String flickr;
  private String instagram;


  /**
   * Create a new Magician object.
   *
   * @param id              The unique ID.
   * @param firstName       The first name of the user.
   * @param lastName        The last name of the magician.
   * @param stageName       The stage name of the magician.
   * @param location        Global location.
   * @param userPhoto       Photograph file of user.
   * @param biography       Biography of user.
   * @param interests       User's interests in magic.
   * @param influences      User's influences.
   * @param experienceLevel User's experience level; pre-set values.
   * @param yearStarted     The year started - used to compute the number of years of experience.
   * @param organizations   Any affiliations or organizations the user is a member of.
   * @param website         User's personal website.
   * @param email           User's email address.
   * @param facebook        The user's facebook account.
   * @param twitter         User's Twitter account.
   * @param linkedIn        User's LinkedIn account.
   * @param googlePlus      User's Google Plus account.
   * @param flickr          User's flickr account.
   * @param instagram       User's instagram account.
   */
  public Magician(long id, String firstName, String lastName, String stageName, String location, File userPhoto,
                  String biography, String interests, String influences, String experienceLevel, Integer yearStarted,
                  String organizations, String website, String email, String facebook, String twitter, String linkedIn,
                  String googlePlus, String flickr, String instagram) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.stageName = stageName;
    this.location = location;
    this.userPhoto = userPhoto;
    this.biography = biography;
    this.interests = interests;
    this.influences = influences;
    this.experienceLevel = experienceLevel;
    this.yearStarted = yearStarted;
    this.organizations = organizations;
    this.website = website;
    this.email = email;
    this.facebook = facebook;
    this.twitter = twitter;
    this.linkedIn = linkedIn;
    this.googlePlus = googlePlus;
    this.flickr = flickr;
    this.instagram = instagram;
  }

  /**
   * Create a new Magician object for testing purposes only.
   *
   * @param firstName       The first name of the user.
   * @param lastName        The last name of the magician.
   * @param stageName       The stage name of the magician.
   * @param location        Global location.
   * @param userPhoto       Photograph file of user.
   * @param biography       Biography of user.
   * @param interests       User's interests in magic.
   * @param influences      User's invluences.
   * @param experienceLevel User's experience level; pre-set values.
   * @param yearStarted     The year started - used to compute the number of years of experience.
   * @param organizations   Any affiliations or organizations the user is a member of.
   * @param website         User's personal website.
   * @param email           User's email address.
   * @param facebook        The user's facebook account.
   * @param twitter         User's Twitter account.
   * @param linkedIn        User's LinkedIn account.
   * @param googlePlus      User's Google Plus account.
   * @param flickr          User's flickr account.
   * @param instagram       User's instagram account.
   */
  public Magician(String firstName, String lastName, String stageName, String location, File userPhoto,
                  String biography, String interests, String influences, String experienceLevel, int yearStarted,
                  String organizations, String website, String email, String facebook, String twitter, String linkedIn,
                  String googlePlus, String flickr, String instagram) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.stageName = stageName;
    this.location = location;
    this.userPhoto = userPhoto;
    this.biography = biography;
    this.interests = interests;
    this.influences = influences;
    this.experienceLevel = experienceLevel;
    this.yearStarted = yearStarted;
    this.organizations = organizations;
    this.website = website;
    this.email = email;
    this.facebook = facebook;
    this.twitter = twitter;
    this.linkedIn = linkedIn;
    this.googlePlus = googlePlus;
    this.flickr = flickr;
    this.instagram = instagram;
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
   * Get the first name of the magician.
   *
   * @return The first name.
   */
  public String getFirstName() {
    return firstName;
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
   * Get the stage name of the magician.
   *
   * @return The stage name.
   */
  public String getStageName() {
    return stageName;
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
   * Get the magician photo file object.
   *
   * @return The magician photo file object.
   */
  public File getUserPhoto() {
    return userPhoto;
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
   * Get the interests of the magician.
   *
   * @return The interests.
   */
  public String getInterests() {
    return interests;
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
   * Get the experience level of the magician.
   *
   * @return The experience level.
   */
  public String getExperienceLevel() {
    return experienceLevel;
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
   * Get the magician's affiliated organizations.
   *
   * @return The organizations.
   */
  public String getOrganizations() {
    return organizations;
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
   * Get the magician's email address.
   *
   * @return The email address.
   */
  public String getEmail() {
    return email;
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
   * Get the magician's Twitter page link.
   *
   * @return The Twitter page link.
   */
  public String getTwitter() {
    return twitter;
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
   * Get the magician's Google Plus link.
   *
   * @return The Google Plus link.
   */
  public String getGooglePlus() {
    return googlePlus;
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
   * Get the magician's Instagram account link.
   *
   * @return The Instagram account.
   */
  public String getInstagram() {
    return instagram;
  }
}
