package com.example.appecommerce.entity.template;

/**
 * These are thew permissions that exists in the system
 */
public enum Permissions {
    CAN_ASSIGN_ROLE_TO_USER, //Gives permission to assign role to others
    EDIT_MY_USER,//Access to edit your own user's name, username, email, password
    DELETE_USER, //Gives access to delete any user in the system
    BLOCK_USER, //Gives access to block users from system
    UNBLOCK_USER, //Can unblock Users from blocked list
    GET_BLOCKED_USERS, //Can view blocked users' list
    CREATE_CATEGORY,//Can add a new category to  the system
    EDIT_CATEGORY,//Can edit any category of the system
    DELETE_CATEGORY,//Can delete any category of the system
    ADD_ROLE,//Gives permission to create a new role for the system
    EDIT_ROLE,//Can edit any role of the system
    VIEW_ROLES,//Gives permission to see all roles of the system
    DELETE_ROLE,//Can delete any role of the system
    ADD_PRODUCT,//Gives access to adding their product
    EDIT_PRODUCT,//Can edit their own product
    DELETE_MY_PRODUCT,//Can delete their own product
    DELETE_ANY_PRODUCT//Gives access to delete any product of the system in case it is illegal
}
