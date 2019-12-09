package Database;

import android.provider.BaseColumns;

public final class UserProfile {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserProfile() {
        //
    }

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "Users";

        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_PASSWORD = "Password";
        public static final String COLUMN_NAME_DATEOFBIRTH = "DateOfBirth";
        public static final String COLUMN_NAME_GENDER = "Gender";
    }
}
