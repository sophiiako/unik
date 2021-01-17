#include <Qt3DExtras/Qt3DWindow>
#include <QGuiApplication>
#include <Qt3DCore/QEntity>
#include <Qt3DRender/QCamera>
#include <Qt3DRender/QPointLight>
#include <Qt3DExtras/QTorusMesh>
#include <Qt3DExtras/QCuboidMesh>
#include <Qt3DExtras/QForwardRenderer>
#include <Qt3DExtras/QConeMesh>
#include <Qt3DExtras/QPhongMaterial>
#include <Qt3DExtras/QOrbitCameraController>
#include <Qt3DCore/QTransform>
#include <QPropertyAnimation>
#include <QTimer>
#include "controller.h"
#include "my3dwindow.h"

//created with API QT3D

Qt3DCore::QEntity *createScene(My3DWindow *view);
OrbitTransformController * RotationStart(Qt3DCore::QTransform *object, float, float , float);


int main(int argc, char *argv[])
{
    QGuiApplication app(argc, argv);

    //основное окно
    My3DWindow *view = new My3DWindow();
    view->defaultFrameGraph()->setClearColor(QColor(QRgb(0x4d4d4f)));

    // корневая сущность(наша сцена)
    Qt3DCore::QEntity *rootEntity = createScene(view);

    //настройка камеры
    //возращаем указатель на камеру
    Qt3DRender::QCamera *camera = view->camera();
    //камера настраивается с помощью линзы
    //установка перспективной проекции
    camera->lens()->setPerspectiveProjection(60.0f, (float)view->width()/view->height(), 0.1f, 1000.f);
    // настройка позиции камеры, отодвинем камеру назад
    camera->setPosition(QVector3D(0.0f, 0.0f, 40.0f));
    camera->setViewCenter(QVector3D(0.0f, 0.0f, 0.0f));

    //создание контроллера камеры, чтобы двигать сцену
    Qt3DExtras::QOrbitCameraController *cameraController = new Qt3DExtras::QOrbitCameraController(rootEntity);
    cameraController->setCamera(camera);
    // скорость вращения вокруг объекта
    cameraController->setLookSpeed(1000.0f);
    //скорость перемещения камеры
    cameraController->setLinearSpeed(50.0f);

    view->setRootEntity(rootEntity);

    //отобразить содержимое
    view->show();

    if (app.arguments().contains(("--bench")))
            QTimer::singleShot(25 * 1000, &app, &QCoreApplication::quit);

    return app.exec();
}

