#include "widget.h"
#include <GL/glu.h>

MyWidget::MyWidget(QWidget *parent) : QOpenGLWidget(parent)
{
    resize(800,600); // задаем размеры окна
    paintTimer = new QTimer(this); // создаю таймер
    connect(paintTimer, SIGNAL(timeout()), this, SLOT(repaint()));
    paintTimer->start();
}

GLuint MyWidget::drawCube()
{
    GLuint num = glGenLists(1);
    glNewList(num,GL_COMPILE);

    glBegin (GL_QUADS);
        glNormal3f(0.0, 0.0, 1.0);
        glVertex3f (1.0, 1.0, 1.0);
        glVertex3f (-1.0, 1.0, 1.0);
        glVertex3f (-1.0, -1.0, 1.0);
        glVertex3f (1.0, -1.0, 1.0);
    glEnd();

    glBegin (GL_QUADS);
        glNormal3f(0.0, 0.0, -1.0);
        glVertex3f (1.0, 1.0, -1.0);
        glVertex3f (1.0, -1.0, -1.0);
        glVertex3f (-1.0, -1.0, -1.0);
        glVertex3f (-1.0, 1.0, -1.0);
    glEnd();

    glBegin (GL_QUADS);
        glNormal3f(-1.0, 0.0, 0.0);
        glVertex3f (-1.0, 1.0, 1.0);
        glVertex3f (-1.0, 1.0, -1.0);
        glVertex3f (-1.0, -1.0, -1.0);
        glVertex3f (-1.0, -1.0, 1.0);
    glEnd();

    glBegin (GL_QUADS);
        glNormal3f(1.0, 0.0, 0.0);
        glVertex3f (1.0, 1.0, 1.0);
        glVertex3f (1.0, -1.0, 1.0);
        glVertex3f (1.0, -1.0, -1.0);
        glVertex3f (1.0, 1.0, -1.0);
    glEnd();

    glBegin (GL_QUADS);
        glNormal3f(0.0, 1.0, 0.0);
        glVertex3f (-1.0, 1.0, -1.0);
        glVertex3f (-1.0, 1.0, 1.0);
        glVertex3f (1.0, 1.0, 1.0);
        glVertex3f (1.0, 1.0, -1.0);
    glEnd();

    glBegin(GL_QUADS);
        glNormal3f(0.0, -1.0, 0.0);
        glVertex3f (-1.0, -1.0, -1.0);
        glVertex3f (1.0, -1.0, -1.0);
        glVertex3f (1.0, -1.0, 1.0);
        glVertex3f (-1.0, -1.0, 1.0);
    glEnd();

    glEndList();
    return num;
}

void MyWidget::keyPressEvent(QKeyEvent *event) {
    if (event->key()==Qt::Key_S) {
        if (paintTimer->isActive()) paintTimer->stop();
        else paintTimer->start();
    }
}



void MyWidget::initializeGL()
{
    glEnable(GL_MULTISAMPLE); // сглаживание MSAA вкл
    glClearColor(0.2, 0.2, 0.2, 0.2); // заливаем цветом
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // Улучшение в вычислении перспективы
    model = drawCube();
}

void MyWidget::resizeGL(int nWidth, int nHeight)
{
    // установка точки обзора
    glViewport(0, 0, nWidth, nHeight);
    qreal aspectratio = qreal(nWidth) / qreal(nHeight);

    // инициализация матрицы проекций
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity(); // сброс матрицы проекции
    gluPerspective( 90.0, aspectratio, 0.1, 100.0 );

    // инициализация матрицы вида модели
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity(); // сброс матрицы вида модели
}

void MyWidget::paintGL() // рисование
{ 
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // очистка экрана и буфера глубины
    glLoadIdentity();           // сбросить текущую матрицу
    glTranslatef(0,0,-2.5);
    glRotatef(angle,0.0f,1.0f,0.0f);
    glColor4f(0.7, 0.7, 0.3, 0.0);
    glCallList(model);
    angle += 0.1f;
}

