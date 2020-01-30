----------Подготовка окружения и конфигов----------
1. На машине должна быть установлена java 1.8
2. Скачиваем репозиторий командой в корень диска C - git clone https://github.com/alexeykovyazin/dgtest.git
 - Папка с тестами должа находиться по пути - C:\dgtest. Это обязательное условие, т.к. пока все пути привязаны отностительно этого пути
3. В папке C:\dgtest есть папка Maven ее нужно подложить в любое место на диске и указать в переменные среды путь до папки bin(например C:\Maven\bin)
 - таким образом мы можем выполнять команды mvn в cmd без указания полного пути
4. Открываем на редактирование файл C:\dgtest\src\test\resurces\testng.xml Для изменения пути к папке с конфиками, нужно в параметре pathhqbirddata изменить значение на нужное(по умолчанию стоит C:\HQBirdData\)
5. Изменения других пути и значенияб при необходимости:
 - файл C:\dgtest\src\test\resurces\BatFiles\Runcmd.bat, изменить путь до файла isql( по умолчанию C:\HQbird\Firebird30\isql)


----------Запуск тестов----------
1. Заходим в папку C:\dgtest и вызываем из нее cmd
2. В cmd вводим mvn clean test
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