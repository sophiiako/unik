#include "my3dwindow.h"
#include <QKeyEvent>

My3DWindow::My3DWindow(QScreen *screen):Qt3DExtras::Qt3DWindow(screen)
{
    controller1 = nullptr;
    controller2 = nullptr;
    controller3 = nullptr;
}

void My3DWindow::setControllers(OrbitTransformController *c1, OrbitTransformController *c2, OrbitTransformController *c3)
{
    // указатели на объекты для управления вращением
     controller1 = c1;
     controller2 = c2;
     controller3 = c3;
}


void My3DWindow::keyPressEvent(QKeyEvent *ev)
{
    if(controller1 != nullptr &&  controller2 != nullptr &&controller3 != nullptr){
       switch (ev->key()) {
       case Qt::Key_S:
           // остановить вращение
           stopControllers();
           break;
       case Qt::Key_W:
           // вращать по всем осям
           startControllers();
           break;
       case Qt::Key_X:
           // вращение по X
           TurnX();
           break;
       case Qt::Key_Y:
           // вращение по Y
           TurnY();
           break;
       case Qt::Key_Z:
           // вращение по Z
           TurnZ();
           break;
       default:
           break;

       }
    }
}

void My3DWindow::startControllers()
{
    controller1->setStopFlag(false);
    controller2->setStopFlag(false);
    controller3->setStopFlag(false);
}

void My3DWindow::stopControllers()
{
    controller1->setStopFlag(true);
    controller2->setStopFlag(true);
    controller3->setStopFlag(true);
}

void My3DWindow::TurnX()
{
    controller1->turnX();
    controller2->turnX();
    controller3->turnX();
}

void My3DWindow::TurnY()
{
    controller1->turnY();
    controller2->turnY();
    controller3->turnY();
}


void My3DWindow::TurnZ()
{
    controller1->turnZ();
    controller2->turnZ();
    controller3->turnZ();
}


