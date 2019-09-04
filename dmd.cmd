cls
title JDL-DMD
echo generate DMD with jhipster....
rmdir /S /Q .jhipster
del   /F /Q .\src\main\resources\config\liquibase\changelog\*added*.xml
TortoiseGitProc.exe /command:cleanup /path:"src" /noui /delunversioned /delignored /revert
jhipster import-jdl entities.jdl
pause