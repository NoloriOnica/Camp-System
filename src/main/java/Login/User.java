package Login;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing a user in the system.
 */
public class User implements Serializable{
    private String userID;
    private String name;
    private String email;
    private String faculty;
    private String userType;

    /**
     * Constructor to create a User object.
     *
     * @param userId    The unique user ID.
     * @param name      The name of the user.
     * @param email     The email of the user.
     * @param faculty   The faculty of the user.
     * @param userType  The type of user (e.g., student or staff).
     */
    
    public User(String userId, String name, String email, String faculty, String userType) {
        this.userID = userId;
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.userType = userType;
    }

    /**
     * Retrieves the user's ID.
     *
     * @return The user's ID.
     */
    
	public String getUserID() {
        return userID;
    }

	/**
     * Retrieves the user's name.
     *
     * @return The user's name.
     */
	
    public String getName() {
        return name;
    }

    /**
     * Retrieves the user's email.
     *
     * @return The user's email.
     */
    
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the user's faculty.
     *
     * @return The user's faculty.
     */
    
    public String getFaculty() {
        return faculty;
    }

    /**
     * Retrieves the user's type (student or staff).
     *
     * @return The user's type.
     */
    
    public String getUserType() {
        return userType;
    }
}
