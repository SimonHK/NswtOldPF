drop table if exists BZCImagePlayerStyle;
create table BZCImagePlayerStyle
(
   ID                   bigint not null,
   Name                 varchar(100) not null,
   ImagePath            varchar(200),
   Template             varchar(200),
   IsDefault            varchar(2),
   Memo                 varchar(500),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

drop table if exists ZCImagePlayerStyle;
create table ZCImagePlayerStyle
(
   ID                   bigint not null,
   Name                 varchar(100) not null,
   ImagePath            varchar(200),
   Template             varchar(200),
   IsDefault            varchar(2),
   Memo                 varchar(500),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

/*图片播放器功能默认数据*/
INSERT INTO zdmaxno (NoType,NoSubType,MaxValue,Length) VALUES ('ImagePlayerStyleID','SN',5,10);
INSERT INTO zcimageplayerstyle (ID,Name,ImagePath,Template,IsDefault,Memo,AddUser,AddTime,ModifyUser,ModifyTime) VALUES (1,'标题+数字导航','/Upload/ImagePlayerStyles/1/thumbIMG.jpg','/Upload/ImagePlayerStyles/1/template.html','Y','','admin','2010-08-04 20:02:58',NULL,NULL);
INSERT INTO zcimageplayerstyle (ID,Name,ImagePath,Template,IsDefault,Memo,AddUser,AddTime,ModifyUser,ModifyTime) VALUES (2,'标题+前后按钮导航','/Upload/ImagePlayerStyles/2/thumbIMG.jpg','/Upload/ImagePlayerStyles/2/template.html','N','','admin','2010-08-04 20:03:28',NULL,NULL);
INSERT INTO zcimageplayerstyle (ID,Name,ImagePath,Template,IsDefault,Memo,AddUser,AddTime,ModifyUser,ModifyTime) VALUES (3,'缩略图导航','/Upload/ImagePlayerStyles/3/thumbIMG.jpg','/Upload/ImagePlayerStyles/3/template.html','N','','admin','2010-08-04 20:03:43',NULL,NULL);
INSERT INTO zcimageplayerstyle (ID,Name,ImagePath,Template,IsDefault,Memo,AddUser,AddTime,ModifyUser,ModifyTime) VALUES (4,'缩略图导航(二)','/Upload/ImagePlayerStyles/4/thumbIMG.jpg','/Upload/ImagePlayerStyles/4/template.html','N','','admin','2010-08-04 20:03:54',NULL,NULL);
INSERT INTO zcimageplayerstyle (ID,Name,ImagePath,Template,IsDefault,Memo,AddUser,AddTime,ModifyUser,ModifyTime) VALUES (5,'缩略图导航+标题+摘要','/Upload/ImagePlayerStyles/5/thumbIMG.jpg','/Upload/ImagePlayerStyles/5/template.html','N','','admin','2010-08-04 20:04:06',NULL,NULL);

/*修改播放器表的数据库结构*/
update zcimageplayer set DisplayType = '1';
alter table zcimageplayer MODIFY ImageSource varchar(20) not null;
alter table zcimageplayer change DisplayType StyleID bigint(20) not null;
alter table zcimageplayer DROP COLUMN IsShowText;
update zcimageplayer set ImageSource = 'local' where ImageSource = '0';
update zcimageplayer set ImageSource = 'catalog_first' where ImageSource = '1';
update zcimageplayer set ImageSource = 'catalog_select' where ImageSource = '2';

update bzcimageplayer set DisplayType = '1';
alter table bzcimageplayer MODIFY ImageSource varchar(20) not null;
alter table bzcimageplayer change DisplayType StyleID bigint(20) not null;
alter table bzcimageplayer DROP COLUMN IsShowText;
update bzcimageplayer set ImageSource = 'local' where ImageSource = '0';
update bzcimageplayer set ImageSource = 'catalog_first' where ImageSource = '1';
update bzcimageplayer set ImageSource = 'catalog_select' where ImageSource = '2';

/*修改统计相关的表 2010-07-26 by wyuch*/
ALTER TABLE zcvisitlog MODIFY COLUMN URL VARCHAR(2000);
ALTER TABLE zcvisitlog MODIFY COLUMN Referer VARCHAR(2000);
ALTER TABLE zcvisitlog MODIFY COLUMN District VARCHAR(100);
ALTER TABLE bzcvisitlog MODIFY COLUMN URL VARCHAR(2000);
ALTER TABLE bzcvisitlog MODIFY COLUMN Referer VARCHAR(2000);
ALTER TABLE bzcvisitlog MODIFY COLUMN District VARCHAR(100);
ALTER TABLE zcstatitem ADD COLUMN Memo VARCHAR(4000) AFTER Count31;
ALTER TABLE bzcstatitem ADD COLUMN Memo VARCHAR(4000) AFTER Count31;

/*新增站点首页重新生成文件名设置 2010-08-04 lanjun*/
INSERT INTO zdconfig  VALUES ('RewriteFileName','站点首页重新生成文件名','index.html',NULL,NULL,NULL,NULL,NULL,'2010-01-18 14:35:52','admin',NULL,NULL);
INSERT INTO zdconfig  VALUES ('Big5ConvertFlag','是否自动转换繁体','N',NULL,NULL,NULL,NULL,NULL,'2010-01-18 14:35:52','admin',NULL,NULL);

/*FTP采集新增功能,修改表结构设计  2010.08.05  欧阳晓亮*/
drop table if exists ZCFtpGather;
create table ZCFtpGather
(
   ID                   bigint not null,
   Name                 varchar(100),
   FtpHost              varchar(20),
   FtpPort              varchar(50) not null,
   GatherDrectory       varchar(200),
   FtpUserName          varchar(50) not null,
   FtpUserPassword      varchar(50),
   GatherType           varchar(200),
   RedirectUrl          varchar(200),
   Content              mediumtext,
   SiteID               varchar(100),
   CatalogID            varchar(20),
   Type                 varchar(2),
   ConfigXML            text,
   ProxyFlag            varchar(2),
   ProxyHost            varchar(255),
   ProxyPort            bigint,
   Status               varchar(2),
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop3                varchar(50),
   Prop4                varchar(50),
   Prop5                varchar(50),
   Prop6                varchar(50),
   AddUser              varchar(50) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(50),
   ModifyTime           datetime,
   primary key (ID)
);

/*Ftp采集新建备份表*/
drop table if exists BZCFtpGather;
create table BZCFtpGather
(
      ID                   bigint not null,
   Name                 varchar(100),
   FtpHost              varchar(20),
   FtpPort              varchar(50) not null,
   GatherDrectory       varchar(200),
   FtpUserName          varchar(50) not null,
   FtpUserPassword      varchar(50),
   GatherType           varchar(200),
   RedirectUrl          varchar(200),
   Content              mediumtext,
   SiteID               varchar(100),
   CatalogID            varchar(20),
   Type                 varchar(2),
   ConfigXML            text,
   ProxyFlag            varchar(2),
   ProxyHost            varchar(255),
   ProxyPort            bigint,
   Status               varchar(2),
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop3                varchar(50),
   Prop4                varchar(50),
   Prop5                varchar(50),
   Prop6                varchar(50),
   AddUser              varchar(50) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(50),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);