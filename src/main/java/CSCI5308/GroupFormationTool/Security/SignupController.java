package CSCI5308.GroupFormationTool.Security;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.AccessControl.User;

@Controller
public class SignupController {
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String PASSWORD_CONFIRMATION = "passwordConfirmation";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String EMAIL = "email";

    @GetMapping("/signup")
    public String displaySignup(Model model) {
        SystemConfig.instance().getPasswordEnforcementPolicy();
        model.addAttribute("pwdPolicies", PasswordEnforcementPolicy.getToDisplayPolicies());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView processSignup(@RequestParam(name = USERNAME) String bannerID,
                                      @RequestParam(name = PASSWORD) String password,
                                      @RequestParam(name = PASSWORD_CONFIRMATION) String passwordConfirm,
                                      @RequestParam(name = FIRST_NAME) String firstName, @RequestParam(name = LAST_NAME) String lastName,
                                      @RequestParam(name = EMAIL) String email) {
        boolean success = false;
        if (User.isBannerIDValid(bannerID) && User.isEmailValid(email) && User.isFirstNameValid(firstName)
                && User.isLastNameValid(lastName) && password.equals(passwordConfirm)
                && User.isPasswordValid(password, SystemConfig.instance().getPasswordEnforcementPolicy())) {
            User u = new User();
            u.setBannerID(bannerID);
            u.setPassword(password);
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setEmail(email);
            IUserPersistence userDB = SystemConfig.instance().getUserDB();
            IPasswordEncryption passwordEncryption = SystemConfig.instance().getPasswordEncryption();
            success = u.createUser(userDB, passwordEncryption, null);
            if (success) {
                IUserHistroyRelationshipPersistence userHistoryDb = SystemConfig.instance()
                        .getUserHistoryRelationshipDB();

                System.out.println("From controller : " + u.insertPasswordtoHistory(userHistoryDb));
            }
        }
        ModelAndView m;
        if (success) {
            m = new ModelAndView("login");
        } else {
            m = new ModelAndView("signup");
            m.addObject("errorMessage", "Invalid data, please check your values.");
        }
        return m;
    }
}