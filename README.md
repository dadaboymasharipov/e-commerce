# Web ecommerce application

<p>This is a web ecommerce application that helps to buy and sell your products online.</p>
<p> It has only 2 roles when the system starts : Owner and User. Later on Owner can create other roles if needed. Owner has all the permissions of the
system and user only has necessary permissions to use application</p>
<h3>Setup:</h3>
<ul>
    <li>First enter database username in application.yml, or you can use application.properties. Just make sure you uncomment properties</li>
    <li>Second enter component.DataLoader class and enter your email for owner creation part</li>
    <li>Third enter configuration.SecurityConfig class and enter email and app password in javaMailSender() method</li>
    <li>After running the program change following properties:<br> 
        <ul>
            <li>spring.jpa.hibernate.ddl-auto -> update</li>
            <li>spring.sql.init.mode=always -> never</li>
        </ul>
    </li>
    <li>Change all tokens and id</li>
</ul>
<h3>Here are the permissions of system:</h3>
<ul>
    <li>CAN_ASSIGN_ROLE_TO_USER</li>
    <li>EDIT_MY_USER</li>
    <li>DELETE_USER</li>
    <li>BLOCK_USER</li>
    <li>UNBLOCK_USER</li>
    <li>GET_BLOCKED_USERS</li>
    <li>CREATE_CATEGORY</li>
    <li>EDIT_CATEGORY</li>
    <li>DELETE_CATEGORY</li>
    <li>ADD_ROLE</li>
    <li>EDIT_ROLE</li>
    <li>VIEW_ROLES</li>
    <li>DELETE_ROLE</li>
    <li>ADD_PRODUCT</li>
    <li>EDIT_PRODUCT</li>
    <li>DELETE_MY_PRODUCT</li>
    <li>DELETE_ANY_PRODUCT</li><br>
</ul>
<h3>And every User has:</h3>
<ul>
    <li>ADD_PRODUCT</li>
    <li>DELETE_MY_PRODUCT</li>
    <li>EDIT_PRODUCT</li>
    <li>EDIT_MY_USER</li>
</ul>

You cannot add any products unless you are a user

## API ENDPOINTS

| Method | Endpoint                             | Access Control                                  | Description                                                       | Request Body                                  |
|--------|--------------------------------------|-------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------|
| POST   | /api/auth/register                   | anyone                                          | To register into a system.                                        | RegisterDto                                   |
| GET    | /api/auth/verifyEmail                | anyone                                          | To verify user's email. Link will be sent as an email message     | email and email code as a param               |
| POST   | /api/auth/login                      | anyone                                          | To sign into the system                                           | LoginDto                                      |
| POST   | /api/category/add                    | users with a privilege: CREATE_CATEGORY         | To add a category to the system                                   | CategoryDto                                   |
| PUT    | /api/category/edit/{id}              | users with a privilege: EDIT_CATEGORY           | To edit the category with given "id"                              | CategoryDto                                   |
| DELETE | /api/category/delete/{id}            | users with a privilege: DELETE_CATEGORY         | To delete category with given "id" from the system                | none                                          |
| GET    | /api/open/getCategory/{id}           | anyone                                          | To get the category with given "id"                               | none                                          |
| GET    | /api/open/getCategories              | anyone                                          | To get all categories in the system                               | none                                          |
| GET    | /api/open/getProduct/{id}            | anyone                                          | To get the product with given "id"                                | none                                          |
| GET    | /api/open/getProducts                | anyone                                          | To get all products page by pge                                   | page number and page size as a param          |
| GET    | /api/open/getProductsByOwner/{id}    | anyone                                          | To get all products of the user with given "id", page by page     | page number and page size as a param          |
| GET    | /api/open/getProductsByCategory/{id} | anyone                                          | To get all products of the category with given "id", page by page | page number and page size as a param          |
| GET    | /api/open/getUser/{id}               | anyone                                          | To get information of the user with given "id"                    | none                                          |
| GET    | /api/open/getUsers                   | anyone                                          | To get information of the users                                   | page number and page size as a param          |
| POST   | /api/product/add                     | all users                                       | To add a product                                                  | attachments as a MultipartFile and ProductDto |
| PUT    | /api/product/edit/{id}               | all users                                       | To edit user's own product                                        | attachments as a MultipartFile and ProductDto |
| DELETE | /api/product/deleteMy/{id}           | all users                                       | To delete user's own product with given id                        | none                                          |
| DELETE | /api/product/deleteAny/{id}          | users with a privilege: DELETE_ANY_PRODUCT      | To delete anyone's product with given "id" from system            | none                                          |
| POST   | /api/role/addRole                    | users with a privilege: ADD_ROLE                | To add a role to system                                           | RoleDto                                       |
| PUT    | /api/role/editRole/{id}              | users with a privilege: EDIT_ROLE               | To edit role with given "id"                                      | RoleDto                                       |
| DELETE | /api/role/deleteRole/{id}            | users with a privilege: DELETE_ROLE             | To delete the role with given "id"                                | none                                          |
| GET    | /api/role/getRole/{id}               | users with a privilege: VIEW_ROLES              | To get the role with given "id"                                   | none                                          |
| GET    | /api/role/getRoles                   | users with a privilege: VIEW_ROLES              | To get all roles of the system                                    | none                                          |
| PUT    | /api/user/editUser/{id}              | all users                                       | To edit user's own information                                    | UserDto                                       |
| DELETE | /api/user/deleteUser/{id}            | users with a privilege: DELETE_USER             | To delete the user with given "id"                                | none                                          |
| DELETE | /api/user/blockUser                  | users with a privilege: BLOCK_USER              | To block the user from system                                     | BlockUserDto                                  |
| GET    | /api/user/unblockUser                | users with a privilege: UNBLOCK_USER            | To unblock the email from system                                  | email as a param                              |
| GET    | /api/user/getBlockedUsers            | users with a privilege: GET_BLOCKED_USERS       | To get all blocked users of the system page by page               | page number and page size as a param          |
| PUT    | /api/user/assignRoleToUser           | users with a privilege: CAN_ASSIGN_ROLE_TO_USER | To give a role to user                                            | userId and roleId as a param                  |
| GET    | /api/user/verify                     | all users                                       | Whenever email is changed user needs to verify again              | email and emailCode as a param                |

