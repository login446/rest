package com.alex.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by admin on 24.10.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class WebControllerTest2 {
    @InjectMocks
    private WebController webController;
    @Mock
    private AuthDataDao authDataDao;

    @Test
    public void getAllUsers() throws Exception {
        authDataDao.getAllUsers();
    }

    @Test
    public void addNewUser() throws Exception {
        String name = "name";
        when(authDataDao.getUser("name")).thenReturn(new User());
        try {
            webController.addNewUser(name);
            fail();
        } catch (WebController.ConflictException ex) {
            name = "";
        }
        try {
            webController.addNewUser(name);
            fail();
        } catch (WebController.BadRequestException ex) {
            name = "adfaf";
        }
        webController.addNewUser(name);
    }

    @Test
    public void delUser() throws Exception {
        String name = "";
        when(authDataDao.getUser("name")).thenReturn(new User());
        try {
            webController.delUser(name);
            fail();
        } catch (WebController.BadRequestException ex) {
            name = "adfa";
        }
        try {
            webController.delUser(name);
            fail();
        } catch (WebController.NotFoundException ex) {
            name = "name";
        }
        webController.delUser(name);

    }

    @Test
    public void rename() throws Exception {
        String name = "name", emptyName = "", notFoundName = "notFoundName";
        String newName = "newName", freeName = "freeName";
        when(authDataDao.getUser(name)).thenReturn(new User());
        when(authDataDao.getUser(newName)).thenReturn(new User());
        try {
            webController.rename(emptyName, freeName);
            fail();
        } catch (WebController.BadRequestException ex) {
        }
        try {
            webController.rename(name, emptyName);
            fail();
        } catch (WebController.BadRequestException ex) {
        }
        try {
            webController.rename(notFoundName, freeName);
            fail();
        } catch (WebController.NotFoundException ex) {
        }
        try {
            webController.rename(name, newName);
            fail();
        } catch (WebController.ConflictException ex) {
        }
        webController.rename(name, freeName);
    }

    @Test
    public void reScore() throws Exception {
        String name = "name", emptyName = "", notFoundName = "notFoundName";
        String score = "22", scoreNotInt = "faf33";
        when(authDataDao.getUser(name)).thenReturn(new User());
        try {
            webController.reScore(emptyName, score);
            fail();
        } catch (WebController.BadRequestException ex) {
        }
        try {
            webController.reScore(name, scoreNotInt);
            fail();
        } catch (WebController.BadRequestException ex) {
        }
        try {
            webController.reScore(notFoundName, score);
            fail();
        } catch (WebController.NotFoundException ex) {
        }
        webController.reScore(name, score);
    }

}