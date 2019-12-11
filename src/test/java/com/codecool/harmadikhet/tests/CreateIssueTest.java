package com.codecool.harmadikhet.tests;

import com.codecool.harmadikhet.pages.CreateIssuePage;
import com.codecool.harmadikhet.pages.HomePage;
import com.codecool.harmadikhet.pages.IssueDetailsPage;
import com.codecool.harmadikhet.pages.LogInPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateIssueTest extends BaseTest {
    private LogInPage logInPage;
    private CreateIssuePage createIssuePage;

    @BeforeEach
    public void setupTest() {
        createIssuePage = new CreateIssuePage(driver);
        logInPage = new LogInPage(driver);
        logInPage.logIn(username, password);
    }

    @Test
    public void testCreateIssue() {
        String expectedUUID = createIssuePage.getUUIDinString();
        IssueDetailsPage issueDetailsPage = createIssuePage.createIssue();
        assertEquals(expectedUUID, issueDetailsPage.getIssueSummary());
        issueDetailsPage.deleteIssue();
    }

    @ParameterizedTest
    @CsvSource({
            "Coala", "Toucan", "Jeti"
    })
    public void testSpecificIssueTypesInSpecificProjects(String projectName) {
        List<String> expectedIssueTypes = createExpectedIssueTypes("Story", "Task", "Bug");

        HomePage homePage = new HomePage(driver);
        homePage.clickCreateButton();
        CreateIssuePage createIssuePage = new CreateIssuePage(driver);
        createIssuePage.fillProjectNameField(projectName);
        List<String> actualIssueTypes = createIssuePage.getAvailableIssueTypes();

        for (String expectedIssueType : expectedIssueTypes) {
            assertThat(actualIssueTypes, hasItem(expectedIssueType));
        }

    }

    public List<String> createExpectedIssueTypes(String...issueTypes) {
        return new ArrayList<>(Arrays.asList(issueTypes));
    }

}
