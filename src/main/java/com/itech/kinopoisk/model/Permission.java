package com.itech.kinopoisk.model;

public enum Permission {

    USERS_PERMISSION("user:permission"),
    MODERATORS_PERMISSION("moderator:permission"),
    ADMINS_PERMISSION("admin:permission");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
