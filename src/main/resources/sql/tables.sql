drop table if exists MAN_TB_MONITORUSER;
drop table if exists MAN_TH_ALARMHISTORY;
create table if not exists MAN_TB_MONITORUSER(
 op_id INTEGER primary key autoincrement,
 account varchar(20) not null,
 password varchar(50) not null,
 real_name varchar(20) not null,
 role_id INTEGER not null,
 update_time INTEGER not null
);

create table if not exists MAN_TH_ALARMHISTORY(
    /** 模块类型代码 */	
     moduletype varchar(10) not null,
	/** 模块标识代码 */
	 module_code VARCHAR(50) not null,
	/** 报警开始时间UTC */
	 alarm_time int not null,
	/** 报警处理时间UTC */
	 deal_time int,
	/** 报警结束时间UTC */
	 finish_time int,
	/** 报警处理状态0-报警中 1-处理中 2-处理结束 */
	 status int not null,
	/** 报警发现方式0-文件不存在 1-模块内容报警 2-监控条件报警 */
	 report_way int not null,
	/** 报警内容 */
	alarm_content VARCHAR(200) not null,
	/** 更新时间UTC */
	 update_time int not null,
	 /** 报警处理人 id */
	 deal_opid int,
	 data_time int not null default 0,
	 primary key(module_code,alarm_time)
);

insert into MAN_TB_MONITORUSER(account,password,real_name,role_id,update_time)
values('admin','c984aed014aec7623a54f0591da07a85fd4b762d','admin',0,current_time());
create index if not exists idx_man_th_alarmhistory on man_th_alarmhistory(module_code,alarm_time);
