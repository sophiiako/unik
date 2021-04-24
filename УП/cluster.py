import random


class kMeansCluster():
    
    def __init__(self, values, width, height, num_cluster = 4, only_color = False):
        self.values = values
        self.width = width
        self.height = height
        self.num_points = len(values)
        self.mark_for_each_point = [0]*self.num_points
        self.num_cluster = num_cluster
        # num_pixel_value это количество значений пикселя, например в RGB - 3
        self.num_pixel_value = len(values[0])
        self.cluster_done = False
        self.only_color = only_color
        if not only_color:
            # axis это то что мы учитываем при кластеризации, в данном случае учитываются значения пикселя и координаты точки
            self.num_axis = len(values[0]) + 2
            self.imageDataToXY()
        else:
            self.num_axis = self.num_pixel_value
            
        
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
        # проверка того что ширина и высота даны правельные
        if len(self.values) != self.width*self.height:
            return 0
        
        return 1
    
    def startClustering(self):
        if not self.checkData():
            print('Arguments are wrong. Can\'t clustering.')
            return 0
        # выбираем k разных центров кластеров случайным образом
        self.getRandomPointsForClusterCentroids()
        #for t in range(self.iterations):
        stop_it = False
        t = 0
        while not stop_it:
            print('iteration %s...'%str(t))
            t += 1
            # распределяем точки по кластерам
            self.assignEachPointToCluster()
            # пересчитываем центроиды
            stop_it = self.recalculateClusterCentroids()
        self.cluster_done = True
        return 1
    
    def imageDataToXY(self):
        self.point_coordinates = []
        for y in range(self.height):
            for x in range(self.width):
                self.point_coordinates.append([x, y])
    
    def getRandomPointsForClusterCentroids(self):
        # ищем рандомные точки для всех кластеров
        # так как точки вычисляются случайно, при маленьком количестве точек может быть большая погрешность
        self.cluster_centroids = []
        for i in range(self.num_cluster):
            cluster_data = []
            random_index = random.randint(0,self.num_points-1)
            random_data = self.values[random_index]
            for j in range(len(random_data)):
                cluster_data.append(random_data[j])
            if not self.only_color:
                cluster_data.append(self.point_coordinates[random_index][0])
                cluster_data.append(self.point_coordinates[random_index][1])
            self.cluster_centroids.append(cluster_data)

    def recalculateClusterCentroids(self):
        # итерация по всем кластерам
        result_cetroids_difference = [0]*self.num_cluster
        for k in range(self.num_cluster):
            count_of_points_for_cluster = 0
            sums = [0]*self.num_axis
            for i in range(len(self.mark_for_each_point)):
                # если найденная точка принадлежит текущему кластеру
                if self.mark_for_each_point[i] == k:
                    # суммируем каждое значение пикселя для вычисления среднего значения
                    for s in range(self.num_pixel_value):
                        sums[s] += self.values[i][s]
                    count_of_points_for_cluster += 1
                    # суммируем координаты
                    if not self.only_color:
                        sums[self.num_pixel_value] += self.point_coordinates[i][0]
                        sums[self.num_pixel_value+1] += self.point_coordinates[i][1]
            # учитывая все добавленные точки, пересчитываем средние центроиды            
            # чтобы не было деления на 0, если у какого то кластера нет точек
            if not count_of_points_for_cluster:
                count_of_points_for_cluster = 1
            # пересчитываем цетроиду
            not_changed = True
            for p in range(self.num_axis):
                mean = sums[p]/count_of_points_for_cluster
                try:
                    if not (mean/self.cluster_centroids[k][p] < 1.04 and mean/self.cluster_centroids[k][p] > 0.96):
                        not_changed = False
                        print(mean/self.cluster_centroids[k][p])
                except ZeroDivisionError:
                    if mean != 0:
                        not_changed = False
                self.cluster_centroids[k][p] = sums[p]/count_of_points_for_cluster
            if not_changed:
                result_cetroids_difference[k] = 1
        if not 0 in result_cetroids_difference:
            return 1
        return 0
    
    def assignEachPointToCluster(self):
        # итерация по всем точкам, чтобы распределить все точки по своим кластерам
        for i in range(self.num_points):
            # находим среднюю величину расстояния между точкой и центроидой
            dist = []
            for j in range(len(self.cluster_centroids)):
                # находим сумму квадратов расстояния между точками данных и центроидами
                sums = 0
                for y in range(self.num_pixel_value):
                    sums += (int(self.cluster_centroids[j][y]) - int(self.values[i][y]))**2
                # отдельно суммируем квадрат расстояния для координат
                if not self.only_color:
                    sums += (int(self.cluster_centroids[j][self.num_pixel_value]) - int(self.point_coordinates[i][0]))**2
                    sums += (int(self.cluster_centroids[j][self.num_pixel_value+1]) - int(self.point_coordinates[i][1]))**2
                # сохраняем среднее значение
                dist.append(sums/self.num_axis)
                
            # наименьшее расстояние от центроиды - найден нужный кластер
            min_dist = min(dist)
            for j in range(len(dist)):
                if dist[j] == min_dist:
                    point_index = j
                    break
            # пометка каждой точки, к какому кластеру она принадлежит
            self.mark_for_each_point[i] = point_index
            
    def getResult(self):
        if not self.cluster_done:
            print('Can\'t get image before clustering.')
            return []
        new_data = [0]*self.num_points
        for k in range(self.num_cluster):
            for i in range(len(self.mark_for_each_point)):
                if self.mark_for_each_point[i] == k:
                    if self.num_pixel_value == 2:
                        color_data = (int(self.cluster_centroids[k][0]), int(self.cluster_centroids[k][1]))
                    elif self.num_pixel_value == 3:
                        color_data = (int(self.cluster_centroids[k][0]), int(self.cluster_centroids[k][1]), int(self.cluster_centroids[k][2]))
                    elif self.num_pixel_value == 4:
                        color_data = (int(self.cluster_centroids[k][0]), int(self.cluster_centroids[k][1]), int(self.cluster_centroids[k][2]), int(self.cluster_centroids[k][3]))
                    new_data[i] = color_data
        return new_data


if __name__ == '__main__':
    pass
