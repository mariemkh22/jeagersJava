package entities;

public class User {
    private int id;
    private String email,roles,password,full_name,phone_number,date_of_birth,feedback,code,feedbackType,feedbackStatus;
    private boolean enabled;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", email='" + roles + '\'' +
                ", full_name='" + full_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                '}';
    }

    public User(){
    }

    public User(int id,String email,String full_name, String phone_number, String date_of_birth) {
        this.id=id;
        this.email = email;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
    }

    public User(String email,  String password, String roles,String full_name, String phone_number, String date_of_birth) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
    }


    public User(int id, String email, String password, String roles, String full_name, String phone_number, String date_of_birth,String feedback, String feedbackType, String feedbackStatus) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.feedback = feedback;
        this.feedbackType = feedbackType;
        this.feedbackStatus = feedbackStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
