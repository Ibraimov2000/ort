export default class User {
    constructor(id = null, firstName = '', lastName = '', email = '', password = '', role = '') {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}