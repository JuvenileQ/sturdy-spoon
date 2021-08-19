package generate;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_user
 * @author 
 */
@Data
public class TUser implements Serializable {
    private Integer id;

    private String username;

    private String password;

    /**
     * 用户状态
            0 启用
            1 禁用
     */
    private Integer state;

    private String email;

    private String source;

    private Date createDate;

    private String remark;

    private static final long serialVersionUID = 1L;
}