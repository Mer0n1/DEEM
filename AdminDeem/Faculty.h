#ifndef FACULTY_H
#define FACULTY_H

#include "Course.h"

#include <QList>


struct Faculty {
    QList<Course*> courses;
    QString name;
};

#endif // FACULTY_H
