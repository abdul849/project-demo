package exmple.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingUpDto {
    private String name;
    private String username;
    private String password;
    private String email;
    private String roleType;
}