Qt3DCore::QEntity *createScene(My3DWindow *view){

    // ROOT
    Qt3DCore::QEntity *resultEntity = new Qt3DCore::QEntity;

    //TORUS
    Qt3DCore::QEntity *torusEntity = new Qt3DCore::QEntity(resultEntity);
    Qt3DExtras::QTorusMesh *torus = new Qt3DExtras::QTorusMesh(torusEntity);
    torus->setRadius(6.0f);
    torus->setMinorRadius((1.5f));
    torus->setSlices(16);
    torus->setRings(32);
    Qt3DExtras::QPhongMaterial *torusMaterial = new Qt3DExtras::QPhongMaterial(torus);
    // Цвет диффузного отражения - красный
    torusMaterial->setDiffuse(Qt::red);
    Qt3DCore::QTransform *torusTransform = new Qt3DCore::QTransform(torus);

    OrbitTransformController *controller1 = RotationStart(torusTransform, -1.0f, -15.0f , -12.0f);

    torusEntity->addComponent(torus);
    torusEntity->addComponent(torusMaterial);
    torusEntity->addComponent(torusTransform);

    //CUBE
    Qt3DCore::QEntity *cubEntity = new Qt3DCore::QEntity(resultEntity);
    Qt3DExtras::QCuboidMesh *cub = new Qt3DExtras::QCuboidMesh(cubEntity);
    Qt3DCore::QTransform * cubTransform = new Qt3DCore::QTransform(cubEntity);

    Qt3DExtras::QPhongMaterial *cubMaterial = new Qt3DExtras::QPhongMaterial(cubEntity);
    cub->setXExtent(8.0f);
    cub->setYExtent(8.0f);
    cub->setZExtent(8.0f);
    OrbitTransformController *controller2 = RotationStart(cubTransform, -14.0f, 10.0f, 8.0f);
    // Цвет отражения фонового излучения - красный
    cubMaterial->setAmbient(Qt::red);
    cubEntity->addComponent(cub);
    cubEntity->addComponent(cubTransform);
    cubEntity->addComponent(cubMaterial);

    //CONE
    Qt3DCore::QEntity *coneEntity = new Qt3DCore::QEntity(resultEntity);
    Qt3DExtras::QConeMesh *cone = new Qt3DExtras::QConeMesh(coneEntity);
    Qt3DCore::QTransform *coneTransform = new Qt3DCore::QTransform(coneEntity);
    Qt3DExtras::QPhongMaterial *coneMaterial = new Qt3DExtras::QPhongMaterial(coneEntity);
    OrbitTransformController *controller3 = RotationStart(coneTransform, 15.0f, -5.0f, -3.0f);
    // Цвет зеркального отражения - красный
    coneMaterial->setSpecular(Qt::red);
    cone->setBottomRadius(5.0f);
    cone->setLength(10.0f);
    coneEntity->addComponent(cone);
    coneEntity->addComponent(coneTransform);
    coneEntity->addComponent(coneMaterial);

    view->setControllers(controller1, controller2, controller3);

    //LIGHT 1 зеленый
    Qt3DCore::QEntity *lightEntity1 = new Qt3DCore::QEntity(resultEntity);
    Qt3DCore::QTransform *lightTransform1 = new Qt3DCore::QTransform(lightEntity1);
    Qt3DRender::QPointLight *pointLight1 = new Qt3DRender::QPointLight(lightEntity1);
    lightTransform1->setTranslation(QVector3D(10.0f, -30.0f, 30.0f));
    pointLight1->setColor(Qt::green);
    lightEntity1->addComponent(pointLight1);
    lightEntity1->addComponent(lightTransform1);

    //LIGHT 2 розовый
    Qt3DCore::QEntity *lightEntity2 = new Qt3DCore::QEntity(resultEntity);
    Qt3DCore::QTransform *lightTransform2 = new Qt3DCore::QTransform(lightEntity2);
    Qt3DRender::QPointLight *pointLight2 = new Qt3DRender::QPointLight(lightEntity2);
    lightTransform2->setTranslation(QVector3D(-30.0f, 20.0f, 20.0f));
    pointLight2->setColor(Qt::magenta);
    lightEntity2->addComponent(pointLight2);
    lightEntity2->addComponent(lightTransform2);

    //LIGHT3 желтый
    Qt3DCore::QEntity *lightEntity3 = new Qt3DCore::QEntity(resultEntity);
    Qt3DCore::QTransform *lightTransform3 = new Qt3DCore::QTransform(lightEntity3);
    Qt3DRender::QPointLight *pointLight3 = new Qt3DRender::QPointLight(lightEntity3);
    lightTransform3->setTranslation(QVector3D(-40.0f, -40.0f, -30.0f));
    pointLight3->setColor(Qt::yellow);
    lightEntity3->addComponent(pointLight3);
    lightEntity3->addComponent(lightTransform3);

    return resultEntity;
}



OrbitTransformController * RotationStart(Qt3DCore::QTransform *object, float x, float y, float z){
    // создание контроллера вращения
    OrbitTransformController *controller = new OrbitTransformController(object);
    controller->setTarget(object);
    controller->setRadius(x);
    controller->setX(x);
    controller->setY(y);
    controller->setZ(z);
    // создание анимации для вращения
    QPropertyAnimation *RotateAnimation = new QPropertyAnimation(object);
    RotateAnimation->setTargetObject(controller);
    RotateAnimation->setPropertyName("angle");
    RotateAnimation->setStartValue(QVariant::fromValue(1));
    RotateAnimation->setEndValue(QVariant::fromValue(360));
    RotateAnimation->setDuration(10000);
    RotateAnimation->setLoopCount(-1);
    RotateAnimation->start();
    return controller;
}













