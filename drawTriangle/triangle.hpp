#ifndef TRIANGLE_HPP
#define TRIANGLE_HPP

#include <QGLWidget>
#include <QMouseEvent>
#include <QTimer>


struct TriangleObj : QGLWidget
{
  TriangleObj();

  void initializeGL() override;
  void resizeGL(int w, int h)override;
  void paintGL()override;
  // функция для выполнения заданного действия, принимает номер действия
  void WhatAction(int);
  // действия:
  void ShiftLeft();
  void ShiftRight();
  void ShiftUp();
  void ShiftDown();
  void IncreaseX();
  void ReduceX();
  // переход в исходное положение
  void StartPosition();
  // выполнение действий происходит с помощью нажатия мыши
  void mouseReleaseEvent(QMouseEvent*) override;

private:

  struct Point{
    float X;
    float Y;
  };

  struct Triangle{
      Point Point1;
      Point Point2;
      Point Point3;
  };
  Triangle MyTriangle;
  // оси координат
  float mScaleFactorX;
  float mScaleFactorY;

  //счетчик действий
  int ActionNum;
  // определение текущего действия
  const char* CurrentAct;

  QPoint mPosition;
  QTimer mpTimer;

};

#endif // TRIANGLE_HPP
