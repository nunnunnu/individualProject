create database melon;

create table member_info(
mi_seq	bigint	not null	auto_increment	primary key     ,
mi_id	varchar(20)	not null		unique key              ,
mi_pwd	varchar(64)	not null		                        ,
mi_name	varchar(20)	not null		                        ,
mi_age	int	not null	default 0	                        ,
mi_phone	varchar(10)	not null		                    ,
mi_email	varchar(100)	not null		                ,
mi_birth	date	not null	default (current_date) 	    ,
mi_reg_dt	datetime	not null	default now()	
);

create table ticket_info(
ti_seq	bigint	not null	auto_increment	primary key  ,
ti_price	int	not null		                         ,
ti_down	int	null		                                 ,
ti_streaming	int	null		                         ,
ti_term	int	not null	default 30	
);

create table ticket_member(
tm_seq	bigint	not null	auto_increment	primary key        ,
tm_ti_seq	bigint	not null		                           ,
tm_mi_seq	bigint	not null		                           ,
tm_reg_dt	datetime	not null	default now()	
);


create table genre_info (
gi_seq	bigint	not null	auto_increment	primary key,
gi_name	varchar(20)	not null		
);


create table publisher_info	(
pi_seq	bigint	not null	auto_increment	primary key,
pi_name	varchar(20)	not null		
)


create table agency_info(
ai_seq	bigint	not null	auto_increment	primary key,
ai_name	varchar(20)	not null		
)

create table artist_info(
art_seq	bigint	not null	auto_increment	primary key  ,
art_name	varchar(100)	not null		             ,
art_debut	date	not null	default (current_date)	 ,
art_ai_seq	bigint	null		                         ,
art_country	varchar(100)	null		                 ,
art_file	text	not null		     ,
art_uri	text	not null ,
art_dtype	varchar(50)	not null		
)

create table artist_solo_info (
art_seq	bigint	not null		primary key,
asi_birth	date	not null		
);


create table artist_group_info	(
art_seq	bigint	not null		primary key,
agi_split	date	null		
);

create table artist_connection		(
ac_seq	bigint	not null	auto_increment	primary key,
ac_agi_seq	bigint	not null		,
ac_asi_seq	bigint	not null		
);

create table album_info		(
album_seq	bigint	not null	auto_increment	primary key  ,
album_reg_dt	date	not null	default (current_date)	 ,
album_gi_seq	bigint	not null                             ,
album_pi_seq	bigint	null		                         ,
album_ai_seq	bigint	null		                         ,
album_type	varchar(10)	not null	,
album_cover	text	not null	,
album_uri	text	not null	
)


create table song_info(
si_seq	bigint	not null	auto_increment	primary key  ,
si_name	varchar(200)	not null		                 ,
si_album_seq	bigint	not null                         ,
si_gi_seq	bigint	not null		                     ,
si_title	tinyint	not null	default false	         ,
si_order	int	not null	default 1	                 ,
si_lyrics	text	null		                         ,
si_reg_dt	date	not null	default (current_date)	
);

create table song_file		(
sf_seq	bigint	not null	auto_increment	primary key  ,
sf_file	text	not null		                         ,
sf_uri	text	not null		                         ,
sf_si_seq	bigint	not null		                     ,
sf_quality	varchar(20)	not null	default 'mp3'	
);

create table song_creator (
sc_seq	bigint	not null	auto_increment	primary key   ,
sc_si_seq	bigint	not null		                      ,
sc_art_seq	bigint	not null		                      ,
sc_type	varchar(10)	not null		
);

create table song_likes		(
sl_seq	bigint	not null	auto_increment	primary key ,
sl_si_seq	bigint	not null		                    ,
sl_mi_seq	bigint	not null		
);

create table album_grade		(
ag_seq	bigint	not null	auto_increment	primary key ,
ag_album_seq	bigint	not null		                ,
ag_grade	int	not null	default 5.0	                ,
ag_mi_seq	bigint	not null		
);

create table album_commnet		(
albumc_seq	bigint	not null	auto_increment	primary key   ,
albumc_mi_seq	bigint	not null		                      ,
albumc_album_seq	bigint	not null                          ,
albumc_commnet	text	not null		                      ,
albumc_reg_dt	datetime	not null	default now()	      ,
albumc_edit_dt	datetime	null		                      ,
albumc_file	text	null		,
albumc_uri	text	null		
);

create table artist_fan		(
af_seq	bigint	not null	auto_increment	primary key   ,
af_art_seq	bigint	not null		                      ,
af_mi_seq	bigint	not null		                      ,
af_reg_dt	datetime	not null	default now()	
);

create table playlist_info		(
playi_seq	bigint	not null	auto_increment	primary key  ,
playi_title	varchar(50)	not null		                     ,
playi_mi_seq	bigint	not null		                     ,
playi_reg_dt	datetime	not null	default now()	     ,
playi_edit_dt	datetime	null		
);

create table playlist_song		(
ps_seq	bigint	not null	auto_increment	primary key,
ps_playi_seq	bigint	not null	                   ,
ps_si_seq	bigint	not null		                   ,
ps_order	int	not null	default 1	
);

create table history_paly		(
hp_seq	bigint	not null	auto_increment	primary key ,
hp_si_seq	bigint	not null		                    ,
hp_mi_seq	bigint	not null		                    ,
hp_start	datetime	not null	default now()	    ,
hp_end	datetime	not null		
);
