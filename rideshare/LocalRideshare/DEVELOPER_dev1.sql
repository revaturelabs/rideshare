select * from RIDE_REQUEST;
select * from USERS;
select * from RIDES;

CREATE OR REPLACE PROCEDURE UPDATE_STALE_STATUS
   AS
   BEGIN
       UPDATE RIDE_REQUEST
        SET STATUS =2
         WHERE TIME <= CURRENT_TIMESTAMP - NUMTODSINTERVAL(15, 'MINUTE') AND STATUS != 1;
         COMMIT;
   END;
   /
   
CREATE OR REPLACE PROCEDURE UPDATE_IS_OPEN_OFFER
    AS
    BEGIN
        UPDATE AVAILABLE_RIDES
        SET IS_OPEN = 0
     WHERE TIME <= CURRENT_TIMESTAMP - NUMTODSINTERVAL(30,'MINUTE');
        COMMIT;
    END;
    /
   
BEGIN
   UPDATE_STALE_STATUS;
END;
/

BEGIN
    UPDATE_IS_OPEN_OFFER;
END;
/
SELECT * FROM AVAILABLE_RIDES;

BEGIN
        DBMS_SCHEDULER.CREATE_JOB (
       job_name           =>  'STALE_REQUEST',
       job_type           =>  'STORED_PROCEDURE',
       job_action         =>  'UPDATE_STALE_STATUS',
       start_date         =>   SYSDATE,
       repeat_interval    =>  'FREQ=MINUTELY;INTERVAL=1', 
       end_date           =>  '28-AUG-18 07.00.00 PM',
       enabled            =>   TRUE,
       auto_drop          =>   FALSE,
       comments           =>  'My new job');
    END;
    /
    
    BEGIN
        DBMS_SCHEDULER.CREATE_JOB (
       job_name           =>  'STALE_OFFER',
       job_type           =>  'STORED_PROCEDURE',
       job_action         =>  'UPDATE_IS_OPEN_OFFER',
       start_date         =>   SYSDATE,
       repeat_interval    =>  'FREQ=MINUTELY;INTERVAL=15', 
       end_date           =>  '28-AUG-18 07.00.00 PM',
       enabled            =>   TRUE,
       auto_drop          =>   FALSE,
       comments           =>  'My second new job');
    END;
    /
    
SELECT JOB_NAME,STATE FROM DBA_SCHEDULER_JOBS WHERE JOB_NAME = 'STALE_REQUEST';
SELECT JOB_NAME,STATE FROM DBA_SCHEDULER_JOBS WHERE JOB_NAME = 'STALE_OFFER';