from PIL import Image
from tkinter import Tk, Canvas, mainloop
from cluster import kMeansCluster

def showImageByPixels(pixels, width, height):
    master = Tk()
    canvas = Canvas(master, width=width*2, height=height*2)
    canvas.pack()
    drawingPixels(pixels, canvas, width, height)    
    mainloop()
                    
def drawingPixels(data, canvas, width, height):
    for y in range(height):
        for x in range(width):
            color = "#%02x%02x%02x" % data[x + y*width]
            x1, y1 = x * 2, y * 2
            x2, y2 = (x+1) * 2, (y+1) * 2
            canvas.create_rectangle(x1, y1, x2, y2, fill=color, outline=color)
            
       
       
       
def main():                
    im = Image.open('4.jpg') 
    # список содержащий значения пикселей    
    pixels = list(im.getdata())
    image_width, image_height = im.size

    #showImageByPixels(pixels, image_width, image_height)

    cluster = kMeansCluster(pixels, image_width, image_height,20, 5)
    cluster.startClustering()
    result = cluster.getResult()
    showImageByPixels(result, image_width, image_height)


                
if __name__ == '__main__':
    main()
