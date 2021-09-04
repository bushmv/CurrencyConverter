# CurrencyConverter

CurrencyConverter - приложение для конвертации валют, сделанное в качестве сестового задания на позицию android разработчика.

# Используемые технологии 

- язык Kotlin
- kotlin coroutines
- viewmodel 
- navigation
- viewBinding
- junit 4

# Фрмула

Для конвертации используется значения, взятые с http://www.cbr.ru/scripts/XML_daily.asp. Это xml файл, в котором содержатся конктерные валюты с их отношением к рублю.    Конвертировать рубли в другую валюту и обратно просто, т.к. нужно просто указанное значение умножить / разделить на 1. Например, если значение валюты 50 к рублю, то 1 валюта равно 50 руб, а 1 рублю = 1 / 50 = 0,02 валюты.
Конвертация из одной валюты в другую немного сложнее. Предположим у нас есть валюта FROM, из которой мы хотим конвертироват и валюта TO, в которую мы хотим конвертировать. Также есть указанное количество AMOUNT, которое мы хотим конвертировать. Нам нужно вычислить формулу и найти N

AMOUNT * FROM = N * TO,

Например, представим что FROM = 50руб, а TO = 10руб, тогда при значении AMOUNT = 5 мы получим, что 1 FROM = 5 TO (т.е. значение N будет равно 5). Это верно, так как TO в 5 раз дешевле.

Но у валюты есть еще такое значение, как номинал. Иногда 1 валюту обменивают на несколько (10, 100 и т.д.) другой. Например, валюта FROM с номиналом 1 и стоимостью 50 = TO с номиналом 10 и стоимостью 10. Это означает, что мы получим при обмене сразу 10 валют, а это значит что истинная стоимость TO в 10 (в значение номинала) дешевле и на 5 валют FROM мы можем получить 25 валют с номиналом 10 или 250 валют с номиналом 1.
Укажем в формуле номинал (мы должны разделит значение на номинал)

AMOUNT * FROM / (номинал FROM) = N * TO / (номинал TO),

Тогда значение N можно высчитать как 

N = (AMOUNT * FROM * (номинал TO)) / (TO * (номинал FROM))

# Архитектура

Приложение состоит из 3-х слоев:

- Domain слой. Здесь представлены 2 основных класса:
  - Currency - представляет отдельную валюту с названием, отношением у рублю и некоторыми другими полями
  - Converter - класс для конвертации значения одной валюты в другую. Содержит один метод convert, который принимает валюту, из которой конвертируют, валюту в которую конвертируют и значение, которое конвертруют. Этот класс должен быть полностью отделен от android и покрыт unit тестами  
- Data слой - этот слой должен получать данные и преобразовывать их в domain объекты. Состоит из 
  - Repository - синглтон который абстрагирует данные от того, как эти данные были получены
  - DataSource - источник данных (DataSource объявлен как интерфейс для того, чтобы в дальнейшем источник данных можно было легко подменить. Конкретный класс RemoteDataSource получает xml файл с сервера.)
  - XmpDocumentParser - парсит xml документ, полученный из dataSource и конструирует объекты Currency  
- Presentation слой - отвечает за представление пользователю
  - viewModel - сохраняет данные на экране при изменении жизненного цикла android
  - fragment - показывает экран пользователю и получает касания от пользователя. Должен содержать минимальное количество логики.
  - некоторые вспомогательные небольшие (в основном enum) классы для чистоты кода
  
# Запустить

Для запуска приложения просто склонируйте репозиторий, откройте в android studio, собери проект с помощью gradle и запустите

# Изображения

Для быстрого визуального представления скриншоты основных экранов

- Экран загрузки

![alt text](https://github.com/bushmv/CurrencyConverter/blob/master/screens_for_readme/load_screen.png)

- Экран конвертации

![alt text](https://github.com/bushmv/CurrencyConverter/blob/master/screens_for_readme/converter_screen.png)

- Экран выбора валюты

![alt text](https://github.com/bushmv/CurrencyConverter/blob/master/screens_for_readme/chooce_screen.png)


# Изображения
