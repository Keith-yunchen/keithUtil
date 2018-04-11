set trimspool on
set trimout on
set term off
set verify off
set TERMOUT OFF
set ECHO OFF
SET FEEDBACK OFF
SET  HEADING OFF
set linesize 3000
set pagesize 0
set newpage none
define fileName=&1
spool &fileName;
{0};
spool off
quit