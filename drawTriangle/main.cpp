#include "mainwindow.h"
#include <QApplication>
#include "triangle.hpp"

int main(int argc, char *argv[])
{
  QApplication a(argc, argv);

  TriangleObj win;
  win.show();
  //win.showFullScreen();

  //MainWindow w;
  //w.show();

  return a.exec();
}
