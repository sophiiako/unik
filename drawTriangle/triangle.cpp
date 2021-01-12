#include "triangle.hpp"
#include <iostream>

TriangleObj::TriangleObj()
{
    CurrentAct = "Исходное положение";
    mScaleFactorX = 200;
    mScaleFactorY = 200;
    //начальные точки для построения треугольника
    Point Point1 = {50, 50};
    Point Point2 = {100, 100};
    Point Point3 = {0, 100};
    // наш треугольник
    MyTriangle = {Point1, Point2, Point3};

    ActionNum = 0;
    connect(&mpTimer, SIGNAL(timeout()), this, SLOT(repaint()));
    mpTimer.start(33);
}

void TriangleObj::initializeGL()
{
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();
  glOrtho(0, 800, 600, 0, 0, 1);
}

void TriangleObj::resizeGL(int w, int h)
{
  glViewport(0, 0, w, h);
}

void TriangleObj::paintGL()
{
  qglClearColor(Qt::black);
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  qglColor(Qt::white);
  renderText(20, 20, 0, QString::fromUtf8("Действие: %1").arg(CurrentAct), QFont());
  qglColor(Qt::red);
  glBegin(GL_LINE_LOOP);
      glVertex2i(mScaleFactorX + MyTriangle.Point1.X, mScaleFactorY + MyTriangle.Point1.Y);
      glVertex2i(mScaleFactorX + MyTriangle.Point2.X, mScaleFactorY + MyTriangle.Point2.Y);
      glVertex2i(mScaleFactorX + MyTriangle.Point3.X, mScaleFactorY + MyTriangle.Point3.Y);
  glEnd();
}

void TriangleObj::mouseReleaseEvent(QMouseEvent *event)
{
    // если была нажата левая кнопка мыши
    if(event->button() == Qt::LeftButton)
    {
        //выполнение следующего действия или возврат в исходное положение
        if (ActionNum%2 == 0) WhatAction(ActionNum);
        else StartPosition();

        if (ActionNum < 11) ++ActionNum;
        else ActionNum = 0;
    }
}

void TriangleObj::StartPosition(){
    CurrentAct = "Исходное положение";
    MyTriangle.Point1.X = 50;
    MyTriangle.Point1.Y = 50;
    MyTriangle.Point2.X = 100;
    MyTriangle.Point2.Y = 100;
    MyTriangle.Point3.X = 0;
    MyTriangle.Point3.Y = 100;
}

void TriangleObj::WhatAction(int number){
    // при нечетном действии возврат в исходное положение
    switch (number){
    case 0:
        ShiftLeft();
        break;
    case 2:
        ShiftRight();
        break;
    case 4:
        ShiftUp();
        break;
    case 6:
        ShiftDown();
        break;
    case 8:
        IncreaseX();
        break;
    case 10:
        ReduceX();
        break;
    }
}

void TriangleObj::ShiftRight(){
    // перенос вправо на 100(длина труегольника)
    CurrentAct = "Перенос вправо";
    MyTriangle.Point1.X +=100;
    MyTriangle.Point2.X +=100;
    MyTriangle.Point3.X +=100;
}

void TriangleObj::ShiftLeft(){
    // перенос влево на 100(длина труегольника)
    CurrentAct = "Перенос влево";
    MyTriangle.Point1.X +=-100;
    MyTriangle.Point2.X +=-100;
    MyTriangle.Point3.X +=-100;
}

void TriangleObj::ShiftDown(){
    // перенос вниз на 100(длина труегольника)
    CurrentAct = "Перенос вниз";
    MyTriangle.Point1.Y += 100;
    MyTriangle.Point2.Y += 100;
    MyTriangle.Point3.Y += 100;
}

void TriangleObj::ShiftUp(){
    // перенос вверх на 100(длина труегольника)
    CurrentAct = "Перенос вверх";
    MyTriangle.Point1.Y +=-100;
    MyTriangle.Point2.Y +=-100;
    MyTriangle.Point3.Y +=-100;
}

void TriangleObj::IncreaseX(){
    CurrentAct = "Увеличение по горизонтали в 2 раза";
    MyTriangle.Point1.X *=2;
    MyTriangle.Point2.X *=2;
    MyTriangle.Point3.X *=2;
}

void TriangleObj::ReduceX(){
    CurrentAct = "Уменьшение по горизонтали в 2 раза";
    MyTriangle.Point1.X /=2;
    MyTriangle.Point2.X /=2;
    MyTriangle.Point3.X /=2;
}












