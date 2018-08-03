package com.hikki.sergey_natalenko.testapp.database;

public class AppDbSchema {
    public static final class UserTable{
        public static final String NAME = "users";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String EMAIL = "email";
            public static final String NAME = "name";
            public static final String PASSWORD = "password";
        }
    }

    public static final class NoteTable{
        public static final String NAME = "notes";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String AUTHOR_UUID = "author";
            public static final String TEXT = "text";
            public static final String HAS_FILES = "files";
        }
    }

    public static final class CommentTable{
        public static final String NAME = "comments";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String ADDRESS_OBJECT_UUID = "addressUuid";
            public static final String DATE = "date";
            public static final String AUTHOR_UUID = "author";
            public static final String TEXT = "text";
        }
    }

    public static final class MessageTable{
        public static final String NAME = "messages";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String ADDRESS_USER_UUID = "addressUuid";
            public static final String DATE = "date";
            public static final String AUTHOR_USER_UUID = "authorUuid";
            public static final String TEXT = "text";
        }
    }

    public static final class LikeTable{
        public static final String NAME = "likes";

        public static final class Cols{
            public static final String ADDRESS_OBJECT_UUID = "uuid";
            public static final String AUTHOR_UUID = "author";
        }

    }
    public static final class AttachedFilesTable{
        public static final String NAME = "files";

        public static final class Cols{
            public static final String ADDRESS_OBJECT_UUID = "uuid";
            public static final String NAME = "name";
            public static final String URL = "url";
        }

    }
}
