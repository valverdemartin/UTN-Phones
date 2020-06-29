DELIMITER //

CREATE TRIGGER tbi_calls BEFORE INSERT ON calls FOR EACH ROW
BEGIN
    DECLARE v_id_o_city INT;
    DECLARE v_id_d_city INT;
    DECLARE v_price_minute DOUBLE;


    /Obtengo ciudad origen/
    select  id into v_id_o_city from cities where NEW.origin_number like concat(city_prefix, '%') order by city_prefix desc limit 1;
    /Obtengo ciudad destino/
    select  id into v_id_d_city from cities where NEW.dest_number like concat(city_prefix, '%') order by city_prefix desc limit 1;
    /calculo rate/
    SET new.id_rate = (SELECT id FROM rates WHERE v_id_o_city = id_origin_city AND v_id_d_city  = id_dest_city ORDER BY rate_date DESC LIMIT 1); 
    SELECT price_minute INTO v_price_minute FROM rates WHERE v_id_o_city = id_origin_city AND v_id_d_city  = id_dest_city ORDER BY rate_date DESC LIMIT 1; 
    /Obtengo linea origen/
    SET NEW.id_origin_line = (SELECT id FROM phone_lines WHERE NEW.origin_number like concat('%',line_number));
    /Obtengo linea destino/
    SET NEW.id_dest_line = (SELECT id FROM phone_lines WHERE NEW.dest_number like concat('%',line_number));
    /Calculo total/
    SET NEW.total_price = (NEW.duration/60) * v_price_minute;


END//