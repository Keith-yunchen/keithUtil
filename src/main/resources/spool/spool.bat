@echo off
set param1=%1%
set param2=%2%
sqlplus {0} @%param1% '%param2%'
exit