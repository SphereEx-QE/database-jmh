update tb_f_user10 set name='TEST_USER_10' limit 100;
update tb_f_user_cert10 set cert_type = 'IDENTITY_CARD', expire_date='2033-12-22' limit 100;
update tb_f_user_contact10 set phone_prefix="156" order by id limit 100;
update tb_f_user10 set id="XXXX-XXXX-XXXX",name="TEST_UNION" order by id limit 1;
update tb_f_user_cert10 set user_id="XXXX-XXXX-XXXX" order by id limit 1;
update tb_f_user_contact10 set user_id="XXXX-XXXX-XXXX" order by id limit 1;
update tb_f_user_cert10 set cert_no="111111111111111111" where user_id="XXXX-XXXX-XXXX";
update tb_f_user_contact10 set phone="19999999999" where user_id="XXXX-XXXX-XXXX";
