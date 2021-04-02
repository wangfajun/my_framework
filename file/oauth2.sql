-- oauth客户端配置表
drop table if exists "oauth_client_details";
CREATE TABLE "oauth_client_details"(
        client_id VARCHAR(128) NOT NULL,
        resource_ids VARCHAR(128),
        client_secret VARCHAR(128),
        scope VARCHAR(128),
        authorized_grant_types VARCHAR(128) ,
        web_server_redirect_uri VARCHAR(128),
        authorities VARCHAR(128),
        access_token_validity INT,
        refresh_token_validity INT,
        additional_information VARCHAR(4096),
        autoapprove VARCHAR(128),
        PRIMARY KEY (client_id)
);
comment on table "oauth_client_details" is 'oauth客户端配置';
comment on column "oauth_client_details"."client_id" is '客户端Id';
comment on column "oauth_client_details"."resource_ids" is '可访问服务资源';
comment on column "oauth_client_details"."client_secret" is '客户端secret';
comment on column "oauth_client_details"."scope" is '允许访问的范围';
comment on column "oauth_client_details"."authorized_grant_types" is '允许的授权模式';
comment on column "oauth_client_details"."web_server_redirect_uri" is '重定向地址';
comment on column "oauth_client_details"."authorities" is '权限';
comment on column "oauth_client_details"."access_token_validity" is 'token的有效期';
comment on column "oauth_client_details"."refresh_token_validity" is '刷新token的有效期';
comment on column "oauth_client_details"."additional_information" is '扩展字段';
comment on column "oauth_client_details"."autoapprove" is '是否跳转授权界面';
INSERT INTO "oauth_client_details"(client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES ('member-client', '', '$2a$10$1hyfkwEOZpqXYU8fOVDnMu1cMEFZNoQhfAATi9rfxUCxDY6vGl5FW', 'MEMBER', 'authorization_code,password,implicit,client_credentials,refresh_token', '', '', 50000, null, null, 'true');

-- token存储
drop table if exists "oauth_access_token";
CREATE TABLE "oauth_access_token"(
        token_id VARCHAR(128),
        token BYTEA,
        authentication_id VARCHAR(128) NOT NULL,
        user_name VARCHAR(128),
        client_id VARCHAR(128),
        authentication BYTEA,
        refresh_token VARCHAR(128),
        PRIMARY KEY (authentication_id)
);
-- 刷新token存储
drop table if exists "oauth_refresh_token";
CREATE TABLE "oauth_refresh_token"(
        token_id VARCHAR(128),
        token BYTEA,
        authentication BYTEA
);

-- 员工表
drop table if exists "fm_demo_info";
create table "fl_user_info"(
	"user_id" bigserial,
	"user_name" varchar(50),
	"mobile" varchar(50),
	"user_img_url" varchar(255),
	"id_card" varchar(18),
	"status" int2 default 0,
	"is_cert" int2 not null default 0,
	"cert_time" timestamp,
	"bank_no"  varchar(50),
    "bank_name" varchar(50),
    "bank_type" varchar(50),
    "ali_pay_no" varchar(50),
	"create_time" timestamp default current_timestamp,
	"create_user" varchar(50),
	"update_time" timestamp default current_timestamp,
	"update_user" varchar(50),
	primary key (user_id)
);
comment on table "fl_user_info" is '灵活用工员工表';
comment on column "fl_user_info"."user_id" is '主键,用户编号';
comment on column "fl_user_info"."user_name" is '姓名';
comment on column "fl_user_info"."mobile" is '手机号';
comment on column "fl_user_info"."user_img_url" is '头像地址';
comment on column "fl_user_info"."status" is '状态 0:正常 1:冻结';
comment on column "fl_user_info"."is_cert" is '是否已经认证 0:默认未认证 1:已认证';
comment on column "fl_user_info"."cert_time" is '认证时间';
comment on column "fl_user_info"."bank_no" is '银行卡号';
comment on column "fl_user_info"."bank_name" is '银行名称';
comment on column "fl_user_info"."bank_type" is '银行类型';
comment on column "fl_user_info"."ali_pay_no" is '阿里支付账号';
comment on column "fl_user_info"."create_time" is '创建时间';
comment on column "fl_user_info"."create_user" is '创建人';
comment on column "fl_user_info"."update_time" is '修改时间';
comment on column "fl_user_info"."update_user" is '修改人';