CREATE TABLE "user_table" (
                        "id" bigint PRIMARY KEY NOT NULL auto_increment,
                        "name" varchar(100) NOT NULL,
                        "use_yn" varchar(1) NOT NULL,
                        "created_at" datetime NOT NULL,
                        "modified_at" datetime NOT NULL
);

CREATE TABLE "lecture_table" (
                           "id" bigint PRIMARY KEY NOT NULL auto_increment,
                           "name" varchar(300) NOT NULL,
                           "status" varchar(50) NOT NULL,
                           "use_yn" varchar(1) NOT NULL,
                           "created_at" datetime NOT NULL,
                           "modified_at" datetime NOT NULL
);

CREATE TABLE "lecture_option_table" (
                                  "id" bigint PRIMARY KEY NOT NULL auto_increment,
                                  "lecture_id" bigint NOT NULL,
                                  "register_begin_at" datetime NOT NULL,
                                  "register_end_at" datetime NOT NULL,
                                  "lecture_datetime" datetime NOT NULL,
                                  "capacity_limit" int NOT NULL,
                                  "use_yn" varchar(1) NOT NULL,
                                  "created_at" datetime NOT NULL,
                                  "modified_at" datetime NOT NULL
);

CREATE TABLE "lecture_change_history_table" (
                                          "id" bigint PRIMARY KEY NOT NULL auto_increment,
                                          "lecture_id" bigint NOT NULL,
                                          "lecture_name" varchar(300) NOT NULL,
                                          "lecture_option_id" bigint NOT NULL,
                                          "status" varchar(50) NOT NULL,
                                          "register_begin_at" datetime NOT NULL,
                                          "register_end_at" datetime NOT NULL,
                                          "lecture_datetime" datetime NOT NULL,
                                          "capacity_limit" int NOT NULL,
                                          "created_at" datetime NOT NULL,
                                          "modified_at" datetime NOT NULL
);

CREATE TABLE "user_lecture_register_table" (
                                         "id" bigint PRIMARY KEY NOT NULL auto_increment,
                                         "user_id" bigint NOT NULL,
                                         "lecture_id" bigint NOT NULL,
                                         "lecture_option_id" bigint NOT NULL,
                                         "use_yn" varchar(1) NOT NULL,
                                         "created_at" datetime NOT NULL,
                                         "modified_at" datetime NOT NULL
);

CREATE TABLE "user_lecture_register_history_table" (
                                                 "id" bigint PRIMARY KEY NOT NULL auto_increment,
                                                 "user_lecture_register_id" bigint NOT NULL,
                                                 "user_id" bigint NOT NULL,
                                                 "lecture_id" bigint NOT NULL,
                                                 "lecture_option_id" bigint NOT NULL,
                                                 "lecture_datetime" datetime NOT NULL,
                                                 "type" varchar(50) NOT NULL,
                                                 "created_at" datetime NOT NULL,
                                                 "modified_at" datetime NOT NULL
);

COMMENT ON COLUMN "lecture_table"."status" IS 'OPEN | CLOSED';

COMMENT ON COLUMN "user_lecture_register_history_table"."type" IS 'APPLIED | CANCELLED';

ALTER TABLE "lecture_option_table" ADD FOREIGN KEY ("lecture_id") REFERENCES "lecture_table" ("id");

ALTER TABLE "lecture_change_history_table" ADD FOREIGN KEY ("lecture_id") REFERENCES "lecture_table" ("id");

ALTER TABLE "user_lecture_register_history_table" ADD FOREIGN KEY ("user_lecture_register_id") REFERENCES "user_lecture_register_table" ("id");