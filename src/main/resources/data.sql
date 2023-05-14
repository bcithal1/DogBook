----HUMANS----
INSERT INTO USERS (EMAIL, FULL_NAME)
VALUES ('HHill53@aol.com', 'Hank Rutherford Hill');

INSERT INTO USERS (EMAIL, FULL_NAME)
VALUES ('lonleysolider@yahoo.com', 'Bill Dautreive');

INSERT INTO USERS (EMAIL, FULL_NAME)
VALUES ('RShackleford@DaleDeadbug.ru', 'Rusty Shackleford');

INSERT INTO USERS (EMAIL, FULL_NAME)
VALUES ('numba1sub4ever@aol.com', 'Peggy Hill');

INSERT INTO USERS (EMAIL, FULL_NAME)
VALUES ('faithful4ever@yahoo.com', 'Nancy Gribble');

INSERT INTO USERS (DISPLAY_NAME, EMAIL, FULL_NAME)
VALUES ('Lixxark', 'bcithal2@gmail.com', 'Brian Ithal');


----CREATE USER_PROFILES----
INSERT INTO USER_PROFILE(ID, ABOUT_SECTION, PROFILE_PHOTO_ID)
VALUES (1, 'I sell propane and propane accessories', 8);

INSERT INTO USER_PROFILE(ID, ABOUT_SECTION, PROFILE_PHOTO_ID)
VALUES (2, 'I am so lonely. I miss LANORE', 9);

INSERT INTO USER_PROFILE(ID, ABOUT_SECTION, PROFILE_PHOTO_ID)
VALUES (3, 'My god Bill, you are so lonely!', 10);

INSERT INTO USER_PROFILE(ID, ABOUT_SECTION, PROFILE_PHOTO_ID)
VALUES (4, 'Estoy aquí para comer muchas de tus perros', 11);

INSERT INTO USER_PROFILE(ID, ABOUT_SECTION, PROFILE_PHOTO_ID)
VALUES (5, 'Weather Girl News Anchor at Channel 84', 12);

INSERT INTO USER_PROFILE(ID, ABOUT_SECTION, PROFILE_PHOTO_ID)
VALUES (6, 'I am only here so users can test stuff', 14);

----USER_FRIENDSHIPS----
INSERT INTO FRIENDSHIP(CREATE_DATE, PRIMARY_USER_ID, SECONDARY_USER_ID)
VALUES ('2023-05-05', 6, 1);

INSERT INTO FRIENDSHIP(CREATE_DATE, PRIMARY_USER_ID, SECONDARY_USER_ID)
VALUES ('2023-05-05', 6, 2);

INSERT INTO FRIENDSHIP(CREATE_DATE, PRIMARY_USER_ID, SECONDARY_USER_ID)
VALUES ('2023-05-05', 6, 3);

INSERT INTO FRIENDSHIP(CREATE_DATE, PRIMARY_USER_ID, SECONDARY_USER_ID)
VALUES ('2023-05-05', 6, 4);

INSERT INTO FRIENDSHIP(CREATE_DATE, PRIMARY_USER_ID, SECONDARY_USER_ID)
VALUES ('2023-05-05', 6, 5);

INSERT INTO FRIENDSHIP(CREATE_DATE, PRIMARY_USER_ID, SECONDARY_USER_ID)
VALUES ('2023-05-05', 1, 2);

INSERT INTO FRIENDSHIP(CREATE_DATE, PRIMARY_USER_ID, SECONDARY_USER_ID)
VALUES ('2023-05-05', 1, 4);

INSERT INTO FRIENDSHIP(CREATE_DATE, PRIMARY_USER_ID, SECONDARY_USER_ID)
VALUES ('2023-05-05', 2, 4);

INSERT INTO FRIENDSHIP(CREATE_DATE, PRIMARY_USER_ID, SECONDARY_USER_ID)
VALUES ('2023-05-05', 3, 5);

----GOOD_BOYES_AND_GIRLES----
--breed_Id is fake, and won't work with API correctly--
INSERT INTO Dog (name, age, breed, breed_Id, sex, altered, size, weight_Lbs)
VALUES ('Buddy', 3, 'Golden Retriever', 1, 0, TRUE, 3, 70);

INSERT INTO Dog (name, age, breed, breed_Id, sex, altered, size, weight_Lbs)
VALUES ('Bella', 0, 'Labrador Retriever', 2, 1, TRUE, 3, 65);

INSERT INTO Dog (name, age, breed, breed_Id, sex, altered, size, weight_Lbs)
VALUES ('Max', 4, 'German Shepherd', 3, 0, TRUE, 4, 1195);

INSERT INTO Dog (name, age, breed, breed_Id, sex, altered, size, weight_Lbs)
VALUES ('Daisy', 2, 'Poodle', 4, 1, TRUE, 1, 10);

INSERT INTO Dog (name, age, breed, breed_Id, sex, altered, size, weight_Lbs)
VALUES ('Charlie', 3, 'Beagle', 5, 0, FALSE, 2, 30);

INSERT INTO Dog (name, age, breed, breed_Id, sex, altered, size, weight_Lbs)
VALUES ('Molly', 6, 'Bulldog', 6, 1, TRUE, 2, 40);

