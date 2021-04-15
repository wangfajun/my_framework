-- demo
drop table if exists "fm_demo";
create table "fm_demo"(
	"user_id" bigserial,
	"user_name" varchar(50),
	"mobile" varchar(50),
	"user_img_url" varchar(255),
	"id_card" varchar(18),
	"status" int2 default 0,
	"create_time" timestamp default current_timestamp,
	"create_user" varchar(50),
	"update_time" timestamp default current_timestamp,
	"update_user" varchar(50),
	primary key (user_id)
);
comment on table "fl_user_info" is '员工表';
comment on column "fl_user_info"."user_id" is '主键,用户编号';
comment on column "fl_user_info"."user_name" is '姓名';
comment on column "fl_user_info"."mobile" is '手机号';
comment on column "fl_user_info"."user_img_url" is '头像地址';
comment on column "fl_user_info"."id_card" is '身份证';
comment on column "fl_user_info"."status" is '状态 0:正常 1:冻结';
comment on column "fl_user_info"."create_time" is '创建时间';
comment on column "fl_user_info"."create_user" is '创建人';
comment on column "fl_user_info"."update_time" is '修改时间';
comment on column "fl_user_info"."update_user" is '修改人';