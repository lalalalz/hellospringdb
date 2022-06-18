package springdb.hellospringdb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
    private String memberId;
    private int money;

    public Member() {
    }
}
