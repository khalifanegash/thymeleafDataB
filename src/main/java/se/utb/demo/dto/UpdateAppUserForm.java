package se.utb.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class UpdateAppUserForm {

        @Positive
        private int userId;
        @NotBlank(message = "First name is mandatory")
        @Size(min = 2, max = 255, message = "First name need to have 2 or more letters")
        private String firstName;
        @NotBlank(message = "Last name is mandatory")
        @Size(min = 2, max = 255, message = "Last name need to have 2 or more letters")
        private String lastName;
        @NotBlank(message = "Email is mandatory")
        @Email(regexp = "^(\\D)+(\\w)*((\\.(\\w)+)?)+@(\\D)+(\\w)*((\\.(\\D)+(\\w)*)+)?(\\.)[a-z]{2,}$", message = "Your email is invalid")
        private String email;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }
