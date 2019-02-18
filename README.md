###Конвейер из пересекающихся очередей

####Web-api

#### GET [http://localhost:8080/api/v1/integer-streamline/get-statistics](http://localhost:8080/api/v1/integer-streamline/get-statistics})
#### получение информации о конвейере

        ответ:
		{
            "capacityA": 6,
            "capacityB": 3,
            "sizeA": 6,
            "sizeB": 3,
            "streamA": [
                22,
                233,
                7653244,
                23,
                2344,
                567765
            ],
            "streamB": [
                7653244,
                23,
                567765
            ]
        }
		
---

#### POST [http://localhost:8080/api/v1/integer-streamline/push-a?item={item}](http://localhost:8080/api/v1/integer-streamline/push-a?item={item})
#### запись в очередь A
        где:
        1. {item} - объект для пуша в очередь
        
        ответ: - item, выпавший из конца очереди
        
---

#### POST [http://localhost:8080/api/v1/integer-streamline/push-b?item={item}](http://localhost:8080/api/v1/integer-streamline/push-b?item={item})
#### запись в очередь B
        где:
        1. {item} - объект для пуша в очередь
                
        ответ: - item, выпавший из конца очереди
---

####Реализации
#####ArrayCrossingStreamline
Реализация на основе массивов. При каждом добавлении в очереди, массивы копируются в новые без выпавшего последнего элемента.
#####LinkedListCrossingStreamline
Реализация на основе связанных списков. Структура рекурсивная. Каждый элемент очереди имеет указатель на следующий. При добавлении элемента, указатель на последний элемент у предпоследнего удаляется.

####Конфигурации запуска

---

    streamline:
      type: Array 
      capacityA: 6
      capacityB: 3
      jointPointsA: 2, 3, 5 
      jointPointsB: 0, 1, 2

     type - Имплементация конвейера. Доступные значения: (Array | LinkedList). Дефолтное значение Array
     capacityA - Длина очереди А
     capacityB - Длина очереди В
     jointPointsA, jointPointsB - Массивы с указанием точек пересечения. Точка_0 = jointPointsA[0], jointPointsB[0] Свойства точек:
        * Количество точек не больше длины меньшей очереди
        * В каждом массиве не может быть дублирущихся индексов точек
        * Точки в каждом массиве должны быть указаны по возрастанию
        * Точки в массивах должны быть расположены в границе от 0 до (capacity - 1)
---