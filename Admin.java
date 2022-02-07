
package de.course.management;


public class Admin {
    
    private int  userid;
     String username,password,name,title;

    public Admin (int userid, String username, String password, String name, String title) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.name = name;
        this.title = title;
    }

    public int getuserid() {
        return userid;
    }

    public void setuserid(int userid) {
        this.userid = userid;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }
}