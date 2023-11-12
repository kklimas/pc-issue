# Metryki do zebrania

- całkowita ilość operacji w zadanym czasie dla zmieniających się parametrów
- ilość operacji na sek w zadanym czasie dla zmieniających się parametrów
- **dodatkowe** średni czas przebywania wątku w locku dla zmieniających się parametrów

# Przyjęte parametry

Umówiliśmy się na następujące parametry

### Stałe
- **EXECUTION_TIME_MILLIS** - czas wykonywania programu, stały, ustawiony na 30sec
- **RANDOM_TOP_BOUND** - górna wartość jaką może przyjąć losowana liczba, domyslnie 10

### Zmienne
- **threadsCount** - liczba wątków - sprawdzamy dla 1, 2, 10, 100
- **bufferSize** - rozmiar bufora - sprawdzamy dla 20, 50, 100

Dla każdego zmiennego parametru trzeba zebrać dane i wrzucić na wykres gdzie na osi x będzie
zwiększająca się wartość parametru a na osi y liczba wykonywanych operacji / cos innego

Każdy z pomiarów trzeba zrobić per randomType
- **randomType** - typ randoma generujacego liczby - 0 lub 1 (0 to defaultowy Random(), 1 to ThreadLocalRandom.current())

### Krótki opis programu
Klaska Main przyjmuje domyslnie trzy argumenty THREAD_COUNT, BUFFER_SIZE, RANDOM type. Można się obejść bez tego i jakoś to
zautomatyzować.
Każde rozwiązań wykonuje się współbieżnie więc wyniki powinny być po danym czasie (domyslnie 30sec - propertka EXECUTION_TIME_MILLIS).
Kod bazuje o Jave 21 dlatego też jest maven a nie gradle. Można śmiało użyć niższych wersji z tym że będzie trzeba podmienić Executors.newVirtualThreadPerTaskExecutor()
na coś z niższej wersji.
