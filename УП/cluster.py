import random


class kMeansCluster():
    
    def __init__(self, values, width, height, num_cluster = 4):
        self.values = values
        self.width = width
        self.height = height
        self.num_points = len(values)
        self.mark_for_each_point = [0]*self.num_points
        self.num_cluster = num_cluster
        # num_pixel_value это количество значений пикселя, например в RGB - 3
        self.num_pixel_value = len(values[0])
        # axis это то что мы учитываем при кластеризации, в данном случае учитываются значения пикселя и координаты точки
        self.num_axis = len(values[0]) + 2
        self.imageDataToXY()
            
        
    def checkData(self):
        # проверка на адекватность массива изображения
        if type(self.values) != list:
            return 0
        for data in self.values:
            if (type(data) != tuple and type(data) != list) or len(data) < 2 or len(data) > 5:
                return 0
        # проверка на остальные целочисленные значения
        if type(self.width) != int or type(self.height) != int or type(self.num_cluster) != int:
            return 0
        
        if self.width < 0 or self.height < 0 or self.num_cluster < 0:
            return 0
        # проверка того что ширина и высота даны правильные
        if len(self.values) != self.width*self.height:
            return 0
        
        return 1
    
    def startClustering(self):
        if not self.checkData():
            print('Arguments are wrong. Can\'t clustering.')
            return 0
        # выбираем k разных центров кластеров случайным образом
        self.getRandomPointsForClusterCentroids()
        stop_it = False
        iterator = 0
        while not stop_it:
            print('iteration %s...'%str(iterator))
            iterator += 1
            # распределяем точки по кластерам
            stop_it = self.assignEachPointToCluster()
        # вернуть выходную последовательность – кластеризированное изображение
        return self.getResult()
    
    def imageDataToXY(self):
        # из последовательности значений пикселей берем координаты каждой точки
        self.point_coordinates = []
        for y in range(self.height):
            for x in range(self.width):
                self.point_coordinates.append([x, y])
    
    def getRandomPointsForClusterCentroids(self):
        # ищем рандомные точки для всех центров кластеров
        self.cluster_centroids = []
        for i in range(self.num_cluster):
            cluster_data = []
            random_index = random.randint(0,self.num_points-1)
            random_data = self.values[random_index]
            for it_data in range(len(random_data)):
                cluster_data.append(random_data[it_data])
            cluster_data.append(self.point_coordinates[random_index][0])
            cluster_data.append(self.point_coordinates[random_index][1])
            self.cluster_centroids.append(cluster_data)
    
    def assignEachPointToCluster(self):
        all_cluster_sums = {}
        for it_cluster in range(self.num_cluster):
            all_cluster_sums[it_cluster] = [0]*self.num_axis
        count_of_points_for_cluster = [0]*self.num_cluster
        result_cetroids_difference = [0]*self.num_cluster

        # итерация по всем точкам, чтобы распределить все точки по своим кластерам
        for it_point in range(self.num_points):
            # находим среднюю величину расстояния между точкой и центроидой
            dist = []
            for centroid_it in range(len(self.cluster_centroids)):
                # находим сумму квадратов расстояния между точками данных и центроидами
                sums = 0
                for it_pixel_value in range(self.num_pixel_value):
                    sums += (int(self.cluster_centroids[centroid_it][it_pixel_value]) - int(self.values[it_point][it_pixel_value]))**2
                # отдельно суммируем квадрат расстояния для координат
                sums += (int(self.cluster_centroids[centroid_it][self.num_pixel_value]) - int(self.point_coordinates[it_point][0]))**2
                sums += (int(self.cluster_centroids[centroid_it][self.num_pixel_value+1]) - int(self.point_coordinates[it_point][1]))**2
                # сохраняем среднее значение
                dist.append(sums/self.num_axis)
                
            # наименьшее расстояние от центроиды - найден нужный кластер
            min_dist = min(dist)
            for it_dist in range(len(dist)):
                if dist[it_dist] == min_dist:
                    point_index = it_dist
                    break
            
            # пометка каждой точки, к какому кластеру она принадлежит
            self.mark_for_each_point[it_point] = point_index
            # суммируем каждое значение пикселя для вычисления среднего значения
            for s in range(self.num_pixel_value):
                all_cluster_sums.get(point_index)[s] += self.values[it_point][s]
            count_of_points_for_cluster[point_index] += 1
            # суммируем координаты
            all_cluster_sums.get(point_index)[self.num_pixel_value] += self.point_coordinates[it_point][0]
            all_cluster_sums.get(point_index)[self.num_pixel_value+1] += self.point_coordinates[it_point][1]
                
        # теперь, когда каждая точка принадлежит своему кластеру, начинаем вычисление центроид заново
        difference_counter = 0
        for it_cluster in range(self.num_cluster):
            # учитывая все добавленные точки, пересчитываем средние центроиды            
            # чтобы не было деления на 0, если у какого то кластера нет точек
            if not count_of_points_for_cluster[it_cluster]:
                count_of_points_for_cluster[it_cluster] = 1
            # пересчитываем цетроиду
            not_changed = True
            for it_axis in range(self.num_axis):
                mean = all_cluster_sums.get(it_cluster)[it_axis]/count_of_points_for_cluster[it_cluster]
                try:
                    if not (mean/self.cluster_centroids[it_cluster][it_axis] < 1.05 and mean/self.cluster_centroids[it_cluster][it_axis] > 0.95):
                        difference_counter += 1
                        not_changed = False
                except ZeroDivisionError:
                    if mean != 0:
                        not_changed = False
                self.cluster_centroids[it_cluster][it_axis] = mean
            if not_changed:
                result_cetroids_difference[it_cluster] = 1
        print('Completed at {}%'.format(str((self.num_axis*self.num_cluster - difference_counter)*100/(self.num_axis*self.num_cluster))))
        if not 0 in result_cetroids_difference:
            return 1
        return 0
            
    def getResult(self):
        new_data = [0]*self.num_points
        for k in range(self.num_cluster):
            for it_point in range(len(self.mark_for_each_point)):
                if self.mark_for_each_point[it_point] == k:
                    if self.num_pixel_value == 2:
                        color_data = (int(self.cluster_centroids[k][0]), int(self.cluster_centroids[k][1]))
                    elif self.num_pixel_value == 3:
                        color_data = (int(self.cluster_centroids[k][0]), int(self.cluster_centroids[k][1]), int(self.cluster_centroids[k][2]))
                    elif self.num_pixel_value == 4:
                        color_data = (int(self.cluster_centroids[k][0]), int(self.cluster_centroids[k][1]), int(self.cluster_centroids[k][2]), int(self.cluster_centroids[k][3]))
                    new_data[it_point] = color_data
        return new_data


if __name__ == '__main__':
    pass

