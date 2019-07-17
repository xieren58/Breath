package com.zkp.zkplib.sign;

/**
 * Created b Zwp on 2017/12/1.
 */

public final class SignAssist {

    /**
     * 添加一项或多项权限
     * ‘|’ ：有1则为1，其余为0
     *
     * @param currentPermission 当前的权限
     * @param permission        需要添加的权限
     */
    public synchronized static int addSign(int currentPermission, int permission) {
        return currentPermission | permission;
    }

    /**
     * 删除一项或多项权限
     * ‘&’：都为1则为1，其余为0
     *
     * @param currentPermission 当前的权限
     * @param permission        需要删除的权限
     */
    public synchronized static int deleteSign(int currentPermission, int permission) {
        return currentPermission & ~permission;
    }

    /**
     * 是否拥某些权限
     *
     * @param currentPermission 当前的权限
     * @param permission        查询的权限
     */
    public synchronized static boolean isAllowSign(int currentPermission, int permission) {
        return (currentPermission & permission) == permission;
    }
}
