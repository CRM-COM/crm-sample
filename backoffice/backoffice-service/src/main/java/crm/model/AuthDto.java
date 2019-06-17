package crm.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
