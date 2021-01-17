#include "controller.h"
#include <Qt3DCore/qtransform.h>


void OrbitTransformController::setStopFlag(bool newFlag)
{
    stop = newFlag;
    prev = 'r';
    x = tempX;
    y = tempY;
    z = tempZ;
}

void OrbitTransformController::turnX()
{
    if(stop) stop = false;
    if (prev != 'x'){
        y = 0.0f;
        z = 0.0f;
        x = tempX;
        prev = 'x';
    }
}

void OrbitTransformController::turnY()
{
    if(stop) stop = false;
    if (prev != 'y'){
        x = 0.0f;
        z = 0.0f;
        y = tempY;
        prev = 'y';
    }
}

void OrbitTransformController::turnZ()
{
    if(stop) stop = false;
    if (prev != 'z'){
        y = 0.0f;
        x = 0.0f;
        z = tempZ;
        prev = 'z';
    }
}

OrbitTransformController::OrbitTransformController(QObject *parent)
    : QObject(parent)
    , m_target(nullptr)
    , m_matrix()
    , m_radius(1.0f)
    , m_angle(0.0f)
    , x(0.0f)
    , y(0.0f)
    , z(0.0f)
    , stop(false)
{
    tempX = x;
    tempY = y;
    tempZ = z;
    prev = 'r';
}

void OrbitTransformController::setX(float m_x)
{
    x = m_x;
    tempX = x;
}

void OrbitTransformController::setY(float m_y)
{
    y = m_y;
    tempY = y;
}

void OrbitTransformController::setZ(float m_z)
{
    z = m_z;
    tempZ = z;
}

void OrbitTransformController::setTarget(Qt3DCore::QTransform *target)
{
    if (m_target != target) {
        m_target = target;
        emit targetChanged();
    }
}

Qt3DCore::QTransform *OrbitTransformController::target() const
{
    return m_target;
}

void OrbitTransformController::setRadius(float radius)
{
    if (!qFuzzyCompare(radius, m_radius)) {
        m_radius = radius;
        updateMatrix();
        emit radiusChanged();
    }
}

float OrbitTransformController::radius() const
{
    return m_radius;
}

void OrbitTransformController::setAngle(float angle)
{
    if (!qFuzzyCompare(angle, m_angle)) {
        m_angle = angle;
        updateMatrix();
        emit angleChanged();
    }
}

float OrbitTransformController::angle() const
{
    return m_angle;
}

void OrbitTransformController::updateMatrix()
{
    if (!stop){
    m_matrix.setToIdentity();
    m_matrix.rotate(m_angle, QVector3D(x, y, z));
    m_matrix.translate(m_radius, y, z);
    m_target->setMatrix(m_matrix);
    }

}



