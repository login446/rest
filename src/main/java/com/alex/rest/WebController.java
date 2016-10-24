package com.alex.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 19.10.2016.
 */
@RestController
public class WebController {
    @Autowired
    private AuthDataDao authDataDao;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class BadRequestException extends RuntimeException {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class NotFoundException extends RuntimeException {
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public class ConflictException extends RuntimeException {
    }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return authDataDao.getAllUsers();
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public User addNewUser(@RequestParam("name") String name) throws IOException {
        if (name.isEmpty()) {
            throw new BadRequestException();
        }
        if (authDataDao.getUser(name) != null) {
            throw new ConflictException();
        }
        return authDataDao.addNewUser(name);
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public void delUser(@RequestParam("name") String name) {
        if (name.isEmpty()) {
            throw new BadRequestException();
        }
        User user = authDataDao.getUser(name);
        if (authDataDao.getUser(name) == null) {
            throw new NotFoundException();
        }
        authDataDao.delUser(name);
    }

    @RequestMapping(value = "/user/name", method = RequestMethod.PATCH)
    public void rename(@RequestParam("name") String name, @RequestParam("newName") String newName) {
        if (name.isEmpty()) {
            throw new BadRequestException();
        }
        if (newName.isEmpty()) {
            throw new BadRequestException();
        }
        if (authDataDao.getUser(name) == null) {
            throw new NotFoundException();
        }
        if (authDataDao.getUser(newName) != null) {
            throw new ConflictException();
        }
        authDataDao.rename(name, newName);
    }

    @RequestMapping(value = "/user/score", method = RequestMethod.PATCH)
    public void reScore(@RequestParam("name") String name, @RequestParam("score") String score) {
        int scoreInt;
        if (name.isEmpty()) {
            throw new BadRequestException();
        }

        try {
            scoreInt = Integer.parseInt(score);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }

        if (authDataDao.getUser(name) == null) {
            throw new NotFoundException();
        }
        authDataDao.reScore(name, scoreInt);
    }
}
