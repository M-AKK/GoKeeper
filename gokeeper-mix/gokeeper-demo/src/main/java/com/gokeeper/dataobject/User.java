package com.gokeeper.dataobject;

/**
 * Created by Akk_Mac
 * Date: 2017/10/4 20:57
 */
import java.security.Principal;

/**
 *
 * @ClassName: User
 * @Description: 客户端用户
 * @author cheng
 * @date 2017年9月29日 下午3:02:54
 */
public final class User implements Principal {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

