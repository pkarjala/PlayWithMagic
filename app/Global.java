import com.typesafe.config.ConfigFactory;
import models.Magician;
import models.MagicianType;
import models.Material;
import models.Routine;
import models.RoutineDBInit1;
import models.RoutineDBInit2;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import views.formdata.RoutineFormData;

/**
 * Global is instantiated by the framework when the application starts.  It performs specific tasks at start-up or
 * shut-down.
 */
public class Global extends GlobalSettings {
  @Override
  public void onStart(Application application) {
    super.onStart(application);

    MagicianType.init();
    Magician.init();
    //RoutineDB.init();
    //SetDB.init();

    Logger.info(ConfigFactory.load().getString("application.name") + " has started");
  }


  /******************************************************************************************************************
   * I N I T I A L I Z E   D A T A B A S E
   ******************************************************************************************************************/

  /**
   * Populate a routine.
   */
  public static void initTemplate() {
    Routine routine = null;
    Material material = null;
    long id;

    routine = new Routine("", "", 1);

    routine.setMethod("");
    routine.setHandling("");
    routine.setResetDuration(1);
    routine.setResetDescription("");
    routine.setYouTubeUrl("https://www.youtube.com/embed/");
    routine.setReviewUrl("");
    routine.setInspiration("");
    routine.setPlacement("");
    routine.setChoices("");

    routine.setImageUrl("images/routines/xxx.jpg");

    routine = Routine.saveRoutineFromForm(new RoutineFormData(routine));

    material = new Material(routine, "");
    material.setIsInspectable(true);
    material.setIsGivenAway(false);
    material.setIsConsumed(false);
    material.setPrice(1);
    material.setPurchaseUrl("");
    material.setImageUrl("images/material/xxx.jpg");
    material.setDescription("");

    routine.getMaterials().add(material);
  }


  /**
   * Initialize the Routine database.
   */
  public static void init() {
    RoutineDBInit1.init01();
    RoutineDBInit1.init02();
    RoutineDBInit1.init03();
    RoutineDBInit2.init205();
    RoutineDBInit1.init04();
    RoutineDBInit1.init05();
    RoutineDBInit1.init06();
    RoutineDBInit1.init07();
    RoutineDBInit1.init08();
    RoutineDBInit1.init09();
    RoutineDBInit1.init10();
    RoutineDBInit1.init11();
    RoutineDBInit1.init12();
    RoutineDBInit1.init13();
    RoutineDBInit1.init14();
    RoutineDBInit1.init15();
    RoutineDBInit1.init16();
    RoutineDBInit1.init17();
    RoutineDBInit1.init18();
    RoutineDBInit1.init19();
    RoutineDBInit2.init20();
    RoutineDBInit2.init21();
    RoutineDBInit2.init22();
    RoutineDBInit2.init23();
    RoutineDBInit2.init24();
    RoutineDBInit2.init30();
    RoutineDBInit2.init201();
    RoutineDBInit2.init31();
    RoutineDBInit2.init32();
    RoutineDBInit2.init33();
    RoutineDBInit2.init34();
    RoutineDBInit2.init35();
    RoutineDBInit2.init36();
    RoutineDBInit2.init37();
    RoutineDBInit2.init38();
    RoutineDBInit2.init39();
    RoutineDBInit2.init40();
    RoutineDBInit2.init41();
    RoutineDBInit2.init42();
    RoutineDBInit2.init43();
    RoutineDBInit2.init44();
    RoutineDBInit2.init45();
    RoutineDBInit2.init202();
    RoutineDBInit2.init203();
    RoutineDBInit2.init204();
    RoutineDBInit2.init206();
    RoutineDBInit2.init207();
    RoutineDBInit2.init208();
    RoutineDBInit2.init209();
    RoutineDBInit2.init210();
  }

}
