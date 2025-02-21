Версия Java - 17 (jbr 17)

Система сборки Maven 3.9.9.

Для формирования .jar файла использовался плагин maven-assembly-plugin. Для этого в каталоге с файлом pom.xml необходимо выполнить команду "mvn clean package" и в папке target появится .jar файл "FileAnalyzer-1-jar-with-dependencies". 

Аргументы для запуска можно передавать в любом порядке, например "java -jar FileAnalyzer-1-jar-with-dependencies.jar -o "папка/1" -p "еще_" in.txt -s" эквивалентно "java -jar FileAnalyzer-1-jar-with-dependencies.jar  -s in.txt -p "еще_" -o "папка/1" "