INSERT INTO Dog (name, age, breed, breed_Id, sex, altered, size, weight_Lbs)
VALUES ('Ladybird', 16, 'Georgia Bloodhound', 7, 0, FALSE, 3, 115);

INSERT INTO Dog (name, age, breed, breed_Id, sex, altered, size, weight_Lbs)
VALUES ('Rika', 2, 'Australian Cattle Dog', 8, 1, TRUE, 2, 60);

----DOG_PROFILES----
INSERT INTO Dog_Profile (profile_Photo_Id, banner_Photo_Id, dog_Id, temperament, bio)
VALUES (1, 1, 1, 'Friendly', 'Buddy is a friendly and playful dog, who loves to run and fetch.');

INSERT INTO Dog_Profile (profile_Photo_Id, banner_Photo_Id, dog_Id, temperament, bio)
VALUES (2, 2, 2, 'Energetic', 'Bella is an energetic and loving Labrador Retriever who enjoys swimming and playing fetch.');

INSERT INTO Dog_Profile (profile_Photo_Id, banner_Photo_Id, dog_Id, temperament, bio)
VALUES (3, 3, 3, 'Protective', 'Max is a loyal and protective German Shepherd, he is definitely not a bear.');

INSERT INTO Dog_Profile (profile_Photo_Id, banner_Photo_Id, dog_Id, temperament, bio)
VALUES (4, 4, 4, 'Intelligent', 'Daisy is an intelligent and friendly Poodle who loves to learn new tricks and enjoys a good grooming session.');

INSERT INTO Dog_Profile (profile_Photo_Id, banner_Photo_Id, dog_Id, temperament, bio)
VALUES (5, 5, 5, 'Curious', 'Charlie is a curious and energetic Beagle, always ready to explore the world and play with other dogs.');

INSERT INTO Dog_Profile (profile_Photo_Id, banner_Photo_Id, dog_Id, temperament, bio)
VALUES (6, 6, 6, 'Affectionate', 'Molly is an affectionate and gentle Bulldog, she loves to cuddle and get belly rubs.');

INSERT INTO Dog_Profile (profile_Photo_Id, banner_Photo_Id, dog_Id, temperament, bio)
VALUES (7, 7, 7, 'Old', 'Lady Bird is named after the wife of former President Lyndon B. Johnson.');

INSERT INTO Dog_Profile (profile_Photo_Id, banner_Photo_Id, dog_Id, temperament, bio)
VALUES (13, 8, 8, 'TOO Energetic', 'She is my baby and hunts turkeys and dogs smaller than her.');

----DOG_FRIEND_REQUEST----
INSERT INTO DOG_FRIEND_REQUEST (Sender_ID, Receiver_ID, create_Date)
Values (1, 2, '2023-05-05');

----DOG_FRIENDSHIP----
INSERT INTO DOG_FRIENDSHIP (create_Date, primary_User_Id, secondary_User_Id)
VALUES ('2023-05-05', 1, 3);

INSERT INTO DOG_FRIENDSHIP (create_Date, primary_User_Id, secondary_User_Id)
VALUES ('2023-05-05', 8, 1);

INSERT INTO DOG_FRIENDSHIP (create_Date, primary_User_Id, secondary_User_Id)
VALUES ('2023-05-05', 8, 2);

INSERT INTO DOG_FRIENDSHIP (create_Date, primary_User_Id, secondary_User_Id)
VALUES ('2023-05-05', 8, 3);

INSERT INTO DOG_FRIENDSHIP (create_Date, primary_User_Id, secondary_User_Id)
VALUES ('2023-05-05', 8, 4);

INSERT INTO DOG_FRIENDSHIP (create_Date, primary_User_Id, secondary_User_Id)
VALUES ('2023-05-05', 8, 5);

INSERT INTO DOG_FRIENDSHIP (create_Date, primary_User_Id, secondary_User_Id)
VALUES ('2023-05-05', 8, 6);

INSERT INTO DOG_FRIENDSHIP (create_Date, primary_User_Id, secondary_User_Id)
VALUES ('2023-05-05', 8, 7);


----DOG_OWNERS----
INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (1, 1, 0);

INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (2, 1, 1);

INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (2, 2, 0);

INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (3, 3, 0);

INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (4, 4, 0);

INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (5, 5, 0);

INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (5, 6, 0);

INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (1, 7, 0);

INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (4, 7, 1);

INSERT INTO Dog_Owner (user_Id, dog_Id, access_Level)
VALUES (6, 8, 0);

INSERT INTO EVENT (EVENT_DESCRIPTION, EVENT_TITLE, HOST_ID, DATE, LAT, LNG, EVENT_LOCATION, TIME) VALUES ('walk the dog in a mexican restaurant', 'Taco Tuseday', 1,  DATE '2015-05-27', 41.12401088661321, -95.95593537227471, '12510 S 29th Ave, Bellevue, NE 68123', TIME '19:35');
INSERT INTO EVENT (EVENT_DESCRIPTION, EVENT_TITLE, HOST_ID, DATE, lat, lng, EVENT_LOCATION, TIME) VALUES ('have class with your favorite pet', 'Campus dog meet', 2,  DATE '2015-05-26', 41.25884390727145, -96.01092462535927, '6001 Dodge St, Omaha, NE 68182', TIME '12:30');