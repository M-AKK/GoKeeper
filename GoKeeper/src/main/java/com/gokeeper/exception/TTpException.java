package com.gokeeper.exception;

import com.gokeeper.enums.ResultEnum;
import lombok.Getter;

/**
 * 抛出异常类，最后由一个拦截器拦截返回给前端错误信息
 * Created by Akk_Mac
 * Date: 2017/10/1 21:05
 */
@Getter
public class TTpException extends RuntimeException {
    private Integer code;

    public TTpException(ResultEnum resultEnum) {
        //把枚举中自己定义的message传到父类的构造方法里,相当于覆盖message
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    //而这个是需要自己去填写code的新的meg，不一定是枚举中的模糊的说法，可以把具体的错误信息信使出来
    public TTpException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
