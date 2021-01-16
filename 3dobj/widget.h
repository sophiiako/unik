#ifndef WIDGET_H
#define WIDGET_H

#include <QOpenGLWidget>
#include <QSurfaceFormat>
#include <QTimer> // таймер
#include <QKeyEvent> // для перехвата нажатий клавиш
#include "objloader.h" // загрузчик моделей в формате obj


class MyWidget : public QOpenGLWidget
{
    Q_OBJECT
    QTimer *paintTimer; // таймер
    GLuint model;
    GLfloat angle = 0; // угол вращения
    GLuint drawCube(); // нарисовать куб
    virtual void keyPressEvent(QKeyEvent *event);


public:
    MyWidget(QWidget *parent = nullptr);
protected:
   void initializeGL();
   void resizeGL(int nWidth, int nHeight);
   void paintGL();
};
#endif // WIDGET_H
