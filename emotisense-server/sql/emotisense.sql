USE emotisense;

DROP TABLE USER_MOOD;
DROP TABLE USER_PHOTO;
DROP TABLE USER_FRIENDS;
DROP TABLE USER_DETAILS;

create table USER_DETAILS
(
	user_id varchar(50) PRIMARY KEY,
	name varchar(100) NOT NULL
);

create table USER_FRIENDS
(
	user_1 varchar(50),
	user_2 varchar(50),
	PRIMARY KEY (user_1, user_2),
	FOREIGN KEY (user_1) REFERENCES USER_DETAILS(user_id),
	FOREIGN KEY (user_2) REFERENCES USER_DETAILS(user_id)
);

create table USER_PHOTO
(
	photo_id integer PRIMARY KEY AUTO_INCREMENT,
	user_id varchar(50) NOT NULL,
	time_stamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	location varchar(500) NOT NULL, 
	FOREIGN KEY (user_id) REFERENCES USER_DETAILS(user_id)
);

create table USER_MOOD
(
	photo_id integer,
	friend_id varchar(50),
	mood integer NOT NULL,
	PRIMARY KEY (photo_id, friend_id),
	FOREIGN KEY (photo_id) REFERENCES USER_PHOTO(photo_id),
	FOREIGN KEY (friend_id) REFERENCES USER_DETAILS(user_id)
);
