alter table hero add name_kana varchar(100) after name;
alter table hero add mail_address varchar(100) after name_kana;
alter table hero add date_of_birth date after name_kana;
alter table hero add place_of_birth varchar(50) after date_of_birth;
alter table hero add blood_type varchar(2) after place_of_birth;