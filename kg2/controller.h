#ifndef CONTROLLER_H
#define CONTROLLER_H

#include <Qt3DInput/QKeyboardDevice>
#include <QObject>
#include <QMatrix4x4>
#include <Qt3DInput/QActionInput>
#include <Qt3DInput/QAction>
#include <QOpenGLWidget>
#include <QKeyEvent> // для перехвата нажатий клавиш

namespace Qt3DCore {
class QTransform;
}

class OrbitTransformController : public QObject
{
    Q_OBJECT
    Q_PROPERTY(Qt3DCore::QTransform* target READ target WRITE setTarget NOTIFY targetChanged)
    Q_PROPERTY(float radius READ radius WRITE setRadius NOTIFY radiusChanged)
    Q_PROPERTY(float angle READ angle WRITE setAngle NOTIFY angleChanged)

public:
    void setStopFlag(bool);
    void turnX();
    void turnY();
    void turnZ();
    void Run();
    OrbitTransformController(QObject *parent = 0);
    void setX(float x);
    void setY(float y);
    void setZ(float z);

    void setTarget(Qt3DCore::QTransform *target);
    Qt3DCore::QTransform *target() const;

    void setRadius(float radius);
    float radius() const;

    void setAngle(float angle);
    float angle() const;

signals:
    void targetChanged();
    void radiusChanged();
    void angleChanged();

protected:
    void updateMatrix();

private:
    char prev;
    Qt3DCore::QTransform *m_target;
    QMatrix4x4 m_matrix;
    float m_radius;
    float m_angle;
    float x;
    float y;
    float z;
    float tempX;
    float tempY;
    float tempZ;
    bool stop;
};



#endif // CONTROLLER_H
