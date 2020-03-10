----------Подготовка окружения и конфигов----------
1. На машине должна быть установлена java 1.8
2. Скачиваем репозиторий командой в корень диска C - git clone https://github.com/alexeykovyazin/dgtest.git
 - Папка с тестами должа находиться по пути - C:\dgtest. Это обязательное условие, т.к. пока все пути привязаны отностительно этого пути
3. В папке C:\dgtest есть папка Maven ее нужно подложить в любое место на диске и указать в переменные среды путь до папки bin(например C:\Maven\bin)
 - таким образом мы можем выполнять команды mvn в cmd без указания полного пути
4. Заходим в папку C:\dgtest\src\test\resurces\BatFiles и редактируем файл config.txt
5. Изменения других пути и значенияб при необходимости:
 - папка C:\dgtest\src\test\resurces\BatFiles\ в bat файлах можно изменить имена служб(по умолчанию FirebirdServerHQBirdInstance и FBDataGuardAgent)


----------Запуск тестов----------
1. Заходим в папку C:\dgtest\src\test\resurces\BatFiles и редактируем файл config.txt
- javaPath - путь до папки bin с java 
- brosername - типа браузера (chrome, firefox)
- pathhqbirddata - путь до папки с HQBirdData
- serverInstallationPath - путь до папки с сервером (C:\HQbird\Firebird25\ или C:\HQbird\Firebird30\)
- serverBinaryPath - путь до папки с бинарными файлами(C:\HQbird\Firebird25\bin\ или C:\HQbird\Firebird30\)
ОБЯЗАТЕЛЬНО! при указании путей, ставить в конце "\"
2. Открываем cmd и выполняем cmd /k C:\dgtest\src\test\resurces\BatFiles\Init.bat
3. По завершению прогона тестов можно сформировать(mvn allure:report) или запустить отчет(mvn allure:serve) allure(подробная инструкция ниже)

----------Получение отчета----------

После выполнения тестов для получения отчета необходимо:
Вариант 1:
 - Вызвать cmd из C:\dgtest
 - Выполнить команду mvn allure:serve
 - Allure сам поднимет веб сервер и откроет отчет в браузере
 - В командной строке будет указан путь, где лежат исходники отчета, обычно это папка AppData/Local/Temp
 - После завершения просмотра отчета, необходимо завершить соединение(убить сервер) в cmd

Вариант 2:
 - Вызвать cmd из C:\dgtest
 - Выполнить команду mvn allure:report
 - Перейти в папку C:\dgtest\src\test\reports\allure-reports и открыть файл index.html в браузере fierfox
 - Отчет можно просматривать без поднятия веб-сервера

Allure генерирует не только html но и js скрипты, для того чтобы все отрабатывало необходим, либо сторонний веб-сервер, либо веб-сервер allure из коробки, либо открываем index.html в firfox.
Отображение отчета в fierfox стало возможно после установки параметра security.fileuri.strict_origin_policy в значение false.

Если выполняем mvn allure:serve, а затем прогоняем тесты несколько раз, исходники отчета остаются доступными в  AppData/Local/Temp ( весят около 2-3 мб)
Если выполняем mvn allure:report, создается папка allure-reports по пути C:\dgtest\src\test\reports\allure-reports. Перед прогоном тестов, эта папка удаляется.

----------Алгоритм Инициализации тестового прогона----------
1. Инициализация
 - Сначала запускается батник на остановку службы FBDataGuardAgent, ожидание 30 сек
2. Затем происходит удаление и копирование нужных папок и файлов
 - Удаление - C:\HQBirdData\config\agent\servers\hqbirdsrv
 - Удаление - C:\HQBirdData\output\logs\agent\servers\hqbirdsrv
 - Удаление - C:\HQBirdData\output\output\hqbirdsrv
 - Удаление - C:\HQBirdData\config\installid.bin
 - Удаление - C:\HQBirdData\config\unlock
 - Удаление - C:\HQBirdData\C:\dgtest\src\test\resurces\WorkDB
 - Удаление - C:\HQBirdData\C:\dgtest\src\test\reports\allure-results
 - Удаление - C:\HQBirdData\C:\dgtest\src\test\reports\allure-reports
 - Копирование из папки C:\dgtest\src\test\resurces\StandartDB в папку C:\dgtest\src\test\resurces\WorkDB
 - Копирование из папки C:\dgtest\src\test\resurces\config в папку C:\HQBirdData\config( только 1 файл ftpsrv.properties)
- Запускается батник на старт службы, ожидание 5 сек
3. Далее проходит регистрация в системе
3. Затем Добавление сервера с дефолтными параметрами( небольшие ожидания на данном этапе пока уместны)
4. Останавливаем обновление страницы
5. Далее происходит добавление БД BackupBD, CloudTestDB, TestDB, MasterAsyncBD, MasterSyncBD, ReplicaSyncBD. БД используются в тестах, в перспективе возможен перенос создания баз в классы с запускаемыми тестами. Пока сделано так для стабильности прогона.
6. После успешного прохождения всех этапов, запускаются тестовые наборы.

----------Алгоритм выполнения тестового набора Реплики----------

----------Асинхронная реплика----------
1. БД мастера и реплики уже созданы при запуске всего набора тестов в EnvContainer/
2. Нажимаем на кнопку реплики у БД мастера, выбираем Мастер-Асинхронный и проверяем поля на ошибки
3. Сохраняем с дефотными параметрами, проверяем что у БД отобразилась Icon replicationslave
4. 
 - Рестарт сервиса - FirebirdServerHQBirdInstance
 - Ожидание 5 сек
 - Рестарт сервиса - FBDataGuardAgent
 - Ожидание 70 сек
 - Запуск createTable.bat, который запускает скрипт createTable.sql на создание таблицы TEST1
 - Ожидание 5 сек
 - Проверка, что в логе src/test/resurces/Logs/createTable.log нет ошибок
5. Нажимаем на кнопку реплики у БД мастера, нажимаем Reinitialize Replica Database и проверяем, что нет ошибок и отобразился путь.
6. Создаем БД реплики на основе файла .4replica полученным от мастера. Проверяем наличие иконки replicationslave
7. Добавляем FTP сервер для Мастера и выполняем Cloud Backup.
8. Для БД реплики выполняем Cloud Backup Receiver на основе файла полученного из п.7.
9. Запуск selectTable.bat, который запускает скрипт selectTable.sql для проверки SELECT COUNT(*) FROM TEST1;

----------Синхронная реплика----------
1. Проверка всех полей на ошибки
2. Создание реплики "//sysdba:masterkey@localhost:" и проверка иконки replicationactive
