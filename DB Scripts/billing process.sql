DELIMITER //
CREATE PROCEDURE sp_bills()
BEGIN
	DECLARE vIdLine BIGINT;
    DECLARE vCallsCount INT DEFAULT 0;
    DECLARE vTotalPrice FLOAT DEFAULT 0;
    DECLARE vTotalCost FLOAT DEFAULT 0;
    DECLARE vBillId INT;
	DECLARE vFinished INT DEFAULT 0;
	
    DECLARE cursor_calls CURSOR FOR 
    SELECT id_origin_line FROM calls 
    WHERE id_bill IS NULL
    GROUP BY id_origin_line;
    DECLARE CONTINUE handler FOR NOT FOUND SET vFinished = 1;
	
    START TRANSACTION; 
    
	OPEN cursor_calls;
	loop_calls : LOOP
			
	FETCH cursor_calls INTO vIdLine;
	IF(vFinished = 1) THEN 
		LEAVE loop_calls;
    END IF;

	INSERT INTO bills(id_line , bill_date , due_date, active, total_price, cost_price, calls_count ) VALUES (vIdLine, now() , now() + INTERVAL 15 DAY, true, 0, 0, 0);
	SET vBillId = last_insert_id();
    
	UPDATE calls 
    SET id_bill = vBillId 
    WHERE id_origin_line = vIdLine 
    AND id_bill IS NULL;
	
                
	SELECT SUM(c.total_price), COUNT(c.id), SUM(r.cost_price) INTO vTotalPrice, vCallsCount, vTotalCost 
    FROM calls as c
    INNER JOIN rates as r
    on r.id = c.id_rate
    WHERE id_origin_line = vIdLine
    AND id_bill = vBillId;
    
    
	UPDATE bills SET calls_count = vCallsCount, total_price = vTotalPrice, cost_price = vTotalCost
    WHERE id = vBillId;
                
	END LOOP loop_calls;
            
    COMMIT; 
	
	CLOSE cursor_calls;
END //

call sp_bills;

DROP PROCEDURE IF EXISTS sp_bills;

update calls set id_bill = null;


-- SHOW PROCESSLIST;
-- SET GLOBAL event_scheduler = ON;
DROP EVENT IF EXISTS event_billing_process;
DELIMITER //
CREATE EVENT event_billing_process ON SCHEDULE EVERY "1" MONTH	STARTS "2020-06-01" DO
BEGIN
	CALL sp_bills();
END //