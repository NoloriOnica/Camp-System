package Login;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class User implements Serializable{
    private String userID;
    private String name;
    private String email;
    private String faculty;
    private String userType;

    public User(String userId, String name, String email, String faculty, String userType) {
        this.userID = userId;
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.userType = userType;
    }

  
	public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getUserType() {
        return userType;
    }
}
