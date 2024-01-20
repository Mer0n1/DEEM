#include <QDate>
#include <QString>

#ifndef EVENT_H
#define EVENT_H



struct Event {
    long id;
    QString type;
    QString name;
    QString description;
    QDateTime publication_date;
    QDateTime start_date;
    QString faculty;
    int course;
    long idGroup;

};
#endif // EVENT_H
