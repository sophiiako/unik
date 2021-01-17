#ifndef MY3DWINDOW_H
#define MY3DWINDOW_H

#include <Qt3DExtras/Qt3DWindow>
#include "controller.h"

class My3DWindow: public Qt3DExtras::Qt3DWindow
{
       Q_OBJECT
public:
       My3DWindow(QScreen *screen = nullptr);
       void setControllers(OrbitTransformController * c1, OrbitTransformController * c2, OrbitTransformController * c3);

protected:
       void keyPressEvent(QKeyEvent *ev);

private:
    OrbitTransformController * controller1;
    OrbitTransformController * controller2;
    OrbitTransformController * controller3;
    void startControllers();
    void stopControllers();
    void TurnX();
    void TurnY();
    void TurnZ();

};
#endif // MY3DWINDOW_H
