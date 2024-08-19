package app.nchu.auth;

public class UserInfo {

    private String loginId;
    private String name;
    private String email;
    private Boolean emailVerified;
    private String gender;
    private String department;
    private String grade;
    private String photo;

    public UserInfo() {
    }

    public UserInfo(String loginId, String name, String email, Boolean emailVerified,
                    String gender, String department, String grade, String photo) {
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.emailVerified = emailVerified;
        this.gender = gender;
        this.department = department;
        this.grade = grade;
        this.photo = photo;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
}
