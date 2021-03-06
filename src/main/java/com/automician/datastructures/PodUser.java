package com.automician.datastructures;

public class PodUser {

    public String userName;
    public String password; //for account Diaspora and for mail
    public String podLink;
    public String email;
    public String fullName;

    public PodUser(String userName, String password, String podLink, String email) {
        this.userName = userName;
        this.password = password;
        this.podLink = podLink;
        this.email = email;
        fullName = userName + "@" + podLink.replaceFirst("https://", "");
    }

    public String toString() {
        return userName;
    }
}
