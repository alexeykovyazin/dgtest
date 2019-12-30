@Echo Off
Set ServiceName=FBDataGuardAgent

:: Получаем состояние службы, проверяем запущена ли она
SC queryex %ServiceName%|Find "STATE"|Find "RUNNING">Nul&&(
    rem Пробуем остановить
    Net stop %ServiceName%>nul||(
        rem Если остановить через net stop не вышло, запрашиваем PID
        For /F "tokens=3" %%A In ('SC queryex %ServiceName%^|Find "PID"') Do (
            rem Убиваем процесс вместе с дочерними, используя полученный PID
            TaskKill /F /T /PID %%A>nul
            rem Задержка, чтобы обновился статус службы после убийства процесса
            Ping -n 4 127.0.0.1>nul
        )
        rem На всякий случай
        Net stop %ServiceName% 2>nul
    )
)
Exit