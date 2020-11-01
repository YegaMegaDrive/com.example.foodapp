package com.example.to.enums;

public enum PermissionEnum {
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private String permission;

    PermissionEnum(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
