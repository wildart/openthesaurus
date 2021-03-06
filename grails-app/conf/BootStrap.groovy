import com.vionto.vithesaurus.*

class BootStrap {

     def init = { servletContext ->
       final String ADMIN_USERID = "admin"
       final String ADMIN_PASSWORD = UserController.md5sum("admin", UserController.DEFAULT_SALT)
       final String ADMIN_PERM = "admin"
       // create a default user:
       if (!ThesaurusUser.findByUserId(ADMIN_USERID)) {
         log.info("Creating initial user: " + ADMIN_USERID)
         def user = new ThesaurusUser(ADMIN_USERID, ADMIN_PASSWORD, UserController.DEFAULT_SALT, ADMIN_PERM)
         // fake a confirmation (usually done by email):
         user.confirmationDate = new Date()
         if (!user.validate()) {
           log.error("User validation failed: ${user.errors}")
         }
         user.save()
       } else {
         log.info("Default admin user already exists")
       }
       // create languages:
       if (!Language.findByShortForm("en")) {
           log.info("Creating language 'en'")
           new Language("English", "en").save()
       }
       if (!Language.findByShortForm("de")) {
           log.info("Creating language 'de'")
           new Language("German", "de").save()
       }

       //
       // the following items have been added for OpenThesaurus: 
       //
       
       if (!Category.findByCategoryName("other")) {
         // add default category so there's at least one category available:
         Category cat = new Category("other")
         cat.isDisabled = false
         cat.save()
       }
       if (!TermLinkType.findByLinkName("Antonym")) {
         // add default section so there's at least one section available:
         TermLinkType termLinkType = new TermLinkType("Antonym")
         termLinkType.otherDirectionLinkName = "Antonym"
         termLinkType.verbName = "ist das Antonym von"
         boolean created = termLinkType.save()
         assert(created)
         log.info("Created " + termLinkType)
       }
     }
     
     def destroy = {
     }
     
}
