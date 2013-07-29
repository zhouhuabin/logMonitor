select * from man_th_alarmhistory;

select * from man_tb_monitoruser;



SELECT moduletype,module_code,alarm_content,alarm_time,report_way,status,ifnull(deal_time,0) deal_time,ifnull(deal_opid,0) deal_opid,data_time
    FROM MAN_TH_ALARMHISTORY th 
    where module_code='Simulation_beijing'
    ;
    
SELECT count(1) count
    FROM MAN_TB_MONITORUSER tb
    where account='admin' and op_id != 1
    ;
    
    
--xinjiang gps
select  top 100 * from GPS20111017 ;
SELECT * FROM "dbo"."GPS20111017";
