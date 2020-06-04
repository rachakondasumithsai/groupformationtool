package com.advsdc.group2.login.services;

import com.advsdc.group2.login.model.UserCredentialsMock;
import com.advsdc.group2.login.models.UserCredentials;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    @Test
    public void validateUserTest() {
        LoginServiceMock loginServiceMock = new LoginServiceMock();
        UserCredentialsMock userCredentialsMock = new UserCredentialsMock();
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(userCredentialsMock.getUserId());
        userCredentials.setPassword(userCredentialsMock.getPassword());
        assertTrue(loginServiceMock.validateUser(userCredentials));
    }

    @Test
    public void generateJsonWebTokenTest(){
        LoginServiceImpl loginService = new LoginServiceImpl();
        assertNotNull(loginService.generateJsonWebToken("B00834696"));
    }
    @Test
    public void getRoleTest(){
        LoginServiceMock loginServiceMock = new LoginServiceMock();
        assertEquals(4, loginServiceMock.getRole("B00834696"));
    }
    @Test
    public void getCoursesTest(){
        LoginServiceMock loginServiceMock = new LoginServiceMock();
        assertNotNull(loginServiceMock.getCourses("B00834696"));
    }
}