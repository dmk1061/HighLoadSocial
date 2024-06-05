CREATE EXTENSION IF NOT EXISTS citus;

SELECT create_reference_table('INTEREST');
SELECT create_reference_table('SUBSCRIPTION');
SELECT create_distributed_table('USERS', 'id', 'hash', 'default',3);
SELECT create_reference_table('USER_INTEREST');
SELECT create_distributed_table('POSTS', 'user_id','hash', 'default', 3);
SELECT create_distributed_table('DIALOG_MESSAGE', 'from_user_id','hash', 'default', 3);