package dataTest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.GlobalConstants;

import java.io.File;

public class EmployeeData {

    public static EmployeeData getEmployee(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(GlobalConstants.PROJECT_PATH + "\\src\\test\\java\\dataTest\\Employee.json"), EmployeeData.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("emailInvalid")
    private String emailInvalid;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailInvalid() {
        return password;
    }

    @JsonProperty("ContactDetail")
    ContactDetail contactDetail;

    public class ContactDetail{
        @JsonProperty("editFirstName")
        private String editFirstName;

        @JsonProperty("editLastName")
        private String editLastName;

        @JsonProperty("editGender")
        private String editGender;

        @JsonProperty("editMaritalStatus")
        private String editMaritalStatus;

        @JsonProperty("editNationality")
        private  String editNationality;
    }

    public String getEditFirstName() {
        return contactDetail.editFirstName;
    }

    public String getEditLastName() {
        return contactDetail.editLastName;
    }

    public String getEditGender() {
        return contactDetail.editGender;
    }

    public String getEditMaritalStatus() {
        return contactDetail.editMaritalStatus;
    }

    public String getEditNationality() {
        return contactDetail.editNationality;
    }


}
