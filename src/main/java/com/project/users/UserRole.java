package com.project.users;

/** assigns user_roles to the application users,
 * was initally designed for joint account users,
 * and to give admin permissions to delete accounts,
 * not fully implemented
 */
public enum UserRole {
    ADMIN, ACCOUNT_OWNER, JOINT_USER;
}
