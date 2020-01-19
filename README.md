--------------------------------------
Инструкция по запуску тестов на сервере:
1. Заходим в папку C:\dgtest и вызываем cmd
2. В cmd вводим mvn clean test
- Сначала запустится батник на остановку службы, ожидание 30 сек
- Затем удаляем несколько папок (config\agent\servers\hqbirdsrv, output\logs\agent\servers\hqbirdsrv) и файлы installid.bin, unlock
- Запускается батник на старт службы, ожидание 5 сек
- Проходим регистрацию и добавляем сервер с дефолтными параметрами( небольшие ожидания на данном этапе пока уметны)
- Добавляем БД для тестов по бэкапам
-----------------------------------
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
 
 Allure генерирует не только html но и js скрипты, для того чтобы все отрабатывало необходим, либо сторонний веб-сервер, либо веб-сервер allure из коробки, либо открываем index.html в firfox. Отображение отчета в fierfox стало возможно после установки параметра security.fileuri.strict_origin_policy в значение false.
 
Если выполняем mvn allure:serve, а затем прогоняем тесты несколько раз, исходники отчета остаются доступными в  AppData/Local/Temp ( весят около 2-3 мб)
Если выполняем mvn allure:report, создается папка allure-reports по пути C:\dgtest\src\test\reports\allure-reports. Перед прогоном тестов, эта папка удаляется.