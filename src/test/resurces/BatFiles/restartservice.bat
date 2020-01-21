@Echo Off
Set ServiceName=FBDataGuardAgent

:: Получаем состояние службы, проверяем запущена ли она
SC queryex %ServiceName%|Find "STOPPED"&&(
    Net start %ServiceName%
)

Exit