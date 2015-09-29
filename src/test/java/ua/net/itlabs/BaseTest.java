package ua.net.itlabs;

import com.codeborne.selenide.Configuration;
import datastructures.DiasporaAspect;
import datastructures.PodUser;
import org.junit.After;
import org.junit.BeforeClass;
import pages.*;
import ru.yandex.qatools.allure.annotations.Step;
import ua.net.itlabs.testDatas.DiasporaAspects;
import ua.net.itlabs.testDatas.Users;

import java.io.IOException;

public class BaseTest {
    @BeforeClass
    public static void clearDataBeforeTests() {
        Configuration.timeout = 20000;
        if (System.getProperty("withClearedDataOnStart").equals("true")) {
            //System.out.println("clearing data before tests");
            clearUserData(Users.ANA);
            clearUserData(Users.BOB);
            clearUserData(Users.ROB);
            clearUserData(Users.SAM);
        }

    }

    @After
    public void tearDown() throws IOException {
        Menu.ensureNewSignIn();
    }

    public static void clearUserData(PodUser user) {
        Diaspora.signInAs(user);

        NavBar.openStream();
        Feed.deleteAllPosts(user);

        NavBar.openTags();
        Tags.deleteAll();

        Menu.logOut();
    }

    @Step
    public void setupLinksFor(PodUser user, PodUser linkedUser, DiasporaAspect diasporaAspect, String followedTag, PodUser ... unlinkedUsers){
        //user relation setup
        Diaspora.signInAs(user);
        Menu.assertLoggedUser(user);
        //user have diasporaAspect relation with linkedUser
        Menu.search(linkedUser.fullName);
        People.assertPerson(linkedUser.fullName);
        People.ensureAspectForContact(diasporaAspect);
        //user have not any relation with unlinkedUser1
        for (PodUser unlinkedUser:unlinkedUsers) {
            Menu.search(unlinkedUser.fullName);
            People.assertPerson(unlinkedUser.fullName);
            People.ensureNoAspectsForContact();
            People.assertAspectsAreNotUsed();
        }
        //Addition followed tag
        Menu.openStream();
        NavBar.openTags();
        Tags.add(followedTag);
        Tags.assertExist(followedTag);
        Menu.logOut();
    }

    @Step
    public void setupLinksFor(PodUser user, String followedTag, PodUser ... unlinkedUsers){
        //user relation setup
        Diaspora.signInAs(user);
        Menu.assertLoggedUser(user);
        //user have not any relation with unlinkedUser1
        for (PodUser unlinkedUser:unlinkedUsers) {
            Menu.search(unlinkedUser.fullName);
            People.assertPerson(unlinkedUser.fullName);
            People.ensureNoAspectsForContact();
            People.assertAspectsAreNotUsed();
        }
        //Addition followed tag
        Menu.openStream();
        NavBar.openTags();
        Tags.add(followedTag);
        Tags.assertExist(followedTag);
        Menu.logOut();
    }

}
