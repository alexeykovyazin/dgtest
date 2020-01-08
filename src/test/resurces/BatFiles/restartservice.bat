@Echo Off
Set ServiceName=FBDataGuardAgent

:: Получаем состояние службы, проверяем запущена ли она
:loop
SC queryex %ServiceName%|Find "STOPPED"&&(
    Net start %ServiceName%
    :loop
    sc query %ServiceName% | find "STATE"| find "RUNNING"
    if errorlevel 1 (
      timeout 1
      goto loop
    )
)

if errorlevel 1 (
  timeout 1
  goto loop
)

Exit